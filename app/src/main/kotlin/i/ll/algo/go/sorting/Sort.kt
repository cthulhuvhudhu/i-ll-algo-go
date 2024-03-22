package i.ll.algo.go.sorting

import i.ll.algo.go.searching.Algorithm

interface Sort : Algorithm {
    fun <T: Comparable<T>> sort(data: Array<T>): Array<T>?
}
