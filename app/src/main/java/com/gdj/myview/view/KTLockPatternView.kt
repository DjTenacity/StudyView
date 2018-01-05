package com.gdj.myview.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.gdj.myview.utils.MathPatternUtils

/**
 * 自定义九宫格
 */
class KTLockPatternView : View {


    // 二维数组初始化，int[3][3]
    private var mPoints: Array<Array<Point?>> = Array(3) { Array<Point?>(3, { null }) }

    //是否初始化,确保初始化只有一次
    private var mISInit = false
    private var mWidth: Int = 0
    private var mHeight: Int = 0
    // 外圆的半径
    private var mDotRadius: Int = 0
    // 画笔
    private lateinit var mLinePaint: Paint
    private var mPressedPaint: Paint? = null
    private var mErrorPaint: Paint? = null
    private var mNormalPaint: Paint? = null
    private var mArrowPaint: Paint? = null
    // 颜色
    private val mOuterPressedColor = 0xff8cbad8.toInt()
    private val mInnerPressedColor = 0xff0596f6.toInt()
    //默认
    private val mOuterNormalColor = 0xffd9d9d9.toInt()
    private val mInnerNormalColor = 0xff929292.toInt()
    //错误的颜色
    private val mOuterErrorColor = 0xff901032.toInt()
    private val mInnerErrorColor = 0xffea0945.toInt()

