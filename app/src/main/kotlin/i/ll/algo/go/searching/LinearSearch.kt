package i.ll.algo.go.searching

class LinearSearch : Search {

    override var runtime: Long = -1

    /** Linear search - O(n*m), 𝛺(1), θ(n*m)
     *  Goes through elements sequentially and checks for a match.
     *
     *  Returns a list of queried items that were _not_ found in the dataset.
     *  This implementation does not utilize the timeout feature.
     */
    override fun <T: Comparable<T>> search(data: Array<T>, queries: Array<T>): List<T> {
        val startTime = System.currentTimeMillis()
        val result = queries.filter { q -> data.none { it == q } }
        runtime = System.currentTimeMillis() - startTime
        return result
    }
}