package com.oguzhanozgokce.healthandprediction.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oguzhanozgokce.healthandprediction.data.model.modelPharmacy.Pharmacy
import com.oguzhanozgokce.healthandprediction.data.repos.PharmacyRepo
import kotlinx.coroutines.launch

class MapsViewModel(private val pharmacyRepo: PharmacyRepo) : ViewModel() {
    private val _pharmacies = MutableLiveData<List<Pharmacy>>()
    val pharmacies: LiveData<List<Pharmacy>>
        get() = _pharmacies

    fun getPharmacies() {
        viewModelScope.launch {
            val response = pharmacyRepo.getPharmacies()
            _pharmacies.postValue(response.result)
        }
    }
}