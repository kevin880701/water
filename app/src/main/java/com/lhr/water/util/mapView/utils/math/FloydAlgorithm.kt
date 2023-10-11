package com.lhr.water.mapView.utils.math

/**
 * FloydAlgorithm
 *
 * @author: onlylemi
 */
class FloydAlgorithm {
    private var dist: Array<FloatArray>? = null

    // the shortest path from i to j
    private var path: Array<IntArray>? = null
    private var result: MutableList<Int>? = null

    private object FloydAlgorithmHolder {
        val instance = FloydAlgorithm()
    }

    private fun init(matrix: Array<FloatArray>) {
        dist = null
        path = null
        result = ArrayList()
        dist = Array(matrix.size) { FloatArray(matrix.size) }
        path = Array(matrix.size) { IntArray(matrix.size) }
    }

    /**
     * the shortest between begin to end
     *
     * @param begin
     * @param end
     * @param matrix
     */
    fun findCheapestPath(begin: Int, end: Int, matrix: Array<FloatArray>): List<Int>? {
        init(matrix)
        floyd(matrix)
        result!!.add(begin)
        findPath(begin, end)
        result!!.add(end)
        return result
    }

    private fun findPath(i: Int, j: Int) {
        val k = path!![i][j]
        if (k == -1) return
        findPath(i, k) // recursion
        result!!.add(k)
        findPath(k, j)
    }

    private fun floyd(matrix: Array<FloatArray>) {
        val size = matrix.size
        // initialize dist and path
        for (i in 0 until size) {
            for (j in 0 until size) {
                path!![i][j] = -1
                dist!![i][j] = matrix[i][j]
            }
        }
        for (k in 0 until size) {
            for (i in 0 until size) {
                for (j in 0 until size) {
                    if (dist!![i][k] != INF.toFloat() && dist!![k][j] != INF.toFloat() && dist!![i][k] + dist!![k][j] < dist!![i][j]) {
                        dist!![i][j] = dist!![i][k] + dist!![k][j]
                        path!![i][j] = k
                    }
                }
            }
        }
    }

    companion object {
        private const val INF = Int.MAX_VALUE
        val instance: FloydAlgorithm
            get() = FloydAlgorithmHolder.instance
    }
}