package com.lhr.water.model

import androidx.fragment.app.Fragment
import com.lhr.water.R
import com.lhr.water.room.TargetEntity

class Model {
    companion object {
        const val IMAGE_REQUEST_CODE : Int = 100
        var regionNameArrayList : ArrayList<String> = ArrayList()
        var allTargetEntityArrayList : ArrayList<TargetEntity> = ArrayList()
        var allTargetDataArrayList : ArrayList<TargetData> = ArrayList()
        var mapFragmentArrayList : ArrayList<Fragment> = ArrayList()
        var markTypeMap = mapOf(0 to "mark", 1 to "office", 2 to "door", 3 to "stairs", 4 to "elevator", 5 to "wc", 6 to "parking")
        var markDrawableIdMap = mapOf(0 to R.drawable.mark, 1 to R.drawable.office, 2 to R.drawable.door, 3 to R.drawable.stairs, 4 to R.drawable.elevator, 5 to R.drawable.wc, 6 to R.drawable.parking)

    }
}