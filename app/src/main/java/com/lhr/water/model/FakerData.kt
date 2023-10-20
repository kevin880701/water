package com.lhr.water.model

import android.os.Parcel
import android.os.Parcelable
import com.lhr.water.room.SqlModel.Companion.id


object FakerData {
    val regionList = arrayListOf(
        RegionFakerData("1區", 1, "文山所", 0, "01-046-A1-00-L1", 200001, 1710f, 740f, "mark", 0),
        RegionFakerData("1區", 1, "文山所", 0, "01-046-A1-00-H1", 200002, 1050f, 1100f, "mark", 0),
        RegionFakerData("1區", 1, "文山所", 0, "01-046-A1-00-H2", 200003, 1050f, 1500f, "mark", 0),
        RegionFakerData("1區", 1, "文山所", 0, "01-046-A1-00-H3", 200004, 1050f, 1870f, "mark", 0),
        RegionFakerData("1區", 1, "文山所", 0, "01-046-A1-00-H4", 200005, 1050f, 2300f, "mark", 0),
        RegionFakerData("1區", 1, "文山所", 0, "01-046-A1-00-L1", 200006, 2760f, 1255f, "mark", 0),
        RegionFakerData("1區", 1, "文山所", 0, "01-046-A1-00-L1", 200007, 2760f, 1580f, "mark", 0),
        RegionFakerData("1區", 1, "文山所", 0, "01-046-A1-00-L1", 200008, 2760f, 1900f, "mark", 0),
        RegionFakerData("1區", 1, "文山所", 0, "01-046-A1-00-H4", 200009, 2000f, 2340f, "mark", 0),
        RegionFakerData("1區", 1, "汐止所", 1, "不鏽鋼S型管", 100001, 610f, 470f, "mark", 0),
        RegionFakerData("1區", 1, "汐止所", 1, "HIP管", 100002, 1330f, 470f, "mark", 0),
        RegionFakerData("1區", 1, "汐止所", 1, "水量計箱", 100003, 2040f, 470f, "mark", 0),
        RegionFakerData("1區", 1, "汐止所", 1, "HIP管", 100004, 580f, 800f, "mark", 0),
        RegionFakerData("1區", 1, "汐止所", 1, "DIP套管與配件", 100005, 1260f, 950f, "mark", 0),
        RegionFakerData("1區", 1, "汐止所", 1, "伸縮接頭", 100006, 580f, 1030f, "mark", 0),
        RegionFakerData("1區", 1, "汐止所", 1, "PVC T型街頭", 100007, 580f, 1290f, "mark", 0),
        RegionFakerData("1區", 1, "汐止所", 1, "不鏽鋼彎頭", 100008, 1110f, 1335f, "mark", 0),
        RegionFakerData("1區", 1, "汐止所", 1, "塑膠L彎頭", 1001, 1580f, 1335f, "mark", 0),
        RegionFakerData("1區", 1, "汐止所", 1, "制水閥盒", 1002, 2040f, 720f, "mark", 0),
        RegionFakerData("1區", 1, "汐止所", 1, "DIP短管", 1003, 2000f, 920f, "mark", 0),
        RegionFakerData("1區", 1, "汐止所", 1, "消防栓", 1004, 2040f, 1165f, "mark", 0),
        RegionFakerData("1區", 1, "貢雙所", 2, "01-0147002-A1-F1-H3", 1005, 1635f, 610f, "mark", 0),
        RegionFakerData("1區", 1, "貢雙所", 2, "01-0147002-A1-F1-L3", 1006, 2715f, 610f, "mark", 0),
        RegionFakerData("1區", 1, "貢雙所", 2, "01-0147002-A1-F1-L2", 1007, 2715f, 1340f, "mark", 0),
        RegionFakerData("1區", 1, "貢雙所", 2, "01-0147002-A1-F1-L1", 1008, 1550f, 1340f, "mark", 0),
        RegionFakerData("1區", 1, "貢雙所", 2, "01-0147002-A1-F1-H3", 1009, 400f, 2040f, "mark",0),
        RegionFakerData("1區", 1, "貢雙所", 2, "01-0147002-A1-F1-H3", 1010, 1290f, 2040f, "mark",0),
        RegionFakerData("1區", 1, "貢雙所", 2, "01-0147002-A1-F1-H3", 1011, 2430f, 2040f, "mark", 0),

        RegionFakerData("2區", 2, "2F", 0, "A201", 200001, 110f, 110f, "mark", 0),
        RegionFakerData("2區", 2, "2F", 0, "A202", 200002, 420f, 110f, "mark", 0),
        RegionFakerData("2區", 2, "2F", 0, "A203", 200003, 790f, 110f, "mark", 0),
        RegionFakerData("2區", 2, "2F", 0, "A204", 200004, 1220f, 110f, "mark", 0),
        RegionFakerData("2區", 2, "2F", 0, "A205", 200005, 1550f, 110f, "mark", 0),
        RegionFakerData("2區", 2, "2F", 0, "A206", 200006, 1750f, 110f, "mark", 0),
        RegionFakerData("2區", 2, "2F", 0, "A207", 200007, 1970f, 110f, "mark", 0),
        RegionFakerData("2區", 2, "2F", 0, "A208", 200008, 2190f, 110f, "mark", 0),
        RegionFakerData("2區", 2, "2F", 0, "A209", 200009, 270f, 550f, "mark", 0),
        RegionFakerData("2區", 2, "2F", 0, "A210", 200010, 800f, 550f, "mark", 0),
        RegionFakerData("2區", 2, "2F", 0, "A211", 200011, 1650f, 550f, "mark", 0),
        RegionFakerData("2區", 2, "2F", 0, "A212", 200012, 2070f, 550f, "mark", 0),
        RegionFakerData("2區", 2, "2F", 0, "A213", 200013, 270f, 1050f, "mark", 0),
        RegionFakerData("2區", 2, "2F", 0, "A214", 200014, 700f, 1050f, "mark", 0),
        RegionFakerData("2區", 2, "2F", 0, "A215", 200015, 950f, 1050f, "mark", 0),
        RegionFakerData("2區", 2, "2F", 0, "A216", 200016, 1520f, 1050f, "mark", 0),
        RegionFakerData("2區", 2, "2F", 0, "A217", 200017, 1800f, 1050f, "mark", 0),
        RegionFakerData("2區", 2, "2F", 0, "A218", 200018, 2120f, 1050f, "mark", 0),
        RegionFakerData("2區", 2, "2F", 0, "樓梯", 200019, 1250f, 1100f, "stairs", 3),
        RegionFakerData("2區", 2, "1F", 1, "A101", 100001, 580f, 150f, "office", 1),
        RegionFakerData("2區", 2, "1F", 1, "A102", 100002, 1175f, 150f, "office", 1),
        RegionFakerData("2區", 2, "1F", 1, "A103", 100003, 2000f, 150f, "office", 1),
        RegionFakerData("2區", 2, "1F", 1, "A104", 100004, 300f, 560f, "office", 1),
        RegionFakerData("2區", 2, "1F", 1, "廁所", 100005, 800f, 560f, "wc", 5),
        RegionFakerData("2區", 2, "1F", 1, "A106", 100006, 2000f, 560f, "office", 1),
        RegionFakerData("2區", 2, "1F", 1, "樓梯", 100007, 1450f, 70f, "stairs", 3),
        RegionFakerData("2區", 2, "1F", 1, "大門", 100008, 1300f, 720f, "door", 2),
        RegionFakerData("2區", 2, "B1", 2, "B101", 1001, 130f, 180f, "parking", 6),
        RegionFakerData("2區", 2, "B1", 2, "B102", 1002, 380f, 180f, "parking", 6),
        RegionFakerData("2區", 2, "B1", 2, "B103", 1003, 640f, 180f, "parking", 6),
        RegionFakerData("2區", 2, "B1", 2, "B104", 1004, 1950f, 180f, "parking", 6),
        RegionFakerData("2區", 2, "B1", 2, "B105", 1005, 2200f, 180f, "parking", 6),
        RegionFakerData("2區", 2, "B1", 2, "B101", 1006, 130f, 940f, "parking", 6),
        RegionFakerData("2區", 2, "B1", 2, "B102", 1007, 380f, 940f, "parking", 6),
        RegionFakerData("2區", 2, "B1", 2, "B103", 1008, 640f, 940f, "parking", 6),
        RegionFakerData("2區", 2, "B1", 2, "B104", 1009, 1950f, 940f, "parking", 6),
        RegionFakerData("2區", 2, "B1", 2, "B105", 1010, 2200f, 940f, "parking", 6),
        RegionFakerData("2區", 2, "B1", 2, "電梯", 1011, 1300f, 1390f, "elevator", 4),

        RegionFakerData("3區", 3, "文山所", 0, "A201", 200001, 110f, 110f, "mark", 0),
        RegionFakerData("3區", 3, "文山所", 0, "A202", 200002, 420f, 110f, "mark", 0),
        RegionFakerData("3區", 3, "文山所", 0, "A203", 200003, 790f, 110f, "mark", 0),
        RegionFakerData("3區", 3, "文山所", 0, "A204", 200004, 1220f, 110f, "mark", 0),
        RegionFakerData("3區", 3, "文山所", 0, "A205", 200005, 1550f, 110f, "mark", 0),
        RegionFakerData("3區", 3, "文山所", 0, "A206", 200006, 1750f, 110f, "mark", 0),
        RegionFakerData("3區", 3, "文山所", 0, "A207", 200007, 1970f, 110f, "mark", 0),
        RegionFakerData("3區", 3, "文山所", 0, "A208", 200008, 2190f, 110f, "mark", 0),
        RegionFakerData("3區", 3, "文山所", 0, "A209", 200009, 270f, 550f, "mark", 0),
        RegionFakerData("3區", 3, "文山所", 0, "A210", 200010, 800f, 550f, "mark", 0),
        RegionFakerData("3區", 3, "文山所", 0, "A211", 200011, 1650f, 550f, "mark", 0),
        RegionFakerData("3區", 3, "文山所", 0, "A212", 200012, 2070f, 550f, "mark", 0),
        RegionFakerData("3區", 3, "文山所", 0, "A213", 200013, 270f, 1050f, "mark", 0),
        RegionFakerData("3區", 3, "文山所", 0, "A214", 200014, 700f, 1050f, "mark", 0),
        RegionFakerData("3區", 3, "文山所", 0, "A215", 200015, 950f, 1050f, "mark", 0),
        RegionFakerData("3區", 3, "文山所", 0, "A216", 200016, 1520f, 1050f, "mark", 0),
        RegionFakerData("3區", 3, "文山所", 0, "A217", 200017, 1800f, 1050f, "mark", 0),
        RegionFakerData("3區", 3, "文山所", 0, "A218", 200018, 2120f, 1050f, "mark", 0),
        RegionFakerData("3區", 3, "文山所", 0, "樓梯", 200019, 1250f, 1100f, "stairs", 3),
        RegionFakerData("3區", 3, "1F", 1, "A101", 100001, 580f, 150f, "office", 1),
        RegionFakerData("3區", 3, "1F", 1, "A102", 100002, 1175f, 150f, "office", 1),
        RegionFakerData("3區", 3, "1F", 1, "A103", 100003, 2000f, 150f, "office", 1),
        RegionFakerData("3區", 3, "1F", 1, "A104", 100004, 300f, 560f, "office", 1),
        RegionFakerData("3區", 3, "1F", 1, "廁所", 100005, 800f, 560f, "wc", 5),
        RegionFakerData("3區", 3, "1F", 1, "A106", 100006, 2000f, 560f, "office", 1),
        RegionFakerData("3區", 3, "1F", 1, "樓梯", 100007, 1450f, 70f, "stairs", 3),
        RegionFakerData("3區", 3, "1F", 1, "大門", 100008, 1300f, 720f, "door", 2),
        RegionFakerData("3區", 3, "B1", 2, "B101", 1001, 130f, 180f, "parking", 6),
        RegionFakerData("3區", 3, "B1", 2, "B102", 1002, 380f, 180f, "parking", 6),
        RegionFakerData("3區", 3, "B1", 2, "B103", 1003, 640f, 180f, "parking", 6),
        RegionFakerData("3區", 3, "B1", 2, "B104", 1004, 1950f, 180f, "parking", 6),
        RegionFakerData("3區", 3, "B1", 2, "B105", 1005, 2200f, 180f, "parking", 6),
        RegionFakerData("3區", 3, "B1", 2, "B101", 1006, 130f, 940f, "parking", 6),
        RegionFakerData("3區", 3, "B1", 2, "B102", 1007, 380f, 940f, "parking", 6),
        RegionFakerData("3區", 3, "B1", 2, "B103", 1008, 640f, 940f, "parking", 6),
        RegionFakerData("3區", 3, "B1", 2, "B104", 1009, 1950f, 940f, "parking", 6),
        RegionFakerData("3區", 3, "B1", 2, "B105", 1010, 2200f, 940f, "parking", 6),
        RegionFakerData("3區", 3, "B1", 2, "電梯", 1011, 1300f, 1390f, "elevator", 4),

        RegionFakerData("4區", 4, "文山所", 0, "A201", 200001, 110f, 110f, "mark", 0),
        RegionFakerData("4區", 4, "文山所", 0, "A202", 200002, 420f, 110f, "mark", 0),
        RegionFakerData("4區", 4, "文山所", 0, "A203", 200003, 790f, 110f, "mark", 0),
        RegionFakerData("4區", 4, "文山所", 0, "A204", 200004, 1220f, 110f, "mark", 0),
        RegionFakerData("4區", 4, "文山所", 0, "A205", 200005, 1550f, 110f, "mark", 0),
        RegionFakerData("4區", 4, "文山所", 0, "A206", 200006, 1750f, 110f, "mark", 0),
        RegionFakerData("4區", 4, "文山所", 0, "A207", 200007, 1970f, 110f, "mark", 0),
        RegionFakerData("4區", 4, "文山所", 0, "A208", 200008, 2190f, 110f, "mark", 0),
        RegionFakerData("4區", 4, "文山所", 0, "A209", 200009, 270f, 550f, "mark", 0),
        RegionFakerData("4區", 4, "文山所", 0, "A210", 200010, 800f, 550f, "mark", 0),
        RegionFakerData("4區", 4, "文山所", 0, "A211", 200011, 1650f, 550f, "mark", 0),
        RegionFakerData("4區", 4, "文山所", 0, "A212", 200012, 2070f, 550f, "mark", 0),
        RegionFakerData("4區", 4, "文山所", 0, "A213", 200013, 270f, 1050f, "mark", 0),
        RegionFakerData("4區", 4, "文山所", 0, "A214", 200014, 700f, 1050f, "mark", 0),
        RegionFakerData("4區", 4, "文山所", 0, "A215", 200015, 950f, 1050f, "mark", 0),
        RegionFakerData("4區", 4, "文山所", 0, "A216", 200016, 1520f, 1050f, "mark", 0),
        RegionFakerData("4區", 4, "文山所", 0, "A217", 200017, 1800f, 1050f, "mark", 0),
        RegionFakerData("4區", 4, "文山所", 0, "A218", 200018, 2120f, 1050f, "mark", 0),
        RegionFakerData("4區", 4, "文山所", 0, "樓梯", 200019, 1250f, 1100f, "stairs", 3),
        RegionFakerData("4區", 4, "1F", 1, "A101", 100001, 580f, 150f, "office", 1),
        RegionFakerData("4區", 4, "1F", 1, "A102", 100002, 1175f, 150f, "office", 1),
        RegionFakerData("4區", 4, "1F", 1, "A103", 100003, 2000f, 150f, "office", 1),
        RegionFakerData("4區", 4, "1F", 1, "A104", 100004, 300f, 560f, "office", 1),
        RegionFakerData("4區", 4, "1F", 1, "廁所", 100005, 800f, 560f, "wc", 5),
        RegionFakerData("4區", 4, "1F", 1, "A106", 100006, 2000f, 560f, "office", 1),
        RegionFakerData("4區", 4, "1F", 1, "樓梯", 100007, 1450f, 70f, "stairs", 3),
        RegionFakerData("4區", 4, "1F", 1, "大門", 100008, 1300f, 720f, "door", 2),
        RegionFakerData("4區", 4, "B1", 2, "B101", 1001, 130f, 180f, "parking", 6),
        RegionFakerData("4區", 4, "B1", 2, "B102", 1002, 380f, 180f, "parking", 6),
        RegionFakerData("4區", 4, "B1", 2, "B103", 1003, 640f, 180f, "parking", 6),
        RegionFakerData("4區", 4, "B1", 2, "B104", 1004, 1950f, 180f, "parking", 6),
        RegionFakerData("4區", 4, "B1", 2, "B105", 1005, 2200f, 180f, "parking", 6),
        RegionFakerData("4區", 4, "B1", 2, "B101", 1006, 130f, 940f, "parking", 6),
        RegionFakerData("4區", 4, "B1", 2, "B102", 1007, 380f, 940f, "parking", 6),
        RegionFakerData("4區", 4, "B1", 2, "B103", 1008, 640f, 940f, "parking", 6),
        RegionFakerData("4區", 4, "B1", 2, "B104", 1009, 1950f, 940f, "parking", 6),
        RegionFakerData("4區", 4, "B1", 2, "B105", 1010, 2200f, 940f, "parking", 6),
        RegionFakerData("4區", 4, "B1", 2, "電梯", 1011, 1300f, 1390f, "elevator", 4)
    )


