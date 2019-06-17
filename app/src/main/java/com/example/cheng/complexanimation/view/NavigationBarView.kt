package com.example.cheng.complexanimation.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import com.example.cheng.complexanimation.R
import com.example.cheng.complexanimation.util.dp2Px


/**
 * 导航条
 *
 * @author chengxiaobo
 * @time 2019/6/13 9:29
 */
class NavigationBarView : ViewGroup {

    //导航底部的高度
    private var navigationBottom = 0
    //导航上部的高度
    private var navigationTop = 0
    //导航的总高度
    private var navigationHeight = 0

    //导航的总宽度
    private var navigationWidth = 0
    //导航某一项的宽度
    private var navigationButtonWidth = 0
    //小球距离顶部的距离
    private var distanceTop = 0
    //小球最大向下的距离
    private var maxTranslationY = 0
    //当前选中的是第几项
    private var currentIndex = -1

    //动画动画（上下左右+旋转）View
    private var animationGroup: AnimationGroup? = null
    //贝塞尔曲线View
    private var band: Band? = null
    //5个View
    private val navigationButtonListView = ArrayList<View>()

    //旋转动画
    private val rotateAnimationManager: RotateAnimationManager  by lazy {
        RotateAnimationManager()
    }
    //位移动画
    private var translationAnimationManager: TranslationAnimationManager? = null

    //资源
    val resouceList = arrayOf(R.drawable.p_1, R.drawable.p_2, R.drawable.p_3, R.drawable.p_4, R.drawable.p_5)

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    fun init() {
        navigationBottom = dp2Px(context, 50.0f)
        navigationTop = dp2Px(context, 30.0f)
        distanceTop = dp2Px(context, 10.0f)
        maxTranslationY = dp2Px(context, 45.0f)
        navigationHeight = navigationBottom + navigationTop
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        measureChildren(widthMeasureSpec, heightMeasureSpec)

        navigationWidth = MeasureSpec.getSize(widthMeasureSpec)
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), navigationHeight)
    }

    fun addImageButtons(views: List<View>) {
        removeAllViews()
        animationGroup = AnimationGroup(context)
        animationGroup?.setImageResouce(R.drawable.p_1)
        rotateAnimationManager.view = animationGroup?.getAnimationView()
        animationGroup?.let {
            super.addView(it)
        }

        band = Band(context)
        band?.let {
            super.addView(it)
            it.post {
                it.setFractionAndX(0.0f, (animationGroup!!.left + animationGroup!!.right) / 2.0f)
            }

        }


        for (i in 0 until views.size) {
            val frameLayout = FrameLayout(context)
            val view = views.get(i)
            super.addView(frameLayout)
            navigationButtonListView.add(frameLayout)
            frameLayout.addView(view)
            val params = view.layoutParams as FrameLayout.LayoutParams
            params.gravity = Gravity.CENTER

            if (i == 0) {
                frameLayout.alpha = 0.0f
            }
            frameLayout.setOnClickListener {
                if (currentIndex == i) {
                    return@setOnClickListener
                }

                animationGroup?.let {
                    translationAnimationManager = TranslationAnimationManager(it.translationX, navigationButtonWidth * i.toFloat(), it.translationY, maxTranslationY.toFloat())
                    translationAnimationManager?.view = animationGroup
                    translationAnimationManager?.setAnimationUpdate(object : AnimationUpdate {
                        var p = 0.0f
                        override fun onAnimationUpdate(precent: Float) {

                            if (p < 0.5f && precent >= 0.5f) {
                                it.setImageResouce(resouceList[i])
                            } else if (precent == 1.0f) {
                                rotateAnimationManager.startAnimation()
                            }
                            p = precent

                            //根据transtionX的值，去设置5个View的透明度
                            animationGroup?.let {
                                val animationViewX = navigationButtonWidth / 2.0f + it.translationX

                                for (i in 0 until navigationButtonListView.size) {
                                    val view = navigationButtonListView.get(i)
                                    val centerX = (view.left + view.right) / 2.0f
                                    val d = Math.abs(animationViewX - centerX)
                                    if (d <= navigationButtonWidth * 0.75f) {
                                        view.alpha = 0.0f
                                    } else if (d >= navigationButtonWidth * 1.0f) {
                                        view.alpha = 1.0f
                                    } else {
                                        view.alpha = (d - navigationButtonWidth * 0.75f) / (navigationButtonWidth * (1.0f - 0.75f))
                                    }
                                }
                            }

                            band?.setFractionAndX(it.translationY / maxTranslationY.toFloat(), it.translationX + (it.left + it.right) * 0.5f)
                        }
                    })
                    translationAnimationManager?.startAnimation()
                }
                currentIndex = i
                Toast.makeText(context, i.toString(), Toast.LENGTH_SHORT).show()
            }
        }
        requestLayout()

    }

    override fun addView(child: View?) {

    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        if (childCount <= 0) {
            return
        }

        if (navigationButtonListView.size > 0) {
            navigationButtonWidth = navigationWidth / (navigationButtonListView.size)
        }

        for (i in 0 until navigationButtonListView.size) {
            val view = navigationButtonListView.get(i)
            view.layout(i * navigationButtonWidth, navigationTop, (i + 1) * navigationButtonWidth, navigationTop + navigationBottom)
        }

        animationGroup?.let {
            it.layout((navigationButtonWidth - it.measuredWidth) / 2, distanceTop, (navigationButtonWidth - it.measuredWidth) / 2 + it.measuredWidth, it.measuredHeight + distanceTop)
        }

        band?.let {
            it.layout(0, navigationTop, this.measuredWidth, this.measuredHeight)
        }
    }
}