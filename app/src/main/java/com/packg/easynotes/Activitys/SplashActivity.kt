package com.packg.easynotes.Activitys

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.packg.easynotes.MainActivity.MainActivity


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
