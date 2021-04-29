package ge.android.gis.tv1.tts

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import android.widget.Toolbar
import androidx.core.content.ContextCompat
import ge.android.gis.tv1.R
import ge.android.gis.tv1.activity.MainActivity
import ge.android.gis.tv1.stt.STTUtils
import kotlin.concurrent.thread


class TTSUtils(var context: Context, var recognizeListener: STTUtils, var  listeningToolbarColor: androidx.appcompat.widget.Toolbar) : TextToSpeech.OnInitListener {

    var tts: TextToSpeech? = TextToSpeech(context, this)


    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            if (status == TextToSpeech.LANG_MISSING_DATA) {
                Log.e("TTS", "The Language specified is not supported!")
            } else {
                Log.e("TTS", "Initialization Failed !")
            }
        }
    }


    fun speakOut(text: String, delay: Long) {

        tts!!.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String) {
                Log.i("TAawdawdwwG", "onStart: ")

            }

            override fun onError(utteranceId: String) {
                Log.i("TAawdawdwwG", "onError: ")


            } // Do nothing.
            override fun onDone(utteranceId: String) {
                Log.i("TAawdawdwwG", "onDone: ")


            }
        })

        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")


//        recognizeListener.stopListening()

//        android.os.Handler(Looper.getMainLooper()).postDelayed({


//            android.os.Handler(Looper.getMainLooper()).postDelayed({

//                recognizeListener.startListening()


//            }, delay)

//        }, 1000)

    }

    fun isSpeaking(): Boolean {

        return tts!!.isSpeaking

    }

    fun stopSpeaking() {

        tts!!.stop()

    }

}

