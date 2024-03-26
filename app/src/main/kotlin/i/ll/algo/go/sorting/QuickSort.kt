package i.ll.algo.go.sorting

import i.ll.algo.go.App
import mu.KotlinLogging

class QuickSort : Sort {

    private val log = KotlinLogging.logger {  }

    /** Quick Sort - O(n^2), 𝛺(n log n), θ(n log n)
     *  Chooses a pivot value, sorts values to less- and greater-than, and iteratively applies to subpartitions.
     */
    override fun <T: Comparable<T>> sort(data: ArrayList<T>): SortResult<T> {
        log.info { this.startMessage() }
        val startTime = System.currentTimeMillis()
        var terminated = false
        val indices = ArrayDeque<Pair<Int, Int>>()
        indices.add(0 to data.size - 1)

        while (indices.isNotEmpty()) {
            if ((System.currentTimeMillis() - startTime) >= App.MAX_SORT_TIME) {
                log.warn { this.terminateMessage() }
                terminated = true
                break
            }
            val pivotRange = indices.removeFirst()
            if (pivotRange.first !in data.indices
                || pivotRange.second !in data.indices
                || pivotRange.first >= pivotRange.second) {
                continue
            }

            // use last for pivot
            var lIdx = pivotRange.first
            var rIdx = pivotRange.second - 1

            while (lIdx < rIdx) {
                if (data[lIdx] > data[pivotRange.second]) {
                    // swap needed
                    if (data[rIdx] < data[pivotRange.second]) {
                        val temp = data[lIdx]
                        data[lIdx] = data[rIdx]
                        data[rIdx] = temp
                        lIdx++
                    }
                    rIdx--
                } else {
                    lIdx++
                }

            }
            if (lIdx == rIdx && data[lIdx] > data[pivotRange.second]) {
                val temp = data[lIdx]
                data[lIdx] = data[pivotRange.second]
                data[pivotRange.second] = temp
                indices.add(pivotRange.first to lIdx - 1)
                indices.add(lIdx + 1 to pivotRange.second)
            } else {
                val temp = data[rIdx + 1]
                data[rIdx + 1] = data[pivotRange.second]
                data[pivotRange.second] = temp
                indices.add(pivotRange.first to lIdx)
                indices.add(rIdx + 2 to pivotRange.second)
            }
        }
        val runtime = System.currentTimeMillis() - startTime
        log.info { this.endMessage(runtime) }
        return SortResult(if (terminated) null else data, runtime, this::class.java.simpleName)
    }
}