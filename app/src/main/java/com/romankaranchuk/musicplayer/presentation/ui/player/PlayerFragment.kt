
package com.romankaranchuk.musicplayer.presentation.ui.player

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.*
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.*
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.annotation.ColorInt
import androidx.core.graphics.ColorUtils
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.palette.graphics.Palette
import androidx.viewpager2.widget.ViewPager2
import com.romankaranchuk.musicplayer.data.Song
import com.romankaranchuk.musicplayer.databinding.FragmentPlayerBinding
import com.romankaranchuk.musicplayer.di.util.Injectable
import com.romankaranchuk.musicplayer.presentation.service.PlayerService
import com.romankaranchuk.musicplayer.presentation.service.PlayerService.PlayerBinder
import com.romankaranchuk.musicplayer.presentation.ui.player.PlayerViewModel.ViewState.ServiceConnectedState
import com.romankaranchuk.musicplayer.presentation.ui.player.page.PlayerPagerAdapter
import com.romankaranchuk.musicplayer.utils.widgets.SecondPageSideShownTransformer
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import javax.inject.Inject


class PlayerFragment : Fragment(), Injectable {

    // assigning non valid null value to oldSongPos to prevent false-positive viewpager swipe detection,
    private var oldSongPos: Int? = null
    private var isFastForwardOrRewindButtons = false
    private var isLeftToRightSwipe = false
    private var isRightToLeftSwipe = false
    private var prevState: Int = -1
    private var isUserScrollChange: Boolean = false

    private val intentPlayerService: Intent by lazy { Intent(requireContext(), PlayerService::class.java) }
    private var playerService: PlayerService? = null
    private var playerPagerAdapter: PlayerPagerAdapter? = null

    private val argbEvaluator = ArgbEvaluator()
    private val centerScaledPageTransformer by lazy { SecondPageSideShownTransformer(requireContext(), isCenterScaled = true) }
    private val pageTransformer by lazy { SecondPageSideShownTransformer(requireContext(), isCenterScaled = false) }


    @Inject lateinit var mViewModelFactory: ViewModelProvider.Factory
    private val viewModel: PlayerViewModel by viewModels { mViewModelFactory }

    private var _binding: FragmentPlayerBinding? = null
    private val binding get() = _binding!!

    private val mPageChangeListener: ViewPager2.OnPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            oldSongPos?.let { oldPos ->
                if (oldPos < position) {
                    isLeftToRightSwipe = true
                } else if (oldPos > position) {
                    isRightToLeftSwipe = true
                }
            }

            oldSongPos = position

