package i.ll.algo.go.hashing

import i.ll.algo.go.App
import mu.KotlinLogging
import java.util.Hashtable

class HashTableSearch : HashSearch {

    private val log = KotlinLogging.logger {  }

    /** Hashtable populate & search(per query): O(1), 𝛺(1), θ(1) - Optimal **KNOWN CAPACITY & UNIQUES, no collisions or rehashing**
     * Caveats: Worse memory performance than HashMap
     *
     * When considering collisions and rehashing:
     * Deletion/Search: O(n), 𝛺(1), θ(1)
     * Insertion: O(n), 𝛺(1), θ(h/n)
     *
     * Space complexity: O(n)
     * Nulls: Not allowed
     * Traversal: Enumerator (not fail fast)
     * Synchronized
     */
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