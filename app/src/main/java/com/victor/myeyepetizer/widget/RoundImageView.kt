package com.victor.myeyepetizer.widget

/**
 * @author victor
 * @date 3/1/18
 * @email vvictorwan@gmail.com
 * @blog www.victorwan.cn                                            #
 */

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import com.victor.myeyepetizer.R

/**
 * 圆形、圆角图片
 */

class RoundImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : android.support.v7.widget.AppCompatImageView(context, attrs, defStyleAttr) {
    /**
     * 图片的类型，圆形or圆角
     */
    private var type: Int = 0
    /**
     * 描边的颜色、宽度
     */
    private var mBorderColor: Int = 0
    private var mBorderWidth: Float = 0.toFloat()
    /**
     * 圆角的大小
     */
    private var mCornerRadius: Float = 0.toFloat()
    //左上角圆角大小
    private var mLeftTopCornerRadius: Float = 0.toFloat()
    //右上角圆角大小
    private var mRightTopCornerRadius: Float = 0.toFloat()
    //左下角圆角大小
    private var mLeftBottomCornerRadius: Float = 0.toFloat()
    //右下角圆角大小
    private var mRightBottomCornerRadius: Float = 0.toFloat()

    /**
     * 绘图的Paint
     */
    private var mBitmapPaint: Paint? = null
    private var mBorderPaint: Paint? = null
    /**
     * 圆角的半径
     */
    private var mRadius: Float = 0.toFloat()
    /**
     * 3x3 矩阵，主要用于缩小放大
     */
    private var mMatrix: Matrix? = null
    /**
     * 渲染图像，使用图像为绘制图形着色
     */
    private var mBitmapShader: BitmapShader? = null
    /**
     * view的宽度
     */
    private var mWidth: Int = 0
    /**
     * 圆角图片区域
     */
    private var mRoundRect: RectF? = null

    private var mRoundPath: Path? = null

    init {

        val a = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView,
                defStyleAttr, 0)

        type = a.getInt(R.styleable.RoundImageView_type, TYPE_OVAL)
        mBorderColor = a.getColor(R.styleable.RoundImageView_border_color, Color.WHITE)
        mBorderWidth = a.getDimension(R.styleable.RoundImageView_border_width, 0f)
        mCornerRadius = a.getDimension(R.styleable.RoundImageView_corner_radius, dp2px(10).toFloat())
        mLeftTopCornerRadius = a.getDimension(R.styleable.RoundImageView_leftTop_corner_radius, 0f)
        mLeftBottomCornerRadius = a.getDimension(R.styleable
                .RoundImageView_leftBottom_corner_radius, 0f)
        mRightTopCornerRadius = a.getDimension(R.styleable.RoundImageView_rightTop_corner_radius,
                0f)
        mRightBottomCornerRadius = a.getDimension(R.styleable
                .RoundImageView_rightBottom_corner_radius, 0f)

        a.recycle()

