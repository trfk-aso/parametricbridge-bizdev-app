package parametricbridge.bizdev.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import parametricbridge.bizdev.app.ui.composable.approot.AppRoot
import parametricbridge.bizdev.app.ui.theme.ServiceSkeletonTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ServiceSkeletonTheme {
                AppRoot()
            }
        }
    }
}