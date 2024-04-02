package com.oguzhanozgokce.healthandprediction.ui.pharmacy

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.oguzhanozgokce.healthandprediction.adaptor.PharmacyListAdapter
import com.oguzhanozgokce.healthandprediction.data.repos.PharmacyRepo
import com.oguzhanozgokce.healthandprediction.databinding.FragmentPharmacyListBinding


class PharmacyListFragment : Fragment() {
    private lateinit var binding: FragmentPharmacyListBinding
    private lateinit var viewModel: PharmacyViewModel
    private lateinit var pharmacyListAdapter: PharmacyListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPharmacyListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = PharmacyRepo()
        val viewModelFactory = PharmacyViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(PharmacyViewModel::class.java)

        val args = arguments
        val latitude: Double = args?.getFloat("latitude", 0f)?.toDouble() ?: 0.0
        val longitude: Double = args?.getFloat("longitude", 0f)?.toDouble() ?: 0.0

        // Observe changes in pharmacies data
        viewModel.pharmacies.observe(viewLifecycleOwner) { pharmacyResponse ->
            // Update RecyclerView adapter with new data
            pharmacyListAdapter = PharmacyListAdapter(pharmacyResponse.result) { pharmacy ->
                // Handle item click here if needed
            }
            binding.recyclerviewPharmacyListId.adapter = pharmacyListAdapter
        }

        // Observe loading state
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                // Yükleme başladığında Toast mesajı göster
                Toast.makeText(requireContext(), "Veriler yükleniyor...", Toast.LENGTH_SHORT).show()
            } else {
                // Yükleme tamamlandığında Toast mesajı göster
                Toast.makeText(requireContext(), "Veriler başarıyla yüklendi.", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        // Observe error messages
        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            // Handle error messages if needed
        }
        // Fetch nearby pharmacies data
        viewModel.getNearbyPharmacies("$latitude,$longitude")
            .also { response ->
                // Handle the response here
                if (response != null) {
                    // Log the response
                    Log.d("Pharmacies data received", response.toString())
                } else {
                    // Log an error message if response is null
                    Log.e("Error", "Pharmacies response is null")
                }
            }

    }
}
