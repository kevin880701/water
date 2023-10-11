package com.lhr.water.mapView.utils.math

import java.util.*

class TSPNearestNeighbour {
    private var numberOfNodes = 0
    private val stack: Deque<Int>
    private val list: MutableList<Int>

    init {
        stack = ArrayDeque()
        list = ArrayList()
    }

    private object TSPNearestNeighbourHolder {
        val instance = TSPNearestNeighbour()
    }

    fun tsp(matrix: Array<FloatArray>): List<Int> {
        numberOfNodes = matrix[0]!!.size
        val visited = IntArray(numberOfNodes)
        visited[0] = 1
        stack.push(0)
        var element: Int
        var dst = 0
        var i: Int
        var minFlag = false

        // System.out.print(0 + "\t");
        list.add(0)
        while (!stack.isEmpty()) {
            element = stack.peek()
            i = 0
            var min = INF
            while (i < numberOfNodes) {
                if (matrix[element][i] < INF && visited[i] == 0 && min > matrix[element][i]) {
                    min = matrix[element][i]
                    dst = i
                    minFlag = true
                }
                i++
            }
            if (minFlag) {
                visited[dst] = 1
                stack.push(dst)
                // System.out.print(dst + "\t");
                list.add(dst)
                minFlag = false
                continue
            }
            stack.pop()
        }
        return list
    }

    companion object {
        private const val INF = Float.MAX_VALUE
        val instance: TSPNearestNeighbour
            get() = TSPNearestNeighbourHolder.instance
    }
}