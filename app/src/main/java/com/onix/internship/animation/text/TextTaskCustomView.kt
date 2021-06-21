package com.onix.internship.animation.text

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View


class TextTaskCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var p: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var text = "some text"
    var fontSize = 100
    var realWidth = 0f
    var realHeight = 0f

    init {
        val bounds = Rect()

        p.typeface = Typeface.DEFAULT

        p.textSize = fontSize.toFloat()

        p.getTextBounds(text, 0, text.length, bounds)

        realHeight = bounds.height().toFloat()
        realWidth = bounds.width().toFloat()

    }


    override fun onDraw(canvas: Canvas) {
        canvas.drawARGB(80, 102, 204, 255)
        p.color = Color.RED

        p.strokeWidth = 3F
        canvas.drawLine((width / 2).toFloat(), 0F, (width / 2).toFloat(), height.toFloat(), p)
        canvas.drawLine(0F, (height / 2).toFloat(), width.toFloat(), (height / 2).toFloat(), p)

        p.color = Color.BLACK
        p.isStrikeThruText = true
        p.textSize = fontSize.toFloat()
        canvas.drawText(
            text,
            ((width / 2).toFloat()) - realWidth / 2,
            ((height / 2).toFloat()) + realHeight / 2,
            p
        )

        p.isStrikeThruText = false
        p.color = Color.RED
        p.textSize = fontSize.toFloat()
        canvas.drawText(
            text, width - realWidth,
            height.toFloat(),
            p
        )

        p.color = Color.BLUE
        p.textSize = fontSize.toFloat()
        canvas.drawText(
            text,
            0F,
            0 + realHeight,
            p
        )

        p.color = Color.BLUE
        canvas.drawLine(
            0F,
            (height / 2).toFloat() + realHeight / 2,
            width.toFloat(),
            (height / 2).toFloat() + realHeight / 2,
            p
        )

        canvas.drawLine(
            0F,
            (height / 2).toFloat() - realHeight / 2,
            width.toFloat(),
            (height / 2).toFloat() - realHeight / 2,
            p
        )

        p.color = Color.GREEN
        canvas.drawLine(
            0F,
            0 + realHeight,
            width.toFloat(),
            0 + realHeight,
            p
        )

        p.color = Color.GREEN
        canvas.drawLine(
            0F,
            height - realHeight,
            width.toFloat(),
            height - realHeight,
            p
        )

        p.color = Color.RED
        canvas.drawLine(
            realWidth / 2,
            0f,
            realWidth / 2,
            height.toFloat(),
            p
        )

        p.color = Color.RED
        canvas.drawLine(
            width - realWidth / 2,
            0f,
            width - realWidth / 2,
            height.toFloat(),
            p
        )
    }
}
