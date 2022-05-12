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
        private const val 