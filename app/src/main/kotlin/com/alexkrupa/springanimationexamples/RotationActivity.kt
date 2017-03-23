package com.alexkrupa.springanimationexamples

import android.os.Bundle
import android.support.animation.SpringAnimation
import android.support.animation.SpringForce
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import com.alexkrupa.springanimationexamples.createSpringAnimation
import com.alexkrupa.springanimationexamples.updateImmersiveMode
import com.alexkrupa.springanimationexamples.R
import kotlinx.android.synthetic.main.activity_rotation.*

class RotationActivity : AppCompatActivity() {
    private companion object Params {
        val INITIAL_ROTATION = 0f
        val STIFFNESS = SpringForce.STIFFNESS_MEDIUM
        val DAMPING_RATIO = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
    }

    lateinit var rotationAnimation: SpringAnimation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rotation)

        updateRotationText()

        // create a rotation SpringAnimation
        rotationAnimation = createSpringAnimation(
                rotatingView, SpringAnimation.ROTATION,
                INITIAL_ROTATION, STIFFNESS, DAMPING_RATIO)
        rotationAnimation.addUpdateListener { _, _, _ -> updateRotationText() }

        var previousRotation = 0f
        var currentRotation = 0f
        rotatingView.setOnTouchListener { view, event ->
            val centerX = view.width / 2.0
            val centerY = view.height / 2.0
            val x = event.x
            val y = event.y

            // angle calculation
            fun updateCurrentRotation() {
                currentRotation = view.rotation +
                        Math.toDegrees(Math.atan2(x - centerX, centerY - y)).toFloat()
            }

            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    // cancel so we can grab the view during previous animation
                    rotationAnimation.cancel()

                    updateCurrentRotation()
                }
                MotionEvent.ACTION_MOVE -> {
                    // save current rotation
                    previousRotation = currentRotation

                    updateCurrentRotation()

                    // rotate view by angle difference
                    val angle = currentRotation - previousRotation
                    view.rotation += angle

                    updateRotationText()
                }
                MotionEvent.ACTION_UP -> rotationAnimation.start()
            }
            true
        }
    }

    private fun updateRotationText() {
        rotationTextView.text = String.format("%.3f", rotatingView.rotation)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        updateImmersiveMode()
    }
}
