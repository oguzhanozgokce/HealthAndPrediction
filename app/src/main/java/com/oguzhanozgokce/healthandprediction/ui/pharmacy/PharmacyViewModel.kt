package com.oguzhanozgokce.healthandprediction.ui.pharmacy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oguzhanozgokce.healthandprediction.api.pharmacyAPI.PharmacyAPI
import com.oguzhanozgokce.healthandprediction.model.modelPharmacy.PharmacyResponse
import com.oguzhanozgokce.healthandprediction.repository.PharmacyRepo
import kotlinx.coroutines.launch

class PharmacyViewModel(private val repository: PharmacyRepo) : ViewModel() {

    private val _pharmacies = MutableLiveData<PharmacyResponse>()
    val pharmacies: LiveData<PharmacyResponse>
        get() = _pharmacies

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    fun getNearbyPharmacies(address: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.getNearbyPharmacies(address, PharmacyAPI.API_KEY)
                _pharmacies.value = response
            } catch (e: Exception) {
                _errorMessage.value = "Error fetching nearby pharmacies: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
