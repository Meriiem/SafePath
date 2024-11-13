package com.example.safepath

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioGroup
import android.widget.RadioButton
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // References to UI components
        val swLocationSharing: Switch = findViewById(R.id.sw_location_sharing)
        val swNotifications: Switch = findViewById(R.id.sw_notifications)
        val swVoiceActivation: Switch = findViewById(R.id.sw_voice_activation)
        val cbLocationPrivacy: CheckBox = findViewById(R.id.cb_location_privacy)
        val cbAudioPrivacy: CheckBox = findViewById(R.id.cb_audio_privacy)
        val btnManageContacts: Button = findViewById(R.id.btn_manage_contacts)
        val rgTheme: RadioGroup = findViewById(R.id.rg_theme)
        val btnLogOut: Button = findViewById(R.id.btn_log_out)
        val tvHelpSupport: TextView = findViewById(R.id.tv_help_support)
        val rbLightTheme: RadioButton = findViewById(R.id.rb_light_theme)
        val rbDarkTheme: RadioButton = findViewById(R.id.rb_dark_theme)

        // Set listeners for settings
        btnLogOut.setOnClickListener {
            // Firebase logout logic
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        // Listen for theme selection
        rgTheme.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_light_theme -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
                R.id.rb_dark_theme -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
            }
        }

        // Handle other settings like location sharing, notifications, etc.
        // You can add more functionality here as needed
    }
}

// Set listeners for settings
//        btnManageContacts.setOnClickListener {
//            // Launch manage contacts activity
//            startActivity(Intent(this, ManageContactsActivity::class.java))
//        }

//        tvHelpSupport.setOnClickListener {
//            // Launch help & support section
//            startActivity(Intent(this, HelpSupportActivity::class.java))
//        }