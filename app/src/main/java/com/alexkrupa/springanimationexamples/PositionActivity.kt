package com.alexkrupa.springanimationexamples

import android.os.Bundle
import android.support.animation.SpringAnimation
import android.support.animation.SpringForce
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import com.alexkrupa.springanimationexamples.createSpringAnimation
import com.alexkrupa.springanimationexamples.updateImmersiveMode
import com.alexkrupa.springanimationexamples.R
import kotlinx.android.synthetic.main.activity_position.*

class PositionActivity : AppCompatActivity() {
    private companion object Params {
        val STIFFNESS = SpringForce.STIFFNESS_MEDIUM
        val DAMPING_RATIO = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
    }

    lateinit var xAnimation: SpringAnimation
    lateinit var yAnimation: SpringAnimation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_position)

        // create X and Y animations for view's initial position once it's known
        movingView.viewTreeObserver.addOnGlobalLayoutListener {
            xAnimation = createSpringAnimation(
                    movingView, SpringAnimation.X, movingView.x, STIFFNESS, DAMPING_RATIO)
            yAnimation = createSpringAnimation(
                    movingView, SpringAnimation.Y, movingView.y, STIFFNESS, DAMPING_RATIO)
        }

        var dX = 0f
        var dY = 0f
        movingView.setOnTouchListener { view, event ->
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    // capture the difference between view's top left corner and touch point
                    dX = view.x - event.rawX
                    dY = view.y - event.rawY

                    // cancel animations so we can grab the view during previous animation
                    xAnimation.cancel()
                    yAnimation.cancel()
                }
                MotionEvent.ACTION_MOVE -> {
                    //  a different approach would be to change the view's LayoutParams.
                    movingView.animate()
                            .x(event.rawX + dX)
                            .y(event.rawY + dY)
                            .setDuration(0)
                            .start()
                }
                MotionEvent.ACTION_UP -> {
                    xAnimation.start()
                    yAnimation.start()
                }
            }
            true
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        updateImmersiveMode()
    }
}
