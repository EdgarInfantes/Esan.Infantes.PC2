package com.example.esaninfantespc2.util

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(onFinish: () -> Unit = {}) {
    val SplashBackgroundColor = Color(0xFF7948DB)

    val alphaAnim = remember { Animatable(0f) }
    val scaleAnim = remember { Animatable(0.8f) }

    LaunchedEffect(true) {
        launch {
            alphaAnim.animateTo(1f, animationSpec = tween(1000))
        }
        launch {
            scaleAnim.animateTo(1f, animationSpec = tween(1000))
        }
        delay(1500)
        onFinish()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SplashBackgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.graphicsLayer(
                alpha = alphaAnim.value,
                scaleX = scaleAnim.value,
                scaleY = scaleAnim.value
            )
        ) {
            /*
            Image(
                painter = painterResource(id = R.drawable.ic_launcher),
                contentDescription = "Logo App",
                modifier = Modifier.size(250.dp)
            )

             */

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Conversor de Monedas",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 32.sp,
                    color = Color.White
                ),
                fontWeight = FontWeight.Bold
            )
        }
    }
}