            Timber.d("onPageSelected, position = $position, isLTRSwipe=$isLeftToRightSwipe, isRTLSwipe=$isRightToLeftSwipe")
        }

        override fun onPageScrolled(
            position: Int, positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            Timber.d(
                "onPageScrolled, position = " + position +
                        " positionOffset = " + positionOffset +
                        " positionOffsetPixels = " + positionOffsetPixels
            )
        }

        override fun onPageScrollStateChanged(state: Int) {
            if (prevState == ViewPager2.SCROLL_STATE_DRAGGING && state == ViewPager2.SCROLL_STATE_SETTLING) {
                isUserScrollChange = true
            } else if (prevState == ViewPager2.SCROLL_STATE_SETTLING && state == ViewPager2.SCROLL_STATE_IDLE) {
                isUserScrollChange = false
            }
            prevState = state

            Timber.d("onPageScrollStateChanged, state = $state, isUserScrollChange=$isUserScrollChange")

//
//            if ((state == ViewPager.SCROLL_STATE_IDLE || state == ViewPager.SCROLL_STATE_SETTLING) && (isLeftToRightSwipe || isRightToLeftSwipe)) {
//                onFastForwardRewind(isFastForward = isLeftToRightSwipe)
//                isLeftToRightSwipe = false
//                isRightToLeftSwipe = false
//            }

//            if (!isUserScrollChange) {
//                return
//            }

            if (state == ViewPager2.SCROLL_STATE_SETTLING || state == ViewPager2.SCROLL_STATE_IDLE) {
//                swap = true
                if (!isFastForwardOrRewindButtons) {
                    if (isLeftToRightSwipe) {
    //                        binding.bottomPart.toNextSongButton.callOnClick()
                        viewModel.onFastForwardRewindClick(isFastForward = true, isClick = false)
                        isLeftToRightSwipe = false
                    } else if (isRightToLeftSwipe) {
                        viewModel.onFastForwardRewindClick(isFastForward = false, isClick = false)
    //                        binding.bottomPart.toPreviousSongButton.callOnClick()
                        isRightToLeftSwipe = false
    //                    }
                    }
//                swap = false
                }
                isFastForwardOrRewindButtons = false
            }
        }

    }
    private val onPlayBtnClickListener = View.OnClickListener { viewModel.onPlayPauseBtnClick() }
    private val mSeekBarChangeListener: OnSeekBarChangeListener = object : OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            viewModel.onSeekbarProgressChanged(progress)
        }
        override fun onStartTrackingTouch(seekBar: SeekBar) {
            viewModel.onSeekbarStartTrackingTouch()
        }

        override fun onStopTrackingTouch(seekBar: SeekBar) {
            viewModel.onSeekbarStopTrackingTouch()
        }
    }
    private val mFastForwardClickListener = View.OnClickListener { viewModel.onFastForwardRewindClick(
        isFastForward = true,
        isClick = true
    ) }
    private val mFastBackwardClickListener = View.OnClickListener { viewModel.onFastForwardRewindClick(
        isFastForward = false,
        isClick = true
    ) }
    private val mReplayClickListener = View.OnClickListener { viewModel.onRepeatBtnClick(it.isSelected) }
    private val timerClickListener = View.OnClickListener { viewModel.onSleepTimerClick() }
    private val mShuffleClickListener = View.OnClickListener { viewModel.onShuffleBtnClick(it.isSelected) }
    private val songNameClickListener = View.OnClickListener { viewModel.onSongNameTitleClick() }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        lifecycle.addObserver(viewModel)
        Timber.d("onAttach")
    }

    override fun onDetach() {
        super.onDetach()

        lifecycle.removeObserver(viewModel)
        Timber.d("onDetach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate")

//        mViewModel = ViewModelProvider.NewInstanceFactory.getInstance().create<PlayerViewModel>(PlayerViewModel::class.java)

//        bindService()
        registerBroadcastReceivers()
    }

    override fun onDestroy() {
        super.onDestroy()

        unbindService()
        unregisterBroadcastReceivers()
        binding.pagerFullscreenPlayer?.unregisterOnPageChangeCallback(mPageChangeListener)

        oldSongPos = null
        _binding = null
        
        Timber.d("onDestroy")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Timber.d("onActivityCreated")
    }

    override fun onStart() {
        super.onStart()
//        if (viewModel.isServiceBound) {
//            setSongFullTimeSeekBarProgress()
//        }
        Timber.d("onStart")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Timber.d("onCreateView")
        _binding = FragmentPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupUI()
        setupListeners()

        setupAdapter()

//        currentSong = viewModel.curSelectedSong
        //        oldPosition = mSongs.indexOf(currentSong);
//        pagerFullscreenPlayer.setCurrentItem(oldPosition,false);

        restoreViewState(savedInstanceState)
        bindViewModels()
    }

    private val forwardButtonFromServiceToFragmentBR: BroadcastReceiver =
        object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
//                onFastForwardRewind(isFastForward = true)
            }
        }
    private val backwardButtonFromServiceToFragmentBR: BroadcastReceiver =
        object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
//                onFastForwardRewind(isFastForward = false)
            }
        }
    private val playButtonFromServiceToFragmentBR: BroadcastReceiver =
        object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                binding.bottomPart.playPauseSongButton.isSelected = !binding.bottomPart.playPauseSongButton.isSelected
            }
        }

    private fun setupAdapter() {
        playerPagerAdapter = PlayerPagerAdapter(this)
        binding.pagerFullscreenPlayer?.let {
            with(it) {
                adapter = playerPagerAdapter
                registerOnPageChangeCallback(mPageChangeListener)
                setPageTransformer(centerScaledPageTransformer)
                clipToPadding = false
                clipChildren = false
                offscreenPageLimit = 2
            }
        }
    }

    private fun registerBroadcastReceivers() {
        with(requireContext()) {
            registerReceiver(forwardButtonFromServiceToFragmentBR, IntentFilter(TAG_FORWARD_BUT_PS_TO_F_BR))
            registerReceiver(backwardButtonFromServiceToFragmentBR, IntentFilter(TAG_BACKWARD_BUT_PS_TO_F_BR))
            registerReceiver(playButtonFromServiceToFragmentBR, IntentFilter(TAG_PLAY_BUT_PS_TO_F_BR))
        }
    }

    private fun unregisterBroadcastReceivers() {
        with(requireContext()) {
            unregisterReceiver(playButtonFromServiceToFragmentBR)
            unregisterReceiver(forwardButtonFromServiceToFragmentBR)
            unregisterReceiver(backwardButtonFromServiceToFragmentBR)
        }
    }

    private fun bindService() {
        with(requireContext()) {
            startService(intentPlayerService)
            this.bindService(intentPlayerService, serviceConnection, 0)