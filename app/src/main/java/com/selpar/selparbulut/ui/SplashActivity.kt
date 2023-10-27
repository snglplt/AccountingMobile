package com.selpar.selparbulut.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.selpar.selparbulut.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed(Runnable { //This method will be executed once the timer is over
            // Start your app main activity
            val i = Intent(this@SplashActivity, SingInActivity::class.java)
            startActivity(i)
            overridePendingTransition(
                R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left
            )
            // close this activity
            finish()
        }, 3000)
    }
}