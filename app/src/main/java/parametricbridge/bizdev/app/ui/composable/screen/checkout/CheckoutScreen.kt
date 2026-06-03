package parametricbridge.bizdev.app.ui.composable.screen.checkout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.androidx.compose.koinViewModel
import parametricbridge.bizdev.app.data.repository.ServiceRepository
import parametricbridge.bizdev.app.ui.state.DataUiState
import parametricbridge.bizdev.app.ui.theme.*
import parametricbridge.bizdev.app.ui.viewmodel.CheckoutViewModel
import java.time.format.DateTimeFormatter
import java.util.Locale
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    serviceId: Int,
    onNavigateToBookingsScreen: () -> Unit,
    viewModel: CheckoutViewModel = koinViewModel(),
    serviceRepository: ServiceRepository = koinInject(),
) {
    val orderState by viewModel.orderState.collectAsState()
    val emailInvalid by viewModel.emailInvalidState.collectAsState()
    val service = remember(serviceId) { serviceRepository.getById(serviceId) }

    var notes by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }

    LaunchedEffect(orderState) {
        if (orderState is DataUiState.Populated) showSuccessDialog = true
    }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val date = java.time.Instant.ofEpochMilli(millis)
                            .atZone(java.time.ZoneId.systemDefault())
                            .toLocalDate()
                        val fmt = DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.ENGLISH)
                        selectedDate = date.format(fmt)
                    }
                    showDatePicker = false
                }) {
                    Text("Confirm", color = Primary, fontFamily = BodyFamily, fontWeight = FontWeight.SemiBold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancel", color = Muted, fontFamily = BodyFamily)
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = {},
            icon = {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = Success,
                    modifier = Modifier.size(48.dp),
                )
            },
            title = {
                Text(
                    text = "Booking Confirmed!",
                    fontFamily = HeadingFamily,
                    fontWeight = FontWeight.Bold,
                    color = OnSurface,
                )
            },
            text = {
                Text(
                    text = "Your consultation has been scheduled. A confirmation will be sent to your email. Our consultant will be in touch shortly.",
                    fontFamily = BodyFamily,
                    fontSize = 14.sp,
                    color = OnSurface,
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showSuccessDialog = false
                        onNavigateToBookingsScreen()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Primary),
                    shape = RoundedCornerShape(10.dp),
                ) {
                    Text("View My Bookings", fontFamily = BodyFamily, fontWeight = FontWeight.SemiBold, color = Color.White)
                }
            },
            containerColor = Surface,
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        // Service summary
        service?.let {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = ChipBackground),
                elevation = CardDefaults.cardElevation(0.dp),
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = it.name,
                            fontFamily = HeadingFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 15.sp,
                            color = OnSurface,
                        )
                        Text(
                            text = "${it.durationMinutes} min session",
                            fontFamily = BodyFamily,
                            fontSize = 12.sp,
                            color = Muted,
                        )
                    }
                    Text(
                        text = "\$${it.price.toInt()}",
                        fontFamily = HeadingFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Primary,
                    )
                }
            }
        }

        Text(
            text = "Your Details",
            fontFamily = HeadingFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            color = OnSurface,
        )

        OutlinedTextField(
            value = viewModel.customerFirstName,
            onValueChange = viewModel::updateCustomerFirstName,
            label = { Text("First Name", fontFamily = BodyFamily) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
        )

        OutlinedTextField(
            value = viewModel.customerLastName,
            onValueChange = viewModel::updateCustomerLastName,
            label = { Text("Last Name", fontFamily = BodyFamily) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
        )



        OutlinedTextField(
            value = viewModel.customerEmail,
            onValueChange = viewModel::updateCustomerEmail,
            label = { Text("Email Address", fontFamily = BodyFamily) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            isError = emailInvalid,
            supportingText = if (emailInvalid) {
                { Text("Please enter a valid email address", color = MaterialTheme.colorScheme.error, fontFamily = BodyFamily) }
            } else null,
        )

        OutlinedTextField(
            value = selectedDate,
            onValueChange = {},
            readOnly = true,
            label = { Text("Preferred Date", fontFamily = BodyFamily) },
            trailingIcon = {
                IconButton(onClick = { showDatePicker = true }) {
                    Icon(Icons.Default.CalendarToday, contentDescription = "Pick date", tint = Primary)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
        )

        OutlinedTextField(
            value = notes,
            onValueChange = { notes = it },
            label = { Text("Additional Notes", fontFamily = BodyFamily) },
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            shape = RoundedCornerShape(12.dp),
            maxLines = 4,
        )

        Spacer(modifier = Modifier.height(8.dp))

        val formComplete = viewModel.customerFirstName.isNotBlank()
            && viewModel.customerLastName.isNotBlank()
            && viewModel.customerEmail.isNotBlank()
            && selectedDate.isNotBlank()

        Button(
            onClick = { viewModel.placeBooking(serviceId) },
            enabled = formComplete,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Primary,
                disabledContainerColor = Border,
            ),
            shape = RoundedCornerShape(12.dp),
        ) {
            Text(
                text = "Confirm Booking",
                fontFamily = HeadingFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = Color.White,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}
