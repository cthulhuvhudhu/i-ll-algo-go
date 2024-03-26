package i.ll.algo.go.hashing

import i.ll.algo.go.App
import mu.KotlinLogging

class HashSetSearch : HashSearch {

    private val log = KotlinLogging.logger {  }
    // TODO Comments with diffs here
    // TODO manual v library impl? data impls.
    // TODO o annotation
    override fun <T : Searchable<T, S>, S : Comparable<S>> search(
        data: List<T>,
        queries: List<S>
    ): HashSearchResult<T> {
        log.info { this.createMessage() }
        var hSet = HashSet<T>(data.size + 1, 1f)
        var found = listOf<T>()
        val hashResult = HashSearchResult.Builder<T>()
        hashResult.sortTime(App.measureTime { hSet = data.toHashSet() })
        log.info { this.endCreateMessage(hashResult.sortTime) }
        log.info { this.searchMessage() }
        hashResult.searchTime(App.measureTime { found = queries.filter { hSet.contains(it as T) }.map { it as T } })
        hashResult.found(found)
        log.info { this.endSearchMessage(hashResult.sortTime) }
        return hashResult.build()
    }
}