package parametricbridge.bizdev.app.ui.composable.screen.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel
import parametricbridge.bizdev.app.R
import parametricbridge.bizdev.app.ui.theme.GradientStart
import parametricbridge.bizdev.app.ui.theme.GradientEnd
import parametricbridge.bizdev.app.ui.theme.HeadingFamily
import parametricbridge.bizdev.app.ui.viewmodel.KBPMHSplashVM

@Composable
fun SplashScreen(
    onNavigateToHomeScreen: () -> Unit,
    onNavigateToOnboarding: () -> Unit,
    viewModel: KBPMHSplashVM = koinViewModel(),
) {
    val onboardingComplete by viewModel.isOnboardingCompleted.collectAsState()
    val scale = remember { Animatable(0.8f) }
    val alpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        scale.animateTo(1.0f, animationSpec = tween(800))
        alpha.animateTo(1.0f, animationSpec = tween(600))
        delay(1500)
        if (onboardingComplete == true) onNavigateToHomeScreen() else onNavigateToOnboarding()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(listOf(GradientStart, GradientEnd))
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(R.drawable.icon),
                contentDescription = "App icon",
                modifier = Modifier
                    .size(120.dp)
                    .scale(scale.value),
                contentScale = ContentScale.Fit,
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Parametric Bridge",
                fontFamily = HeadingFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 26.sp,
                color = Color.White,
            )
        }
    }
}
