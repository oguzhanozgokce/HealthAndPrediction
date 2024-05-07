package com.oguzhanozgokce.healthandprediction.ui.profile

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
import com.oguzhanozgokce.healthandprediction.databinding.FragmentForgotPasswordBinding


class ForgotPasswordFragment : Fragment() {
    private lateinit var binding: FragmentForgotPasswordBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.forgotButtonId.setOnClickListener{
            val email = binding.emailForgotEdittextId.text.toString().trim()
            if(checkEmail()){
                auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        binding.emailForgotEdittextId.text?.clear()
                        binding.emailForgotEdittextId.error = "Email sent"
                        Toast.makeText(requireContext(), "An e-mail has been sent. Log in again with a new password", Toast.LENGTH_LONG).show()
                        findNavController().navigate(R.id.action_forgotPasswordFragment_to_loginFragment)
                    }else{
                        binding.emailForgotEdittextId.error = "Email not sent"
                    }
                }
            }
        }
    }

    private fun checkEmail(): Boolean{
        val email = binding.emailForgotEdittextId.text.toString().trim()
        if (email.isEmpty()) {
            binding.emailForgotEdittextId.error = "This field cannot be empty"
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailForgotEdittextId.error = "Invalid email address"
            return false
        }
        return true
    }
}