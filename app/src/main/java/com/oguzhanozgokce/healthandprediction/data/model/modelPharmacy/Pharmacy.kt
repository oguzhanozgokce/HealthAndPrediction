package com.oguzhanozgokce.healthandprediction.data.model.modelPharmacy

data class Pharmacy(
    val name: String,
    val dist: String,
    val address: String,
    val city: String,
    val district: String,
    val phone: String,
    val pharmacyDutyStart: String?,
    val pharmacyDutyEnd: String?,
    val loc: String // Yer bilgisi tek bir String olarak alınıyor
)
