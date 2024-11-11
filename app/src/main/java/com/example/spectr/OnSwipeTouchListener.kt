import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.MotionEvent
import android.view.View
import kotlin.math.abs

open class OnSwipeTouchListener(ctx: Context) : View.OnTouchListener {

    private var lastTouchTime: Long = 0
    private var lastTouchPoint: Pair<Float, Float>? = null
    private var isSwiping = false

    companion object {
        private const val SWIPE_THRESHOLD = 100
        private const val SWIPE_VELOCITY_THRESHOLD = 100
        private const val DOUBLE_TAP_TIMEOUT = 300 // Время для определения двойного касания в миллисекундах
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val currentTime = System.currentTimeMillis()
                if (currentTime - lastTouchTime <= DOUBLE_TAP_TIMEOUT) {
                    onDoubleTapAction()
                }
                lastTouchTime = currentTime
                lastTouchPoint = Pair(event.x, event.y)
                isSwiping = false // Сброс состояния свайпа
            }

            MotionEvent.ACTION_MOVE -> {
                lastTouchPoint?.let { lastPoint ->
                    val diffX = event.x - lastPoint.first
                    val diffY = event.y - lastPoint.second

                    // Проверяем, превышает ли движение порог свайпа
                    if (!isSwiping && (abs(diffX) > SWIPE_THRESHOLD || abs(diffY) > SWIPE_THRESHOLD)) {
                        isSwiping = true
                    }
                }
            }

            MotionEvent.ACTION_UP -> {
                if (isSwiping) {
                    lastTouchPoint?.let { lastPoint ->
                        val diffX = event.x - lastPoint.first
                        val diffY = event.y - lastPoint.second

                        // Оценка скорости (приблизительно)
                        val velocityX = event.x - lastPoint.first // Простой расчет
                        val velocityY = event.y - lastPoint.second // Простой расчет

                        if (abs(diffX) > abs(diffY)) {
                            if (abs(diffX) > SWIPE_THRESHOLD && abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                                if (diffX > 0) {
                                    onSwipeRight()
                                } else {
                                    onSwipeLeft()
                                }
                            }
                        } else {
                            if (abs(diffY) > SWIPE_THRESHOLD && abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                                if (diffY > 0) {
                                    onSwipeBottom()
                                } else {
                                    onSwipeTop()
                                }
                            }
                        }
                    }
                }
            }
        }
        return true
    }

    open fun onSwipeRight() {
        Log.d("debug", "Swiped right")
    }

    open fun onSwipeLeft() {
        Log.d("debug", "Swiped left")
    }

    open fun onSwipeTop() {
        Log.d("debug", "Swiped up")
    }

    open fun onSwipeBottom() {
        Log.d("debug", "Swiped down")
    }

    open fun onDoubleTapAction() {
        Log.d("debug", "Double tap detected")
    }
}