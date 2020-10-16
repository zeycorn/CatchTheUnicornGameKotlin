package com.example.catchtheunicornkotlin

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var score = 0
    val imageArray = arrayListOf<ImageView>(imageView,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7,imageView8,imageView9)
    var handler = Handler()
    var runnable = Runnable {  }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Timer
        object : CountDownTimer(15700, 1000) {
            override fun onFinish() {
                timeText.text = "Time: 0"
                handler.removeCallbacks(runnable)
                for (image in imageArray){
                    image.visibility = View.INVISIBLE
                }

                // Alert
                val alertDialogBuilder = AlertDialog.Builder(this@MainActivity)
                alertDialogBuilder.setMessage("Süreniz bitmiştir. Puanınız: $score Baştan başlamak ister misiniz?")
                alertDialogBuilder.setPositiveButton("Evet"){dialog, which ->
                    val intent = intent
                    finish()
                    startActivity(intent)
                }
                alertDialogBuilder.setNegativeButton("Hayır"){dialog, which ->
                    Toast.makeText(this@MainActivity,"Oyun Bitti.",Toast.LENGTH_LONG).show()
                }
                alertDialogBuilder.show()
                score = 0

            }

            override fun onTick(millisUntilFinished: Long) {
                timeText.text = "Time: $(millisUntilFinished/1000)"

            }

        }.start()

        hideImages()

    }

    fun increaseScore(view : View){
        score++
        scoreText.text = "Score: $score"

    }

    fun hideImages(){

        runnable = object : Runnable {
            override fun run() {
                for (image in imageArray){
                    image.visibility = View.INVISIBLE
                }
                val index = (0..8).random()
                imageArray[index].visibility = View.VISIBLE

                handler.postDelayed(runnable,500)
            }
        }
        handler.post(runnable)
    }

}