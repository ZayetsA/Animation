package com.onix.internship.animation.test

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.onix.internship.animation.R
import com.onix.internship.animation.util.dpToPx

class BackgroundAnimator @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        private const val DEFAULT_SIZE = 100
    }

    private var p: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private lateinit var bitmap: Bitmap
    private var drawableRectFirst: Rect
    private var drawableRectSecond: Rect
    private lateinit var bitMapp: Drawable
    private val newPos = 0

    init {
//        context.theme.obtainStyledAttributes(
//            attrs,
//            R.styleable.BackgroundAnimator,
//            0, 0
//        ).apply {
//            val image = getDrawable(R.styleable.BackgroundAnimator_android_src)
//            if (image != null) {
//                val bitMap = Bitmap.createBitmap(
//                    image.intrinsicWidth,
//                    image.intrinsicHeight, Bitmap.Config.ARGB_8888
//                )
//                bitMapp = bitMap
//            }
//        }
        bitMapp = ContextCompat.getDrawable(context, R.drawable.bg)!!
        bitmap = Bitmap.createBitmap(
            bitMapp.intrinsicWidth,
            bitMapp.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        drawableRectFirst = Rect()
        drawableRectSecond = Rect()

    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val initWidth = resolveDefaultSize(widthMeasureSpec)
        val initHeight = resolveDefaultSize(heightMeasureSpec)
        setMeasuredDimension(initWidth, initHeight)
    }

    override fun onDraw(canvas: Canvas?) {
        if (canvas != null) {
            canvas.drawBitmap(bitmap, Rect(0, 0, 100, 100), rectangle, null)
        }

        super.onDraw(canvas)
    }


    private fun resolveDefaultSize(measureSpec: Int): Int {
        return when (MeasureSpec.getMode(measureSpec)) {
            MeasureSpec.UNSPECIFIED -> context.dpToPx(DEFAULT_SIZE).toInt()
            MeasureSpec.AT_MOST -> MeasureSpec.getSize(measureSpec)
            MeasureSpec.EXACTLY -> MeasureSpec.getSize(measureSpec)
            else -> MeasureSpec.getSize(measureSpec)
        }
    }
}