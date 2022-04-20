package com.romankaranchuk.musicplayer.utils

import android.view.View
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.romankaranchuk.musicplayer.R

fun com.google.android.material.bottomsheet.BottomSheetDialog.setExpandToFullHeightBehavior(
    isCancellableByDragging: Boolean = true
) {
    val bottomSheet = this.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
    val behavi