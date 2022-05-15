package com.romankaranchuk.musicplayer.utils.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.content.res.use
import com.romankaranchuk.musicplayer.R
import com.romankaranchuk.musicplayer.databinding.ViewTimerCircularSeekbarBinding
import com.romankaranchuk.musicplayer.utils.spToPx
import timber.log.Timber

class TimerCircularSeekBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    companion object {
        private const val TEXT_SIZE_SP = 12f
        private const val DEFAULT_WIDTH_PX = 200
        private const val PROGRESS_MIN = 0f
        private const val PROGRESS_MAX = 60f
        private const val PROGRESS_START_RANGE_LEFT = PROGRESS_MIN
        private const val PROGRESS_START_RANGE_RIGHT = 14f
        private const val PROGRESS_END_RANGE_LEFT = 46f
        private const val PROGRESS_END_RANGE_RIGHT = PROGRESS_MAX
        private const val LAP_COUNT_MAX = 2
        private const val LAP_COUNT_MIN = 0
        private const val SEEKBAR_1_SCALE_OFFSET = 0.2f
        private const val SEEKBAR_2_SCALE_OFFSET = 0.3f
        private const val CLOCK_LABEL_COUNT = 11
        private const val CLOCK_LABEL_MULTIPLIER = 5
    }

    private val clockLabelRange = 0..CLOCK_LABEL_COUNT
    private val labels: List<String> by lazy {
        clockLabelRange.map { "${it * CLOCK_LABEL_MULTIPLIER}" }
    }

    // text
    private val textPaint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            textSize = TEXT_SIZE_SP.spToPx()
            color = Color.BLACK
        }
    }

    private lateinit var binding: ViewTimerCircularSeekbarBinding
    private var showMinutesOnly = true

    private var listener: CircularSeekBar.OnCircularSeekBarChangeListener? = null

    private var lapCount = LAP_COUNT_MIN
    private var isEnd = false
    private var isStart = false
    private val startRange = PROGRESS_START_RANGE_LEFT..PROGRESS_START_RANGE_RIGHT
    private val endRange = PROGRESS_END_RANGE_LEFT..PROGRESS_END_RANGE_RIGHT

    private val seekbarChangeListener = object : CircularSeekBar.OnCirc