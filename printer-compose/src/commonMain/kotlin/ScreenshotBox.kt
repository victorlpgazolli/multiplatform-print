
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.layer.drawLayer

@Composable
fun ScreenshotArea(
    screenshotState: ScreenshotState,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = Modifier
            .drawWithContent {
                screenshotState.graphicsLayer.record {
                    this@drawWithContent.drawContent()
                }
                drawLayer(screenshotState.graphicsLayer)

            }
            .background(Color.White)

    ) {
        content()
    }
}
