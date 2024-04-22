package i.ll.algo.go.hashing

import i.ll.algo.go.App
import mu.KotlinLogging

class HashMapSearch : HashSearch {

    private val log = KotlinLogging.logger {  }

    /** HashMap populate & search(per query): O(1), 𝛺(1), θ(1) - Optimal **KNOWN CAPACITY & UNIQUES, no collisions or rehashing**
     * Caveats: Unsynchronized
     *
     * When considering collisions and rehashing:
     * Deletion/Search: O(n), 𝛺(1), θ(1)
     * Insertion: O(n), 𝛺(1), θ(h/n)
     *
     * Space complexity: O(n); more memory efficient than hashtable
     * Nulls: 1 Key allowed, values OK
     * Traversal: fail fast iterator
     * Duplicates: Not keys, values yes
     * Unsynchronized -> Use ConcurrentHashMap instead
     * Backed by hashtable
     */
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