package parametricbridge.bizdev.app.ui.composable.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel
import parametricbridge.bizdev.app.data.model.ServiceModel
import parametricbridge.bizdev.app.ui.state.DataUiState
import parametricbridge.bizdev.app.ui.theme.*
import parametricbridge.bizdev.app.ui.viewmodel.ServiceViewModel

private data class CategoryItem(val label: String, val icon: ImageVector)

private val categories = listOf(
    CategoryItem("Strategic", Icons.Default.TrendingUp),
    CategoryItem("Org Design", Icons.Default.AccountTree),
    CategoryItem("Efficiency", Icons.Default.Speed),
    CategoryItem("Leadership", Icons.Default.Person),
)

@Composable
fun HomeScreen(
    onNavigateToServiceDetails: (Int) -> Unit,
    viewModel: ServiceViewModel = koinViewModel(),
) {
    val servicesState by viewModel.servicesState.collectAsState()
    var selectedCategory by remember { mutableStateOf<String?>(null) }

    val allServices = when (val s = servicesState) {
        is DataUiState.Populated -> s.data
        else -> emptyList()
    }

    val categoryMap = mapOf(
        "Strategic" to "Strategic Planning",
        "Org Design" to "Organizational Development",
        "Efficiency" to "Performance & Efficiency",
        "Leadership" to "Leadership & Coaching",
    )

    val filteredServices = if (selectedCategory == null) allServices
    else allServices.filter { it.category == categoryMap[selectedCategory] }

    val featuredServices = allServices.take(4)
    val pagerState = rememberPagerState(pageCount = { featuredServices.size })

    LaunchedEffect(featuredServices.size) {
        while (featuredServices.size > 1) {
            delay(3500)
            val next = (pagerState.currentPage + 1) % featuredServices.size
            pagerState.animateScrollToPage(next)
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
        contentPadding = PaddingValues(bottom = 24.dp),
    ) {
        // Next available banner
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Brush.horizontalGradient(listOf(GradientStart, GradientEnd)))
                    .padding(20.dp)
            ) {
                Column {
                    Text(
                        text = "Next Available Slot",
                        fontFamily = BodyFamily,
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.8f),
                        fontWeight = FontWeight.Medium,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Today at 2:00 PM",
                        fontFamily = HeadingFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.White,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Strategic Planning Session · From \$800",
                        fontFamily = BodyFamily,
                        fontSize = 13.sp,
                        color = Color.White.copy(alpha = 0.85f),
                    )
                }
            }
        }

        // Featured carousel
        item {
            if (featuredServices.isNotEmpty()) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(180.dp)
                        .clip(RoundedCornerShape(16.dp)),
                ) { page ->
                    val service = featuredServices[page]
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable { onNavigateToServiceDetails(service.id) }
                    ) {
                        AsyncImage(
                            model = service.imageUrl,
                            contentDescription = service.name,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop,
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        listOf(Color.Transparent, Color(0xCC1A1F2E)),
                                        startY = 60f
                                    )
                                )
                        )
                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(16.dp)
                        ) {
                            Text(
                                text = service.category,
                                fontFamily = BodyFamily,
                                fontSize = 11.sp,
                                color = Color.White.copy(alpha = 0.75f),
                            )
                            Text(
                                text = service.name,
                                fontFamily = HeadingFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = Color.White,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                    }
                }
                // Dot indicators
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    repeat(featuredServices.size) { index ->
                        Box(
                            modifier = Modifier
                                .size(if (pagerState.currentPage == index) 8.dp else 5.dp)
                                .clip(CircleShape)
                                .background(
                                    if (pagerState.currentPage == index) Primary else Border
                                )
                        )
                    }
                }
            }
        }

        // Category icons row
        item {
            Text(
                text = "Browse Categories",
                fontFamily = HeadingFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = OnSurface,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp),
            )
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(categories) { cat ->
                    val isSelected = selectedCategory == cat.label
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clickable { selectedCategory = if (selectedCategory == cat.label) null else cat.label }
                            .padding(4.dp),
                    ) {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(if (isSelected) Primary else ChipBackground),
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(
                                imageVector = cat.icon,
                                contentDescription = cat.label,
                                tint = if (isSelected) Color.White else ChipContent,
                                modifier = Modifier.size(28.dp),
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = cat.label,
                            fontFamily = BodyFamily,
                            fontSize = 11.sp,
                            color = if (isSelected) Primary else Muted,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                        )
                    }
                }
            }
        }

        // Services list header
        item {
            Text(
                text = if (selectedCategory != null) "$selectedCategory Services" else "All Services",
                fontFamily = HeadingFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = OnSurface,
                modifier = Modifier.padding(start = 16.dp, top = 20.dp, bottom = 8.dp),
            )
        }

        // Service cards
        items(filteredServices) { service ->
            ServiceCard(
                service = service,
                onClick = { onNavigateToServiceDetails(service.id) },
            )
        }
    }
}

@Composable
private fun ServiceCard(
    service: ServiceModel,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Row(modifier = Modifier.padding(12.dp)) {
            AsyncImage(
                model = service.imageUrl,
                contentDescription = service.name,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop,
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top,
                ) {
                    Text(
                        text = service.name,
                        fontFamily = HeadingFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = OnSurface,
                        modifier = Modifier.weight(1f),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(ChipBackground)
                            .padding(horizontal = 8.dp, vertical = 3.dp),
                    ) {
                        Text(
                            text = "Book Now",
                            fontFamily = BodyFamily,
                            fontSize = 10.sp,
                            color = ChipContent,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = service.description,
                    fontFamily = BodyFamily,
                    fontSize = 12.sp,
                    color = Muted,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 18.sp,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(
                        text = "From \$${service.price.toInt()}",
                        fontFamily = HeadingFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = Primary,
                    )
                    Text(
                        text = "·",
                        color = Muted,
                        fontSize = 12.sp,
                    )
                    Text(
                        text = "${service.durationMinutes} min",
                        fontFamily = BodyFamily,
                        fontSize = 12.sp,
                        color = Muted,
                    )
                }
            }
        }
    }
}
