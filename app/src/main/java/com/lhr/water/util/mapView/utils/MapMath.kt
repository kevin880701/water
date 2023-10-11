package com.lhr.water.mapView.utils

import android.graphics.PointF
import com.lhr.water.mapView.utils.math.FloydAlgorithm
import com.lhr.water.mapView.utils.math.GeneticAlgorithm
import com.lhr.water.mapView.utils.math.TSPNearestNeighbour
import kotlin.math.sqrt

/**
 * MapMath
 *
 * @author onlylemi
 */
object MapMath {
    /**
     * the distance between two points
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    fun getDistanceBetweenTwoPoints(
        x1: Float, y1: Float,
        x2: Float, y2: Float
    ): Float {
        return sqrt(Math.pow((x2 - x1).toDouble(), 2.0) + Math.pow((y2 - y1).toDouble(), 2.0)).toFloat()
    }

    /**
     * the distance between two points
     *
     * @param start
     * @param end
     * @return
     */
    fun getDistanceBetweenTwoPoints(start: PointF, end: PointF): Float {
        return Math.sqrt(
            Math.pow((end.x - start.x).toDouble(), 2.0)
                    + Math.pow((end.y - start.y).toDouble(), 2.0)
        ).toFloat()
    }

    /**
     * the shortest path between two points (FloydAlgorithm)
     *
     * @param begin
     * @param end
     * @param matrix adjacency matrix
     * @return
     */
    fun getShortestPathBetweenTwoPoints(
        begin: Int,
        end: Int, matrix: Array<FloatArray>
    ): List<Int>? {
        return FloydAlgorithm.instance.findCheapestPath(begin, end, matrix)
    }

    /**
     * the best path between some points (NearestNeighbour tsp)
     *
     * @param matrix adjacency matrix
     * @return
     */
    fun getBestPathBetweenPointsByNearestNeighbour(matrix: Array<FloatArray>): List<Int> {
        return TSPNearestNeighbour.instance.tsp(matrix)
    }

    /**
     * the best path between some points (GeneticAlgorithm tsp)
     *
     * @param matrix
     * @return
     */
    fun getBestPathBetweenPointsByGeneticAlgorithm(matrix: Array<FloatArray>): List<Int> {
        val ga = GeneticAlgorithm.instance
        ga.setAutoNextGeneration(true)
        ga.setMaxGeneration(200)
        val best = ga.tsp(matrix)
        val result: MutableList<Int> = ArrayList(best.size)
        for (i in best.indices) {
            result.add(best[i])
        }
        return result
    }

    /**
     * get the angle between two points and the horizontal plane
     *
     * @param start
     * @param end
     * @return
     */
    fun getDegreeBetweenTwoPointsWithHorizontal(start: PointF, end: PointF): Float {
        var angle = 90.0f
        if (start.x != end.x) {
            angle = Math.toDegrees(
                Math.atan(
                    ((end.y - start.y)
                            / (end.x - start.x)).toDouble()
                )
            ).toFloat()
            if (end.x < start.x && end.y >= start.y) {
                angle = angle + 180.0f
            } else if (end.x < start.x && end.y > start.y) {
                angle = angle - 180f
            }
        } else {
            if (start.y < end.y) {
                angle = 90.0f
            } else if (start.y > end.y) {
                angle = -90.0f
            }
        }
        return angle
    }

    /**
     * get the angle between two points and the vertical plane
     *
     * @param start
     * @param end
     * @return
     */
    fun getDegreeBetweenTwoPointsWithVertical(start: PointF, end: PointF): Float {
        var angle = 90.0f
        if (start.y != end.y) {
            angle = -Math.toDegrees(
                Math.atan(
                    ((end.x - start.x)
                            / (end.y - start.y)).toDouble()
                )
            ).toFloat()
            if (end.y > start.y && end.x >= start.x) {
                angle = angle + 180.0f
            } else if (end.y > start.y && end.x > start.x) {
                angle = angle - 180f
            }
        } else {
            if (start.x < end.x) {
                angle = 90.0f
            } else if (start.x > end.x) {
                angle = -90.0f
            }
        }
        return angle
    }

    /**
     * get degree between two points
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    fun getDegreeBetweenTwoPoints(x1: Float, y1: Float, x2: Float, y2: Float): Float {
        val radians = Math.atan2((y1 - y2).toDouble(), (x1 - x2).toDouble())
        return Math.toDegrees(radians).toFloat()
    }

    /**
     * get degree between two points
     *
     * @param start
     * @param end
     * @return
     */
    fun getDegreeBetweenTwoPoints(start: PointF, end: PointF): Float {
        return getDegreeBetweenTwoPoints(start.x, start.y, end.x, end.y)
    }

