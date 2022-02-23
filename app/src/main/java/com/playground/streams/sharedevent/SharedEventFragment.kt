package com.playground.streams.sharedevent

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.playground.streams.databinding.FragmentSharedEventBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SharedEventFragment : Fragment() {

    private var _binding: FragmentSharedEventBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSharedEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textViewSampleName.text = "Fragment ${requireArguments().getInt(INDEX_KEY)}"

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                sharedViewModel.sharedEvent.collect {
                    binding.textViewSampleName.text = it
                }
            }
        }

        sharedViewModel.singleEvent.observe(viewLifecycleOwner) {
            binding.textViewSampleName.text = it
        }

        sharedViewModel.liveEvent.observe(viewLifecycleOwner) {
            binding.textViewSampleName.text = it
        }
    }

    companion object {

        private const val INDEX_KEY = "index"
        const val TAG = "SharedEventFragment"

        fun newInstance(index: Int) = SharedEventFragment().apply {
            arguments = bundleOf(INDEX_KEY to index)
        }
    }
}
