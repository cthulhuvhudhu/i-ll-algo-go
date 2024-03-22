package i.ll.algo.go.sorting

import i.ll.algo.go.App

class BubbleSort(override val timeLimit: Boolean = false) : Sort {

    override var runtime: Long = -1

    /** Bubble Sort - O(n^2), 𝛺(n), θ(n^2)
     *  "Bubbles" the largest value to the end of the collection iteratively.
     *
     *  Returns a sorted list, or null if runtime exceeds max permitted time and timeout flag is set to true.
     */
    override fun <T: Comparable<T>> sort(data: Array<T>): Array<T>? {
        val startTime = System.currentTimeMillis()
        var lastIdx = data.size - 1
        val startIdx = 0
        var terminated = false

        while (startIdx < lastIdx) {
            if ( timeLimit && (System.currentTimeMillis() - startTime) < App.MAX_SORT_TIME) {
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
        runtime = System.currentTimeMillis() - startTime
        return if (terminated) null else data
    }
}
