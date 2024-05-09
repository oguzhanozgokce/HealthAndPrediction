package com.oguzhanozgokce.healthandprediction.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oguzhanozgokce.healthandprediction.data.model.modelPharmacy.Pharmacy
import com.oguzhanozgokce.healthandprediction.databinding.ViewholderPharmacyBinding

class PharmacyListAdapter(
    private var pharmacies: List<Pharmacy>,
    private val onItemClickListener: (Pharmacy) -> Unit
) : RecyclerView.Adapter<PharmacyListAdapter.PharmacyViewHolder>() {

    class PharmacyViewHolder(val binding: ViewholderPharmacyBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PharmacyViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val binding = ViewholderPharmacyBinding.inflate(inflate,parent,false)
        return PharmacyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PharmacyViewHolder, position: Int) {
        with(holder.binding){
            textViewPharmacyNameId.text = pharmacies[position].name
            textViewAddress.text = pharmacies[position].address
            textViewPhoneId.text = pharmacies[position].phone
            textViewDistrict.text = pharmacies[position].dist

        }
    }

    override fun getItemCount(): Int {
        return pharmacies.size
    }

    fun updateData(newPharmacies: List<Pharmacy>) {
        pharmacies = newPharmacies
        notifyDataSetChanged()
    }
}