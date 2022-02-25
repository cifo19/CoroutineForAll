package com.playground.streams

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.playground.streams.consumeresource.presentation.ConsumeResourceActivity
import com.playground.streams.databinding.ActivityMainBinding
import com.playground.streams.presentation.PresentationActivity
import com.playground.streams.sharedevent.SharedEventActivity
import com.playground.streams.subjects.SubjectsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonShowPresentation.setOnClickListener {
            startActivity(Intent(this, PresentationActivity::class.java))
        }
        binding.buttonShowConsumeResource.setOnClickListener {
            startActivity(Intent(this, ConsumeResourceActivity::class.java))
        }
        binding.buttonShowSharedEvent.setOnClickListener {
            startActivity(Intent(this, SharedEventActivity::class.java))
        }
        binding.buttonSubjects.setOnClickListener {
            startActivity(Intent(this, SubjectsActivity::class.java))
        }
    }
}
