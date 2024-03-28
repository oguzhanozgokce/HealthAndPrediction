package com.oguzhanozgokce.healthandprediction.ui.sigup

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.oguzhanozgokce.healthandprediction.databinding.FragmentSigupBinding


class SignupFragment : Fragment() {
    private lateinit var binding: FragmentSigupBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSigupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.sigUpButtonId.setOnClickListener {
            val email = binding.emailEditTextSigUpId.text.toString()
            val password = binding.passwordEditTextSigUpId.text.toString()

            if (checkAllFields(email, password)) {
                signUpUser(email, password)
            }
        }
    }

    private fun signUpUser(email: String, password: String) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Kayıt başarılı
                    Toast.makeText(requireContext(), "Signup successful", Toast.LENGTH_SHORT).show()
                    // LoginFragment'e geri dön
                    findNavController().popBackStack()
                } else {
                    // Kayıt başarısız
                    Toast.makeText(requireContext(), "Signup failed", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun checkAllFields(email: String, password: String): Boolean {
        if (email.isEmpty()) {
            binding.emailTextInputLayoutId.error = "This field cannot be empty"
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailTextInputLayoutId.error = "Invalid email address"
            return false
        }
        if (password.isEmpty()) {
            binding.passwordTextInputLayoutId.error = "This field cannot be empty"
            return false
        }
        if (password.length < 6) {
            binding.passwordTextInputLayoutId.error = "Password must be at least 6 characters"
            return false
        }
        return true
    }
}