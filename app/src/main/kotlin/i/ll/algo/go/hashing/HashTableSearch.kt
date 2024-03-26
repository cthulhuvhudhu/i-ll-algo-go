package i.ll.algo.go.hashing

import i.ll.algo.go.App
import mu.KotlinLogging
import java.util.Hashtable

class HashTableSearch : HashSearch {

    private val log = KotlinLogging.logger {  }

    // TODO Comments with diffs here
    // TODO manual v library impl?
    // TODO o annotation
    override fun <T : Searchable<T, S>, S : Comparable<S>> search(
        data: List<T>,
        queries: List<S>
    ): HashSearchResult<T> {
        log.info { this.createMessage() }
        var hTable = Hashtable<S,T>(data.size + 1, 1f)
        var found = listOf<T>()
        val hashResult = HashSearchResult.Builder<T>()
        hashResult.sortTime(App.measureTime { hTable = Hashtable(data.associateBy { it.getKey() }) })
        log.info { this.endCreateMessage(hashResult.sortTime) }
        log.info { this.searchMessage() }
        hashResult.searchTime(App.measureTime { found = queries.mapNotNull { hTable[it] } })
        hashResult.found(found)
        log.info { this.endSearchMessage(hashResult.searchTime) }
        return hashResult.build()
    }
}