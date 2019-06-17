package com.example.cheng.complexanimation.view

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.View

/**
 *  放大缩小动画
 *
 * @author chengxiaobo
 * @time 2019/6/13 17:52
 */
class RotateAnimationManager : AnimationInterface {

    var view: View? = null
    private var animator: ObjectAnimator? = null
    private var onAnimationUpdate: AnimationUpdate? = null


    override fun startAnimation() {
        view?.let {
            if (animator == null) {
                animator = ObjectAnimator.ofFloat(it, "rotation", 360.0f)
            }

            animator?.duration = 333L
            animator?.start()
            animator?.addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
                override fun onAnimationUpdate(animation: ValueAnimator) {
                    onAnimationUpdate?.onAnimationUpdate(animation.animatedFraction)
                }
            })
        }
    }

    override fun stopAnimation() {
        view?.let {
            cancelAnimation()
        }
    }

    override fun cancelAnimation() {
        view?.let {
            animator?.cancel()
            it.scaleX = 1.0f
            it.scaleY = 1.0f
        }
    }

    override fun setAnimationUpdate(animationUpdate: AnimationUpdate) {
        this.onAnimationUpdate = animationUpdate
    }
}