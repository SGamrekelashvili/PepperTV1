package ge.android.gis.tv1.stt

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import ge.android.gis.tv1.R
import ge.android.gis.tv1.tts.TTSUtils

class STTUtils(var context: Context, var toolbar: Toolbar, var recognizedTextView: TextView) :
    RecognitionListener {
    var LOG_TAG = "VoiceRecognitionActivity"

    var recognizedText: String? = null
    var speech: SpeechRecognizer? = null
    var recognizerIntent: Intent? = null





    fun startListening() {

        onStartedListeningView()

        Log.i("LISTENING", "LISTENING STARTED: ")

        speech = SpeechRecognizer.createSpeechRecognizer(context)
        speech!!.setRecognitionListener(this)
        recognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        recognizerIntent!!.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE,
            "ka-GE"
        )
        recognizerIntent!!.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1)

        speech!!.startListening(recognizerIntent)



    }

    fun stopListening() {
        speech!!.stopListening()
        speech!!.cancel()
        speech!!.destroy()
        toolbar.setBackgroundColor(
            ContextCompat.getColor(
                context,
                R.color.disable_toolbar
            )
        )

    }



    override fun onBeginningOfSpeech() {

        Log.i(LOG_TAG, "I'M LISTENING ......: ")
    }


    override fun onBufferReceived(buffer: ByteArray) {
        Log.i(LOG_TAG, "onBufferReceived: $buffer")
    }

    override fun onEndOfSpeech() {
        Log.i(LOG_TAG, "STOPPING LISTENING")
    }

    override fun onError(errorCode: Int) {
        val errorMessage = getErrorText(errorCode)
        Log.d(LOG_TAG, "FAILED $errorMessage")

        recognizedText = " "

        onResultListeningView()

        Handler(Looper.getMainLooper()).postDelayed({

            startListening()

        }, 500)

    }

    override fun onEvent(arg0: Int, arg1: Bundle?) {
        Log.i(LOG_TAG, "onEvent")
    }

    override fun onPartialResults(partialResults: Bundle?) {
        val data = partialResults!!.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        val word = data!![data.size - 1] as String
//        recognisedText.setText(word)

        Log.i("TEST", "partial_results: $word")
    }

    override fun onReadyForSpeech(arg0: Bundle?) {

        recognizedTextView.text = "Listening ..."

        Log.i(LOG_TAG, "onReadyForSpeech")
    }

    override fun onResults(results: Bundle) {
        Log.i(LOG_TAG, "onResults")
        if (results.toString() != "?") {
            if (results.get("results_recognition").toString().contains("გამარჯობა") || results.get("results_recognition").toString().contains("გამარჯობა პეპერ")) {

                recognizedTextView.text = "გამარჯობა"
                recognizedText = "გამარჯობა , მადლობა რომ მომიწვიეთ,"


//                TTSUtils(context, this, ).speakOut(recognizedText!!, 4000)


            }else if (results.toString().contains("როგორ ხარ")
                || results.get("results_recognition").toString().contains("როგორ ხარ")
                || results.get("results_recognition").toString().contains("ხარ")){

                recognizedTextView.text = "როგორ ხარ?"
                recognizedText = "ყველაზე,მაგრად. შენ , როგორ ხარ.?"

//                TTSUtils(context, this).speakOut(recognizedText!!, 4000)
            }else if (results.toString().contains("რას მეტყვი პარტნიორობაზე")
                || results.get("results_recognition").toString().contains("პარტნიორობაზე")
                || results.get("results_recognition").toString().contains("პარტნიორებს")
                || results.get("results_recognition").toString().contains("პარტნიორი")
                || results.get("results_recognition").toString().contains("პარტნიორობა")){

                recognizedTextView.text = "რას მეტყვი პარტნიორობაზე"
                recognizedText = "ჩვენი ღონისძიება იქნება საქართველოში, და რეგიონში სტარტაპ ეკოსისტემის მთავარი მამოძრავებელი ძალა. შესაბამისად თუ თქვენ დაინტერესებული ხართ სტარტაპ ეკოსისტემით,, ქროსროუდის პარტნიორობა არის სწორედ ის რაც, თქვენ გჭირდებათ.!"

//                TTSUtils(context, this).speakOut(recognizedText!!, 16000)
            }else if (results.toString().contains("და სპონსორობაზე")
                || results.toString().contains("დასპონსორობა")
                || results.get("results_recognition").toString().contains("სპონსორი")
                || results.get("results_recognition").toString().contains("სპონსორობაზე")
                || results.get("results_recognition").toString().contains("სპონსორობა")){

                recognizedTextView.text = "სპონსორობაზე"
                recognizedText = "2021 წლის სექტემბერში საქართველოს ეწვევა სტარტაპებისა და დამოუკიდებელი მეწარმეების სამყარო! " +
                        "ნუთუ,? არ გინდათ, რომ თქვენი ბრენდი, ამ მოვლენასთან ასოცირდებოდეს.??"

//                TTSUtils(context, this).speakOut(recognizedText!!, 15000)
            }else if (results.toString().contains("რატომ უნდა დავესწრო")
                || results.get("results_recognition").toString().contains("დასწრება")
                || results.get("results_recognition").toString().contains("დასწრებაზე")
                || results.get("results_recognition").toString().contains("დავესწრო")){

                recognizedTextView.text = "დასწრება"
                recognizedText = "ქროსროუდი შესაძლოა თქვენი წარმატების საწინდარი გახდეს. თქვენი მომავლის შექმნა მხოლოდ თქვენ შეგიძლიათ. ჩვენ კი შეგვიძლია ამაში დაგეხმაროთ!"

//                TTSUtils(context, this).speakOut(recognizedText!!, 14000)
            }else{
                recognizedText = "?"
                Log.i("Error","ERROR 1 ")
                Toast.makeText(context, recognizedText, Toast.LENGTH_LONG).show()
                onResultListeningView()
                startListening()
            }
        } else {
            recognizedText = " "
            Log.i("Error","ERROR 2")
            Toast.makeText(context, recognizedText, Toast.LENGTH_LONG).show()
            onResultListeningView()
            startListening()
        }

    }


    override fun onRmsChanged(rmsdB: Float) {

//        Log.i(LOG_TAG, "onRmsChanged : $rmsdB")



    }

    private fun getErrorText(errorCode: Int): String {
        return when (errorCode) {
            SpeechRecognizer.ERROR_AUDIO -> "Audio recording error"
            SpeechRecognizer.ERROR_CLIENT -> "Client side error"
            SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "Insufficient permissions"
            SpeechRecognizer.ERROR_NETWORK -> "Network error"
            SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> "Network timeout"
            SpeechRecognizer.ERROR_NO_MATCH -> "?"
            SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> "RecognitionService busy"
            SpeechRecognizer.ERROR_SERVER -> "error from server"
            SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "No speech input"
            else -> "Didn't understand, please try again."
        }
    }

    fun onResultListeningView() {


        toolbar.setBackgroundColor(
            ContextCompat.getColor(
                context,
                R.color.disable_toolbar
            )
        )

        recognizedTextView.text = recognizedText

//        TTSUtils(context).speakOut(recognizedText!!)

        recognizedText = " "
    }

    fun onStartedListeningView() {


        toolbar.setBackgroundColor(
            ContextCompat.getColor(
                context,
                R.color.active_toolbar
            )
        )

        recognizedTextView.text = "  "


    }

}