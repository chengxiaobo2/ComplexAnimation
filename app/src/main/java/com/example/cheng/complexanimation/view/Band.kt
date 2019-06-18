package com.example.cheng.complexanimation.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import com.example.cheng.complexanimation.util.getPx

/**
 * 画凹字形的曲线
 */
class Band : View {


    internal var _fraction: Float = 0.toFloat()
    internal var _x: Float = 0.toFloat()

    var _curveWidth0 = 340.0f
    var _curveWidth1 = 180.0f
    var _curveDepth0 = 120.0f
    var _curveDepth1 = 75.0f
    var _curveControlA0 = 80.0f
    var _curveControlA1 = 40.0f
    var _curveControlB0 = 115.0f
    var _curveControlB1 = 50.0f

    internal var _paint = Paint(Paint.ANTI_ALIAS_FLAG)
    internal var _path = Path()

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
        _curveWidth0 = getPx(context, 340.0f)
        _curveWidth1 = getPx(context, 180.0f)
        _curveDepth0 = getPx(context, 120.0f)
        _curveDepth1 = getPx(context, 75.0f)
        _curveControlA0 = getPx(context, 80.0f)
        _curveControlA1 = getPx(context, 40.0f)
        _curveControlB0 = getPx(context, 115.0f)
        _curveControlB1 = getPx(context, 50.0f)
    }

    fun setParams(width0: Float, width1: Float, depth0: Float, depth1: Float, controlA0: Float, controlA1: Float, controlB0: Float, controlB1: Float) {
        _curveWidth0 = width0
        _curveWidth1 = width1
        _curveDepth0 = depth0
        _curveDepth1 = depth1
        _curveControlA0 = controlA0
        _curveControlA1 = controlA1
        _curveControlB0 = controlB0
        _curveControlB1 = controlB1
    }

    /**
     * 根据球的上下位置，画贝塞尔曲线。仔细观察移动的过程中 凹的形状是不一样的
     * 球在上面的时候凹形大，球在下面的时候凹形状小
     */
    fun setFractionAndX(fraction: Float, x: Float) {
        _fraction = fraction
        _x = x
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        _path.reset()

        val width = _curveWidth0 + _fraction * (_curveWidth1 - _curveWidth0)
        val depth = _curveDepth0 + _fraction * (_curveDepth1 - _curveDepth0)
        val controlA = _curveControlA0 + _fraction * (_curveControlA1 - _curveControlA0)
        val controlB = _curveControlB0 + _fraction * (_curveControlB1 - _curveControlB0)

        val cl = _x - width * 0.5f
        val cr = _x + width * 0.5f

        val left = Math.min(cl, 0.0f)
        val right = Math.max(cr, getWidth().toFloat())
        val bottom = height.toFloat()

        _path.moveTo(cl, 0.0f)
        _path.cubicTo(cl + controlA, 0.0f, _x - controlB, depth, _x, depth)
        _path.cubicTo(_x + controlB, depth, cr - controlA, 0.0f, cr, 0.0f)
        if (right != cr) {
            _path.lineTo(right, 0.0f)
        }
        _path.lineTo(right, bottom)
        _path.lineTo(left, bottom)
        _path.lineTo(left, 0.0f)
        if (left != cl) {
            _path.lineTo(cl, 0.0f)
        }

        canvas.drawPath(_path, _paint)
    }

    init {
        _paint.color = -0x1
        _paint.style = Paint.Style.FILL
    }
}
