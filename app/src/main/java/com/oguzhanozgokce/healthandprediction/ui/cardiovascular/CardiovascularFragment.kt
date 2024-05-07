package com.oguzhanozgokce.healthandprediction.ui.cardiovascular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.oguzhanozgokce.healthandprediction.R
import com.oguzhanozgokce.healthandprediction.data.model.cardiovascular.UserInput
import com.oguzhanozgokce.healthandprediction.data.repos.CardiovascularRepo
import com.oguzhanozgokce.healthandprediction.databinding.FragmentCardiovascularBinding


class CardiovascularFragment : Fragment() {
    private lateinit var binding: FragmentCardiovascularBinding
    private val repository = CardiovascularRepo()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCardiovascularBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    fun getData() {
        val age = binding.ageEdittextId.text.toString()
        val height = binding.heightEdittextId.text.toString()
        val weight = binding.weightEdittextId.text.toString()
        val gender = binding.genderEdittextId.text.toString()
        val sBloodPressure = binding.sBloodPressureEdittextId.text.toString()
        val dBloodPressure = binding.dBloodPressureEdittextId.text.toString()

        val glucoseRadioGroup = binding.glucoseRadioGroupId
        var glucoseLevel = ""
        when (glucoseRadioGroup.checkedRadioButtonId) {
            R.id.glucoseNormalRadio -> glucoseLevel = "Normal"
            R.id.glucoseAboveNormalRadio -> glucoseLevel = "Above Normal"
            R.id.glucoseWellAboveNormalRadio -> glucoseLevel = "Well Above Normal"
        }

        val smokingRadioGroup = binding.smokingRadioGroupId
        var smokingHabit = ""
        when (smokingRadioGroup.checkedRadioButtonId) {
            R.id.yesSmokingRadio -> smokingHabit = "Yes"
            R.id.noSmokingRadio -> smokingHabit = "No"
        }

        val alcoholRadioGroup = binding.alcoholRadioGroupId
        var alcoholConsumption = ""
        when (alcoholRadioGroup.checkedRadioButtonId) {
            R.id.yesAlcoholRadio -> alcoholConsumption = "Yes"
            R.id.noAlcoholRadio -> alcoholConsumption = "No"
        }

        val activityRadioGroup = binding.activityRadioGroupId
        var physicalActivity = ""
        when (activityRadioGroup.checkedRadioButtonId) {
            R.id.yesActivityRadio  -> physicalActivity = "Active"
            R.id.noActivityRadio-> physicalActivity = "Not Active"
        }

        val cardioRadioGroup = binding.cardioRadioGroupId
        var cardioActivity = ""
        when (cardioRadioGroup.checkedRadioButtonId) {
            R.id.yesCardioRadio -> cardioActivity = "Yes"
            R.id.noCardioRadio -> cardioActivity = "No"
        }

        if (age.isEmpty()|| height.isEmpty() || weight.isEmpty() || gender.isEmpty() || sBloodPressure.isEmpty() || dBloodPressure.isEmpty() || glucoseLevel.isEmpty() || smokingHabit.isEmpty() || alcoholConsumption.isEmpty() || physicalActivity.isEmpty() || cardioActivity.isEmpty()) {
            Toast.makeText(requireContext(), "Lütfen tüm bilgileri doldurun", Toast.LENGTH_SHORT).show()
            return
        }
        val ageInt = age.toIntOrNull()
        val heightDouble = height.toDoubleOrNull()
        val weightDouble = weight.toDoubleOrNull()
        val sBloodPressureDouble = sBloodPressure.toDoubleOrNull()
        val dBloodPressureDouble = dBloodPressure.toDoubleOrNull()

        if (ageInt == null || heightDouble == null || weightDouble == null || sBloodPressureDouble == null || dBloodPressureDouble == null) {
            Toast.makeText(requireContext(), "Lütfen geçerli sayısal değerler girin", Toast.LENGTH_SHORT).show()
            return
        }

        val userInput = UserInput(
            ageInt,
            heightDouble,
            weightDouble,
            gender,
            sBloodPressureDouble,
            dBloodPressureDouble,
            glucoseLevel,
            smokingHabit,
            alcoholConsumption,
            physicalActivity,
            cardioActivity
        )
        repository.sendUserInputToModel(userInput)
    }

}