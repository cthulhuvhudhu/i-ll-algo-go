package i.ll.algo.go.hashing

import i.ll.algo.go.App
import mu.KotlinLogging

class HashMapSearch : HashSearch {

    private val log = KotlinLogging.logger {  }

    // TODO Comments with diffs here
    // TODO manual v library impl?
    // TODO o annotation
    override fun <T : Searchable<T, S>, S : Comparable<S>> search(
        data: List<T>,
        queries: List<S>
    ): HashSearchResult<T> {
        log.info { this.createMessage() }
        var hMap = HashMap<S, T>(data.size + 1, 1f)
        var found = listOf<T>()
        val hashResult = HashSearchResult.Builder<T>()
        hashResult.sortTime(App.measureTime { hMap = hashMapOf( *data.map { it.getKey() to it }.toTypedArray() ) })
        log.info { this.endCreateMessage(hashResult.sortTime) }
        log.info { this.searchMessage() }
        hashResult.searchTime(App.measureTime { found = queries.mapNotNull { hMap[it] } })
        hashResult.found(found)
        log.info { this.endSearchMessage(hashResult.searchTime) }
        return hashResult.build()
    }
}