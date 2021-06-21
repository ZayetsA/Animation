package com.onix.internship.animation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.onix.internship.animation.custom.CustomStarBackground
import com.onix.internship.animation.databinding.FragmentAnimationBinding


class AnimationFragment : Fragment() {

    private val viewModel: AnimationViewModel by viewModels {
        AnimationViewModelFactory()
    }

    private lateinit var binding: FragmentAnimationBinding

    private lateinit var customBg: CustomStarBackground

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAnimationBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        requireView().viewTreeObserver.addOnWindowFocusChangeListener {
//            if (it) {
//                viewModel.setupAnimation(binding.backgroundOne.width)
//            }
        }
        customBg = binding.animatedView
        binding.btnPause.setOnClickListener {
            customBg.pause()
        }
        binding.btnResume.setOnClickListener {
            customBg.resume()
        }
    }

    override fun onPause() {
        super.onPause()
        customBg.pause()
    }

    override fun onResume() {
        super.onResume()
        customBg.resume()
    }
}
