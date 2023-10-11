package com.lhr.water.mapView.utils.math

import java.util.*

/**
 * GeneticAlgorithm
 *
 * @author: onlylemi
 */
class GeneticAlgorithm {
    private val crossoverProbability = DEFAULT_CROSSOVER_PROBABILITY // 交叉概率
    private val mutationProbability = DEFAULT_MUTATION_PROBABILITY // 突变概率
    private val populationSize = DEFAULT_POPULATION_SIZE // 种群数量
    var mutationTimes = 0 // 变异次数
        private set
    var currentGeneration = 0 // 当前的一代
        private set
    private var maxGeneration = 1000 // 最大代数
    private var pointNum = 0
    private lateinit var population // 种群集
            : Array<IntArray>
    private lateinit var dist // 点集间的邻接矩阵
            : Array<FloatArray>
    private var bestIndivial // 最短的结果集
            : IntArray? = null
    var bestDist // 最短的距离
            = 0f
        private set
    private var currentBestPosition // 当前最好个体的位置
            = 0
    private var currentBestDist // 当前最好个体的距离
            = 0f
    private lateinit var values // 种群中每个个体的dist
            : FloatArray
    private lateinit var fitnessValues // 适应度集
            : FloatArray
    private lateinit var roulette: FloatArray
    private var isAutoNextGeneration = false

    private object GeneticAlgorithmHolder {
        val instance = GeneticAlgorithm()
    }

    /**
     * 点集间的邻接矩阵
     *
     * @param matrix
     * @return
     */
    fun tsp(matrix: Array<FloatArray>): IntArray {
        dist = matrix
        pointNum = matrix.size
        init()
        if (isAutoNextGeneration) {
            var i = 0
            while (i++ < maxGeneration) {
                nextGeneration()
            }
        }
        isAutoNextGeneration = false
        return getBestIndivial()
    }

    /**
     * 初始化
     */
    private fun init() {
        mutationTimes = 0
        currentGeneration = 0
        bestIndivial = null
        bestDist = 0f
        currentBestPosition = 0
        currentBestDist = 0f
        values = FloatArray(populationSize)
        fitnessValues = FloatArray(populationSize)
        roulette = FloatArray(populationSize)
        population = Array(populationSize) { IntArray(pointNum) }

        //initDist(points);
        // 父代
        for (i in 0 until populationSize) {
            population[i] = randomIndivial(pointNum)
        }
        evaluateBestIndivial()
    }

    /**
     * 下一代
     */
    fun nextGeneration(): IntArray {
        currentGeneration++

        // 选择
        selection()
        // 交叉
        crossover()
        // 变异
        mutation()
        // 评价最好
        evaluateBestIndivial()
        return getBestIndivial()
    }

    /**
     * 选择
     */
    private fun selection() {
        val parents = Array(populationSize) { IntArray(pointNum) }
        val initnum = 4
        parents[0] = population[currentBestPosition] // 当前种群中最好的个体
        parents[1] = exchangeMutate(bestIndivial!!.clone()) // 对最好的个体进行交换变异
        parents[2] = insertMutate(bestIndivial!!.clone()) // 对最好的个体进行插入变异
        parents[3] = bestIndivial!!.clone() // 所有代中最好的个体
        setRoulette()
        for (i in initnum until populationSize) {
            parents[i] = population[wheelOut(Math.random().toInt())]
        }
        population = parents
    }

    /**
     *
     */
    private fun setRoulette() {
        //calculate all the fitness
        for (i in values.indices) {
            fitnessValues[i] = 1.0f / values[i] // 适应度为路径长的导数
        }

        //set the roulette
        var sum = 0f
        for (i in fitnessValues.indices) {
            sum += fitnessValues[i]
        }
        for (i in roulette.indices) {
            roulette[i] = fitnessValues[i] / sum
        }
        for (i in 1 until roulette.size) {
            roulette[i] += roulette[i - 1]
        }
    }

    /**
     * 模拟转盘，进行子代选取
     *
     * @param ran
     * @return
     */
    private fun wheelOut(ran: Int): Int {
        for (i in roulette.indices) {
            if (ran <= roulette[i]) {
                return i
            }
        }
        return 0
    }

    /**
     * 交换变异
     *
     * @param seq
     * @return
     */
    private fun exchangeMutate(seq: IntArray): IntArray {
        mutationTimes++
        var m: Int
        var n: Int
        do {
            m = random(seq.size - 2)
            n = random(seq.size)
        } while (m >= n)
        val j = n - m + 1 shr 1
        for (i in 0 until j) {
            val tmp = seq[m + i]
            seq[m + i] = seq[n - i]
            seq[n - i] = tmp
        }
        return seq
    }

    /**
     * 插入变异
     *
     * @param seq
     * @return
     */
    private fun insertMutate(seq: IntArray): IntArray {
        mutationTimes++
        var m: Int
        var n: Int
        do {
            m = random(seq.size shr 1)
            n = random(seq.size)
        } while (m >= n)
        val s1 = Arrays.copyOfRange(seq, 0, m)
        val s2 = Arrays.copyOfRange(seq, m, n)
        for (i in 0 until m) {
            seq[i + n - m] = s1[i]
        }
        for (i in 0 until n - m) {
            seq[i] = s2[i]
        }
        return seq
    }

