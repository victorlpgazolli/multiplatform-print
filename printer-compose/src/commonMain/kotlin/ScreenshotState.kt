import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.layer.GraphicsLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Composable
fun rememberScreenshotState(refreshScreenshotTimeout: Long? = null): ScreenshotState {
    val graphicsLayer = rememberGraphicsLayer()
    return remember { ScreenshotState(
        refreshScreenshotTimeout = refreshScreenshotTimeout ?: 20L,
        graphicsLayer = graphicsLayer,
    ) }
}


class ScreenshotState internal constructor(
    val refreshScreenshotTimeout: Long,
    val graphicsLayer: GraphicsLayer,
) {

    @Composable
    fun startRecording(): Flow<ImageBitmap> {
        return remember {
            flow {
                while (true) {
                    emit(takeScreenshot())
                    delay(refreshScreenshotTimeout)
                }
            }
        }
    }

    suspend fun takeScreenshot(): ImageBitmap {
        return graphicsLayer.toImageBitmap()
    }

}