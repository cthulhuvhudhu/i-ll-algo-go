package i.ll.algo.go.searching

import i.ll.algo.go.App
import i.ll.algo.go.hashing.Searchable
import mu.KotlinLogging

class LinearSearch : Search {

    private val log = KotlinLogging.logger {  }

    /** Linear search - O(n*m), 𝛺(1*m), θ(n*m)
     *  Goes through elements sequentially and checks for a match.
     *
     *  Returns a list of queried items that were found in the dataset, or null if timeout.
     */
    override fun <T : Searchable<T, S>, S : Comparable<S>> search(data: List<T>, queries: List<S>): SearchResult<T> {
        log.info { this.startMessage() }
        val startTime = System.currentTimeMillis()
        val found = mutableListOf<T>()
        var terminated = false
        for (q in queries) {
            if ((System.currentTimeMillis() - startTime) >= App.MAX_SEARCH_TIME) {
                log.warn { this.terminateMessage() }
                terminated = true
                break
            }
            data.find { it.getKey() == q }?.apply { found.add(this) }
        }
        val runtime = System.currentTimeMillis() - startTime
        log.info { this.endMessage(runtime) }
        return SearchResult(if (terminated) null else found, runtime, this::class.java.simpleName)
    }
}