    /**
     * The coordinates of the midpoint between two points are obtained
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    fun getMidPointBetweenTwoPoints(x1: Float, y1: Float, x2: Float, y2: Float): PointF {
        return PointF((x1 + x2) / 2, (y1 + y2) / 2)
    }

    /**
     * The coordinates of the midpoint between two points are obtained
     *
     * @param start
     * @param end
     * @return
     */
    fun getMidPointBetweenTwoPoints(start: PointF, end: PointF): PointF {
        return getMidPointBetweenTwoPoints(start.x, start.y, end.x, end.y)
    }

    /**
     * Get the coordinates of any point between two points
     *
     * @param start
     * @param end
     * @param value
     * @return
     */
    fun getEveryPointBetweenTwoPoints(start: PointF, end: PointF, value: Float): PointF {
        // y=kx+b
        val x: Float
        val y: Float
        // with slope
        if (start.x != end.x) {
            val k = (end.y - start.y) / (end.x - start.x)
            val b = end.y - k * end.x
            x = if (end.x > start.x) {
                Math.min(end.x, start.x) + (end.x - start.x) * value
            } else {
                Math.max(end.x, start.x) + (end.x - start.x) * value
            }
            y = k * x + b
        } else { // no slope
            x = start.x
            y = if (end.y > start.y) {
                Math.min(end.y, start.y) + (end.y - start.y) * value
            } else {
                Math.max(end.y, start.y) + (end.y - start.y) * value
            }
        }
        return PointF(x, y)
    }

    /**
     * Get a shortest distance from point to line
     *
     * @param point
     * @param linePoint1 Determine the first point of a straight line
     * @param linePoint2 Determine the second point of a straight line
     * @return
     */
    fun getDistanceFromPointToLine(point: PointF, linePoint1: PointF, linePoint2: PointF): Float {
        // y = kx + b;
        // d = |kx-y+b| / √(k^2+1)
        val d: Float
        d = if (linePoint1.x != linePoint2.x) { // with slope
            val k = (linePoint2.y - linePoint1.y) / (linePoint2.x - linePoint1.x)
            val b = linePoint2.y - k * linePoint2.x
            Math.abs(k * point.x - point.y + b) / Math.sqrt((k * k + 1).toDouble()).toFloat()
        } else { // no slope
            Math.abs(point.x - linePoint1.x)
        }
        return d
    }

    /**
     * get intersection coordinates from a point to a line
     *
     * @param point
     * @param linePoint1
     * @param linePoint2
     * @return
     */
    fun getIntersectionCoordinatesFromPointToLine(point: PointF, linePoint1: PointF, linePoint2: PointF): PointF {
        // y = kx + b;
        val x: Float
        val y: Float
        if (linePoint1.x != linePoint2.x) { // with slope
            val k = (linePoint2.y - linePoint1.y) / (linePoint2.x - linePoint1.x)
            val b = linePoint2.y - k * linePoint2.x
            // The equation of point
            if (k != 0f) {
                val kV = -1 / k
                val bV = point.y - kV * point.x
                x = (b - bV) / (kV - k)
                y = kV * x + bV
            } else {
                x = point.x
                y = linePoint1.y
            }
        } else { // no slope
            x = linePoint1.x
            y = point.y
        }
        return PointF(x, y)
    }

    /**
     * is/not obtuse angle between a point and a line
     *
     * @param point
     * @param linePoint1
     * @param linePoint2
     * @return
     */
    fun isObtuseAnglePointAndLine(point: PointF, linePoint1: PointF, linePoint2: PointF): Boolean {
        // A*A + B*B < C*C
        val p_l1: Float
        val p_l2: Float
        val l1_l2: Float
        p_l1 = getDistanceBetweenTwoPoints(point, linePoint1)
        p_l2 = getDistanceBetweenTwoPoints(point, linePoint2)
        l1_l2 = getDistanceBetweenTwoPoints(linePoint1, linePoint2)
        return p_l1 * p_l1 + l1_l2 * l1_l2 < p_l2 * p_l2 || p_l2 * p_l2 + l1_l2 * l1_l2 < p_l1 * p_l1
    }
}