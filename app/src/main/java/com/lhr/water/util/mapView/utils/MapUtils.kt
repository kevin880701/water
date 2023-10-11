package com.lhr.water.mapView.utils

import android.graphics.Bitmap
import android.graphics.Picture
import android.graphics.PointF
import android.graphics.RectF

/**
 * MapUtils
 *
 * @author onlylemi
 */
object MapUtils {
    private const val TAG = "MapUtils: "
    private const val INF = Float.MAX_VALUE
    private var nodesSize = 0
    private var nodesContactSize = 0
    fun init(nodessize: Int, nodescontactsize: Int) {
        nodesSize = nodessize
        nodesContactSize = nodescontactsize
    }

    /**
     * Get the distance between points
     *
     * @param nodes
     * @param list
     * @return
     */
    fun getDistanceBetweenList(
        nodes: List<PointF?>,
        list: List<Int?>
    ): Float {
        var distance = 0f
        for (i in 0 until list.size - 1) {
            distance += MapMath.getDistanceBetweenTwoPoints(
                nodes[list[i]!!]!!,
                nodes[list[i + 1]!!]!!
            )
        }
        return distance
    }

    /**
     * get degrees between two points(list) with horizontal plane
     *
     * @param routeList
     * @param nodes
     * @return
     */
    fun getDegreeBetweenTwoPointsWithHorizontal(
        routeList: List<Int?>,
        nodes: List<PointF?>
    ): List<Float> {
        val routeListDegrees: MutableList<Float> = ArrayList()
        for (i in 0 until routeList.size - 1) {
            routeListDegrees.add(
                MapMath.getDegreeBetweenTwoPointsWithHorizontal(
                    nodes[routeList[i]!!]!!,
                    nodes[routeList[i + 1]!!]!!
                )
            )
        }
        return routeListDegrees
    }

    /**
     * get degrees between two points(list) with vertical plane
     *
     * @param routeList
     * @param nodes
     * @return
     */
    fun getDegreeBetweenTwoPointsWithVertical(
        routeList: List<Int?>,
        nodes: List<PointF?>
    ): List<Float> {
        val routeListDegrees: MutableList<Float> = ArrayList()
        for (i in 0 until routeList.size - 1) {
            routeListDegrees.add(
                MapMath.getDegreeBetweenTwoPointsWithVertical(
                    nodes[routeList[i]!!]!!,
                    nodes[routeList[i + 1]!!]!!
                )
            )
        }
        return routeListDegrees
    }

    /**
     * get shortest path between two points
     *
     * @param start        start point
     * @param end          end point
     * @param nodes        nodes list
     * @param nodesContact nodesContact list
     * @return
     */
    fun getShortestPathBetweenTwoPoints(
        start: Int,
        end: Int, nodes: List<PointF?>,
        nodesContact: List<PointF>
    ): List<Int?> {
        val matrix = getMatrixBetweenFloorPlanNodes(nodes, nodesContact)
        return MapMath.getShortestPathBetweenTwoPoints(start, end, matrix)!!
    }

    /**
     * get best path between points
     *
     * @param points
     * @param nodes
     * @param nodesContact
     * @return
     */
    fun getBestPathBetweenPoints(
        points: IntArray, nodes: List<PointF?>,
        nodesContact: List<PointF>
    ): List<Int?> {
        // adjacency matrix
        val matrix = Array(points.size) { FloatArray(points.size) }
        for (i in matrix.indices) {
            for (j in i until matrix[i].size) {
                if (i == j) {
                    matrix[i][j] = INF
                } else {
                    matrix[i][j] = getDistanceBetweenList(
                        nodes, getShortestPathBetweenTwoPoints(
                            points[i],
                            points[j], nodes, nodesContact
                        )
                    )
                    matrix[j][i] = matrix[i][j]
                }
            }
        }

        // TSP to get best path
        val routeList: MutableList<Int?> = ArrayList()
        val result = MapMath.getBestPathBetweenPointsByGeneticAlgorithm(matrix)
        for (i in 0 until result.size - 1) {
            val size = routeList.size
            routeList.addAll(
                getShortestPathBetweenTwoPoints(
                    points[result[i]], points[result[i + 1]], nodes,
                    nodesContact
                )
            )
            if (i != 0) {
                routeList.removeAt(size)
            }
        }
        return routeList
    }

    /**
     * get best path between points
     *
     * @param pointList
     * @param nodes
     * @param nodesContact
     * @return
     */
    fun getBestPathBetweenPoints(
        pointList: List<PointF?>,
        nodes: MutableList<PointF?>, nodesContact: MutableList<PointF>
    ): List<Int?> {
        if (nodesSize != nodes.size) {
            var value = nodes.size - nodesSize
            for (i in 0 until value) {
                nodes.removeAt(nodes.size - 1)
            }
            value = nodesContact.size - nodesContactSize
            for (i in 0 until value) {
                nodesContact.removeAt(nodesContact.size - 1)
            }
        }

        //find the point on the nearest route
        val points = IntArray(pointList.size)
        for (i in pointList.indices) {
            addPointToList(pointList[i], nodes, nodesContact)
            points[i] = nodes.size - 1
        }
        return getBestPathBetweenPoints(points, nodes, nodesContact)
    }

