package com.oguzhanozgokce.healthandprediction.ui.map

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
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
            findNearestPharmacies()
        }


    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // Enable zoom controls
        mMap.uiSettings.isZoomControlsEnabled = true
        checkLocationPermissionAndProceed()
        showInformationPopup()
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

                        // new location
                        mMap.setOnMapClickListener { newLatLng ->
                            currentLat = newLatLng.latitude
                            currentLng = newLatLng.longitude
                            mMap.clear()
                            val head = "New Location"
                            val markerOptions = MarkerOptions().position(newLatLng).title(head)
                            mMap.addMarker(markerOptions)
                        }
                    } else {
                        Log.d("MapsFragment", "Location not available")
                    }
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
            } else {
                AlertDialog.Builder(requireContext())
                    .setTitle("Location Permission Denied")
                    .setMessage("Location permission denied. Do you want to manually select location?")
                    .setPositiveButton("Yes") { dialog, which ->
                        navigateToDefaultLocation()
                    }
                    .setNegativeButton("No") { dialog, which ->
                        navigateToDefaultLocation()
                    }
                    .show()
            }
        }
    }

    private fun navigateToDefaultLocation() {
        // Default location: Ankara, Turkey
        val defaultLocation = LatLng(39.9334, 32.8597)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 10f))
    }

    private fun showInformationPopup() {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setTitle("Bilgilendirme")
        alertDialogBuilder.setMessage("Lütfen bir konum seçin. Seçiminizi yaptıktan sonra 'Devam' düğmesine tıklayın ve en yakın nöbetçi eczaneleri görmek için devam edin.")
        alertDialogBuilder.setPositiveButton("Devam") { dialog, which ->
            // Kullanıcı 'Devam' düğmesine tıkladığında yapılacak işlemler
            // Örneğin, en yakın nöbetçi eczaneleri bulmak için bir fonksiyonu çağırabilirsiniz
            findNearestPharmacies()
        }
        alertDialogBuilder.setNegativeButton("İptal") { dialog, which ->
            // Kullanıcı 'İptal' düğmesine tıkladığında yapılacak işlemler
            // Örneğin, popup'ı kapatmak için bir şeyler yapabilirsiniz
        }
        alertDialogBuilder.show()
    }

    private fun findNearestPharmacies() {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setTitle("Mevcut Konum Bilgileri")
        alertDialogBuilder.setMessage("Şu anda bulunduğunuz konumun bilgileri:\nLatitude: $currentLat\nLongitude: $currentLng")
        alertDialogBuilder.setPositiveButton("Tamam") { dialog, which ->
            val latitude = currentLat
            val longitude = currentLng

            // Ardından, bu eczaneleri bir fragment'a göndermek için bir action çağrısı yapabilirsiniz
            // Örneğin:
            val action = MapsFragmentDirections.actionMapsFragmentToPharmacyListFragment(latitude.toFloat(), longitude.toFloat()
            )
            findNavController().navigate(action)
        }
        alertDialogBuilder.show()
    }
}
