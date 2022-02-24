package com.playground.streams.subjects

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.playground.streams.databinding.ActivitySubjectsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SubjectsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySubjectsBinding

    private val subjectsViewModel: SubjectsViewModel by viewModels()
    private val sharedFlowViewModel: SharedFlowViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubjectsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeTimers()
        observeSubjects()
        observeFlows()
        renderView()
    }

    private fun observeTimers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                subjectsViewModel.time.collect {
                    setText(binding.textViewTimelineSubjects, it)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                sharedFlowViewModel.time.collect {
                    setText(binding.textViewTimelineSharedFlow, it)
                }
            }
        }
    }

    private fun observeSubjects() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                subjectsViewModel.behaviorType.collect {
                    when (it) {
                        is BehaviorType.Publish -> setText(binding.textView1, it.value)
                        is BehaviorType.Behavior -> setText(binding.textView2, it.value)
                        is BehaviorType.Replay -> setText(binding.textView3, it.value)
                    }
                }
            }
        }
    }

    private fun observeFlows() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                sharedFlowViewModel.behaviorType.collect {
                    when (it) {
                        is BehaviorType.Publish -> setText(binding.textView4, it.value)
                        is BehaviorType.Behavior -> setText(binding.textView5, it.value)
                        is BehaviorType.Replay -> setText(binding.textView6, it.value)
                    }
                }
            }
        }
    }

    private fun renderView() {
        binding.startObservingSubjects.setOnClickListener {
            subjectsViewModel.startObserving()
        }
        binding.startObservingSharedFlows.setOnClickListener {
            sharedFlowViewModel.startObserving()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setText(textView: TextView, num: Int) {
        val oldText = textView.text
        textView.text = "$oldText $num "
    }
}
