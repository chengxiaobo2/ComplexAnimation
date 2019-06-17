package com.example.cheng.complexanimation.view

import android.animation.ValueAnimator
import android.view.View
import android.view.animation.LinearInterpolator


/**
 * 位移的动画
 *
 * @author chengxiaobo
 * @time 2019/6/14 4:14
 */
class TranslationAnimationManager(val startTranslateX: Float, val endTranslationX: Float, val startTranslateY: Float, val maxTranslationY: Float) : AnimationInterface {
    var view: View? = null
    private var animator: ValueAnimator? = null
    private var onAnimationUpdate: AnimationUpdate? = null


    override fun startAnimation() {
        view?.let {
            if (animator == null) {
                animator = ValueAnimator.ofFloat(0.0f, 1.0f)
            }

            animator?.duration = 333L
            animator?.start()
            animator?.interpolator = LinearInterpolator()
            animator?.addUpdateListener { animation ->
                onAnimationUpdate?.onAnimationUpdate(animation.animatedFraction)
                val t = animation.animatedFraction
                //X方向为三角函数曲线的运动轨迹
                val translationX = (-0.5f * Math.cos(t * Math.PI).toFloat() + 0.5f) * (endTranslationX - startTranslateX) + startTranslateX

                val v0 = t * 2.0f - 1.0f
                //y方向为抛物线的运动轨迹
                val translationY = (-v0 * v0 + 1.0f) * (maxTranslationY - 0)

                it.translationX = translationX
                it.translationY = translationY
            }
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
        }
    }

    override fun setAnimationUpdate(animationUpdate: AnimationUpdate) {
        this.onAnimationUpdate = animationUpdate
    }
}