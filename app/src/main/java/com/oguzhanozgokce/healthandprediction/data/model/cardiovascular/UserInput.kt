package com.oguzhanozgokce.healthandprediction.data.model.cardiovascular

data class UserInput(
    val age: Int,
    val height: Double,
    val weight: Double,
    val gender: String,
    val sBloodPressure: Double,
    val dBloodPressure: Double,
    val glucoseLevel: String?,
    val smokingHabit: String?,
    val alcoholConsumption: String?,
    val physicalActivity: String?,
    val cardioActivity: String?
)