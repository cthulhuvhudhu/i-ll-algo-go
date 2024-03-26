package i.ll.algo.go.searching

import i.ll.algo.go.App
import i.ll.algo.go.hashing.Searchable
import mu.KotlinLogging
import kotlin.math.sqrt

class JumpSearch : Search {

    private val log = KotlinLogging.logger {  }

    /** Linear search - O(log(n) * m), 𝛺(1*m), θ(log(n) * m)
     *  Jumps through elements in size sqrt(n) steps until current step is greater than query,
     *  then linear search down.
     *
     *  Returns a list of queried items that were found in the dataset, or null if timeout.
     */
    override fun <T : Searchable<T, S>, S : Comparable<S>> search(data: List<T>, queries: List<S>): SearchResult<T> {
        log.info { this.startMessage() }
        val startTime = System.currentTimeMillis()
        var terminated = false
        val jump = sqrt(data.size.toDouble()).toInt()
        val found = mutableListOf<T>()

        for (q in queries) {
            if ((System.currentTimeMillis() - startTime) >= App.MAX_SEARCH_TIME) {
                log.warn { this.terminateMessage() }
                terminated = true
                break
            }
            loop@ for (i in data.indices step jump) {
                if (data[i].getKey() == q) {
                    found.add(data[i])
                    break
                }
                if(q < data[i].getKey()) {
                    for (j in i - 1 downTo i - jump + 1) {
                        if (data[j] == q) {
                            found.add(data[j])
                            break@loop
                        }
                    }
                }
            }
        }
        val runtime = System.currentTimeMillis() - startTime
        log.info { this.endMessage(runtime) }
        return SearchResult(if (terminated) null else found, runtime, this::class.java.simpleName)
    }
}