    /**
     * 交叉
     */
    private fun crossover() {
        var queue = IntArray(populationSize)
        var num = 0
        for (i in 0 until populationSize) {
            if (Math.random() < crossoverProbability) {
                queue[num] = i
                num++
            }
        }
        queue = Arrays.copyOfRange(queue, 0, num)
        queue = shuffle(queue)
        var i = 0
        while (i < num - 1) {
            doCrossover(queue[i], queue[i + 1])
            i += 2
        }
    }

    private fun doCrossover(x: Int, y: Int) {
        population[x] = getChild(x, y, PREVIOUS)
        population[y] = getChild(x, y, NEXT)
    }

    /**
     * 根据父代求子代
     *
     * @param x
     * @param y
     * @param pos
     * @return
     */
    private fun getChild(x: Int, y: Int, pos: Int): IntArray {
        val solution = IntArray(pointNum)
        var px = population[x].clone()
        var py = population[y].clone()
        var dx = 0
        var dy = 0
        var c = px[random(px.size)]
        solution[0] = c
        for (i in 1 until pointNum) {
            val posX = indexOf(px, c)
            val posY = indexOf(py, c)
            if (pos == PREVIOUS) {
                dx = px[(posX + px.size - 1) % px.size]
                dy = py[(posY + py.size - 1) % py.size]
            } else if (pos == NEXT) {
                dx = px[(posX + px.size + 1) % px.size]
                dy = py[(posY + py.size + 1) % py.size]
            }
            for (j in posX until px.size - 1) {
                px[j] = px[j + 1]
            }
            px = Arrays.copyOfRange(px, 0, px.size - 1)
            for (j in posY until py.size - 1) {
                py[j] = py[j + 1]
            }
            py = Arrays.copyOfRange(py, 0, py.size - 1)
            c = if (dist[c][dx] < dist[c][dy]) dx else dy
            solution[i] = c
        }
        return solution
    }

    /**
     * 变异
     */
    private fun mutation() {
        var i = 0
        while (i < populationSize) {
            if (Math.random() < mutationProbability) {
                if (Math.random() > 0.5) {
                    population[i] = insertMutate(population[i])
                } else {
                    population[i] = exchangeMutate(population[i])
                }
                i--
            }
            i++
        }
    }

    /**
     * 评估最好的个体
     */
    private fun evaluateBestIndivial() {
        for (i in population.indices) {
            values[i] = calculateIndivialDist(population[i])
        }
        evaluateBestCurrentDist()
        if (bestDist == 0f || bestDist > currentBestDist) {
            bestDist = currentBestDist
            bestIndivial = population[currentBestPosition].clone()
        }
    }

    /**
     * 计算个体的距离
     *
     * @return
     */
    private fun calculateIndivialDist(indivial: IntArray): Float {
        var sum = dist[indivial[0]][indivial[indivial.size - 1]]
        for (i in 1 until indivial.size) {
            sum += dist[indivial[i]][indivial[i - 1]]
        }
        return sum
    }

    /**
     * 评估得到最短距离
     */
    fun evaluateBestCurrentDist() {
        currentBestDist = values[0]
        for (i in 1 until populationSize) {
            if (values[i] < currentBestDist) {
                currentBestDist = values[i]
                currentBestPosition = i
            }
        }
    }

    /**
     * 产生个体（乱序）
     *
     * @param n
     * @return
     */
    private fun randomIndivial(n: Int): IntArray {
        val a = IntArray(n)
        for (i in 0 until n) {
            a[i] = i
        }
        return shuffle(a)
    }

    /**
     * 乱序处理
     *
     * @param a
     * @return
     */
    private fun shuffle(a: IntArray): IntArray {
        for (i in a.indices) {
            val p = random(a.size)
            val tmp = a[i]
            a[i] = a[p]
            a[p] = tmp
        }
        return a
    }

    private fun random(n: Int): Int {
        var ran = rd
        if (ran == null) {
            ran = Random()
        }
        return ran.nextInt(n)
    }

    private fun concatAllArray(first: IntArray, vararg rest: IntArray): IntArray {
        var totalLength = first.size
        for (array in rest) {
            totalLength += array.size
        }
        val result = Arrays.copyOf(first, totalLength)
        var offset = first.size
        for (array in rest) {
            System.arraycopy(array, 0, result, offset, array.size)
            offset += array.size
        }
        return result
    }

    private fun indexOf(a: IntArray?, index: Int): Int {
        for (i in a!!.indices) {
            if (a[i] == index) {
                return i
            }
        }
        return 0
    }

    fun getBestIndivial(): IntArray {
        val best = IntArray(bestIndivial!!.size)
        val pos = indexOf(bestIndivial, 0)
        for (i in best.indices) {
            best[i] = bestIndivial!![(i + pos) % bestIndivial!!.size]
        }
        return best
    }

    fun setMaxGeneration(maxGeneration: Int) {
        this.maxGeneration = maxGeneration
    }

    fun setAutoNextGeneration(autoNextGeneration: Boolean) {
        isAutoNextGeneration = autoNextGeneration
    }

    companion object {
        private const val DEFAULT_CROSSOVER_PROBABILITY = 0.9f // 默认交叉概率
        private const val DEFAULT_MUTATION_PROBABILITY = 0.01f // 默认突变概率
        private const val DEFAULT_POPULATION_SIZE = 30 // 默认种群数量
        private const val PREVIOUS = 0
        private const val NEXT = 1
        private val rd: Random? = null
        val instance: GeneticAlgorithm
            get() = GeneticAlgorithmHolder.instance
    }
}