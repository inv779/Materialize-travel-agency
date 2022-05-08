package com.romankaranchuk.musicplayer.utils.widgets

import android.content.Context
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.romankaranchuk.musicplayer.R

class SecondPageSideShownTransformer(
    private val context: Context,
    private val offsetPx: Int = context.resources.getDimensionPixelOffset(R.dimen.offset),
    private val pageMarginPx: Int = context.resources.getDimensionPixelOffset(R.dimen.page_margin),
    private val isCenterScaled: Boolean = false
) : ViewPager2.PageTransformer {

    override fun transformPage(page: View, positi