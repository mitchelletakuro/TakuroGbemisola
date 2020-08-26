package com.mitchelletakuro.gbemisolatakurofilter.ui.splash

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager.LayoutParams
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.mitchelletakuro.gbemisolatakurofilter.R
import com.mitchelletakuro.gbemisolatakurofilter.ui.filterList.FilterActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {


    var splashAnimation: Animation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            LayoutParams.FLAG_FULLSCREEN,
            LayoutParams.FLAG_FULLSCREEN)
        if (supportActionBar != null)
            supportActionBar?.hide()
        setStatusBackgroundColor()
        setContentView(R.layout.activity_splash)
        splashAnimation()

    }

    private fun setStatusBackgroundColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this,
                R.color.black_transparent
            )
        }
    }

    private fun splashAnimation() {
        splashAnimation = AnimationUtils.loadAnimation(this,
            R.anim.splash_left2right
        )
        splash_iv.startAnimation(splashAnimation)
        Handler().postDelayed(
            {
                val i = Intent(this@SplashActivity, FilterActivity::class.java)
                startActivity(i)
                finish()
            }, 5000L)

    }



}
