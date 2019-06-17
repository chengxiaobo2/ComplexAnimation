package com.example.cheng.complexanimation.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.example.cheng.complexanimation.R
import kotlinx.android.synthetic.main.circle_animation.view.*


/**
 *  执行动画的View(上下左右移动+旋转)
 *
 * @author chengxiaobo
 * @time 2019/6/13 20:27
 */
class AnimationGroup : FrameLayout {

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        LayoutInflater.from(context).inflate(R.layout.circle_animation, this)
    }

    fun setImageResouce(resouce: Int) {
        animationView.setImageResource(resouce)
    }

    fun getAnimationView(): View {
        return animationView
    }
}