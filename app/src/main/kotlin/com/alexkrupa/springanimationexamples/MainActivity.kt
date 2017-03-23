package com.alexkrupa.springanimationexamples

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.alexkrupa.springanimationexamples.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        positionActivityButton.setOnClickListener { startActivity(Intent(this, PositionActivity::class.java)) }
        rotationActivityButton.setOnClickListener { startActivity(Intent(this, RotationActivity::class.java)) }
        scaleActivityButton.setOnClickListener { startActivity(Intent(this, ScaleActivity::class.java)) }
    }
}
