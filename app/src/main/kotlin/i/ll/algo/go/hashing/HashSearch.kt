package i.ll.algo.go.hashing

import kotlin.properties.Delegates

interface HashSearch {
    fun <T : Searchable<T,S>, S : Comparable<S>> search(data: List<T>, queries: List<S>): HashSearchResult<T>
}

fun HashSearch.createMessage(): String = "Start sort via creation (${this::class.java.simpleName})..."
fun HashSearch.endCreateMessage(runtime: Long): String = "Finished creation (${this::class.java.simpleName}): $runtime"
fun HashSearch.searchMessage(): String = "Start search (${this::class.java.simpleName})..."
fun HashSearch.endSearchMessage(runtime: Long): String = "Finished search (${this::class.java.simpleName}): $runtime"

class HashSearchResult<T> private constructor(builder: Builder<T>) {
    private val sortTime: Long
    // Does not implement timeout feature due to data structure
    private val found: List<T>
    private val searchTime: Long

    init {
        this.sortTime = builder.sortTime
        this.searchTime = builder.searchTime
        this.found = builder.found
    }

    class Builder<T> {
        var sortTime by Delegates.notNull<Long>()
            private set
        var searchTime by Delegates.notNull<Long>()
            private set
        var found: List<T> = emptyList()
            private set

        fun sortTime(sortTime: Long) = apply { this.sortTime = sortTime }
        fun searchTime(searchTime: Long) = apply { this.searchTime = searchTime }
        fun found(found: List<T>) = apply { this.found = found }
        fun build() = HashSearchResult(this)
    }
}

interface Searchable<in T, out S> : Comparable<T> {
    fun getKey(): S
}