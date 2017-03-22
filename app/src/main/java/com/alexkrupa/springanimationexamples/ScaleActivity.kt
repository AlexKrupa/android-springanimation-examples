package com.alexkrupa.springanimationexamples

import android.os.Bundle
import android.support.animation.SpringAnimation
import android.support.animation.SpringForce
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import com.alexkrupa.springanimationexamples.createSpringAnimation
import com.alexkrupa.springanimationexamples.updateImmersiveMode
import com.alexkrupa.springanimationexamples.R
import kotlinx.android.synthetic.main.activity_scale.*

class ScaleActivity : AppCompatActivity() {
    private companion object Params {
        val INITIAL_SCALE = 1f
        val STIFFNESS = SpringForce.STIFFNESS_MEDIUM
        val DAMPING_RATIO = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
    }

    lateinit var scaleXAnimation: SpringAnimation
    lateinit var scaleYAnimation: SpringAnimation
    lateinit var scaleGestureDetector: ScaleGestureDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scale)

        updateScaleText()

        // create scaleX and scaleY animations
        scaleXAnimation = createSpringAnimation(
                scalingView, SpringAnimation.SCALE_X,
                INITIAL_SCALE, STIFFNESS, DAMPING_RATIO)
        scaleYAnimation = createSpringAnimation(
                scalingView, SpringAnimation.SCALE_Y,
                INITIAL_SCALE, STIFFNESS, DAMPING_RATIO)
        scaleXAnimation.addUpdateListener { _, _, _ -> updateScaleText() }

        setupPinchToZoom()

        scalingView.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                scaleXAnimation.start()
                scaleYAnimation.start()
            } else {
                // cancel animations so we can grab the view during previous animation
                scaleXAnimation.cancel()
                scaleYAnimation.cancel()

                // pass touch event to ScaleGestureDetector
                scaleGestureDetector.onTouchEvent(event)
            }
            true
        }
    }

    private fun setupPinchToZoom() {
        var scaleFactor = 1f
        scaleGestureDetector = ScaleGestureDetector(this,
                object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
                    override fun onScale(detector: ScaleGestureDetector): Boolean {
                        scaleFactor *= detector.scaleFactor
                        scalingView.scaleX *= scaleFactor
                        scalingView.scaleY *= scaleFactor
                        updateScaleText()
                        return true
                    }
                })
    }

    private fun updateScaleText() {
        scaleTextView.text = String.format("%.3f", scalingView.scaleX)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        updateImmersiveMode()
    }
}
