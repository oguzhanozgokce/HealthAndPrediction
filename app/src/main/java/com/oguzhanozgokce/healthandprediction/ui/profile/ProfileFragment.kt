package com.oguzhanozgokce.healthandprediction.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.oguzhanozgokce.healthandprediction.R
import com.oguzhanozgokce.healthandprediction.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var currentUser: FirebaseUser
    private lateinit var firestore: FirebaseFirestore

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        currentUser = auth.currentUser!!

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set user information
        binding.profileName.text = currentUser.displayName
        binding.profileEmail.text = currentUser.email
        Glide.with(this)
            .load(currentUser.photoUrl)
            .into(binding.profileImage)

        // Set delete account button click listener
        binding.deleteAccountButton.setOnClickListener {
            val userId = currentUser.uid
            currentUser.delete()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        firestore.collection("users").document(userId).delete()
                            .addOnSuccessListener {
                                Toast.makeText(context, "Hesap ve ilgili veriler başarıyla silindi.", Toast.LENGTH_SHORT).show()
                                findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(context, "Veriler silinirken bir hata oluştu: $e", Toast.LENGTH_SHORT).show()
                            }
                    } else {
                        Toast.makeText(context, "Hesap silinirken bir hata oluştu.", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}