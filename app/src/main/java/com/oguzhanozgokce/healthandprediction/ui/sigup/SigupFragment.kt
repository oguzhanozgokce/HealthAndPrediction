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
import com.google.firebase.firestore.FirebaseFirestore
import com.oguzhanozgokce.healthandprediction.databinding.FragmentSigupBinding


class SigupFragment : Fragment() {
    private lateinit var binding: FragmentSigupBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db : FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSigupBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.sigUpButtonId.setOnClickListener {
            val fullName = binding.nameEditTextSigUpId.text.toString()
            val email = binding.emailEditTextSigUpId.text.toString()
            val password = binding.passwordEditTextSigUpId.text.toString()
            if (checkAllFields()) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(requireActivity()) { task ->
                        if (task.isSuccessful) {
                            val userId = auth.currentUser?.uid // Kullanıcının ID'sini al
                            if (userId != null) {
                                val user = hashMapOf(
                                    "id" to userId,
                                    "full name" to fullName,
                                    "e_mail" to email
                                )
                                // Kullanıcının tam adını ve e-postasını veritabanına kaydet
                                db.collection("users").document(userId)
                                    .set(user)
                                    .addOnSuccessListener {
                                        auth.signOut()
                                        Toast.makeText(requireContext(), "Signup successful", Toast.LENGTH_SHORT).show()
                                        // LoginFragment'e geri dön
                                        findNavController().popBackStack()
                                    }
                                    .addOnFailureListener { e ->
                                        Toast.makeText(requireContext(), "Error saving user info: ${e.message}", Toast.LENGTH_SHORT).show()
                                    }
                            }
                        } else {
                            // Kayıt başarısız
                            Toast.makeText(requireContext(), "Signup failed", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }

    private fun checkAllFields(): Boolean {
        val fullName = binding.nameEditTextSigUpId.text.toString()
        val email = binding.emailEditTextSigUpId.text.toString()
        val password = binding.passwordEditTextSigUpId.text.toString()
        if (fullName.isEmpty()) {
            binding.nameTextInputLayoutId.error = "This field cannot be empty"
            return false
        }
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