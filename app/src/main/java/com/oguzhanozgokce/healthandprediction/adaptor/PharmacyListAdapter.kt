package com.oguzhanozgokce.healthandprediction.adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.oguzhanozgokce.healthandprediction.R
import com.oguzhanozgokce.healthandprediction.model.modelPharmacy.Pharmacy

class PharmacyListAdapter(
    private val pharmacies: List<Pharmacy>,
    private val onItemClickListener: (Pharmacy) -> Unit
) : RecyclerView.Adapter<PharmacyListAdapter.PharmacyViewHolder>() {

    inner class PharmacyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView_pharmacy_icon_Id)
        private val pharmacyNameTextView: TextView = itemView.findViewById(R.id.name_Id)
        private val pharmacyAddressTextView: TextView = itemView.findViewById(R.id.textView_address)
        private val pharmacyCityTextView: TextView = itemView.findViewById(R.id.textView_city)
        private val pharmacyDistrictTextView: TextView = itemView.findViewById(R.id.textView_district)
        private val pharmacyPhoneTextView: TextView = itemView.findViewById(R.id.textView_phone_Id)
        //private val pharmacyDutyTimeTextView: TextView = itemView.findViewById(R.id.)
        //private val pharmacyDistanceTextView: TextView = itemView.findViewById(R.id.di)

        fun bind(item: Pharmacy, clickListener: (Pharmacy) -> Unit) {
            pharmacyNameTextView.text = item.name
            pharmacyAddressTextView.text = item.address
            pharmacyCityTextView.text = item.city
            pharmacyDistrictTextView.text = item.district
            pharmacyPhoneTextView.text = item.phone

            itemView.setOnClickListener { clickListener(item) }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PharmacyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_pharmacy, parent, false)
        return PharmacyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PharmacyViewHolder, position: Int) {
        holder.bind(pharmacies[position], onItemClickListener)
    }

    override fun getItemCount(): Int {
        return pharmacies.size
    }
}