package i.ll.algo.go.hashing

import i.ll.algo.go.App
import mu.KotlinLogging

class HashSetSearch : HashSearch {

    private val log = KotlinLogging.logger {  }
    /** HashSet populate & search(per query): O(1), 𝛺(1), θ(1) - Optimal **KNOWN CAPACITY & UNIQUES, no collisions or rehashing**
     * Caveats: Slower than hashmap
     *
     * When considering collisions and rehashing:
     * Deletion/Search: O(n), 𝛺(1), θ(1)
     * Insertion: O(n), 𝛺(1), θ(h/n)
     *
     * Space complexity: O(n)
     * Nulls: 1 allowed
     * Duplicates: Not allowed
     * Traversal: fail fast iterator
     * Backed by hashmap>hashtable
     */
    @Suppress("UNCHECKED_CAST")
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