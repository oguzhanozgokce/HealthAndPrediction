package com.oguzhanozgokce.healthandprediction

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.oguzhanozgokce.healthandprediction.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // NavController'a bir destination change listener ekliyoruz
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginFragment, R.id.sigupFragment, R.id.forgotPasswordFragment -> {
                    // Eğer hedef login veya signup sayfası ise, BottomNavigationView'ı gizle
                    binding.bottomNavigationView.visibility = View.GONE
                }
                else -> {
                    // Diğer durumlarda, BottomNavigationView'ı göster
                    binding.bottomNavigationView.visibility = View.VISIBLE
                }
            }
        }

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    // Home sayfasına yönlendirme yaparken, geri yığınından tüm hedefleri çıkar
                    navController.navigate(R.id.homeFragment, null, NavOptions.Builder()
                        .setPopUpTo(R.id.homeFragment, true)
                        .build())
                    true
                }
                R.id.profileFragment -> {
                    // Profile sayfasına yönlendirme yaparken, geri yığınından tüm hedefleri çıkar
                    navController.navigate(R.id.profileFragment, null, NavOptions.Builder()
                        .setPopUpTo(R.id.profileFragment, true)
                        .build())
                    true
                }
                else -> false
            }
        }
        checkStepCounterSensor()
    }

    // Geri düğmesine basıldığında çağrılır
    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp()
    }

    private fun checkStepCounterSensor() {
        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if (stepSensor == null) {
            // Cihaz bu sensörü desteklemiyor
            Log.e("StepCounter", "Device does not support step counter sensor")
        } else {
            // Cihaz bu sensörü destekliyor
            Log.e("StepCounter", "Device supports step counter sensor")
        }
    }
}