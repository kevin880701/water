package com.lhr.water.model

import android.os.Parcel
import android.os.Parcelable

data class RegionData(
    val id: String,
    val regionName: String,
    val regionNumber: Int,
    val mapName: String,
    val mapNumber: Int,
    val storageName: String,
    val storageNumber: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt()!!,
        parcel.readString()!!,
        parcel.readInt()!!,
        parcel.readString()!!,
        parcel.readInt()!!
    ) {
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RegionData

        if (id != other.id) return false
        if (regionName != other.regionName) return false
        if (regionNumber != other.regionNumber) return false
        if (mapName != other.mapName) return false
        if (mapNumber != other.mapNumber) return false
        if (storageName != other.storageName) return false
        if (storageNumber != other.storageNumber) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + regionName.hashCode()
        result = 31 * result + regionNumber.hashCode()
        result = 31 * result + mapName.hashCode()
        result = 31 * result + mapNumber.hashCode()
        result = 31 * result + storageName.hashCode()
        result = 31 * result + storageNumber.hashCode()
        return result
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(regionName)
        parcel.writeInt(regionNumber)
        parcel.writeString(mapName)
        parcel.writeInt(mapNumber)
        parcel.writeString(storageName)
        parcel.writeInt(storageNumber)
    }

    override fun describeContents(): Int {
        return 0
    }


//    fun getBatteryDescription(context: Context): String{
//        return if (battery <= 10) context.getString(R.string.battery_too_low) else context.getString(
//            R.string.battery_enough)
//    }


    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<RegionData> = object : Parcelable.Creator<RegionData> {
            override fun createFromParcel(parcel: Parcel): RegionData {
                return RegionData(parcel)
            }

            override fun newArray(size: Int): Array<RegionData?> {
                return arrayOfNulls(size)
            }
        }
    }
}