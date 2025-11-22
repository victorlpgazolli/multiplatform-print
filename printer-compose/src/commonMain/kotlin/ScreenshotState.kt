import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.layer.GraphicsLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds


@Composable
fun rememberScreenshotState(refreshRate: Duration = 20.milliseconds): ScreenshotState {
    val graphicsLayer = rememberGraphicsLayer()
    return remember { ScreenshotStateImpl(
        refreshRate = refreshRate,
        graphicsLayer = graphicsLayer,
    ) }
}

interface ScreenshotState {
    val refreshRate: Duration
    val graphicsLayer: GraphicsLayer

    @Composable
    fun startRecording(): Flow<ImageBitmap>
    suspend fun takeScreenshot(): ImageBitmap
}


class ScreenshotStateImpl internal constructor(
    override val refreshRate: Duration,
    override val graphicsLayer: GraphicsLayer,
): ScreenshotState {

    @Composable
    override fun startRecording(): Flow<ImageBitmap> {
        return remember {
            flow {
                while (true) {
                    emit(takeScreenshot())
                    delay(refreshRate.inWholeMilliseconds)
                }
            }
        }
    }

    override suspend fun takeScreenshot(): ImageBitmap {
        return graphicsLayer.toImageBitmap()
    }

}