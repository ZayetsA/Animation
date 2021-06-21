package com.onix.internship.animation.ui

import android.animation.ValueAnimator
import android.view.animation.LinearInterpolator
import androidx.lifecycle.ViewModel


class AnimationViewModel : ViewModel() {
    val model = AnimationModel()

    private val animator: ValueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f)

    fun setupAnimation(width: Int) {
        animator.repeatCount = ValueAnimator.INFINITE
        animator.interpolator = LinearInterpolator()
        animator.duration = 10000L
        animator.addUpdateListener { animation ->
            model.apply {
                val progress = animation.animatedValue as Float
                val translationX = width * progress
                firstImageTransition = translationX
                secondImageTransition = translationX - width
            }
        }
        animator.start()
    }
}