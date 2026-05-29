package parametricbridge.bizdev.app.ui.composable.screen.bookings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.EventNote
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.androidx.compose.koinViewModel
import parametricbridge.bizdev.app.ui.state.BookingUiState
import parametricbridge.bizdev.app.ui.state.DataUiState
import parametricbridge.bizdev.app.ui.theme.*
import parametricbridge.bizdev.app.ui.viewmodel.BookingViewModel

@Composable
fun BookingsScreen(
    viewModel: BookingViewModel = koinViewModel(),
) {
    val bookingsState by viewModel.bookingsState.collectAsState()
    var cancelTarget by remember { mutableStateOf<BookingUiState?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        when (val state = bookingsState) {
            is DataUiState.Populated -> {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    item {
                        Text(
                            text = "My Bookings",
                            fontFamily = HeadingFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            color = OnSurface,
                            modifier = Modifier.padding(bottom = 4.dp),
                        )
                    }
                    items(state.data) { booking ->
                        BookingCard(
                            booking = booking,
                            onCancelClick = { cancelTarget = booking },
                        )
                    }
                }
            }
            else -> {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Icon(
                        imageVector = Icons.Default.EventNote,
                        contentDescription = null,
                        tint = Muted,
                        modifier = Modifier.size(64.dp),
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "No bookings yet",
                        fontFamily = HeadingFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        color = OnSurface,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Browse services and schedule\nyour first consultation.",
                        fontFamily = BodyFamily,
                        fontSize = 14.sp,
                        color = Muted,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    )
                }
            }
        }
    }

    cancelTarget?.let { booking ->
        AlertDialog(
            onDismissRequest = { cancelTarget = null },
            title = {
                Text(
                    text = "Cancel Booking",
                    fontFamily = HeadingFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = OnSurface,
                )
            },
            text = {
                Text(
                    text = "Are you sure you want to cancel your booking for \"${booking.serviceName}\"?",
                    fontFamily = BodyFamily,
                    fontSize = 14.sp,
                    color = OnSurface,
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.cancelBooking(booking.bookingNumber)
                        cancelTarget = null
                    }
                ) {
                    Text(
                        text = "Cancel Booking",
                        color = MaterialTheme.colorScheme.error,
                        fontFamily = BodyFamily,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { cancelTarget = null }) {
                    Text(
                        text = "Keep Booking",
                        color = Primary,
                        fontFamily = BodyFamily,
                        fontWeight = FontWeight.Medium,
                    )
                }
            },
            containerColor = Surface,
        )
    }
}

@Composable
private fun BookingCard(
    booking: BookingUiState,
    onCancelClick: () -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top,
            ) {
                Text(
                    text = booking.serviceName,
                    fontFamily = HeadingFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    color = OnSurface,
                    modifier = Modifier.weight(1f),
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(Success.copy(alpha = 0.15f))
                        .padding(horizontal = 10.dp, vertical = 4.dp),
                ) {
                    Text(
                        text = "Confirmed",
                        fontFamily = BodyFamily,
                        fontSize = 11.sp,
                        color = Success,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Icon(
                    imageVector = Icons.Default.CalendarToday,
                    contentDescription = null,
                    tint = Muted,
                    modifier = Modifier.size(14.dp),
                )
                Text(
                    text = booking.timestamp,
                    fontFamily = BodyFamily,
                    fontSize = 12.sp,
                    color = Muted,
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Ref: ${booking.bookingNumber}  ·  ${booking.customerFirstName} ${booking.customerLastName}",
                fontFamily = BodyFamily,
                fontSize = 12.sp,
                color = Muted,
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextButton(
                onClick = onCancelClick,
                contentPadding = PaddingValues(0.dp),
            ) {
                Text(
                    text = "Cancel Booking",
                    fontFamily = BodyFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.error,
                )
            }
        }
    }
}
