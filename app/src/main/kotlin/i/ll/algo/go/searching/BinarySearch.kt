package i.ll.algo.go.searching

import i.ll.algo.go.App
import i.ll.algo.go.hashing.Searchable
import mu.KotlinLogging

class BinarySearch : Search {

    private val log = KotlinLogging.logger {  }

    /** Binary search - O(log(n) * m), 𝛺(1*m), θ(log(n) * m) - Optimal
     *  Evaluate middle element; if equal to target,return.
     *  Else:
     *      If target is less than, repeat procedure with left half of dataset.
     *      Else, repeat procedure with right half of dataset.
     *
     *  Returns a list of queried items that were found in the dataset, or null if timeout.
     */
    override fun <T : Searchable<T, S>, S : Comparable<S>> search(data: List<T>, queries: List<S>): SearchResult<T> {
        log.info { this.startMessage() }
        var terminated = false
        val startTime = System.currentTimeMillis()
        val found = mutableListOf<T>()
        for (q in queries) {
            if ((System.currentTimeMillis() - startTime) >= App.MAX_SEARCH_TIME) {
                log.warn { this.terminateMessage() }
                terminated = true
                break
            }
            var lookup = 0 to data.size - 1
            while (lookup.second >= lookup.first) {
                val candidateIdx = lookup.first + (lookup.second - lookup.first) / 2
                val candidate = data[candidateIdx]
                if (candidate.getKey() == q) {
                    found += candidate
                    break
                }
                if (lookup.first != lookup.second) {
                    lookup = if (candidate.getKey() > q) {
                        lookup.first to candidateIdx - 1
                    } else {
                        candidateIdx + 1 to lookup.second
                    }
                } else {
                    break
                }
            }
        }
        val runtime = System.currentTimeMillis() - startTime
        log.info { this.endMessage(runtime) }
        return SearchResult(if (terminated) null else found, runtime, this::class.java.simpleName)
    }
}
