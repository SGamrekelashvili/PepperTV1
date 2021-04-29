package ge.android.gis.tv1.activity

import android.Manifest
import android.app.Dialog
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.QiSDK
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks
import com.aldebaran.qi.sdk.`object`.actuation.Animation
import com.aldebaran.qi.sdk.`object`.touch.Touch
import com.aldebaran.qi.sdk.`object`.touch.TouchSensor
import com.aldebaran.qi.sdk.builder.AnimateBuilder
import com.aldebaran.qi.sdk.builder.AnimationBuilder
import com.aldebaran.qi.sdk.design.activity.RobotActivity
import com.aldebaran.qi.sdk.design.activity.conversationstatus.SpeechBarDisplayStrategy
import com.google.firebase.database.*
import ge.android.gis.tv1.R
import ge.android.gis.tv1.activity.fragment.FragmentUtils
import ge.android.gis.tv1.activity.fragment.MainFragment
import ge.android.gis.tv1.databinding.ActivityMainBinding
import ge.android.gis.tv1.stt.STTUtils
import ge.android.gis.tv1.tts.TTSUtils


class MainActivity : RobotActivity(), RobotLifecycleCallbacks {

    private lateinit var binding: ActivityMainBinding

    private var mContactDialog: Dialog? = null
    private var headTouchSensor: TouchSensor? = null
    var qiContext:QiContext? = null
    var tts : TTSUtils? = null
    var recognizeListener: STTUtils? = null

    val REQUEST_RECORD_PERMISSION = 1
    private lateinit var database:FirebaseDatabase
    private lateinit var reference:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        QiSDK.register(this, this)
        setSpeechBarDisplayStrategy(SpeechBarDisplayStrategy.IMMERSIVE)

        FragmentUtils.fragmentManager = supportFragmentManager

        recognizeListener = STTUtils(
            this,
            binding.recognitionToolbar.mainToolbar,
            binding.recognitionToolbar.recognizedText
        )
        tts = TTSUtils(this, recognizeListener!!, binding.recognitionToolbar.mainToolbar)

        FragmentUtils.setFragment(MainFragment(), false)


        ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.RECORD_AUDIO),
            REQUEST_RECORD_PERMISSION
        )

        database = FirebaseDatabase.getInstance()
        reference = database.getReference("message")
    }


    override fun onStart() {
        super.onStart()
        reference.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.child("text").value
                if(value != null){
                    tts!!.speakOut("$value", 2000)
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_RECORD_PERMISSION -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(
                    this@MainActivity,
                    "Permission Granted!",
                    Toast.LENGTH_SHORT
                ).show()

            } else {
                Toast.makeText(
                    this@MainActivity,
                    "Permission Denied!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    override fun onRobotFocusGained(qiContext: QiContext?) {
        this.qiContext=qiContext
        val touch = qiContext!!.touch
        headTouchSensor = touch.getSensor("Head/Touch")
        headTouchSensor!!.addOnStateChangedListener { touchState ->
            if (touchState.touched) {
                headTouch()
            }
        }
        Log.i("LOG_TAG", "GainedFOCUS")

    }

    private fun headTouch() {
        val myAnimation: Animation = AnimationBuilder.with(qiContext)
            .withResources(R.raw.reaction)
            .build()
        val btnClick: MediaPlayer = MediaPlayer.create(this, R.raw.laught)
        btnClick.start()
        val animate = AnimateBuilder.with(qiContext)
            .withAnimation(myAnimation)
            .build()
        animate.async().run()
    }


    override fun onRobotFocusLost() {
        Log.i("LOG_TAG", "FOCUS LOST")

    }

    override fun onRobotFocusRefused(reason: String?) {
        Log.i("LOG_TAG", "FOCUS REFUSED")

    }


    override fun onDestroy() {
        super.onDestroy()
        recognizeListener!!.speech!!.cancel()
        recognizeListener!!.speech!!.destroy()
        Log.i(recognizeListener!!.LOG_TAG, "destroy")

        if (tts != null) {
            tts!!.tts!!.stop()
            tts!!.tts!!.shutdown()
        }
        QiSDK.unregister(this)
    }

    fun showContactDialog() {

        tts!!.speakOut("აქ შეგიძლიათ ნახოთ ჩვენი საკონტაქტო ინფორმაცია...", 8000)
        mContactDialog = Dialog(this)

        mContactDialog!!.setContentView(R.layout.contact_layout)
        mContactDialog!!.setTitle("You can contact us here : ")


        mContactDialog!!.show()
    }

    fun cancelContactDialog() {

        if (tts!!.isSpeaking()) tts!!.stopSpeaking()
        mContactDialog!!.dismiss()
    }







}