package i.ll.algo.go.sorting

import i.ll.algo.go.App

interface Sort {
    fun <T: Comparable<T>> sort(data: ArrayList<T>): SortResult<T>
}


fun Sort.startMessage(): String = "Start sort (${this::class.java.simpleName})..."
fun Sort.terminateMessage(): String = "Timeout - terminating sort (${this::class.java.simpleName})"
fun Sort.endMessage(runtime: Long): String = "Finished sort (${this::class.java.simpleName}): $runtime"

data class SortResult<T> (
//    Holds a sorted list, or null if runtime exceeds max permitted time
    val sorted: ArrayList<T>?,
    val runtime: Long,
    val name: String
) {

    override fun toString(): String {
        val termStr = sorted?.let { "" } ?: "[aborted]"
        return buildString {
            appendLine("Sort: $name")
            append("runtime: ${App.formatTime(runtime)} $termStr")
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SortResult<*>

        if (sorted != other.sorted) return false
        if (runtime != other.runtime) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = sorted?.hashCode() ?: 0
        result = 31 * result + runtime.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }
}
