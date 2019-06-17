package com.example.cheng.complexanimation.view


/**
 * 执行动画的接口
 *
 * @author chengxiaobo
 * @time 2019/6/13 17:52
 */
interface AnimationInterface {
    fun startAnimation()
    fun stopAnimation()
    fun cancelAnimation()
    fun setAnimationUpdate(animationUpdate: AnimationUpdate)

}

interface AnimationUpdate {
    fun onAnimationUpdate(precent: Float)
}