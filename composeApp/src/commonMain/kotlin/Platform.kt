import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

@Composable
expect fun VideoPlayer(modifier: Modifier, url: String)

expect fun logError(tag: String, message: String)