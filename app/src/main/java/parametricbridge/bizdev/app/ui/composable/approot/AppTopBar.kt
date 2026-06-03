package parametricbridge.bizdev.app.ui.composable.approot

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import parametricbridge.bizdev.app.R
import parametricbridge.bizdev.app.ui.composable.navigation.NavRoute
import kotlin.reflect.KClass

private val canNavigateBackRoutes: List<KClass<out NavRoute>> = listOf(
    NavRoute.ServiceDetails::class,
    NavRoute.Checkout::class,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    currentDestination: NavDestination?,
    onNavigateBack: () -> Unit,
) {
    val canNavigateBack = currentDestination.matchesAnyRoute(canNavigateBackRoutes)

    TopAppBar(
        title = {
            Text(
                text = getTitle(currentDestination)?.let { stringResource(it) }.orEmpty()
            )
        },

        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Navigate Back",
                        tint = MaterialTheme.colorScheme.onPrimary,
                    )
                }
            }
        },

        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
        ),
    )
}

private fun getTitle(currentDestination: NavDestination?): Int? {
    return when {
        currentDestination == null -> null

        currentDestination.hierarchy.any { it.hasRoute(NavRoute.Home::class) } -> {
            (R.string.parametric_bridge)
        }

        currentDestination.hierarchy.any { it.hasRoute(NavRoute.ServiceDetails::class) } -> {
            (R.string.details)
        }

        currentDestination.hierarchy.any { it.hasRoute(NavRoute.Checkout::class) } -> {
            (R.string.checkout)
        }

        currentDestination.hierarchy.any { it.hasRoute(NavRoute.Bookings::class) } -> {
            R.string.bottom_bar_nav_item_bookings_title
        }

        currentDestination.hierarchy.any { it.hasRoute(NavRoute.Settings::class) } -> {
            R.string.bottom_bar_nav_item_settings_title
        }

        else -> null
    }
}