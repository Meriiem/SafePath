package com.example.safepathproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.safepath.ContactListActivity
import com.example.safepath.LoginActivity
import com.example.safepath.MapActivity
import com.example.safepath.R
import com.example.safepath.SettingsActivity
import com.example.safepath.ThreatDetectionActivity
import com.example.safepath.VoiceActivationActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this) // Ensure Firebase is initialized
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        // Button references
        val btnMap = findViewById<Button>(R.id.btn_map)
        val btnThreatDetection = findViewById<Button>(R.id.btn_threat_detection)
        val btnVoiceActivation = findViewById<Button>(R.id.btn_voice_activation)
        val btnSettings = findViewById<Button>(R.id.btn_settings)
        val btnContacts = findViewById<Button>(R.id.btn_contacts)

        // Check if the user is authenticated
        if (auth.currentUser == null) {
            Toast.makeText(this, "Please log in to access features", Toast.LENGTH_LONG).show()
            // Redirect to LoginActivity or equivalent
            startActivity(Intent(this, LoginActivity::class.java))
            finish() // Close this activity to prevent user from coming back
        }

        // Button functionality
        btnMap.setOnClickListener {
            startActivity(Intent(this, MapActivity::class.java))
        }

        btnThreatDetection.setOnClickListener {
            startActivity(Intent(this, ThreatDetectionActivity::class.java))
        }

        btnVoiceActivation.setOnClickListener {
            startActivity(Intent(this, VoiceActivationActivity::class.java))
        }

        btnSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        btnContacts.setOnClickListener {
            startActivity(Intent(this, ContactListActivity::class.java))
        }
    }
}