        init()

    }

    private fun init() {
        mRoundPath = Path()
        mMatrix = Matrix()
        mBitmapPaint = Paint()
        mBitmapPaint!!.isAntiAlias = true
        mBorderPaint = Paint()
        mBorderPaint!!.isAntiAlias = true
        mBorderPaint!!.style = Paint.Style.STROKE
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        /**
         * 如果类型是圆形，则强制改变view的宽高一致，以小值为准
         */
        if (type == TYPE_CIRCLE) {
            mWidth = Math.min(View.MeasureSpec.getSize(widthMeasureSpec),
                    View.MeasureSpec.getSize(heightMeasureSpec))
            mRadius = mWidth / 2 - mBorderWidth / 2
            setMeasuredDimension(mWidth, mWidth)
        }

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        // 圆角图片的范围
        if (type == TYPE_ROUND || type == TYPE_OVAL) {
            mRoundRect = RectF(mBorderWidth / 2, mBorderWidth / 2, w - mBorderWidth / 2, h - mBorderWidth / 2)
        }
    }

    override fun onDraw(canvas: Canvas) {

        mBorderPaint!!.color = mBorderColor
        mBorderPaint!!.strokeWidth = mBorderWidth

        if (drawable == null) {
            return
        }
        setUpShader()

        if (type == TYPE_ROUND) {
            setRoundPath()

            canvas.drawPath(mRoundPath!!, mBitmapPaint!!)

            //绘制描边
            canvas.drawPath(mRoundPath!!, mBorderPaint!!)
        } else if (type == TYPE_CIRCLE) {

            canvas.drawCircle(mRadius + mBorderWidth / 2, mRadius + mBorderWidth / 2, mRadius,
                    mBitmapPaint!!)

            //绘制描边
            canvas.drawCircle(mRadius + mBorderWidth / 2, mRadius + mBorderWidth / 2, mRadius,
                    mBorderPaint!!)

        } else {
            canvas.drawOval(mRoundRect!!, mBitmapPaint!!)

            canvas.drawOval(mRoundRect!!, mBorderPaint!!)
        }
    }


    private fun setRoundPath() {
        mRoundPath!!.reset()

        /**
         * 如果四个圆角大小都是默认值0，
         * 则将四个圆角大小设置为mCornerRadius的值
         */
        if (mLeftTopCornerRadius == 0f &&
                mLeftBottomCornerRadius == 0f &&
                mRightTopCornerRadius == 0f &&
                mRightBottomCornerRadius == 0f) {

            mRoundPath!!.addRoundRect(mRoundRect,
                    floatArrayOf(mCornerRadius, mCornerRadius, mCornerRadius, mCornerRadius, mCornerRadius, mCornerRadius, mCornerRadius, mCornerRadius),
                    Path.Direction.CW)

        } else {
            mRoundPath!!.addRoundRect(mRoundRect,
                    floatArrayOf(mLeftTopCornerRadius, mLeftTopCornerRadius, mRightTopCornerRadius, mRightTopCornerRadius, mRightBottomCornerRadius, mRightBottomCornerRadius, mLeftBottomCornerRadius, mLeftBottomCornerRadius),
                    Path.Direction.CW)
        }

    }


    /**
     * 初始化BitmapShader
     */
    private fun setUpShader() {

        val drawable = drawable ?: return

        val bmp = drawableToBitamp(drawable)
        // 将bmp作为着色器，就是在指定区域内绘制bmp
        mBitmapShader = BitmapShader(bmp, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        var scale = 1.0f
        if (type == TYPE_CIRCLE) {
            // 拿到bitmap宽或高的小值
            val bSize = Math.min(bmp.width, bmp.height)
            scale = mWidth * 1.0f / bSize
            //使缩放后的图片居中
            val dx = (bmp.width * scale - mWidth) / 2
            val dy = (bmp.height * scale - mWidth) / 2
            mMatrix!!.setTranslate(-dx, -dy)

        } else if (type == TYPE_ROUND || type == TYPE_OVAL) {

            if (!(bmp.width == width && bmp.height == height)) {
                // 如果图片的宽或者高与view的宽高不匹配，计算出需要缩放的比例；缩放后的图片的宽高，一定要大于我们view的宽高；所以我们这里取大值；
                scale = Math.max(width * 1.0f / bmp.width,
                        height * 1.0f / bmp.height)
                //使缩放后的图片居中
                val dx = (scale * bmp.width - width) / 2
                val dy = (scale * bmp.height - height) / 2
                mMatrix!!.setTranslate(-dx, -dy)
            }
        }
        // shader的变换矩阵，我们这里主要用于放大或者缩小
        mMatrix!!.preScale(scale, scale)

        mBitmapShader!!.setLocalMatrix(mMatrix)

        // 设置变换矩阵
        mBitmapShader!!.setLocalMatrix(mMatrix)
        // 设置shader
        mBitmapPaint!!.shader = mBitmapShader
    }


    /**
     * drawable转bitmap
     */
    private fun drawableToBitamp(drawable: Drawable): Bitmap {
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }
        val w = drawable.intrinsicWidth
        val h = drawable.intrinsicHeight
        val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, w, h)
        drawable.draw(canvas)
        return bitmap
    }


    /**
     * 设置图片类型:
     * imageType=0 圆形图片
     * imageType=1 圆角图片
     * 默认为圆形图片
     */
    fun setType(imageType: Int): RoundImageView {
        if (this.type != imageType) {
            this.type = imageType
            if (this.type != TYPE_ROUND && this.type != TYPE_CIRCLE && this.type != TYPE_OVAL) {
                this.type = TYPE_OVAL
            }
            requestLayout()
        }
        return this
    }


    /**
     * 设置圆角图片的圆角大小
     */
    fun setCornerRadius(cornerRadius: Int): RoundImageView {
        var cornerRadius = cornerRadius
        cornerRadius = dp2px(cornerRadius)
        if (mCornerRadius != cornerRadius.toFloat()) {
            mCornerRadius = cornerRadius.toFloat()
            invalidate()
        }
        return this
    }

    /**
     * 设置圆角图片的左上圆角大小
     */
    fun setLeftTopCornerRadius(cornerRadius: Int): RoundImageView {
        var cornerRadius = cornerRadius
        cornerRadius = dp2px(cornerRadius)
        if (mLeftTopCornerRadius != cornerRadius.toFloat()) {
            mLeftTopCornerRadius = cornerRadius.toFloat()
            invalidate()
        }
        return this
    }

    /**
     * 设置圆角图片的右上圆角大小
     */
    fun setRightTopCornerRadius(cornerRadius: Int): RoundImageView {
        var cornerRadius = cornerRadius
        cornerRadius = dp2px(cornerRadius)
        if (mRightTopCornerRadius != cornerRadius.toFloat()) {
            mRightTopCornerRadius = cornerRadius.toFloat()
            invalidate()
        }
        return this
    }

    /**
     * 设置圆角图片的左下圆角大小
     */
    fun setLeftBottomCornerRadius(cornerRadius: Int): RoundImageView {
        var cornerRadius = cornerRadius
        cornerRadius = dp2px(cornerRadius)
        if (mLeftBottomCornerRadius != cornerRadius.toFloat()) {
            mLeftBottomCornerRadius = cornerRadius.toFloat()
            invalidate()
        }
        return this
    }

    /**
     * 设置圆角图片的右下圆角大小
     */
    fun setRightBottomCornerRadius(cornerRadius: Int): RoundImageView {
        var cornerRadius = cornerRadius
        cornerRadius = dp2px(cornerRadius)
        if (mRightBottomCornerRadius != cornerRadius.toFloat()) {
            mRightBottomCornerRadius = cornerRadius.toFloat()
            invalidate()
        }

        return this
    }


    /**
     * 设置描边宽度
     */
    fun setBorderWidth(borderWidth: Int): RoundImageView {
        var borderWidth = borderWidth
        borderWidth = dp2px(borderWidth)
        if (mBorderWidth != borderWidth.toFloat()) {
            mBorderWidth = borderWidth.toFloat()
            invalidate()
        }

        return this
    }

    /**
     * 设置描边颜色
     */
    fun setBorderColor(borderColor: Int): RoundImageView {
        if (mBorderColor != borderColor) {
            mBorderColor = borderColor
            invalidate()
        }

        return this
    }

    private fun dp2px(dpVal: Int): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal.toFloat(), resources.displayMetrics).toInt()
    }

    companion object {
        val TYPE_CIRCLE = 0
        val TYPE_ROUND = 1
        val TYPE_OVAL = 2
    }
}