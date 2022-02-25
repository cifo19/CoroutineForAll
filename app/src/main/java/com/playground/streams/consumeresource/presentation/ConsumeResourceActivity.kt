package com.playground.streams.consumeresource.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.playground.streams.R
import com.playground.streams.databinding.ActivityConsumeResourceBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConsumeResourceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConsumeResourceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConsumeResourceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            showConsumeResourcesFragment()
        }
    }

    private fun showConsumeResourcesFragment() {
        supportFragmentManager.beginTransaction()
            .add(
                R.id.fragmentContainer,
                ConsumeResourceFragment.newInstance(),
                ConsumeResourceFragment.TAG
            )
            .commit()
    }
}
