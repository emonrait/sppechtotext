package com.emon.raihan.soundtotext

import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private lateinit var btn_click:Button
    private lateinit var edt_value:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_click=findViewById(R.id.btn_click)
        edt_value=findViewById(R.id.edt_value)

        btn_click.setOnClickListener {

            promptSpeechInput()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            10 -> if (resultCode == RESULT_OK && data != null) {
                val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                Toast.makeText(this, result!![0], Toast.LENGTH_SHORT).show()
                edt_value.setText(result[0].trim())
            }
        }
    }

    fun promptSpeechInput() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(
            RecognizerIntent.EXTRA_CALLING_PACKAGE,
            "com.domain.app"
        )
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        } else {
            Toast.makeText(this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
        }
        val recognizer = SpeechRecognizer
            .createSpeechRecognizer(this.applicationContext)
        val listener: RecognitionListener = object : RecognitionListener {
            override fun onResults(results: Bundle) {
                val voiceResults = results
                    .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (voiceResults == null) {
                    Log.d("No voice results","No voice results")
                } else {
                    println("Printing matches: ")
                    for (match in voiceResults) {
                        Log.d("match",match)
                    }
                }
            }

            override fun onReadyForSpeech(params: Bundle) {
                Log.d("Ready for speech","Ready for speech")
            }

            /**
             * ERROR_NETWORK_TIMEOUT = 1;
             * ERROR_NETWORK = 2;
             * ERROR_AUDIO = 3;
             * ERROR_SERVER = 4;
             * ERROR_CLIENT = 5;
             * ERROR_SPEECH_TIMEOUT = 6;
             * ERROR_NO_MATCH = 7;
             * ERROR_RECOGNIZER_BUSY = 8;
             * ERROR_INSUFFICIENT_PERMISSIONS = 9;
             *
             * @param error code is defined in SpeechRecognizer
             */
            override fun onError(error: Int) {
               Log.d("Errorlisteningforspeech", error.toString())
            }

            override fun onBeginningOfSpeech() {
                Log.d("Speech starting","Speech starting")
            }

            override fun onBufferReceived(buffer: ByteArray) {
                // TODO Auto-generated method stub
            }

            override fun onEndOfSpeech() {
                // TODO Auto-generated method stub
            }

            override fun onEvent(eventType: Int, params: Bundle) {
                // TODO Auto-generated method stub
            }

            override fun onPartialResults(partialResults: Bundle) {
                // TODO Auto-generated method stub
            }

            override fun onRmsChanged(rmsdB: Float) {
                // TODO Auto-generated method stub
            }
        }
        recognizer.setRecognitionListener(listener)
        recognizer.startListening(intent)
    }
}