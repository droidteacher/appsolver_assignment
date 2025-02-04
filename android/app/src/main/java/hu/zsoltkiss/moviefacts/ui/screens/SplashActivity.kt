package hu.zsoltkiss.moviefacts.ui.screens

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import hu.zsoltkiss.moviefacts.R
import hu.zsoltkiss.moviefacts.ui.theme.MovieFactsTheme
import hu.zsoltkiss.moviefacts.ui.theme.splashBackground
import java.util.Timer
import kotlin.concurrent.timerTask

class SplashActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieFactsTheme {
                Scaffold(

                    content = {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .background(splashBackground)

                        ) {
                            AnimatedLogo(modifier = Modifier.padding(it), 800)
                        }
                    })

                }
            }
        }

    override fun onResume() {
        super.onResume()
        val intent = Intent(this, MainActivity::class.java)
        Timer().schedule(timerTask {
            startActivity(intent)
        }, 4000)
    }

}

@Composable
fun AnimatedLogo(modifier: Modifier, speed: Int) {
    val image: Painter = painterResource(id = R.drawable.splash_icon)

    val value by rememberInfiniteTransition(label = "").animateFloat(
        initialValue = 25f,
        targetValue = -25f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = speed,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    Image(painter = image, contentDescription = "App logo animated", modifier = Modifier
        .graphicsLayer(
            transformOrigin = TransformOrigin(
                pivotFractionX = 0.5f,
                pivotFractionY = 0.0f,
            ),
            rotationZ = value
        ))

}