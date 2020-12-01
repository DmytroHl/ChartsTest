package com.example.chartstest.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.chartstest.databinding.ActivitySplashBinding
import com.example.chartstest.ui.main.MainActivity
import com.example.chartstest.utils.extentions.viewBinding

class SplashActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivitySplashBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(MainActivity.newIntent(this))
        }, 3000)
    }
}