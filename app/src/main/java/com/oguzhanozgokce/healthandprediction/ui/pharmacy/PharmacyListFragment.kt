package com.oguzhanozgokce.healthandprediction.ui.pharmacy

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.oguzhanozgokce.healthandprediction.adaptor.PharmacyListAdapter
import com.oguzhanozgokce.healthandprediction.databinding.FragmentPharmacyListBinding
import com.oguzhanozgokce.healthandprediction.repository.PharmacyRepo


class PharmacyListFragment : Fragment() {
    private lateinit var binding: FragmentPharmacyListBinding
    private lateinit var viewModel: PharmacyViewModel
    private lateinit var pharmacyListAdapter: PharmacyListAdapter // Sınıf seviyesinde tanımlanır

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = PharmacyRepo()
        val viewModelFactory = PharmacyViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(PharmacyViewModel::class.java)

        val args = arguments
        val latitude: Double = args?.getFloat("latitude", 0f)?.toDouble() ?: 0.0
        val longitude: Double = args?.getFloat("longitude", 0f)?.toDouble() ?: 0.0



        viewModel.pharmacies.observe(viewLifecycleOwner) { pharmacyResponse ->
            // Handle pharmacyResponse, update RecyclerView adapter if necessary
        }

        viewModel.getNearbyPharmacies(latitude, longitude) // Latitude ve longitude değerlerini double tipine çevirerek gönderiyoruz

        // RecyclerView ayarları
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        pharmacyListAdapter = PharmacyListAdapter(emptyList()) { selectedPharmacy ->
            // Burada seçilen eczanenin işlenmesi veya işlemesi gereken adımları gerçekleştirin
        }
        binding.recyclerviewPharmacyListId.apply {
            adapter = pharmacyListAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false) // Dikey olarak düzenleme
            setHasFixedSize(true) // Performans için RecyclerView boyutunun sabit olduğunu belirtin
        }
    }
}
