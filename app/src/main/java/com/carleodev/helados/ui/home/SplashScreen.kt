package com.carleodev.helados.ui.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight


import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.carleodev.helados.AppViewModelProvider
import com.carleodev.helados.BuildConfig
import com.carleodev.helados.navigation.NavigationDestination
import com.carleodev.helados.viewmodels.SplashViewModel


import kotlinx.coroutines.delay

object SplashDestino : NavigationDestination {
    override val route = "splash"
    override val titleRes = 1
}


@Composable
fun AnimatedSplashScreen(navController: NavHostController,
                         viewModel: SplashViewModel = viewModel(factory = AppViewModelProvider.Factory)) {

    var startAnimation by remember { mutableStateOf(false) }


    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 3000
        )
    )


    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(4000)
        navController.popBackStack()
        navController.navigate(ListarItemDestination.route)

    }
    Splash(alpha = alphaAnim.value)
}

@Composable
fun Splash(alpha: Float) {

    val rainbowColorsBrush = remember {
        Brush.sweepGradient(
            listOf(
                Color(0xFF9575CD),
                Color(0xFFBA68C8),
                Color(0xFFE57373),
                Color(0xFFFFB74D),
                Color(0xFFFFF176),
                Color(0xFFAED581),
                Color(0xFF4DD0E1),
                Color(0xFF9575CD)
            )
        )
    }

    val largeRadialGradient = object : ShaderBrush() {
        override fun createShader(size: Size): Shader {
            val biggerDimension = maxOf(size.height, size.width)
            return RadialGradientShader(
                colors = listOf(Color(0xFF2be4dc), Color(0xFF243484)),
                center = size.center,
                radius = biggerDimension / 2f,
                colorStops = listOf(0f, 0.95f)
            )
        }
    }
    Box(
        modifier = Modifier
            .background( rainbowColorsBrush)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {


        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            val borderWidth = 4.dp
           /* Image(painterResource(R.drawable.ancla),
                "Ancla",
                modifier = Modifier.size(150.dp)
                    .border(
                        BorderStroke(borderWidth, rainbowColorsBrush),
                        CircleShape
                    )
                    .padding(borderWidth)
                    .clip(CircleShape))*/

            Spacer(modifier = Modifier.height(48.dp))
            Text(text="HELADOS XXX",
                color = Color.Black, fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Cursive)

            Text(text="Aplicación de Tickets",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(32.dp))

            Text(text="Version ${BuildConfig.VERSION_NAME}",
                fontSize = 12.sp)

            Spacer(modifier = Modifier.height(20.dp))

            /*Image(painterResource(R.drawable.botepedal),
                "Botes",
                modifier = Modifier.size(100.dp))*/



        }


    }
}

@Composable
@Preview
fun SplashScreenPreview() {
    Splash(alpha = 1f)
}

@Composable
@Preview(uiMode = UI_MODE_NIGHT_YES)
fun SplashScreenDarkPreview() {
    Splash(alpha = 1f)
}