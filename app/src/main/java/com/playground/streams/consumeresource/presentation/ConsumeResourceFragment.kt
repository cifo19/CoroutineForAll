package com.playground.streams.consumeresource.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.playground.streams.consumeresource.presentation.data.ConsumeResourceUIState
import com.playground.streams.databinding.FragmentConsumeResourceBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ConsumeResourceFragment: Fragment() {

    private var _binding: FragmentConsumeResourceBinding? = null
    private val binding get() = _binding!!

    private val consumeResourceViewModel: ConsumeResourceViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConsumeResourceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        consumeResourceViewModel.getDetailAsFlow("Android")

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                consumeResourceViewModel.uiState.collect { uiState -> renderUIState(uiState) }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun renderUIState(uiState: ConsumeResourceUIState) {
        when(uiState) {
            is ConsumeResourceUIState.Content -> {
                binding.title.text = uiState.contentEntity.title
                binding.description.text = uiState.contentEntity.description
            }
            ConsumeResourceUIState.Loading -> {
                binding.title.text = "Loading"
            }
            is ConsumeResourceUIState.Failure -> {
                binding.title.text = "Failed"
                binding.description.text = ""
            }
        }
    }

    companion object {

        const val TAG = "ConsumeResourceFragment"

        fun newInstance(): ConsumeResourceFragment = ConsumeResourceFragment()
    }
}
