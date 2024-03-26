package i.ll.algo.go.sorting

import i.ll.algo.go.App
import mu.KotlinLogging

class BubbleSort : Sort {

    private val log = KotlinLogging.logger {  }

    /** Bubble Sort - O(n^2), 𝛺(n), θ(n^2)
     *  "Bubbles" the largest value to the end of the collection iteratively.
     *
     *  Returns a sorted list, or null if runtime exceeds max permitted time and timeout flag is set to true.
     */
    override fun <T: Comparable<T>> sort(data: ArrayList<T>): SortResult<T> {
        log.info { this.startMessage() }
        val startTime = System.currentTimeMillis()
        var lastIdx = data.size - 1
        val startIdx = 0
        var terminated = false

        while (startIdx < lastIdx) {
            if ((System.currentTimeMillis() - startTime) >= App.MAX_SORT_TIME) {
                log.warn { this.terminateMessage() }
                terminated = true
                break
            }
            (startIdx until lastIdx).forEach { i ->
                val line1 = data[i]
                val line2 = data[i + 1]
                if (line1 > line2) {
                    data[i] = line2
                    data[i + 1] = line1
                }
            }
            lastIdx--
        }
        val runtime = System.currentTimeMillis() - startTime
        log.info { this.endMessage(runtime) }
        return SortResult(if (terminated) null else data, runtime, this::class.java.simpleName)
    }
}
