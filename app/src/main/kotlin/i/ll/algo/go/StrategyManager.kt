package i.ll.algo.go

import i.ll.algo.go.hashing.HashSearch
import i.ll.algo.go.hashing.Searchable
import i.ll.algo.go.searching.Search
import i.ll.algo.go.searching.SearchResult
import i.ll.algo.go.sorting.Sort
import i.ll.algo.go.sorting.SortResult
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class StrategyManager<T: Searchable<T,S>, S: Comparable<S>>(
    dataFilename: String,
    queryFilename: String,
    private val sortAlgos: List<() -> Sort>,
    private val searchAlgos: List<() -> Search>,
    private val hashAlgos: List<() -> HashSearch>,
    private val autoStrategy: Boolean,
    dataCreator: (String) -> T,
    queryCreator: (String) -> S
) : KoinComponent {

    private val fileManager: FileManager by inject()
    private val data = ArrayList<T>(fileManager.getData(dataFilename, dataCreator))
    private val queries = fileManager.getData(queryFilename, queryCreator)

    fun <T: Searchable<T,S>, S: Comparable<S>> run() {
        if (autoStrategy) { runStrategies() }
        else {
            val sortResults = sortAlgos.map { s -> (1..App.SORT_REPS).map { s.invoke().sort(data) } }.flatten()
            val sortedData = sortResults.firstNotNullOf { it.sorted as? List<T> }
            val searchResults = searchAlgos.map { s -> (1..App.SEARCH_REPS).map { s.invoke().search(data, queries) } }.flatten()
            val hashResults = hashAlgos.map { h -> (1..App.SEARCH_REPS).map { h.invoke().search(data, queries) } }.flatten()
            // TODO output, stats
        }
    }

    fun runStrategies() {
        // TODO autogen strategies; run and output
        /**
         *
         *
         * private fun <T: Searchable<T,S>, S: Comparable<S>> sortBubbleJumpSearch(data: Array<T>, queries: Array<S>) {
         *     println("Start searching (bubble sort + jump search)...")
         *     val strategy = Strategy(BubbleSort(), JumpSearch(), data)
         *     println(strategy.sort())
         *     println(strategy.search(queries))
         * }
         *
         * private fun <T: Searchable<T,S>, S: Comparable<S>> sortQuickBinSearch(data: Array<T>, queries: Array<S>) {
         *     println("Start searching (quick sort + binary search)...")
         *     val strategy = Strategy(QuickSort(), BinarySearch(), data)
         *     println(strategy.sort())
         *     println(strategy.search(queries))
         *
         *      println("Start searching (linear search)...")
         *         val results = LinearSearch().search(directory.toTypedArray(), queries.toTypedArray())
         *         println(formatTime(results.runtime))
         *
         *         sortBubbleJumpSearch(directory.toTypedArray(), queries)
         *         println("Start searching (bubble sort + jump search)...")
         *         val strategy = Strategy(BubbleSort(true), JumpSearch(), directory.toTypedArray())
         *         val searchResult = strategy.search(queries.toTypedArray())
         *
         *         sortQuickBinSearch(directory.toTypedArray(), queries)
         * }
         */
    }
}

class Strategy<T: Searchable<T,S>, S: Comparable<S>> (val sortAlgo: Sort, val searchAlgo: Search, val data: ArrayList<T>) {
    private val sortResults: MutableList<SortResult<T>> = mutableListOf()
    private var searchResults: MutableList<SearchResult<T>> = mutableListOf()

    fun sort(): SortResult<T> {
        return sortAlgo.sort(data).apply { sortResults.add(this) }
    }

    fun search(queries: List<S>): SearchResult<T> {
        return searchAlgo.search(sortResults.firstNotNullOf { it.sorted }, queries).apply { searchResults.add(this) }
    }
}