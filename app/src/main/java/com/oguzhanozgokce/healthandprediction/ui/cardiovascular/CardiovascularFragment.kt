package com.oguzhanozgokce.healthandprediction.ui.cardiovascular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
/**
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonStartId.setOnClickListener {
            val userInput = getUserInput()
            val result = repository.sendUserInputToModel(userInput)
            showResultInPopup(requireContext(), result)
        }
    }

    private fun getUserInput(): UserInput {
        val age = binding.ageEdittextId.text.toString().toInt()
        val height = binding.heightEdittextId.text.toString().toDouble()
        val weight = binding.weightEdittextId.text.toString().toDouble()
        val gender = binding.genderEdittextId.text.toString()
        val sBloodPressure = binding.sBloodPressureEdittextId.text.toString().toDouble()
        val dBloodPressure = binding.dBloodPressureEdittextId.text.toString().toDouble()
        val glucoseLevel = when (binding.glucoseRadioGroupId.checkedRadioButtonId) {
            R.id.glucoseNormalRadio -> "Normal"
            R.id.glucoseAboveNormalRadio -> "Above Normal"
            R.id.glucoseWellAboveNormalRadio -> "Well Above Normal"
            else -> null
        }
        val smokingHabit = when (binding.smokingRadioGroupId.checkedRadioButtonId) {
            R.id.yesSmokingRadio -> "Yes"
            R.id.noSmokingRadio -> "No"
            else -> null
        }
        val alcoholConsumption = when (binding.alcoholRadioGroupId.checkedRadioButtonId) {
            R.id.yesAlcoholRadio -> "Yes"
            R.id.noAlcoholRadio -> "No"
            else -> null
        }
        val physicalActivity = when (binding.activityRadioGroupId.checkedRadioButtonId) {
            R.id.yesActivityRadio -> "Yes"
            R.id.noActivityRadio -> "No"
            else -> null
        }
        val cardioActivity = when (binding.cardioRadioGroupId.checkedRadioButtonId) {
            R.id.yesCardioRadio -> "Yes"
            R.id.noCardioRadio -> "No"
            else -> null
        }

        return UserInput(
            age,
            height,
            weight,
            gender,
            sBloodPressure,
            dBloodPressure,
            glucoseLevel,
            smokingHabit,
            alcoholConsumption,
            physicalActivity,
            cardioActivity
        )
    }

    fun showResultInPopup(context: Context, result: FloatArray) {
        val alertDialog = AlertDialog.Builder(context)
            .setTitle("Model Result")
            .setMessage("The result from the model is: ${result[0]}")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        alertDialog.show()
    }
    */
}