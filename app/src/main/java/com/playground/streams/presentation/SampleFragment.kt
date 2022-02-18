package com.playground.streams.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import com.playground.streams.databinding.FragmentSampleBinding
import com.playground.streams.presentation.data.UIEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SampleFragment : Fragment() {

    private var _binding: FragmentSampleBinding? = null
    private val binding get() = _binding!!

    private val sampleViewModel: SampleViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSampleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeLiveData()
        observeFlow()
        setClickListeners()
    }

    // region ObserveLiveData
    private fun observeLiveData() = with(sampleViewModel) {
        sampleEventLiveData.observe(viewLifecycleOwner) {
            Snackbar.make(requireView(), it.message, Snackbar.LENGTH_SHORT).show()
        }
        sampleStateLiveData.observe(viewLifecycleOwner) {
            binding.textViewSampleName.text = it.message
        }
    }
    // endregion

    // region observeFlow
    private fun observeFlow() = with(sampleViewModel) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                sampleEventFlow.collect {
                    Snackbar.make(requireView(), it.message, Snackbar.LENGTH_SHORT).show()
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                sampleStateStateFlow.collect {
                    binding.textViewSampleName.text = it.message
                }
            }
        }
    }
    // endregion

    private fun setClickListeners() = with(binding) {
        buttonEvent.setOnClickListener {
            sampleViewModel.onUIEvent(UIEvent.TRIGGER_ONE_SHOW_EVENT)
        }
        buttonState.setOnClickListener {
            sampleViewModel.onUIEvent(UIEvent.UPDATE_STATE)
        }
    }

    companion object {

        const val TAG = "SampleFragment"

        fun newInstance(): SampleFragment = SampleFragment()
    }
}
