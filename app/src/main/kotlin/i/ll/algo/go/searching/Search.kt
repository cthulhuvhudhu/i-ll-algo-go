package i.ll.algo.go.searching

import i.ll.algo.go.App
import i.ll.algo.go.hashing.Searchable

interface Search {
    fun <T : Searchable<T, S>, S : Comparable<S>> search(data: List<T>, queries: List<S>): SearchResult<T>
}

fun Search.startMessage(): String = "Start searching (${this::class.java.simpleName})..."
fun Search.terminateMessage(): String = "Timeout - terminating search (${this::class.java.simpleName})"
fun Search.endMessage(runtime: Long): String = "Finished search (${this::class.java.simpleName}): $runtime"

data class SearchResult<T> (
//    Holds a sorted list, or null if runtime exceeds max permitted time
    val found: List<T>?,
    val runtime: Long,
    val name: String
) {

    override fun toString(): String {
        val termStr = found?.let { "" } ?: "[aborted]"
        return buildString {
            appendLine("Search: $name")
            append("runtime: ${App.formatTime(runtime)} $termStr")
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SearchResult<*>

        if (found != other.found) return false
        if (runtime != other.runtime) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = found?.hashCode() ?: 0
        result = 31 * result + runtime.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }
}