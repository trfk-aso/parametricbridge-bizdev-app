package parametricbridge.bizdev.app.ui.composable.screen.servicedetails

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel
import parametricbridge.bizdev.app.ui.state.DataUiState
import parametricbridge.bizdev.app.ui.theme.*
import parametricbridge.bizdev.app.ui.viewmodel.ServiceDetailsViewModel
import java.time.format.DateTimeFormatter

@Composable
fun ServiceDetailsScreen(
    serviceId: Int,
    onNavigateToCheckout: (Int) -> Unit,
    viewModel: ServiceDetailsViewModel = koinViewModel(),
) {
    val serviceState by viewModel.serviceState.collectAsState()
    var selectedTime by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(serviceId) {
        viewModel.observeServiceById(serviceId)
    }

    when (val state = serviceState) {
        is DataUiState.Populated -> {
            val service = state.data
            val timeFormatter = DateTimeFormatter.ofPattern("h:mm a")

            Box(modifier = Modifier.fillMaxSize().background(Background)) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(bottom = 100.dp)
                ) {
                    // Hero image
                    AsyncImage(
                        model = service.imageUrl,
                        contentDescription = service.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(280.dp)
                            .clip(
                                RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
                            ),
                        contentScale = ContentScale.Crop,
                    )

                    Column(modifier = Modifier.padding(20.dp)) {
                        // Category chip
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(ChipBackground)
                                .padding(horizontal = 12.dp, vertical = 5.dp)
                        ) {
                            Text(
                                text = service.category,
                                fontFamily = BodyFamily,
                                fontSize = 12.sp,
                                color = ChipContent,
                                fontWeight = FontWeight.SemiBold,
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Name
                        Text(
                            text = service.name,
                            fontFamily = HeadingFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            color = OnSurface,
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Price and duration
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                        ) {
                            Text(
                                text = "From \$${service.price.toInt()}",
                                fontFamily = HeadingFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                color = Primary,
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Schedule,
                                    contentDescription = null,
                                    tint = Muted,
                                    modifier = Modifier.size(16.dp),
                                )
                                Text(
                                    text = "${service.durationMinutes} min",
                                    fontFamily = BodyFamily,
                                    fontSize = 14.sp,
                                    color = Muted,
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Divider(color = Border)

                        Spacer(modifier = Modifier.height(16.dp))

                        // Description
                        Text(
                            text = "About This Service",
                            fontFamily = HeadingFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            color = OnSurface,
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = service.description,
                            fontFamily = BodyFamily,
                            fontSize = 14.sp,
                            color = OnSurface.copy(alpha = 0.75f),
                            lineHeight = 22.sp,
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        // Features
                        if (service.features.isNotEmpty()) {
                            Text(
                                text = "What's Included",
                                fontFamily = HeadingFamily,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp,
                                color = OnSurface,
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            service.features.forEach { feature ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(vertical = 4.dp),
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = Success,
                                        modifier = Modifier.size(18.dp),
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = feature,
                                        fontFamily = BodyFamily,
                                        fontSize = 14.sp,
                                        color = OnSurface,
                                    )
                                }
                            }
                        }

                        // Availability slots
                        if (!service.availableTime.isNullOrEmpty()) {
                            Spacer(modifier = Modifier.height(20.dp))
                            Text(
                                text = "Available Times",
                                fontFamily = HeadingFamily,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp,
                                color = OnSurface,
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                            ) {
                                items(service.availableTime) { time ->
                                    val timeStr = time.format(timeFormatter)
                                    val isSelected = selectedTime == timeStr
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(20.dp))
                                            .background(if (isSelected) Primary else ChipBackground)
                                            .clickable { selectedTime = timeStr }
                                            .padding(horizontal = 16.dp, vertical = 8.dp),
                                    ) {
                                        Text(
                                            text = timeStr,
                                            fontFamily = BodyFamily,
                                            fontSize = 13.sp,
                                            color = if (isSelected) Color.White else ChipContent,
                                            fontWeight = FontWeight.Medium,
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // Sticky Book Now button
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .background(Surface)
                        .padding(16.dp),
                ) {
                    Button(
                        onClick = { onNavigateToCheckout(serviceId) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Primary),
                        shape = RoundedCornerShape(12.dp),
                    ) {
                        Text(
                            text = "Book a Consultation",
                            fontFamily = HeadingFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            color = Color.White,
                        )
                    }
                }
            }
        }
        else -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Primary)
            }
        }
    }
}