    //按下的时候是否是按在一个点上
    private var mIsTouchPoint = false
    //选中的所有点
    private var mSelectPoints = ArrayList<Point>()
    //把选择的密码传给Activity,下标 index
    //设置错误--->需要价格错误方法,把选中点状态改变,清空, 然后刷新

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr)


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //初始化9个宫格，onDraw 回调多次
        if (!mISInit) {
            initDot();
            initPaint();
            mISInit = true
        }


        //绘制9个宫格
        drawShow(canvas)
    }

    private fun drawShow(canvas: Canvas?) {
        for (i in 0..2) {
            for (point in mPoints[i]) {

                //默认状态
                if (point!!.statusIsNrmal()) {
                    //先绘制外圆
                    mNormalPaint!!.color = mOuterNormalColor
                    canvas!!.drawCircle(point!!.centerX.toFloat(), point!!.centerY.toFloat(),
                            mDotRadius.toFloat(), mNormalPaint)

                    //绘制内圆
                    mNormalPaint!!.color = mInnerNormalColor
                    canvas!!.drawCircle(point!!.centerX.toFloat(), point!!.centerY.toFloat(),
                            mDotRadius.toFloat() / 6, mNormalPaint)
                }
                //按下
                if (point!!.statusIsPressed()) {
                    mPressedPaint!!.color = mOuterPressedColor
                    canvas!!.drawCircle(point!!.centerX.toFloat(), point!!.centerY.toFloat(),
                            mDotRadius.toFloat(), mPressedPaint)

                    mPressedPaint!!.color = mInnerPressedColor
                    canvas!!.drawCircle(point!!.centerX.toFloat(), point!!.centerY.toFloat(),
                            mDotRadius.toFloat() / 6, mPressedPaint)
                }
                //错误
                if (point!!.statusIsError()) {
                    mErrorPaint!!.color = mOuterErrorColor
                    canvas!!.drawCircle(point!!.centerX.toFloat(), point!!.centerY.toFloat(),
                            mDotRadius.toFloat(), mErrorPaint)

                    mErrorPaint!!.color = mInnerErrorColor
                    canvas!!.drawCircle(point!!.centerX.toFloat(), point!!.centerY.toFloat(),
                            mDotRadius.toFloat() / 6, mErrorPaint)
                }
            }
        }
        //绘制两个点之间的连线以及箭头
        drawLine(canvas)
    }

    /**绘制两个点之间的连线以及箭头**/
    private fun drawLine(canvas: Canvas?) {
        if (mSelectPoints.size >= 1) {
            var lastPoint = mSelectPoints.get(0);

            //两个点之间需要绘制一条线和箭头
            for (index in 1..mSelectPoints.size - 1) {
                //两个点之间绘制一条线 sin  cos
                //贝塞尔曲线
                drawLine(lastPoint, mSelectPoints[index]!!, canvas!!, mLinePaint)
                //不断绘制两个点，两个点之间会址一条线

                //两个点之间绘制一个箭头
                drawArrow(canvas, mArrowPaint!!, lastPoint, mSelectPoints[index], (mDotRadius / 4).toFloat(), 38)

                lastPoint = mSelectPoints[index]

            }

            //绘制最后一个点 到 手指当前位置的连线
            //如果手指在内圆里面就不要绘制
            var isInnerPoint = MathPatternUtils.checkInRoud(lastPoint!!.centerX.toFloat(), lastPoint!!.centerY.toFloat(),
                    mDotRadius.toFloat() / 5, mMovingX, mMovingY)

            if (!isInnerPoint && mIsTouchPoint) {
                drawLine(lastPoint, Point(mMovingX.toInt(), mMovingY.toInt(), -1), canvas!!, mLinePaint)
            }

        }

    }

    /**画线 ,不能小圆心到小圆心**/
    private fun drawLine(start: Point, end: Point, canvas: Canvas, paint: Paint) {
        val pointDistance = MathPatternUtils.distance(start.centerX.toDouble(), start.centerY.toDouble(),
                end.centerX.toDouble(), end.centerY.toDouble())
        var dx = end.centerX - start.centerX  //两圆之间x轴距离
        var dy = end.centerY - start.centerY  //两个圆之间y轴距离

        val rx = (dx / pointDistance * (mDotRadius / 6.0)).toFloat()  //cos
        val ry = (dy / pointDistance * (mDotRadius / 6.0)).toFloat()  //sin
        canvas.drawLine(start.centerX + rx, start.centerY + ry, end.centerX - rx, end.centerY - ry, paint)


    }

    /**
     * 画箭头
     */
    private fun drawArrow(canvas: Canvas, paint: Paint, start: Point, end: Point, arrowHeight: Float, angle: Int) {
        val d = MathPatternUtils.distance(start.centerX.toDouble(), start.centerY.toDouble(), end.centerX.toDouble(), end.centerY.toDouble())
        val sin_B = ((end.centerX - start.centerX) / d).toFloat()
        val cos_B = ((end.centerY - start.centerY) / d).toFloat()
        val tan_A = Math.tan(Math.toRadians(angle.toDouble())).toFloat()
        val h = (d - arrowHeight.toDouble() - mDotRadius * 1.1).toFloat()
        val l = arrowHeight * tan_A
        val a = l * sin_B
        val b = l * cos_B
        val x0 = h * sin_B
        val y0 = h * cos_B
        val x1 = start.centerX + (h + arrowHeight) * sin_B
        val y1 = start.centerY + (h + arrowHeight) * cos_B
        val x2 = start.centerX + x0 - b
        val y2 = start.centerY.toFloat() + y0 + a
        val x3 = start.centerX.toFloat() + x0 + b
        val y3 = start.centerY + y0 - a
        val path = Path()
        path.moveTo(x1, y1)
        path.lineTo(x2, y2)
        path.lineTo(x3, y3)
        path.close()
        canvas.drawPath(path, paint)
    }


    /**初始化画笔
     * 3个点状态的画笔，默认选中错误，  线的画笔，两个线之间还有箭头
     * */
    private fun initPaint() {
        //new paint 对象  设置paint颜色

        // 线的画笔
        mLinePaint = Paint()
        mLinePaint.color = mInnerPressedColor
        mLinePaint.style = Paint.Style.STROKE
        mLinePaint.isAntiAlias = true
        mLinePaint.strokeWidth = (mDotRadius / 9).toFloat()
        // 按下的画笔
        mPressedPaint = Paint()
        mPressedPaint!!.style = Paint.Style.STROKE
        mPressedPaint!!.isAntiAlias = true
        mPressedPaint!!.strokeWidth = (mDotRadius / 6).toFloat()
        // 错误的画笔
        mErrorPaint = Paint()
        mErrorPaint!!.style = Paint.Style.STROKE
        mErrorPaint!!.isAntiAlias = true
        mErrorPaint!!.strokeWidth = (mDotRadius / 6).toFloat()
        // 默认的画笔
        mNormalPaint = Paint()
        mNormalPaint!!.style = Paint.Style.STROKE
        mNormalPaint!!.isAntiAlias = true
        mNormalPaint!!.strokeWidth = (mDotRadius / 9).toFloat()//宽度
        // 箭头的画笔
        mArrowPaint = Paint()
        mArrowPaint!!.color = mInnerPressedColor
        mArrowPaint!!.style = Paint.Style.FILL
        mArrowPaint!!.isAntiAlias = true//抗锯齿
    }

    /**初始化点*/
    fun initDot() {
        //9 个宫格，存到集合  Point {3}{3} 二维数组
        //不断绘制的时候这几个点都有状态，而且后期肯定需要回调密码，点都有下标  点肯定是对象
        //
        var width = this.width
        var height = this.height

        //兼容横竖屏
        var offSetX = 0
        var offSetY = 0

        if (height > width) {
            offSetY = (height - width) / 2
            height = width
        } else {
            offSetX = (width - height) / 2
            width = height
        }


        var squareWidth = width / 3

        //外圆的大小，根据宽度来
        mDotRadius = width / 12


        //正方形，  offSetY是距离屏幕顶部距离
        mPoints[0][0] = Point(offSetX + squareWidth / 2, squareWidth / 2 + offSetY, 0);
        mPoints[0][1] = Point(offSetX + squareWidth * 3 / 2, squareWidth / 2 + offSetY, 1);
        mPoints[0][2] = Point(offSetX + squareWidth * 5 / 2, squareWidth / 2 + offSetY, 2);

        mPoints[1][0] = Point(offSetX + squareWidth / 2, squareWidth * 3 / 2 + offSetY, 3);
        mPoints[1][1] = Point(offSetX + squareWidth * 3 / 2, squareWidth * 3 / 2 + offSetY, 4);
        mPoints[1][2] = Point(offSetX + squareWidth * 5 / 2, squareWidth * 3 / 2 + offSetY, 5);

        mPoints[2][0] = Point(offSetX + squareWidth / 2, squareWidth * 5 / 2 + offSetY, 6);
        mPoints[2][1] = Point(offSetX + squareWidth * 3 / 2, squareWidth * 5 / 2 + offSetY, 7)
        mPoints[2][2] = Point(offSetX + squareWidth * 5 / 2, squareWidth * 5 / 2 + offSetY, 8)

    }

    //手指触摸的位置
    private var mMovingX = 0f
    private var mMovingY = 0f
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        mMovingX = event!!.x
        mMovingY = event.y

        when (event!!.action) {

            MotionEvent.ACTION_DOWN -> {
                //判断手指是否按在一个宫格上面
                //如何判断一个点在圆里面 ：  点到圆心的距离小于半径
                var point = point
                if (point != null) {
                    mIsTouchPoint = true
                    mSelectPoints.add(point)
                    //改变当前点的状态
                    point.setStatusPressed()
                }

            }
            MotionEvent.ACTION_MOVE -> {
                if (mIsTouchPoint) {
                    //按下的时候一定要在一个点上，不断触摸的时候不断去判断新的点
                    var point = point
                    if (point != null) {

                        if (!mSelectPoints.contains(point)) {
                            mSelectPoints.add(point)
                        }
                        //改变当前点的状态
                        point.setStatusPressed()
                    }
                }

            }
            MotionEvent.ACTION_UP -> {
                //回调 密码获取监听  显示错误，错误显示完之后要清空恢复默认
                mIsTouchPoint = false
            }

        }
        invalidate()
        return true
    }

    /**获取按下的点，一个变量，都会默认有set，get方法*/

    private val point: Point?
        get() {
            for (i in 0..2) {
                //for循环 9 个点，判断手指位置是否在这
                for (point in mPoints[i]) {
                    if (MathPatternUtils.checkInRoud(point!!.centerX.toFloat(), point!!.centerY.toFloat(),
                            mDotRadius.toFloat(), mMovingX, mMovingY)) {

                        return point;
                    }
                }
            }
            return null
        }

    /**宫格的类**/
    class Point(var centerX: Int, var centerY: Int, var index: Int) {
        private val STATUS_NORMAL = 1
        private val STATUS_PRESSED = 2
        private val STATUS_ERROR = 3

        //当前点的状态  有三种状态
        private var status = STATUS_NORMAL


        fun setStatusPressed() {
            status = STATUS_PRESSED
        }

        fun setStatusNormal() {
            status = STATUS_NORMAL
        }

        fun setStatusError() {
            status = STATUS_ERROR
        }

        fun statusIsPressed(): Boolean {
            return status == STATUS_PRESSED
        }

        fun statusIsError(): Boolean {
            return status == STATUS_ERROR
        }

        fun statusIsNrmal(): Boolean {
            return status == STATUS_NORMAL
        }
    }

}