package com.example.safepath

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class VoiceActivationActivity : AppCompatActivity() {

    private lateinit var speechRecognizer: SpeechRecognizer
    private lateinit var textToSpeech: TextToSpeech
    private lateinit var tvStatus: TextView
    private lateinit var etCommand: EditText
    private lateinit var btnSave: Button
    private lateinit var btnTest: Button
    private lateinit var btnCancel: Button

    private var voiceCommand: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voice_activation)

        tvStatus = findViewById(R.id.tv_status)
        etCommand = findViewById(R.id.et_command)
        btnSave = findViewById(R.id.btn_save_command)
        btnTest = findViewById(R.id.btn_test_command)
        btnCancel = findViewById(R.id.btn_cancel)

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
        textToSpeech = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val langResult = textToSpeech.setLanguage(Locale.US)
                if (langResult == TextToSpeech.LANG_MISSING_DATA || langResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("VoiceActivation", "Language not supported")
                }
            }
        }

        btnTest.setOnClickListener {
            startListeningForVoiceCommand()
        }

        btnSave.setOnClickListener {
            voiceCommand = etCommand.text.toString().trim()
            if (voiceCommand.isEmpty()) {
                Toast.makeText(this, "Please enter a voice command", Toast.LENGTH_SHORT).show()
            } else {
                // Save the voice command to shared preferences or Firebase
                saveVoiceCommand(voiceCommand)
                Toast.makeText(this, "Voice command saved", Toast.LENGTH_SHORT).show()
            }
        }

        btnCancel.setOnClickListener {
            finish()  // Close the activity if user cancels
        }
    }

    private fun startListeningForVoiceCommand() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_PROMPT, "Say your distress command")
            putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1) // Get the best result
        }

        speechRecognizer.setRecognitionListener(object : android.speech.RecognitionListener {
            override fun onReadyForSpeech(p0: Bundle?) {
                tvStatus.text = "Listening..."
            }

            override fun onBeginningOfSpeech() {
                tvStatus.text = "Listening..."
            }

            override fun onRmsChanged(p0: Float) {}
            override fun onBufferReceived(p0: ByteArray?) {}
            override fun onEndOfSpeech() {
                tvStatus.text = "Processing..."
            }

            override fun onError(error: Int) {
                tvStatus.text = "Error: $error"
                Toast.makeText(applicationContext, "Speech recognition failed", Toast.LENGTH_SHORT).show()
            }

            override fun onResults(results: Bundle?) {
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (matches != null && matches.isNotEmpty()) {
                    val spokenCommand = matches[0]
                    tvStatus.text = "Heard: $spokenCommand"
                    if (spokenCommand.equals(voiceCommand, ignoreCase = true)) {
                        triggerDistressSignal()
                    } else {
                        tvStatus.text = "Command not recognized. Try again."
                    }
                }
            }

            override fun onPartialResults(p0: Bundle?) {}
            override fun onEvent(p0: Int, p1: Bundle?) {}
        })

        speechRecognizer.startListening(intent)
    }

    private fun triggerDistressSignal() {
        // Trigger distress signal, e.g., send location, notify contacts
        tvStatus.text = "Distress signal triggered!"
        textToSpeech.speak("Distress signal triggered", TextToSpeech.QUEUE_FLUSH, null, null)
    }

    private fun saveVoiceCommand(command: String) {
        // Save the voice command in shared preferences or Firebase
        val sharedPref = getSharedPreferences("SafePathPreferences", MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("voice_command", command)
            apply()
        }
    }
}
