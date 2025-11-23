
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.layer.drawLayer

@Composable
fun ScreenshotArea(
    screenshotState: ScreenshotState,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .drawWithContent {

                screenshotState.graphicsLayer.record {
                    this@drawWithContent.drawRect(
                        color = Color.White,
                        size = size
                    )
                    this@drawWithContent.drawContent()
                }
                drawLayer(screenshotState.graphicsLayer)

            }

    ) {
        content()
    }
}
