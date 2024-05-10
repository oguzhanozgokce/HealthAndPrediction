package com.oguzhanozgokce.healthandprediction.ui.profile.login

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.oguzhanozgokce.healthandprediction.R
import com.oguzhanozgokce.healthandprediction.databinding.FragmentLoginBinding

//Oguzhan123.

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        googleStart()
        return binding.root
    }

    private  fun googleStart(){
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.registerId.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_sigupFragment)
        }

        binding.loginButtonId.setOnClickListener {
            val email = binding.emailLoginEdittextId.text.toString()
            val password = binding.editTextPasswordId.text.toString()
            if (checkAllFields()) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(requireActivity()) { task ->
                        if (task.isSuccessful) {
                            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                        } else {
                            binding.emailId.error = "Invalid email or password"
                            binding.passwordTextInputLayout.error = "Invalid email or password"
                        }
                    }
            }
        }

        binding.googleButtonId.setOnClickListener {
            signIn()
        }

        binding.forgotPasswordId.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }
    }
    private fun checkAllFields(): Boolean {
        val email = binding.emailLoginEdittextId.text.toString()
        val password = binding.editTextPasswordId.text.toString()
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

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val data: Intent? = result.data
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                handleResult(task)
            } catch (e: ApiException) {
                Toast.makeText(requireContext(), "Google sign in failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleResult(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful) {
            val account:GoogleSignInAccount? = task.result
            if (account != null) {
                updateUI(account)
            }
        }
        else{
            Toast.makeText(requireContext(), "Google sign in failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    val fullName = account.displayName
                    val email = account.email
                    val userId = auth.currentUser?.uid

                    // Create a new user with full name, email, and ID
                    val user = hashMapOf(
                        "id" to userId,
                        "fullName" to fullName,
                        "email" to email
                    )
                    // Add a new document with the current user's ID
                    val db = FirebaseFirestore.getInstance()
                    db.collection("users")
                        .document(userId!!)
                        .set(user)
                        .addOnSuccessListener {
                            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(requireContext(), "Error adding document: $e", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    Toast.makeText(requireContext(), "Google sign in failed", Toast.LENGTH_SHORT).show()
                }
            }
    }


}
