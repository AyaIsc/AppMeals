package com.example.appMeals.ui

import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.appMeals.R
import kotlinx.coroutines.delay
/**
 * Composable function that displays a splash screen with an animation.
 * The animation fades in the app logo, holds for a certain duration, and then fades out.
 * After the animation ends, it triggers the [onAnimationEnd] callback.
 *
 * @param onAnimationEnd Callback function triggered when the animation ends.
 *
 * @Composable
 * @SplashScreen
 * Displays a splash screen with a fading animation for the app logo.
 */

@Composable
fun SplashScreen(onAnimationEnd: () -> Unit) {

    var alpha by remember { mutableFloatStateOf(0f) }


    val animationDuration = 3000


    LaunchedEffect(Unit) {
        val fadeInDuration = 1000
        val fadeOutDuration = 1000
        val displayDuration = animationDuration - fadeInDuration - fadeOutDuration


        animate(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = tween(durationMillis = fadeInDuration)
        ) { value, _ ->
            alpha = value
        }


        delay(displayDuration.toLong())


        animate(
            initialValue = 1f,
            targetValue = 0f,
            animationSpec = tween(durationMillis = fadeOutDuration)
        ) { value, _ ->
            alpha = value
        }


        onAnimationEnd()
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer(alpha = alpha),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "App Logo",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}