    /**
     * get shortest distance between two points
     *
     * @param start
     * @param end
     * @param nodes
     * @param nodesContact
     * @return
     */
    fun getShortestDistanceBetweenTwoPoints(
        start: Int, end: Int,
        nodes: List<PointF?>, nodesContact: List<PointF>
    ): Float {
        val list = getShortestPathBetweenTwoPoints(
            start, end, nodes,
            nodesContact
        )
        return getDistanceBetweenList(nodes, list)
    }

    /**
     * adjacency matrix with points
     *
     * @param nodes
     * @param nodesContact
     * @return
     */
    fun getMatrixBetweenFloorPlanNodes(nodes: List<PointF?>, nodesContact: List<PointF>): Array<FloatArray> {
        // set default is INF
        val matrix = Array(nodes.size) { FloatArray(nodes.size) }
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                matrix[i][j] = INF
            }
        }

        // set value for matrix
        for (i in nodesContact.indices) {
            matrix[nodesContact[i].x.toInt()][nodesContact[i].y.toInt()] = MapMath
                .getDistanceBetweenTwoPoints(
                    nodes[nodesContact[i].x.toInt()]!!,
                    nodes[nodesContact[i].y.toInt()]!!
                )
            matrix[nodesContact[i].y.toInt()][nodesContact[i].x.toInt()] =
                matrix[nodesContact[i].x.toInt()][nodesContact[i].y.toInt()]
        }
        return matrix
    }

    /**
     * get shortest distance between two points
     *
     * @param start
     * @param end
     * @param nodes
     * @param nodesContact
     * @return
     */
    fun getShortestDistanceBetweenTwoPoints(
        start: PointF?, end: PointF?,
        nodes: MutableList<PointF?>,
        nodesContact: MutableList<PointF>
    ): List<Int?> {
        if (nodesSize != nodes.size) {
            var value = nodes.size - nodesSize
            for (i in 0 until value) {
                nodes.removeAt(nodes.size - 1)
            }
            value = nodesContact.size - nodesContactSize
            for (i in 0 until value) {
                nodesContact.removeAt(nodesContact.size - 1)
            }
        }
        addPointToList(start, nodes, nodesContact)
        addPointToList(end, nodes, nodesContact)
        return getShortestPathBetweenTwoPoints(
            nodes.size - 2, nodes.size - 1, nodes,
            nodesContact
        )
    }

    /**
     * get the shortest path from the position point to the target point in the map
     *
     * @param position
     * @param target
     * @param nodes
     * @param nodesContact
     * @return
     */
    fun getShortestDistanceBetweenTwoPoints(
        position: PointF?, target: Int,
        nodes: MutableList<PointF?>,
        nodesContact: MutableList<PointF>
    ): List<Int?> {
        if (nodesSize != nodes.size) {
            var value = nodes.size - nodesSize
            for (i in 0 until value) {
                nodes.removeAt(nodes.size - 1)
            }
            value = nodesContact.size - nodesContactSize
            for (i in 0 until value) {
                nodesContact.removeAt(nodesContact.size - 1)
            }
        }
        addPointToList(position, nodes, nodesContact)
        return getShortestPathBetweenTwoPoints(nodes.size - 1, target, nodes, nodesContact)
    }

    /**
     * add point to list
     *
     * @param point
     * @param nodes
     * @param nodesContact
     */
    private fun addPointToList(point: PointF?, nodes: MutableList<PointF?>, nodesContact: MutableList<PointF>) {
        if (point != null) {
            var pV: PointF? = null
            var po1 = 0
            var po2 = 0
            var min1 = INF
            for (i in 0 until nodesContact.size - 1) {
                val p1 = nodes[nodesContact[i].x.toInt()]!!
                val p2 = nodes[nodesContact[i].y.toInt()]!!
                if (!MapMath.isObtuseAnglePointAndLine(point, p1, p2)) {
                    val minDis = MapMath.getDistanceFromPointToLine(point, p1, p2)
                    if (min1 > minDis) {
                        pV = MapMath.getIntersectionCoordinatesFromPointToLine(point, p1, p2)
                        min1 = minDis
                        po1 = nodesContact[i].x.toInt()
                        po2 = nodesContact[i].y.toInt()
                    }
                }
            }
            // get intersection
            nodes.add(pV)
            //Log.i(TAG, "node=" + (nodes.size() - 1) + ", po1=" + po1 + ", po2=" + po2);
            nodesContact.add(PointF(po1.toFloat(), (nodes.size - 1).toFloat()))
            nodesContact.add(PointF(po2.toFloat(), (nodes.size - 1).toFloat()))
        }
    }

    /**
     * bitmap to picture
     *
     * @param bitmap
     * @return
     */
    @JvmStatic
    fun getPictureFromBitmap(bitmap: Bitmap): Picture {
        val picture = Picture()
        val canvas = picture.beginRecording(
            bitmap.width,
            bitmap.height
        )
        canvas.drawBitmap(
            bitmap,
            null,
            RectF(
                0f, 0f, bitmap.width.toFloat(), bitmap
                    .height.toFloat()
            ), null
        )
        picture.endRecording()
        return picture
    }
}