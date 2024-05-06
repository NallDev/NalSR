
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.RenderEffect
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import data.RequestState
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import org.jetbrains.compose.ui.tooling.preview.Preview

import ui.MainViewModel

@Composable
@Preview
fun App() {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        val viewModel = getViewModel(Unit, viewModelFactory { MainViewModel() })
        val state by viewModel.listCctv.collectAsState()
        state.DisplayResult(
            onIdle = {viewModel.getListCctv()},
            onLoading = {
                MainContent()
            },
            onSuccess = {
                MainContent(it.first().cctvName)
            },
            onError = {
                MainContent(it)
            }
        )
    }
}

@Composable
fun BlurredBackground() {
    val hazeState = remember { HazeState() }
        val background = Color(0xFF2A1F36)
    val circleColor1 = Color(0xFFD6026C)
    val circleColor2 = Color(0xFF50D5F2)
    val blurRadius = 80f

    Box(modifier = Modifier.fillMaxSize().background(color = background)) {
        // Blurred Circle 1
        Box(
            modifier = Modifier
                .size(300.dp)
                .background(circleColor1)
                .haze(hazeState)
        )
    }
}

@Composable
fun MainContent(text : String? = null) {
    val hazeState = remember { HazeState() }

    Scaffold(
        topBar = {
            TopAppBar(
                // Need to make app bar transparent to see the content behind
                backgroundColor = Color.Transparent,
                modifier = Modifier
                    .hazeChild(state = hazeState)
                    .fillMaxWidth(),
            ) {
                /* todo */
            }
        },
    ) {

    }
}