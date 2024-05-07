package com.oguzhanozgokce.healthandprediction.ui.stepCounter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.oguzhanozgokce.healthandprediction.databinding.FragmentStepCounterBinding

class StepCounterFragment : Fragment() {
    private lateinit var binding: FragmentStepCounterBinding
    private lateinit var viewModel: StepCounterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStepCounterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ViewModel'i oluştur
        val factory = StepCounterViewModelFactory(requireContext(), 10000)
        viewModel = ViewModelProvider(this, factory).get(StepCounterViewModel::class.java)

        // LiveData'yı izle ve UI'ı güncelle
        viewModel.progress.observe(viewLifecycleOwner) { progress ->
            binding.progressBar.progress = progress
        }
    }
}