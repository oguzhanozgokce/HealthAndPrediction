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

        val newsImageView = binding.imageViewNewsId
        newsImageView.setOnClickListener {
            navigateToNewsFragment()
        }

        val pharmacyImageView = binding.imageViewPharmacyId
        pharmacyImageView.setOnClickListener {
            navigateToMapFragment()
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

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
