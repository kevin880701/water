package com.lhr.water.model

import android.os.Parcel
import android.os.Parcelable

data class FormData(
    val id: String,
    val formName: String,
    val formNumber: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt()!!
    ) {
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FormData

        if (id != other.id) return false
        if (formName != other.formName) return false
        if (formNumber != other.formNumber) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + formName.hashCode()
        result = 31 * result + formNumber.hashCode()
        return result
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(formName)
        parcel.writeInt(formNumber)
    }

    override fun describeContents(): Int {
        return 0
    }


//    fun getBatteryDescription(context: Context): String{
//        return if (battery <= 10) context.getString(R.string.battery_too_low) else context.getString(
//            R.string.battery_enough)
//    }


    companion object{
        @JvmField
        val CREATOR: Parcelable.Creator<FormData> = object : Parcelable.Creator<FormData> {
            override fun createFromParcel(parcel: Parcel): FormData {
                return FormData(parcel)
            }

            override fun newArray(size: Int): Array<FormData?> {
                return arrayOfNulls(size)
            }
        }
    }
}