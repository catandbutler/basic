package com.example.basic

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.basic.databinding.ActivityMainBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private var job: Job? = null
    private lateinit var binding: ActivityMainBinding
    private val TAG = "MainActivity"
    private var counter = 1
    private var randomValue = (1..100).random()
    private var isClick = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate")
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        /*  savedInstanceState?.let {
              counter = it.getInt("counter")
          }*/
        if (savedInstanceState != null) {
            randomValue = savedInstanceState.getInt("random")
            counter = savedInstanceState.getInt("counter")
            if(counter == 100) {
                counter = 100
            }
            isClick = savedInstanceState.getBoolean("isClick")
            binding.spartaTextView.text = counter.toString()


        } else{
            setJobAndLaunch()
        }

        setupButton()
        setRandomValueBetweenOneToHundred()
//      setJobAndLaunch()
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(TAG, "onRestart")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause")

    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop")
        job?.cancel()

    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart")

            setJobAndLaunch()

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.i(TAG, "onRestoreInstanceState")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i(TAG, "onSaveInstanceState")
        outState.putInt("counter", counter)
        outState.putInt("random", randomValue)
        outState.putBoolean("isClick", isClick)
    }

    private fun setupButton() {
        binding.clickButton.setOnClickListener {
            checkAnswerAndShowToast()
            job?.cancel()
            isClick = true
        }
    }

    private fun setRandomValueBetweenOneToHundred() {
        binding.textViewRandom.text = randomValue.toString()
    }


    private fun setJobAndLaunch() {
        job?.cancel() // job is uninitialized exception
        job = lifecycleScope.launch {
            while (counter <= 100) {
                if (isActive) {
                    binding.spartaTextView.text = counter++.toString()
                    delay(1000) // 1초 = 1000
                } else {
                    break
                }
            }
        }
    }

    private fun checkAnswerAndShowToast() {
        val spartaText = binding.spartaTextView.text.toString()
        val randomText = binding.textViewRandom.text.toString()
        if (spartaText == randomText) {
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Wrong!", Toast.LENGTH_SHORT).show()
        }
    }
}