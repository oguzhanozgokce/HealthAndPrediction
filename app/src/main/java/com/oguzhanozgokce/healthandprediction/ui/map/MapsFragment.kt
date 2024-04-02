package com.oguzhanozgokce.healthandprediction.ui.map

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.oguzhanozgokce.healthandprediction.R
import com.oguzhanozgokce.healthandprediction.databinding.FragmentMapsBinding
import java.util.Locale

class MapsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var mMap: GoogleMap
    private val LOCATION_PERMISSION_REQUEST_CODE = 1001
    private lateinit var binding: FragmentMapsBinding
    private var currentLat: Double = 0.0
    private var currentLng: Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map_container) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.floatingActionButton.setOnClickListener {
            showInformationPopup()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true
        checkLocationPermissionAndProceed()
    }

    private fun checkLocationPermissionAndProceed() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            showLocationOnMap()
        } else {
            requestLocationPermission()
        }
    }

    private fun showLocationOnMap() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
            fusedLocationProviderClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        currentLat = location.latitude
                        currentLng = location.longitude
                        val latLng = LatLng(currentLat, currentLng)
                        mMap.addMarker(MarkerOptions().position(latLng).title("Current Location"))
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))

                        mMap.setOnMapClickListener { newLatLng ->
                            currentLat = newLatLng.latitude
                            currentLng = newLatLng.longitude
                            mMap.clear()
                            val head = "Selected Location"
                            val markerOptions = MarkerOptions().position(newLatLng).title(head)
                            mMap.addMarker(markerOptions)

                            val address = getAddressFromLatLng(newLatLng)
                            address?.let { showConfirmationDialog(it) }
                        }
                    }
                }
                .addOnFailureListener { e -> // Hata durumunda loglama eklendi
                    Log.e("MapsFragment", "addOnSuccessListener error: ${e.message}")
                }
        } else {
            requestLocationPermission()
        }
    }
    private fun requestLocationPermission() {
        requestPermissions(
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showLocationOnMap()
            }
        }
    }

    private fun getAddressFromLatLng(latLng: LatLng): String? {
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
        return if (!addresses.isNullOrEmpty()) {
            addresses[0].locality + ", " + addresses[0].subAdminArea
        } else {
            null
        }
    }
    private fun showConfirmationDialog(address: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Konum Onayı")
            .setMessage("Seçtiğiniz konumu kaydetmek istiyor musunuz?\nAdres: $address")
            .setPositiveButton("Evet") { dialog, which ->
                // Kullanıcı "Evet" dediğinde yapılacak işlemler
                navigateToPharmacyList(currentLat, currentLng, address)
            }
            .setNegativeButton("Hayır") { dialog, which ->
                // Kullanıcı "Hayır" dediğinde yapılacak işlemler
            }
            .show()
    }
    private fun navigateToPharmacyList(latitude: Double, longitude: Double, address: String) {
        // Diğer fragmenta geçiş yaparken, adres bilgisini ve koordinatları argüman olarak iletebilirsiniz
        Log.e("Navigation Info", "Latitude: $latitude, Longitude: $longitude, Address: $address")
        val action = MapsFragmentDirections.actionMapsFragmentToPharmacyListFragment(latitude.toFloat(), longitude.toFloat(), address)
        findNavController().navigate(action)
    }

    private fun showInformationPopup() {
        AlertDialog.Builder(requireContext())
            .setTitle("Bilgilendirme")
            .setMessage("Lütfen bir konum seçin. Seçiminizi yaptıktan sonra 'Devam' düğmesine tıklayın ve en yakın nöbetçi eczaneleri görmek için devam edin.")
            .setPositiveButton("Devam") { dialog, which ->
                // Kullanıcı "Devam" dediğinde yapılacak işlemler
                findNearestPharmacies()
            }
            .setNegativeButton("İptal") { dialog, which ->
                // Kullanıcı "İptal" dediğinde yapılacak işlemler
            }
            .show()
    }

    private fun findNearestPharmacies() {
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        val addresses: MutableList<Address>? = geocoder.getFromLocation(currentLat, currentLng, 1)
        val address: String = addresses!!.firstOrNull()?.getAddressLine(0) ?: "Unknown Address"

        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setTitle("Selected Location Information")
        alertDialogBuilder.setMessage("Selected Location:\n$address")
        alertDialogBuilder.setPositiveButton("Continue") { dialog, which ->
            val action = MapsFragmentDirections.actionMapsFragmentToPharmacyListFragment(currentLat.toFloat(), currentLng.toFloat(), address)
            findNavController().navigate(action)
        }
        alertDialogBuilder.show()
    }
}