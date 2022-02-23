package com.playground.streams.sharedevent

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.playground.streams.R
import com.playground.streams.databinding.ActivitySharedEventBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SharedEventActivity : AppCompatActivity() {

    private var _binding: ActivitySharedEventBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySharedEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        renderView()
        if (savedInstanceState == null) {
            addFragments()
        }
    }

    private fun renderView() = with(binding) {
        buttonTriggerSharedEvent.setOnClickListener {
            sharedViewModel.triggerSharedEvent()
        }
        buttonTriggerSingleEvent.setOnClickListener {
            sharedViewModel.triggerSingleEvent()
        }
        buttonTriggerLiveEvent.setOnClickListener {
            sharedViewModel.triggerLiveEvent()
        }
    }

    private fun addFragments() {
        addFragment(R.id.fragmentContainer1, 0)
        addFragment(R.id.fragmentContainer2, 1)
        addFragment(R.id.fragmentContainer3, 2)
    }

    private fun addFragment(containerId: Int, index: Int) {
        supportFragmentManager.beginTransaction()
            .add(
                containerId,
                SharedEventFragment.newInstance(index),
                SharedEventFragment.TAG
            )
            .commit()
    }
}