package com.oguzhanozgokce.healthandprediction.ui.cardiovascular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.oguzhanozgokce.healthandprediction.R
import com.oguzhanozgokce.healthandprediction.databinding.FragmentCardiovascularBinding


class CardiovascularFragment : Fragment() {
    private lateinit var binding: FragmentCardiovascularBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCardiovascularBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Glucose seviyesi için RadioGroup'u bulma
        val glucoseRadioGroup = binding.glucoseRadioGroupId

        // RadioGroup içindeki RadioButton'ları kullanarak işlemler yapabilirsiniz
        glucoseRadioGroup.setOnCheckedChangeListener { radioGroup, checkedId ->
            when (checkedId) {
                R.id.glucoseNormalRadio -> {
                    // Normal seçildiğinde yapılacak işlemler
                }

                R.id.glucoseAboveNormalRadio -> {
                    // Above Normal seçildiğinde yapılacak işlemler
                }

                R.id.glucoseWellAboveNormalRadio -> {
                    // Well Above Normal seçildiğinde yapılacak işlemler
                }
            }
        }
    }
}