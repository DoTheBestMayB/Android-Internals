import java.util.concurrent.LinkedBlockingQueue
import kotlin.concurrent.thread

/**
 * Simplified Looper
 */
class MyLooper {

    private var thread: Thread? = null
    private val messageQueue = LinkedBlockingQueue<Runnable>()

    fun enqueue(runnable: Runnable) {
        if (thread == null) {
            createLooperThread()
        }
        messageQueue.offer(runnable)
    }

    private fun createLooperThread() {
        thread = thread ?: thread {
            // hard quit으로 exception이 발생하는 경우를 위한 try-catch
            try {
                while (true) { // keep alive를 위해 while 문 필요
                    // queue에서 첫 아이템을 꺼내고 제거한다. 만약 비어 있다면 아이템이 들어올 때까지 기다린다.(blocking)
                    val message = messageQueue.take()

                    message.run() // 요청 처리하기. MainThread Looper라면 UI를 그리거나 callback 함수를 처리하는 등의 작업을 할 것이다.
                }
            } catch (_: InterruptedException) {
                return@thread
            }
        }
    }

    // hard quit : 요청 즉시 종료한다.
    fun quit() {
        thread?.interrupt()
        thread = null
    }

    // safe quit : Queue에 있는 작업을 모두 처리하고 종료한다.
    fun quitSafely() {

    }
}
