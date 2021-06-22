package com.onix.internship.animation.custom

import android.animation.TimeAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.onix.internship.animation.R
import com.onix.internship.animation.util.dpToPx
import kotlin.math.roundToInt
import kotlin.random.Random


class CustomStarBackground @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        private const val BASE_SPEED_DP_PER_S = 200
        private const val DEFAULT_SIZE = 100
        private const val DEFAULT_IMAGE_SIZE = 80f
        private const val DEFAULT_NUM_OF_FRAMES = 32
        private const val SEED = 1337
        private const val SCALE_MIN_PART = 0.45f
        private const val SCALE_RANDOM_PART = 0.55f
        private const val ALPHA_SCALE_PART = 0.5f
        private const val ALPHA_RANDOM_PART = 0.5f
    }

    private val mStars: MutableList<Star> = arrayListOf()
    private val mRnd: Random = Random(SEED)

    private val mTimeAnimator: TimeAnimator by lazy {
        TimeAnimator()
    }

    private val handler: TimeAnimator.TimeListener by lazy {
        object : TimeAnimator.TimeListener {
            override fun onTimeUpdate(animation: TimeAnimator?, totalTime: Long, deltaTime: Long) {
                if (!isLaidOut) {
                    return
                }
                updateState(deltaTime)
                invalidate()
            }
        }
    }

    private lateinit var mDrawable: Drawable

    private var mBaseSpeed = 0f
    private var mBaseSize = 0f
    private var numOfFrames = 0
    private var mCurrentPlayTime: Long = 0


    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomStarBackground,
            0, 0
        ).apply {
            val image = getDrawable(R.styleable.CustomStarBackground_android_src)
                ?: ContextCompat.getDrawable(getContext(), R.drawable.star)
            if (image != null) {
                mDrawable = image
            }
            mBaseSize =
                getDimension(R.styleable.CustomStarBackground_frame_size, DEFAULT_IMAGE_SIZE)
            mBaseSpeed = getInteger(
                R.styleable.CustomStarBackground_frame_speed,
                BASE_SPEED_DP_PER_S
            ) * resources.displayMetrics.density
            numOfFrames = getInteger(
                R.styleable.CustomStarBackground_current_frames,
                DEFAULT_NUM_OF_FRAMES
            )
            recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val initWidth = resolveDefaultSize(widthMeasureSpec)
        val initHeight = resolveDefaultSize(heightMeasureSpec)
        setMeasuredDimension(initWidth, initHeight)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        for (i in 0..numOfFrames) {
            val star = Star()
            initializeStar(star, w, h)
            mStars.add(star)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        for (star in mStars) {
            val starSize = star.scale * mBaseSize
            if (star.y + starSize < 0 || star.y - starSize > height) {
                continue
            }
            if (canvas != null) {
                val save = canvas.save()
                canvas.translate(star.x, star.y)
                val progress = (star.y + starSize) / height
                canvas.rotate(360 * progress)
                val size: Int = starSize.roundToInt()
                mDrawable.setBounds(-size, -size, size, size)
                mDrawable.alpha = (255 * star.alpha).roundToInt()
                mDrawable.draw(canvas)
                canvas.restoreToCount(save)
            }
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (!isInEditMode) {
            mTimeAnimator.setTimeListener(handler)
            mTimeAnimator.start()
        }
    }

    override fun onDetachedFromWindow() {
        if (!isInEditMode) {
            mTimeAnimator.cancel()
            mTimeAnimator.removeAllListeners()
        }
        super.onDetachedFromWindow()
    }

    private fun resolveDefaultSize(measureSpec: Int): Int {
        return when (MeasureSpec.getMode(measureSpec)) {
            MeasureSpec.UNSPECIFIED -> context.dpToPx(DEFAULT_SIZE).toInt()
            MeasureSpec.AT_MOST -> MeasureSpec.getSize(measureSpec)
            MeasureSpec.EXACTLY -> MeasureSpec.getSize(measureSpec)
            else -> MeasureSpec.getSize(measureSpec)
        }
    }

    fun pause() {
        if (!isInEditMode) {
            if (mTimeAnimator.isRunning) {
                mCurrentPlayTime = mTimeAnimator.currentPlayTime
                mTimeAnimator.pause()
            }
        }
    }

    fun resume() {
        if (!isInEditMode) {
            if (mTimeAnimator.isPaused) {
                mTimeAnimator.start()
                mTimeAnimator.currentPlayTime = mCurrentPlayTime
            }
        }
    }

    private fun updateState(deltaTime: Long) {
        val deltaSeconds: Float = deltaTime / 1000f
        val viewWidth = width
        val viewHeight = height

        for (star in mStars) {
            star.y -= star.speed * deltaSeconds
            val size = star.scale * mBaseSize
            if (star.y + size < 0) {
                initializeStar(star, viewWidth, viewHeight)
            }
        }
    }

    private fun initializeStar(star: Star, w: Int, h: Int) {
        with(star) {
            scale = SCALE_MIN_PART + SCALE_RANDOM_PART * mRnd.nextFloat()
            x = w * mRnd.nextFloat()
            y = h.toFloat()
            y += scale * mBaseSize
            y += h * mRnd.nextFloat() / 4f
            alpha = ALPHA_SCALE_PART * scale + ALPHA_RANDOM_PART * mRnd.nextFloat()
            speed = mBaseSpeed * alpha * scale
        }
    }
}
