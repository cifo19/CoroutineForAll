package com.playground.streams

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.playground.streams.consumeresource.presentation.ConsumeResourceFragment
import com.playground.streams.databinding.ActivityMainBinding
import com.playground.streams.presentation.SampleFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonShowPresentation.setOnClickListener {
            showSampleFragment()
        }
        binding.buttonShowConsumeResource.setOnClickListener {
            showConsumeResourcesFragment()
        }
    }

    private fun showSampleFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, SampleFragment.newInstance(), SampleFragment.TAG)
            .addToBackStack(SampleFragment.TAG)
            .commit()
    }

    private fun showConsumeResourcesFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, ConsumeResourceFragment.newInstance(), ConsumeResourceFragment.TAG)
            .addToBackStack(SampleFragment.TAG)
            .commit()
    }
}
