package com.oguzhanozgokce.healthandprediction.ui.pharmacy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oguzhanozgokce.healthandprediction.adaptor.PharmacyListAdapter
import com.oguzhanozgokce.healthandprediction.data.api.pharmacyAPI.PharmacyAPI
import com.oguzhanozgokce.healthandprediction.data.repos.PharmacyRepo
import com.oguzhanozgokce.healthandprediction.databinding.FragmentPharmacyListBinding
import kotlinx.coroutines.launch


class PharmacyListFragment : Fragment() {
    private lateinit var binding: FragmentPharmacyListBinding
    private lateinit var viewModel: PharmacyViewModel
    private lateinit var pharmacyListAdapter: PharmacyListAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPharmacyListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.recyclerviewPharmacyListId

        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager

        // ViewModel'i oluştur
        viewModel = PharmacyViewModel(PharmacyRepo(PharmacyAPI.pharmacyService))

        // Adapter'ı oluştur ve RecyclerView'a ata
        pharmacyListAdapter = PharmacyListAdapter(listOf()) { pharmacy ->
            Toast.makeText(context, "${pharmacy.name} eczanesine tıkladınız.", Toast.LENGTH_SHORT).show()
        }

        recyclerView.adapter = pharmacyListAdapter

        viewModel.pharmacies.observe(viewLifecycleOwner , Observer { pharmacies ->
            pharmacies?.let {
                pharmacyListAdapter.updateData(it)
            } ?: run {
                Toast.makeText(context, "Eczane listesi boş.", Toast.LENGTH_SHORT).show()
            }
        })

        lifecycleScope.launch {
            viewModel.getPharmacies()
        }

        binding.buttonMapsId.setOnClickListener {
            val action = PharmacyListFragmentDirections.actionPharmacyListFragmentToMapsFragment()
            findNavController().navigate(action)
        }
    }
}