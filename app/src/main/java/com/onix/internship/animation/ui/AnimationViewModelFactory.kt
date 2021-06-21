package com.onix.internship.animation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class AnimationViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AnimationViewModel::class.java)) {
            return AnimationViewModel() as T
        }
        throw IllegalArgumentException("Unknown view model class!")
    }
}