package com.onix.internship.animation.ui

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR

data class AnimationModel(
    private var _firstImageTransition: Float = 0F,
    private var _secondImageTransition: Float = 0F
) : BaseObservable() {

    @get:Bindable
    var firstImageTransition: Float = _firstImageTransition
        set(value) {
            _firstImageTransition = value
            field = value
            notifyPropertyChanged(BR.firstImageTransition)
        }

    @get:Bindable
    var secondImageTransition: Float = _secondImageTransition
        set(value) {
            _secondImageTransition = value
            field = value
            notifyPropertyChanged(BR.secondImageTransition)
        }

}