package com.oguzhanozgokce.healthandprediction.ui.login

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.oguzhanozgokce.healthandprediction.R
import com.oguzhanozgokce.healthandprediction.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.registerId.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_sigupFragment)
        }

        binding.loginButtonId.setOnClickListener {
            val email = binding.emailLoginEdittextId.text.toString()
            val password = binding.editTextPasswordId.text.toString()

            if (checkAllFields(email, password)) {
                signInUser(email, password)
            }
        }
    }

    private fun signInUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Giriş başarılı
                    Toast.makeText(requireContext(), "Login successful", Toast.LENGTH_SHORT).show()
                    // HomeFragment'e git
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                } else {
                    // Giriş başarısız
                    Toast.makeText(requireContext(), "Login failed", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun checkAllFields(email: String, password: String): Boolean {
        if (email.isEmpty()) {
            binding.emailId.error = "This field cannot be empty"
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailId.error = "Invalid email address"
            return false
        }
        if (password.isEmpty()) {
            binding.passwordTextInputLayout.error = "This field cannot be empty"
            return false
        }
        if (password.length < 6) {
            binding.passwordTextInputLayout.error = "Password must be at least 6 characters"
            return false
        }
        return true
    }
}
