package com.example.safepath

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.safepathproject.MainActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var registerButton: Button
    private lateinit var forgotPasswordButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        // Initialize views
        emailEditText = findViewById(R.id.editTextEmail)
        passwordEditText = findViewById(R.id.editTextPassword)
        loginButton = findViewById(R.id.btnLogin)
        progressBar = findViewById(R.id.progressBar)
        registerButton = findViewById(R.id.btnRegister)
        forgotPasswordButton = findViewById(R.id.btnForgotPassword)

        // Set listeners for buttons
        loginButton.setOnClickListener { loginUser() }
        registerButton.setOnClickListener {  }
        forgotPasswordButton.setOnClickListener {  }
//        registerButton.setOnClickListener { navigateToRegister() }
//        forgotPasswordButton.setOnClickListener { navigateToForgotPassword() }
    }

    private fun loginUser() {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        // Validate input
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in both fields", Toast.LENGTH_SHORT).show()
            return
        }

        // Show progress bar while logging in
        progressBar.visibility = View.VISIBLE

        // Authenticate with Firebase
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                progressBar.visibility = View.GONE

                if (task.isSuccessful) {
                    // Navigate to MainActivity after successful login
                    startActivity(Intent(this, MainActivity::class.java))
                    finish() // Close LoginActivity
                } else {
                    // Show error message
                    Toast.makeText(this, "Login failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }

//    private fun navigateToRegister() {
//        // Navigate to RegisterActivity (for creating a new account)
//        startActivity(Intent(this, RegisterActivity::class.java))
//    }
//
//    private fun navigateToForgotPassword() {
//        // Navigate to ForgotPasswordActivity (for password recovery)
//        startActivity(Intent(this, ForgotPasswordActivity::class.java))
//    }
}
