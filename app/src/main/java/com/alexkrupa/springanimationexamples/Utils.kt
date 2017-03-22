package com.alexkrupa.springanimationexamples

import android.app.Activity
import android.support.animation.DynamicAnimation
import android.support.animation.SpringAnimation
import android.support.animation.SpringForce
import android.support.annotation.FloatRange
import android.view.View

/*
Concise SpringAnimation creation.
 */
fun createSpringAnimation(view: View,
                          property: DynamicAnimation.ViewProperty,
                          finalPosition: Float,
                          @FloatRange(from = 0.0) stiffness: Float,
                          @FloatRange(from = 0.0) dampingRatio: Float): SpringAnimation {
    val animation = SpringAnimation(view, property)
    val spring = SpringForce(finalPosition)
    spring.stiffness = stiffness
    spring.dampingRatio = dampingRatio
    animation.spring = spring
    return animation
}

/*
Enables immersive mode to automatically hide system bars.
 */
fun Activity.updateImmersiveMode() {
    if (hasWindowFocus()) {
        window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
    }
}