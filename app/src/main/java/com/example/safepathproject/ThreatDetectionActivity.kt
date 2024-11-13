package com.example.safepath

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.ProgressBar
import android.widget.RadioGroup
import android.widget.RadioButton
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth

class ThreatDetectionActivity : AppCompatActivity() {

    private lateinit var statusTextView: TextView
    private lateinit var threatLevelTextView: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var startButton: Button
    private lateinit var stopButton: Button
    private lateinit var historyButton: Button

    private var isDetecting = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_threat_detection)

        statusTextView = findViewById(R.id.tv_status)
        threatLevelTextView = findViewById(R.id.tv_threat_level)
        progressBar = findViewById(R.id.progress_bar)
        startButton = findViewById(R.id.btn_start_detection)
        stopButton = findViewById(R.id.btn_stop_detection)
        historyButton = findViewById(R.id.btn_view_history)

        startButton.setOnClickListener {
            startDetection()
        }

        stopButton.setOnClickListener {
            stopDetection()
        }

        historyButton.setOnClickListener {
            // Open the activity or fragment to show previous detected threats
            openThreatHistory()
        }
    }

    private fun startDetection() {
        isDetecting = true
        statusTextView.text = "Status: Listening..."
        threatLevelTextView.text = "Threat Level: Safe"
        progressBar.visibility = View.VISIBLE

        // Begin sound recording and threat detection
        // This is where the sound detection logic using Google Gemini or ML Kit would be triggered
        // For now, simulating detection.

        Handler(Looper.getMainLooper()).postDelayed({
            detectThreat() // Simulate detection after a short delay
        }, 3000)
    }

    private fun stopDetection() {
        isDetecting = false
        statusTextView.text = "Status: Detection Stopped"
        progressBar.visibility = View.GONE
        // Stop sound recording and analysis
    }

    private fun detectThreat() {
        if (isDetecting) {
            // Simulate threat detection logic
            // Based on audio analysis, update the threat level
            val detectedThreat = true // For simulation purposes, assuming threat detected.

            if (detectedThreat) {
                threatLevelTextView.text = "Threat Level: High"
                statusTextView.text = "Threat Detected!"
                // Trigger alert: vibration or sound notification.
            } else {
                threatLevelTextView.text = "Threat Level: Safe"
                statusTextView.text = "Status: All clear."
            }
        }
    }

    private fun openThreatHistory() {
        // Open a new activity or fragment to show the history of detected threats
        Toast.makeText(this, "Showing threat history", Toast.LENGTH_SHORT).show()
    }
}