    data class RegionFakerData(
        val regionName: String,
        val regionNumber: Int,
        val mapName: String,
        val mapNumber: Int,
        val storageName: String,
        val storageNumber: Int,
        val targetCoordinateX: Float,
        val targetCoordinateY: Float,
        val targetType: String,
        val targetTypeNum: Int
    ) : Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readString()!!,
            parcel.readInt(),
            parcel.readString()!!,
            parcel.readInt(),
            parcel.readString()!!,
            parcel.readInt(),
            parcel.readFloat(),
            parcel.readFloat(),
            parcel.readString()!!,
            parcel.readInt()
        ) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(id)
            parcel.writeString(regionName)
            parcel.writeInt(regionNumber)
            parcel.writeString(mapName)
            parcel.writeInt(mapNumber)
            parcel.writeString(storageName)
            parcel.writeInt(storageNumber)
            parcel.writeFloat(targetCoordinateX)
            parcel.writeFloat(targetCoordinateY)
            parcel.writeString(targetType)
            parcel.writeInt(targetTypeNum)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<RegionFakerData> {
            override fun createFromParcel(parcel: Parcel): RegionFakerData {
                return RegionFakerData(parcel)
            }

            override fun newArray(size: Int): Array<RegionFakerData?> {
                return arrayOfNulls(size)
            }
        }
}


}