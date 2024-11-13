package com.example.safepath

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.safepathproject.MainActivity

class OnboardingActivity : AppCompatActivity() {

    private val LOCATION_PERMISSION_REQUEST_CODE = 1
    private val MICROPHONE_PERMISSION_REQUEST_CODE = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        val btnFinish = findViewById<Button>(R.id.btn_finish_onboarding)
        val btnLocationPermission = findViewById<Button>(R.id.btn_location_permission)
        val btnMicrophonePermission = findViewById<Button>(R.id.btn_microphone_permission)

        // Finish onboarding and proceed to the MainActivity
        btnFinish.setOnClickListener {
            // Move to MainActivity
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        // Request Location Permission
        btnLocationPermission.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            }
        }

        // Request Microphone Permission
        btnMicrophonePermission.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), MICROPHONE_PERMISSION_REQUEST_CODE)
            }
        }
    }

    // Handle permission request results
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted for location
                    // You can now use location-based features
                }
            }
            MICROPHONE_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted for microphone
                    // You can now use the microphone for audio-based features
                }
            }
        }
    }
}
