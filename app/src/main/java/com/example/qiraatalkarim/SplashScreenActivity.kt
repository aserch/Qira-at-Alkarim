package com.example.qiraatalkarim

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        val splashTime: Long = 1000
        Handler().postDelayed({
            val intent = Intent(this, QuranIndexActivity::class.java)
            startActivity(intent) // Pindah ke Home Activity setelah 1 detik
            finish()
        }, splashTime)

    }
}