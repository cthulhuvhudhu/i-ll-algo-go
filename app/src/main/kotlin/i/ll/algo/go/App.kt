package i.ll.algo.go

import i.ll.algo.go.hashing.HashMapSearch
import i.ll.algo.go.hashing.HashSetSearch
import i.ll.algo.go.hashing.HashTableSearch
import i.ll.algo.go.hashing.Searchable
import i.ll.algo.go.searching.*
import i.ll.algo.go.sorting.BubbleSort
import i.ll.algo.go.sorting.QuickSort
import kotlinx.cli.*
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import kotlin.properties.Delegates

class App {

    companion object {
        var MAX_SORT_TIME by Delegates.notNull<Int>()
        var MAX_SEARCH_TIME by Delegates.notNull<Int>()
        var SORT_REPS by Delegates.notNull<Int>()
        var SEARCH_REPS by Delegates.notNull<Int>()

        inline fun measureTime(block: () -> Unit): Long {
            val startTime = System.currentTimeMillis()
            block()
            return System.currentTimeMillis() - startTime
        }

        fun formatTime(ms: Long): String {
            return String.format("%1\$tM min. %1\$tS sec. %1\$tL ms.", ms)
        }
    }
}

val appModule = module {
    singleOf(::FileManager)
}

fun main(args: Array<String>) {
    val parser = ArgParser("i-ll-algo-go")
    val dataFilename by parser.option(
        ArgType.String,
        shortName = "d",
        fullName = "data",
        description = "Filename for data"
    ).default("tiny_directory.txt")
    val queryFilename by parser.option(
        ArgType.String,
        shortName = "q",
        fullName = "query",
        description = "Filename for queries"
    ).default("tiny_find.txt")
    val sortTimeLimit by parser.option(
        ArgType.Int,
        shortName = "st",
        fullName = "sortMax",
        description = "Max sort time permitted in minutes"
    ).default(5)
    val searchTimeLimit by parser.option(
        ArgType.Int,
        shortName = "ft",
        fullName = "findMax",
        description = "Max search time permitted in minutes"
    ).default(5)
    val sortReps by parser.option(
        ArgType.Int,
        shortName = "sr",
        fullName = "sortReps",
        description = "Number of times to execute the sort function per strategy"
    ).default(1)
    val searchReps by parser.option(
        ArgType.Int,
        shortName = "fr",
        fullName = "findReps",
        description = "Number of times to execute the find/search function per strategy"
    ).default(1)
    val sorts by parser.option(
        ArgType.String,
        shortName = "s",
        fullName = "sort",
        description = "Specify which sort algorithms to use from: [b]ubble, [q]uick. Defaults to all"
    ).multiple()
    parser.parse(args)
    val searches by parser.option(
        ArgType.String,
        shortName = "f",
        fullName = "find",
        description = "Specify which search(find) algorithms to use from: [b]inary, [j]ump, [l]inear. Defaults to all"
    ).multiple()
    parser.parse(args)
    val hashes by parser.option(
        ArgType.String,
        shortName = "hh",
        fullName = "hash",
        description = "Specify which hashing sort & find algorithms to use from: [m]ap, [s]et, [t]able. Defaults to all"
    ).multiple()
    val jsonMode by parser.option(
        ArgType.Boolean,
        shortName = "j",
        fullName = "json",
        description = "Will parse data as json object, and query as the key field"
    ).default(false)
    val autoStrategy by parser.option(
        ArgType.Boolean,
        shortName = "a",
        fullName = "auto",
        description = "Automatically generate and run strategies"
    ).default(false)
    parser.parse(args)

    App.MAX_SORT_TIME = sortTimeLimit * 60 * 1000
    App.MAX_SEARCH_TIME = searchTimeLimit * 60 * 1000
    App.SORT_REPS = sortReps
    App.SEARCH_REPS = searchReps

    val sortAlgos = sorts.mapNotNull {
        when (it) {
            "b","bubble" -> ::BubbleSort
            "q","quick" -> ::QuickSort
            else -> null
        }
    }

    val searchAlgos = searches.mapNotNull {
        when (it) {
            "b", "binary" -> ::BinarySearch
            "j", "jump" -> ::JumpSearch
            "l", "linear" -> ::LinearSearch
            else -> null
        }
    }

    val hashAlgos = searches.mapNotNull {
        when (it) {
            "m", "map" -> ::HashMapSearch
            "s", "set" -> ::HashSetSearch
            "t", "table" -> ::HashTableSearch
            else -> null
        }
    }

    startKoin {
        modules(appModule)
    }

    StrategyManager(dataFilename, queryFilename, sortAlgos, searchAlgos, hashAlgos, autoStrategy, ::PhonebookEntry) { it }
}


/** Demo data structure for testing and learning purposes, used on all input. */
data class PhonebookEntry(private val fromStr: String) : Searchable<PhonebookEntry, String>, Comparable<PhonebookEntry> {
    private val name = fromStr.split("\\s+".toRegex()).drop(1).joinToString(" ")
    private val number = fromStr.split("\\s+".toRegex())[0]

    override fun compareTo(other: PhonebookEntry): Int {
        return this.name.compareTo(other.name)
    }

    override fun getKey(): String {
        return name
    }
}
