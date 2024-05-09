package com.oguzhanozgokce.healthandprediction.ui.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.oguzhanozgokce.healthandprediction.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var image: ImageView
    private val GALLERY_REQUEST_CODE = 1001
    val storage = FirebaseStorage.getInstance()
    val storageRef = storage.reference
    private lateinit var db : FirebaseFirestore


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        db = FirebaseFirestore.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userId = FirebaseAuth.getInstance().currentUser?.uid

        // Load the image from SharedPreferences with user-specific key
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val imageUrl = sharedPref.getString(userId, null)
        if (imageUrl != null) {
            Glide.with(this).load(imageUrl).into(binding.profileImageId)
        }


        binding.imageViewNewsId.setOnClickListener {
            navigateToNewsFragment()
        }
        binding.imageViewPharmacyId.setOnClickListener {
            navigateToPharmacyListFragment()
        }
        binding.imageViewCardiologyId.setOnClickListener {
            navigateToCardiovascularFragment()
        }
        binding.imageViewPedometerId.setOnClickListener {
            navigateToPedometerFragment()
        }
        binding.profileImageId.setOnClickListener {
            openGallery()
        }
    }

    private fun navigateToPharmacyListFragment() {
        val action = HomeFragmentDirections.actionHomeFragmentToPharmacyListFragment()
        findNavController().navigate(action)
    }
    private fun navigateToNewsFragment() {
        val action = HomeFragmentDirections.actionHomeFragmentToNewsFragment()
        findNavController().navigate(action)
    }
    private fun navigateToCardiovascularFragment() {
        val action = HomeFragmentDirections.actionHomeFragmentToCardiovascularFragment()
        findNavController().navigate(action)
    }
    private fun navigateToPedometerFragment() {
        val action = HomeFragmentDirections.actionHomeFragmentToStepCounterFragment()
        findNavController().navigate(action)
    }
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val selectedImageUri = data?.data
            // Load the selected image into the ImageView
            binding.profileImageId.setImageURI(selectedImageUri)

            // Firebase Storage'a resmi yükle
            val imageRef = storageRef.child("images/${selectedImageUri?.lastPathSegment}")
            val uploadTask = selectedImageUri?.let { imageRef.putFile(it) }

            // Yükleme işlemi tamamlandığında URL'yi al ve Firestore'a kaydet
            uploadTask?.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                imageRef.downloadUrl
            }?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result

                    val userId = FirebaseAuth.getInstance().currentUser?.uid
                    val userDocument = db.collection("users").document(userId.toString())
                    userDocument.update("url", downloadUri.toString())

                    // Save the image URL to SharedPreferences with user-specific key
                    val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return@addOnCompleteListener
                    with (sharedPref.edit()) {
                        putString(userId, downloadUri.toString())
                        apply()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
