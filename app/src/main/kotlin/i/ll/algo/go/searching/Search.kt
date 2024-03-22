package i.ll.algo.go.searching

import i.ll.algo.go.App

interface Search : Algorithm {
    fun <T: Comparable<T>> search(data: Array<T>, queries: Array<T>): List<T>
}

interface Algorithm {
    val timeLimit: Boolean
        get() = false
    var runtime: Long
    val prettyRunTime: String
        get() = App.formatTime(runtime)
}