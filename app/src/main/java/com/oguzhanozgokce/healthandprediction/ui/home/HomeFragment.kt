package com.oguzhanozgokce.healthandprediction.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.oguzhanozgokce.healthandprediction.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageViewNewsId.setOnClickListener {
            navigateToNewsFragment()
        }

        binding.imageViewPharmacyId.setOnClickListener {
            navigateToMapFragment()
        }

        binding.imageViewCardiologyId.setOnClickListener {
            navigateToCardiovascularFragment()
        }
        binding.imageViewPedometerId.setOnClickListener {
            navigateToPedometerFragment()
        }
    }

    private fun navigateToMapFragment() {
        val action = HomeFragmentDirections.actionHomeFragmentToMapsFragment()
        findNavController().navigate(action)
    }

    private fun navigateToNewsFragment() {
        val action = HomeFragmentDirections.actionHomeFragmentToNewsFragment()
        findNavController().navigate(action)
    }

    private fun navigateToCardiovascularFragment() {
        val action = HomeFragmentDirections.actionHomeFragmentToCardiovascularFragment()
        findNavController().navigate(action)
    }
    private fun navigateToPedometerFragment() {
        val action = HomeFragmentDirections.actionHomeFragmentToStepCounterFragment()
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
