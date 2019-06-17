package com.example.cheng.complexanimation.util

import android.content.Context


/**
 *
 * @author chengxiaobo
 * @time 2019/6/13 9:46
 */
// px dp 互转
fun dp2Px(context: Context, dp: Float): Int {
    val scale = context.resources.displayMetrics.density
    return (dp * scale + 0.5f).toInt()
}

fun getPx(context: Context, px1080: Float): Float {
    val screenWidth = context.resources.displayMetrics.widthPixels
    return screenWidth * px1080 / 1080
}