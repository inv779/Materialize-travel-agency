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

    override fun transformPage(page: View, position: Float) {
//        val viewPager = page.parent.parent as ViewPager2
        val offset = position * -(2 * offsetPx + pageMarginPx)
//        if (viewPager.orientation == ViewPager2.ORIENTATION_HORIZONTAL) {
//            if (ViewCompat.getLayoutDirection(viewPager) == ViewCompat.LAYOUT_DIRECTION_RTL) {
//                page.translationX = -offset
//            } else {
                page.translationX = offset
//            }
//        } else {
//            page.translationY = offset
//        }

        if (isCenterScaled) {
            page.apply {
//                when {
//     