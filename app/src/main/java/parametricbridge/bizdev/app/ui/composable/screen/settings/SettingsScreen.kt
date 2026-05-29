package parametricbridge.bizdev.app.ui.composable.screen.settings

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import parametricbridge.bizdev.app.ui.theme.*

@Composable
fun SettingsScreen() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = "Settings",
            fontFamily = HeadingFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = OnSurface,
        )

        // About section
        SectionCard(title = "About") {
            SettingsRow(
                icon = Icons.Default.Business,
                label = "Company",
                value = "PARAMETRIC RECRUITMENT SOLUTIONS LTD",
            )
            Divider(color = Border, thickness = 0.5.dp)
            SettingsRow(
                icon = Icons.Default.AppSettingsAlt,
                label = "App Name",
                value = "Parametric Bridge",
            )
            Divider(color = Border, thickness = 0.5.dp)
            SettingsRow(
                icon = Icons.Default.Info,
                label = "Version",
                value = "1.0.0",
            )
        }

        // Customer Support section
        SectionCard(title = "Customer Support") {
            SettingsRow(
                icon = Icons.Default.OpenInBrowser,
                label = "Visit Our Website",
                value = "parametric-solutions.digital",
                isClickable = true,
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://parametric-solutions.digital"))
                    context.startActivity(intent)
                }
            )
            Divider(color = Border, thickness = 0.5.dp)
            SettingsRow(
                icon = Icons.Default.Email,
                label = "Email Support",
                value = "info@parametric-solutions.digital",
                isClickable = true,
                onClick = {
                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:info@parametric-solutions.digital")
                    }
                    context.startActivity(intent)
                }
            )
        }

        // Legal section
        SectionCard(title = "Legal") {
            SettingsRow(
                icon = Icons.Default.Policy,
                label = "Privacy Policy",
                isClickable = true,
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://parametric-solutions.digital/privacy"))
                    context.startActivity(intent)
                }
            )
            Divider(color = Border, thickness = 0.5.dp)
            SettingsRow(
                icon = Icons.Default.Gavel,
                label = "Terms of Service",
                isClickable = true,
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://parametric-solutions.digital/terms"))
                    context.startActivity(intent)
                }
            )
        }
    }
}

@Composable
private fun SectionCard(
    title: String,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column {
        Text(
            text = title,
            fontFamily = BodyFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
            color = Muted,
            modifier = Modifier.padding(start = 4.dp, bottom = 8.dp),
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        ) {
            Column(modifier = Modifier.padding(horizontal = 4.dp), content = content)
        }
    }
}

@Composable
private fun SettingsRow(
    icon: ImageVector,
    label: String,
    value: String? = null,
    isClickable: Boolean = false,
    onClick: (() -> Unit)? = null,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (isClickable && onClick != null) Modifier.clickable { onClick() }
                else Modifier
            )
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Primary,
            modifier = Modifier.size(20.dp),
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                fontFamily = BodyFamily,
                fontSize = 14.sp,
                color = OnSurface,
                fontWeight = FontWeight.Medium,
            )
            if (value != null) {
                Text(
                    text = value,
                    fontFamily = BodyFamily,
                    fontSize = 12.sp,
                    color = Muted,
                )
            }
        }
        if (isClickable) {
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = Muted,
                modifier = Modifier.size(18.dp),
            )
        }
    }
}
