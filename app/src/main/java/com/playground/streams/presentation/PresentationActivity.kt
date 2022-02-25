package com.playground.streams.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.playground.streams.R
import com.playground.streams.databinding.ActivityPresentationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PresentationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPresentationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPresentationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            showSampleFragment()
        }
    }

    private fun showSampleFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, SampleFragment.newInstance(), SampleFragment.TAG)
            .commit()
    }
}