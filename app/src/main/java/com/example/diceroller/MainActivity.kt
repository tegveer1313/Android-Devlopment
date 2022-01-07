package com.example.diceroller

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rollButton: Button = findViewById(R.id.button)

        rollButton.setOnClickListener { val toast = Toast.makeText(this, "Dice Rolled!", Toast.LENGTH_SHORT)
            rollDice()
            toast.show()
        }

        val sm: SensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val mysensor: Sensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        val se = object : SensorEventListener{
            override fun onSensorChanged(event: SensorEvent) {
                val values = event.values
                val x = values[0]
                val y = values[1]
                val z = values[2]

                val acc_sq: Double = (x * x + y * y + z * z) / (9.8 * 9.8)
                if(acc_sq >= 4){
                    rollDice()
                }

            }

            override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
            }

        }
        sm.registerListener(se, mysensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    private fun rollDice() {
        musicPlay()
        val dice = Dice(6)
        val diceRoll = dice.roll()
        val resultOutput: TextView = findViewById(R.id.textView)
        resultOutput.text = diceRoll.toString()

        val diceImage: ImageView = findViewById(R.id.imageView)
        when(diceRoll){
            1 -> diceImage.setImageResource(R.drawable.dice_1)
            2 -> diceImage.setImageResource(R.drawable.dice_2)
            3 -> diceImage.setImageResource(R.drawable.dice_3)
            4 -> diceImage.setImageResource(R.drawable.dice_4)
            5 -> diceImage.setImageResource(R.drawable.dice_5)
            6 -> diceImage.setImageResource(R.drawable.dice_6)
        }
    }


    private fun musicPlay() {
        mediaPlayer = MediaPlayer.create(this, R.raw.roll_mu)
        mediaPlayer!!.start()
    }
    private fun musicStop() {
        mediaPlayer!!.stop()
    }

}

class Dice(val numSide: Int){
    fun roll(): Int{
        return (1..numSide).random()
    }
}