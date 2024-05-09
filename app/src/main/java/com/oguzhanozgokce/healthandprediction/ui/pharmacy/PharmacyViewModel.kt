package com.oguzhanozgokce.healthandprediction.ui.pharmacy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oguzhanozgokce.healthandprediction.data.model.modelPharmacy.Pharmacy
import com.oguzhanozgokce.healthandprediction.data.repos.PharmacyRepo

class PharmacyViewModel(private val pharmacyRepo: PharmacyRepo) : ViewModel(){
    private val _pharmacies = MutableLiveData<List<Pharmacy>>()
    val pharmacies: LiveData<List<Pharmacy>>
        get() = _pharmacies

    suspend fun getPharmacies() {
        val response = pharmacyRepo.getPharmacies()
        _pharmacies.postValue(response.result)
    }

}