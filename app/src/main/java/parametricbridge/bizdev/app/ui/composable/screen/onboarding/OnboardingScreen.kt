package parametricbridge.bizdev.app.ui.composable.screen.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import parametricbridge.bizdev.app.R
import parametricbridge.bizdev.app.ui.theme.*
import parametricbridge.bizdev.app.ui.viewmodel.KBPMHOnboardingVM

private data class OnboardingSlide(
    val icon: Int,
    val title: String,
    val description: String,
    val imageUrl: String,
)

private val slides = listOf(
    OnboardingSlide(
        icon = R.drawable.icon,
        title = "Unlock Strategic Potential",
        description = "Transform your organization with expert management consulting tailored to your unique business challenges and growth ambitions.",
        imageUrl = "https://images.unsplash.com/photo-1552664730-d307ca884978?w=600"
    ),
    OnboardingSlide(
        icon = R.drawable.icon,
        title = "Expert-Led Solutions",
        description = "Our seasoned consultants bring decades of experience in organizational design, process optimization, and change management.",
        imageUrl = "https://images.unsplash.com/photo-1542744173-8e7e53415bb0?w=600"
    ),
    OnboardingSlide(
        icon = R.drawable.icon,
        title = "Measurable Results",
        description = "We deliver concrete outcomes — improved efficiency, stronger teams, and sustainable competitive advantage for your business.",
        imageUrl = "https://images.unsplash.com/photo-1553877522-43269d4ea984?w=600"
    ),
)

@Composable
fun OnboardingScreen(
    onNavigateToHomeScreen: () -> Unit,
    viewModel: KBPMHOnboardingVM = koinViewModel(),
) {
    val pagerState = rememberPagerState(pageCount = { slides.size })
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f),
        ) { page ->
            val slide = slides[page]
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Spacer(modifier = Modifier.height(40.dp))
                Image(
                    painter = painterResource(slide.icon),
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                )
                Spacer(modifier = Modifier.height(28.dp))
                Text(
                    text = slide.title,
                    fontFamily = HeadingFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = OnSurface,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = slide.description,
                    fontFamily = BodyFamily,
                    fontSize = 14.sp,
                    color = Muted,
                    textAlign = TextAlign.Center,
                    lineHeight = 22.sp,
                )
                Spacer(modifier = Modifier.height(28.dp))
                AsyncImage(
                    model = slide.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop,
                )
                Spacer(modifier = Modifier.height(40.dp))
            }
        }

        // Dot indicators
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(bottom = 16.dp),
        ) {
            repeat(slides.size) { index ->
                Box(
                    modifier = Modifier
                        .size(if (pagerState.currentPage == index) 10.dp else 7.dp)
                        .clip(CircleShape)
                        .background(
                            if (pagerState.currentPage == index) Primary else Border
                        )
                )
            }
        }

        val isLastPage = pagerState.currentPage == slides.size - 1

        if (isLastPage) {
            Button(
                onClick = {
                    viewModel.setOnboardingCompleted()
                    onNavigateToHomeScreen()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 8.dp)
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Primary),
                shape = RoundedCornerShape(12.dp),
            ) {
                Text(
                    text = "Get Started",
                    fontFamily = HeadingFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = Color.White,
                )
            }
        } else {
            Button(
                onClick = {
                    scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 8.dp)
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Primary),
                shape = RoundedCornerShape(12.dp),
            ) {
                Text(
                    text = "Next",
                    fontFamily = HeadingFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = Color.White,
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}
