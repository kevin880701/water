package com.lhr.water.util

import com.lhr.water.data.MapData
import com.lhr.water.room.RegionEntity
import com.lhr.water.util.FormName.deliveryFormName
import com.lhr.water.util.FormName.inventoryFormName
import com.lhr.water.util.FormName.pickingFormName
import com.lhr.water.util.FormName.returningFormName
import com.lhr.water.util.FormName.transferFormName

val API_BASE: String = "http://localhost:8081"

object TransferStatus {
    const val transferInput = "transferInput"
    const val transferOutput = "transferOutput"
    const val notTransfer = "notTransfer"
}

object FormName {
    const val deliveryFormName = "交貨通知單"
    const val pickingFormName = "材料領料單"
    const val transferFormName = "材料調撥單"
    const val returningFormName = "材料退料單"
    const val inventoryFormName = "材料盤點單"
}


object DealStatus {
    const val waitDeal = "待處理"
    const val nowDeal = "處理中"
    const val completeDeal = "處理完成"
}

object FormField {
    // 交貨通知單欄位
    private val deliveryFormField = arrayOf(
        "id",
        "deliveryStatus",
        "reportId",
        "reportTitle",
        "date",
        "formNumber",
        "underwriter",
        "ano",
        "contractNumber",
        "deliveryDay",
        "deliveryDate",
        "extendDate1",
        "extendDate2",
        "extendDate3",
        "receiptDept",
        "deliverylocation",
        "extendNo1",
        "extendNo2",
        "extendNo3",
        "projectNumber",
        "contact",
        "contactPhone",
        "applyNo",
        "sumAddition"
    )
    private val deliveryMaterialField = arrayOf(
        "number",
        "itemNo",
        "batch",
        "no",
        "materialNumber",
        "materialName",
        "materialSpec",
        "materialUnit",
        "length",
        "deliveryQuantity",
        "receivedQuantity",
        "price",
        "itemCost"
    )

    // 材料領料單欄位
    private val pickFormField = arrayOf(
        "id",
        "dealStatus",
        "reportId",
        "reportTitle",
        "date",
        "issuingUnit",
        "pickingDate",
        "pickingDept",
        "formNumber",
        "originalVoucherNumber",
        "costAllocationUnit",
        "accountingSubject",
        "systemCode",
        "usageCode",
        "projectNumber",
        "projectName",
        "caseNumber"
    )
    private val pickMaterialField = arrayOf(
        "number",
        "materialNumber",
        "materialName",
        "materialSpec",
        "materialUnit",
        "requestedQuantity",
        "actualQuantity",
        "price",
        "itemCost"
    )

    // 材料調撥單欄位
    private val transferFormField = arrayOf(
        "id",
        "dealStatus",
        "reportId",
        "reportTitle",
        "date",
        "transferringTransferNumber",
        "receivingApplyTransferNumber",
        "receivingTransferNumber",
        "transferringDept",
        "originalVoucherNumber",
        "requiredDate",
        "transferringTransferDate",
        "receivingTransferDate",
        "receivingApplyTransferDate",
        "receivingDept",
        "receivingLocation",
        "contact",
        "contactPhone",
        "transferDescription"
    )
    private val transferMaterialField = arrayOf(
        "number",
        "materialNumber",
        "materialName",
        "materialSpec",
        "materialUnit",
        "approvedQuantity",
        "applyNumber",
        "allocatedQuantity",
        "receivedQuantity"
    )

    // 材料退料單欄位
    private val returningFormField = arrayOf(
        "id",
        "dealStatus",
        "reportId",
        "reportTitle",
        "date",
        "receiptDept",
        "leadNumber",
        "leadDept",
        "formNumber",
        "receivedDate",
        "accountingSubject",
        "costAllocationUnit",
        "returnDept",
        "systemCode",
        "usageCode",
        "projectNumber",
        "projectName",
        "originalVoucherNumber"
    )
    private val returningMaterialField = arrayOf(
        "number",
        "materialNumber",
        "materialName",
        "materialSpec",
        "materialUnit",
        "returnedQuantity",
        "receivedQuantity",
        "stockingPrice"
    )

    // 材料盤點單欄位
    private val inventoryFormField = arrayOf(
        "id",
        "dealStatus",
        "reportId",
        "reportTitle",
        "date",
        "inventoryUnit",
        "deptName",
        "materialNumber",
        "materialUnit",
        "materialName",
        "materialSpec",
        "actualQuantity",
        "checkDate",
        "lastUseDate",
        "approvedDate"
    )


    val formFieldMap: Map<String, Array<Array<String>>> = mapOf(
        deliveryFormName to arrayOf(deliveryFormField, deliveryMaterialField),
        pickingFormName to arrayOf(pickFormField, pickMaterialField),
        transferFormName to arrayOf(transferFormField, transferMaterialField),
        returningFormName to arrayOf(returningFormField, returningMaterialField),
        inventoryFormName to arrayOf(inventoryFormField)
    )
}
// 1-交貨，2-驗收，3-調撥，4-領料，5-退料，6-盤點
val formTypeMap: Map<String, Int> = mapOf(
    deliveryFormName to 1,
    transferFormName to 3,
    pickingFormName to 4,
    returningFormName to 5,
    inventoryFormName to 6
)

// 1: 已交貨，2: 已驗收，3: 已移出
val materialStatusMap: Map<Int, String> = mapOf(
    1 to "已交貨",
    2 to "已驗收",
    3 to "已移出",
)

val MapDataList: List<RegionEntity> =
    listOf(
        RegionEntity(regionName = "屏東區管理處", regionNumber = "0D", deptName = "屏東區管理處", deptNumber = "0D", mapSeq = 1),
        RegionEntity(regionName = "屏東區管理處", regionNumber = "0D", deptName = "屏東區管理處", deptNumber = "0D", mapSeq = 2),
        RegionEntity(regionName = "屏東區管理處", regionNumber = "0D", deptName = "屏東區管理處", deptNumber = "0D", mapSeq = 3),
        RegionEntity(regionName = "屏東區管理處", regionNumber = "0D", deptName = "屏東區管理處", deptNumber = "0D", mapSeq = 4),
        RegionEntity(regionName = "屏東區管理處", regionNumber = "0D", deptName = "屏東區管理處", deptNumber = "0D", mapSeq = 5),
        RegionEntity(regionName = "屏東區管理處", regionNumber = "0D", deptName = "屏東區管理處", deptNumber = "0D", mapSeq = 6),
        RegionEntity(regionName = "屏東區管理處", regionNumber = "0D", deptName = "恆春營運所", deptNumber = "0D43", mapSeq = 1),
        RegionEntity(regionName = "屏東區管理處", regionNumber = "0D", deptName = "恆春營運所", deptNumber = "0D43", mapSeq = 2),
        RegionEntity(regionName = "屏東區管理處", regionNumber = "0D", deptName = "東港營運所", deptNumber = "0D41", mapSeq = 1),
        RegionEntity(regionName = "屏東區管理處", regionNumber = "0D", deptName = "東港營運所", deptNumber = "0D41", mapSeq = 2),
        RegionEntity(regionName = "屏東區管理處", regionNumber = "0D", deptName = "潮州營運所", deptNumber = "0D42", mapSeq = 1),
        RegionEntity(regionName = "屏東區管理處", regionNumber = "0D", deptName = "潮州營運所", deptNumber = "0D42", mapSeq = 2),
        RegionEntity(regionName = "屏東區管理處", regionNumber = "0D", deptName = "牡丹給水廠", deptNumber = "0D60", mapSeq = 1),
        RegionEntity(regionName = "屏東區管理處", regionNumber = "0D", deptName = "牡丹給水廠", deptNumber = "0D60", mapSeq = 2),
        RegionEntity(regionName = "屏東區管理處", regionNumber = "0D", deptName = "牡丹給水廠", deptNumber = "0D60", mapSeq = 3),
        RegionEntity(regionName = "屏東區管理處", regionNumber = "0D", deptName = "高樹營運所", deptNumber = "0D44", mapSeq = 1),
        RegionEntity(regionName = "屏東區管理處", regionNumber = "0D", deptName = "高樹營運所", deptNumber = "0D44", mapSeq = 2),
        RegionEntity(regionName = "屏東區管理處", regionNumber = "0D", deptName = "高樹營運所", deptNumber = "0D44", mapSeq = 3),
        RegionEntity(regionName = "第一區管理處", regionNumber = "01", deptName = "基隆服務所", deptNumber = "0121", mapSeq = 1),
        RegionEntity(regionName = "第一區管理處", regionNumber = "01", deptName = "文山營運所", deptNumber = "0146", mapSeq = 1),
        RegionEntity(regionName = "第一區管理處", regionNumber = "01", deptName = "暖暖淨水場", deptNumber = "016201", mapSeq = 1),
        RegionEntity(regionName = "第一區管理處", regionNumber = "01", deptName = "汐止營運所", deptNumber = "0143", mapSeq = 1),
        RegionEntity(regionName = "第一區管理處", regionNumber = "01", deptName = "淡水所雙圳頭淨水場", deptNumber = "014401", mapSeq = 1),
        RegionEntity(regionName = "第一區管理處", regionNumber = "01", deptName = "淡水營運所", deptNumber = "0144", mapSeq = 1),
        RegionEntity(regionName = "第一區管理處", regionNumber = "01", deptName = "瑞芳營運所", deptNumber = "0141", mapSeq = 1),
        RegionEntity(regionName = "第一區管理處", regionNumber = "01", deptName = "貢寮雙溪營運所", deptNumber = "0147", mapSeq = 1),
        RegionEntity(regionName = "第一區管理處", regionNumber = "01", deptName = "金瓜石淨水", deptNumber = "014104", mapSeq = 1),
        RegionEntity(regionName = "第七區管理處", regionNumber = "07", deptName = "七區物料課", deptNumber = "0710", mapSeq = 1),
        RegionEntity(regionName = "第七區管理處", regionNumber = "07", deptName = "七區物料課", deptNumber = "0710", mapSeq = 2),
        RegionEntity(regionName = "第七區管理處", regionNumber = "07", deptName = "七區物料課", deptNumber = "0710", mapSeq = 3),
        RegionEntity(regionName = "第七區管理處", regionNumber = "07", deptName = "坪頂給水廠", deptNumber = "0764", mapSeq = 1),
        RegionEntity(regionName = "第七區管理處", regionNumber = "07", deptName = "大崗山廠寮淨水場", deptNumber = "076103", mapSeq = 1),
        RegionEntity(regionName = "第七區管理處", regionNumber = "07", deptName = "岡山服務所", deptNumber = "0726", mapSeq = 1),
        RegionEntity(regionName = "第七區管理處", regionNumber = "07", deptName = "楠梓服務所", deptNumber = "0727", mapSeq = 1),
        RegionEntity(regionName = "第七區管理處", regionNumber = "07", deptName = "澄清湖給水廠", deptNumber = "0762", mapSeq = 1),
        RegionEntity(regionName = "第七區管理處", regionNumber = "07", deptName = "澄清湖給水廠", deptNumber = "0762", mapSeq = 2),
        RegionEntity(regionName = "第七區管理處", regionNumber = "07", deptName = "澎湖營運所", deptNumber = "0749", mapSeq = 1),
        RegionEntity(regionName = "第七區管理處", regionNumber = "07", deptName = "澎湖營運所", deptNumber = "0749", mapSeq = 2),
        RegionEntity(regionName = "第七區管理處", regionNumber = "07", deptName = "澎湖營運所", deptNumber = "0749", mapSeq = 3),
        RegionEntity(regionName = "第七區管理處", regionNumber = "07", deptName = "第七區管理處", deptNumber = "07", mapSeq = 1),
        RegionEntity(regionName = "第七區管理處", regionNumber = "07", deptName = "路竹服務所", deptNumber = "0725", mapSeq = 1),
        RegionEntity(regionName = "第七區管理處", regionNumber = "07", deptName = "高雄北區服務所", deptNumber = "0721", mapSeq = 1),
        RegionEntity(regionName = "第七區管理處", regionNumber = "07", deptName = "高雄服務所", deptNumber = "0729", mapSeq = 1),
        RegionEntity(regionName = "第七區管理處", regionNumber = "07", deptName = "鳳山服務所", deptNumber = "0724", mapSeq = 1),
        RegionEntity(regionName = "第三區管理處", regionNumber = "03", deptName = "湖口營運所", deptNumber = "0343", mapSeq = 1),
        RegionEntity(regionName = "第三區管理處", regionNumber = "03", deptName = "竹北營運所", deptNumber = "0348", mapSeq = 1),
        RegionEntity(regionName = "第三區管理處", regionNumber = "03", deptName = "竹東營運所", deptNumber = "0341", mapSeq = 1),
        RegionEntity(regionName = "第三區管理處", regionNumber = "03", deptName = "第三區管理處", deptNumber = "03", mapSeq = 1),
        RegionEntity(regionName = "第三區管理處", regionNumber = "03", deptName = "第三區管理處", deptNumber = "03", mapSeq = 2),
        RegionEntity(regionName = "第三區管理處", regionNumber = "03", deptName = "苗栗營運所", deptNumber = "0345", mapSeq = 1),
        RegionEntity(regionName = "第三區管理處", regionNumber = "03", deptName = "銅鑼營運所", deptNumber = "0346", mapSeq = 1),
        RegionEntity(regionName = "第九區管理處", regionNumber = "09", deptName = "九區物料課", deptNumber = "0910", mapSeq = 1),
        RegionEntity(regionName = "第九區管理處", regionNumber = "09", deptName = "玉里所玉里淨水場", deptNumber = "094603", mapSeq = 1),
        RegionEntity(regionName = "第九區管理處", regionNumber = "09", deptName = "第九區管理處", deptNumber = "09", mapSeq = 1),
        RegionEntity(regionName = "第九區管理處", regionNumber = "09", deptName = "第九區管理處", deptNumber = "09", mapSeq = 2),
        RegionEntity(regionName = "第九區管理處", regionNumber = "09", deptName = "第九區管理處", deptNumber = "09", mapSeq = 3),
        RegionEntity(regionName = "第二區管理處", regionNumber = "02", deptName = "二區物料課", deptNumber = "0210", mapSeq = 1),
        RegionEntity(regionName = "第二區管理處", regionNumber = "02", deptName = "大湳給水廠", deptNumber = "0263", mapSeq = 1),
        RegionEntity(regionName = "第二區管理處", regionNumber = "02", deptName = "平鎮給水廠", deptNumber = "0262", mapSeq = 1),
        RegionEntity(regionName = "第二區管理處", regionNumber = "02", deptName = "平鎮給水廠", deptNumber = "0262", mapSeq = 2),
        RegionEntity(regionName = "第二區管理處", regionNumber = "02", deptName = "林口服務所", deptNumber = "0229", mapSeq = 1),
        RegionEntity(regionName = "第二區管理處", regionNumber = "02", deptName = "龍潭營運所", deptNumber = "0244", mapSeq = 1),
        RegionEntity(regionName = "第二區管理處", regionNumber = "02", deptName = "龍潭營運所", deptNumber = "0244", mapSeq = 2),
        RegionEntity(regionName = "第二區管理處", regionNumber = "02", deptName = "龜山服務所", deptNumber = "0222", mapSeq = 1),
        RegionEntity(regionName = "第五區管理處", regionNumber = "05", deptName = "五區物料課", deptNumber = "0510", mapSeq = 1),
        RegionEntity(regionName = "第五區管理處", regionNumber = "05", deptName = "五區物料課", deptNumber = "0510", mapSeq = 10),
        RegionEntity(regionName = "第五區管理處", regionNumber = "05", deptName = "五區物料課", deptNumber = "0510", mapSeq = 11),
        RegionEntity(regionName = "第五區管理處", regionNumber = "05", deptName = "五區物料課", deptNumber = "0510", mapSeq = 12),
        RegionEntity(regionName = "第五區管理處", regionNumber = "05", deptName = "五區物料課", deptNumber = "0510", mapSeq = 2),
        RegionEntity(regionName = "第五區管理處", regionNumber = "05", deptName = "五區物料課", deptNumber = "0510", mapSeq = 3),
        RegionEntity(regionName = "第五區管理處", regionNumber = "05", deptName = "五區物料課", deptNumber = "0510", mapSeq = 4),
        RegionEntity(regionName = "第五區管理處", regionNumber = "05", deptName = "五區物料課", deptNumber = "0510", mapSeq = 5),
        RegionEntity(regionName = "第五區管理處", regionNumber = "05", deptName = "五區物料課", deptNumber = "0510", mapSeq = 6),
        RegionEntity(regionName = "第五區管理處", regionNumber = "05", deptName = "五區物料課", deptNumber = "0510", mapSeq = 7),
        RegionEntity(regionName = "第五區管理處", regionNumber = "05", deptName = "五區物料課", deptNumber = "0510", mapSeq = 8),
        RegionEntity(regionName = "第五區管理處", regionNumber = "05", deptName = "五區物料課", deptNumber = "0510", mapSeq = 9),
        RegionEntity(regionName = "第五區管理處", regionNumber = "05", deptName = "五區虛擬倉庫", deptNumber = "0599", mapSeq = 1),
        RegionEntity(regionName = "第五區管理處", regionNumber = "05", deptName = "五區虛擬倉庫", deptNumber = "0599", mapSeq = 2),
        RegionEntity(regionName = "第五區管理處", regionNumber = "05", deptName = "五區虛擬倉庫", deptNumber = "0599", mapSeq = 3),
        RegionEntity(regionName = "第五區管理處", regionNumber = "05", deptName = "五區虛擬倉庫", deptNumber = "0599", mapSeq = 4),
        RegionEntity(regionName = "第五區管理處", regionNumber = "05", deptName = "嘉義給水廠", deptNumber = "0561", mapSeq = 1),
        RegionEntity(regionName = "第五區管理處", regionNumber = "05", deptName = "竹崎所阿里山淨水場", deptNumber = "056006", mapSeq = 1),
        RegionEntity(regionName = "第五區管理處", regionNumber = "05", deptName = "義竹服務所", deptNumber = "0523", mapSeq = 1),
        RegionEntity(regionName = "第五區管理處", regionNumber = "05", deptName = "雲林給水廠", deptNumber = "0564", mapSeq = 1),
        RegionEntity(regionName = "第八區管理處", regionNumber = "08", deptName = "八區決策部", deptNumber = "0800", mapSeq = 1),
        RegionEntity(regionName = "第八區管理處", regionNumber = "08", deptName = "八區決策部", deptNumber = "0800", mapSeq = 2),
        RegionEntity(regionName = "第八區管理處", regionNumber = "08", deptName = "八區決策部", deptNumber = "0800", mapSeq = 3),
        RegionEntity(regionName = "第六區管理處", regionNumber = "06", deptName = "佳里服務所", deptNumber = "0627", mapSeq = 1),
        RegionEntity(regionName = "第六區管理處", regionNumber = "06", deptName = "南化給水廠", deptNumber = "0664", mapSeq = 1),
        RegionEntity(regionName = "第六區管理處", regionNumber = "06", deptName = "南化給水廠", deptNumber = "0664", mapSeq = 2),
        RegionEntity(regionName = "第六區管理處", regionNumber = "06", deptName = "台南東區服務所(裁併)", deptNumber = "0621", mapSeq = 1),
        RegionEntity(regionName = "第六區管理處", regionNumber = "06", deptName = "台南東區服務所(裁併)", deptNumber = "0621", mapSeq = 2),
        RegionEntity(regionName = "第六區管理處", regionNumber = "06", deptName = "台南東區服務所(裁併)", deptNumber = "0621", mapSeq = 3),
        RegionEntity(regionName = "第六區管理處", regionNumber = "06", deptName = "台南東區服務所(裁併)", deptNumber = "0621", mapSeq = 4),
        RegionEntity(regionName = "第六區管理處", regionNumber = "06", deptName = "台南東區服務所(裁併)", deptNumber = "0621", mapSeq = 5),
        RegionEntity(regionName = "第六區管理處", regionNumber = "06", deptName = "台南東區服務所(裁併)", deptNumber = "0621", mapSeq = 6),
        RegionEntity(regionName = "第六區管理處", regionNumber = "06", deptName = "台南給水廠", deptNumber = "0661", mapSeq = 1),
        RegionEntity(regionName = "第六區管理處", regionNumber = "06", deptName = "新化服務所(裁併)", deptNumber = "0624", mapSeq = 1),
        RegionEntity(regionName = "第六區管理處", regionNumber = "06", deptName = "新化服務所(裁併)", deptNumber = "0624", mapSeq = 2),
        RegionEntity(regionName = "第六區管理處", regionNumber = "06", deptName = "烏山頭給水廠", deptNumber = "0663", mapSeq = 1),
        RegionEntity(regionName = "第六區管理處", regionNumber = "06", deptName = "白河營運所", deptNumber = "0643", mapSeq = 1),
        RegionEntity(regionName = "第十一區管理處", regionNumber = "0B", deptName = "二林營運所", deptNumber = "0B48", mapSeq = 1),
        RegionEntity(regionName = "第十一區管理處", regionNumber = "0B", deptName = "二林營運所", deptNumber = "0B48", mapSeq = 2),
        RegionEntity(regionName = "第十一區管理處", regionNumber = "0B", deptName = "北斗營運所", deptNumber = "0B43", mapSeq = 1),
        RegionEntity(regionName = "第十一區管理處", regionNumber = "0B", deptName = "北斗營運所", deptNumber = "0B43", mapSeq = 2),
        RegionEntity(regionName = "第十一區管理處", regionNumber = "0B", deptName = "十一區物料課", deptNumber = "0B10", mapSeq = 1),
        RegionEntity(regionName = "第十一區管理處", regionNumber = "0B", deptName = "十一區物料課", deptNumber = "0B10", mapSeq = 10),
        RegionEntity(regionName = "第十一區管理處", regionNumber = "0B", deptName = "十一區物料課", deptNumber = "0B10", mapSeq = 11),
        RegionEntity(regionName = "第十一區管理處", regionNumber = "0B", deptName = "十一區物料課", deptNumber = "0B10", mapSeq = 12),
        RegionEntity(regionName = "第十一區管理處", regionNumber = "0B", deptName = "十一區物料課", deptNumber = "0B10", mapSeq = 13),
        RegionEntity(regionName = "第十一區管理處", regionNumber = "0B", deptName = "十一區物料課", deptNumber = "0B10", mapSeq = 2),
        RegionEntity(regionName = "第十一區管理處", regionNumber = "0B", deptName = "十一區物料課", deptNumber = "0B10", mapSeq = 3),
        RegionEntity(regionName = "第十一區管理處", regionNumber = "0B", deptName = "十一區物料課", deptNumber = "0B10", mapSeq = 4),
        RegionEntity(regionName = "第十一區管理處", regionNumber = "0B", deptName = "十一區物料課", deptNumber = "0B10", mapSeq = 5),
        RegionEntity(regionName = "第十一區管理處", regionNumber = "0B", deptName = "十一區物料課", deptNumber = "0B10", mapSeq = 6),
        RegionEntity(regionName = "第十一區管理處", regionNumber = "0B", deptName = "十一區物料課", deptNumber = "0B10", mapSeq = 7),
        RegionEntity(regionName = "第十一區管理處", regionNumber = "0B", deptName = "十一區物料課", deptNumber = "0B10", mapSeq = 8),
        RegionEntity(regionName = "第十一區管理處", regionNumber = "0B", deptName = "十一區物料課", deptNumber = "0B10", mapSeq = 9),
        RegionEntity(regionName = "第十一區管理處", regionNumber = "0B", deptName = "員林營運所", deptNumber = "0B42", mapSeq = 1),
        RegionEntity(regionName = "第十一區管理處", regionNumber = "0B", deptName = "彰化給水廠", deptNumber = "0B61", mapSeq = 1),
        RegionEntity(regionName = "第十一區管理處", regionNumber = "0B", deptName = "彰化給水廠", deptNumber = "0B61", mapSeq = 2),
        RegionEntity(regionName = "第十一區管理處", regionNumber = "0B", deptName = "彰化給水廠", deptNumber = "0B61", mapSeq = 3),
        RegionEntity(regionName = "第十一區管理處", regionNumber = "0B", deptName = "田中營運所(裁併)", deptNumber = "0B45", mapSeq = 1),
        RegionEntity(regionName = "第十一區管理處", regionNumber = "0B", deptName = "田中營運所(裁併)", deptNumber = "0B45", mapSeq = 2),
        RegionEntity(regionName = "第十一區管理處", regionNumber = "0B", deptName = "花壇營運所", deptNumber = "0B52", mapSeq = 1),
        RegionEntity(regionName = "第十一區管理處", regionNumber = "0B", deptName = "花壇營運所", deptNumber = "0B52", mapSeq = 2),
        RegionEntity(regionName = "第十一區管理處", regionNumber = "0B", deptName = "鹿港營運所", deptNumber = "0B47", mapSeq = 1),
        RegionEntity(regionName = "第十二區管理處", regionNumber = "0C", deptName = "十二區決策部", deptNumber = "0C00", mapSeq = 1),
        RegionEntity(regionName = "第十二區管理處", regionNumber = "0C", deptName = "十二區決策部", deptNumber = "0C00", mapSeq = 2),
        RegionEntity(regionName = "第十二區管理處", regionNumber = "0C", deptName = "十二區決策部", deptNumber = "0C00", mapSeq = 3),
        RegionEntity(regionName = "第十區管理處", regionNumber = "0A", deptName = "十區物料課", deptNumber = "0A10", mapSeq = 1),
        RegionEntity(regionName = "第十區管理處", regionNumber = "0A", deptName = "十區物料課", deptNumber = "0A10", mapSeq = 2),
        RegionEntity(regionName = "第十區管理處", regionNumber = "0A", deptName = "十區物料課", deptNumber = "0A10", mapSeq = 3),
        RegionEntity(regionName = "第十區管理處", regionNumber = "0A", deptName = "十區物料課", deptNumber = "0A10", mapSeq = 4),
        RegionEntity(regionName = "第十區管理處", regionNumber = "0A", deptName = "卑南營運所(裁併)", deptNumber = "0A42", mapSeq = 1),
        RegionEntity(regionName = "第十區管理處", regionNumber = "0A", deptName = "卑南營運所(裁併)", deptNumber = "0A42", mapSeq = 2),
        RegionEntity(regionName = "第十區管理處", regionNumber = "0A", deptName = "卑南營運所(裁併)", deptNumber = "0A42", mapSeq = 3),
        RegionEntity(regionName = "第十區管理處", regionNumber = "0A", deptName = "太麻里淨水", deptNumber = "0A4404", mapSeq = 1),
        RegionEntity(regionName = "第十區管理處", regionNumber = "0A", deptName = "太麻里淨水", deptNumber = "0A4404", mapSeq = 2),
        RegionEntity(regionName = "第四區管理處", regionNumber = "04", deptName = "南投營運所", deptNumber = "0444", mapSeq = 1),
        RegionEntity(regionName = "第四區管理處", regionNumber = "04", deptName = "后里服務所(裁併)", deptNumber = "0450", mapSeq = 1),
        RegionEntity(regionName = "第四區管理處", regionNumber = "04", deptName = "后里服務所(裁併)", deptNumber = "0450", mapSeq = 2),
        RegionEntity(regionName = "第四區管理處", regionNumber = "04", deptName = "大甲營運所", deptNumber = "0443", mapSeq = 1),
        RegionEntity(regionName = "第四區管理處", regionNumber = "04", deptName = "大肚龍井營運所(裁併)", deptNumber = "0425", mapSeq = 1),
        RegionEntity(regionName = "第四區管理處", regionNumber = "04", deptName = "大肚龍井營運所(裁併)", deptNumber = "0425", mapSeq = 2),
        RegionEntity(regionName = "第四區管理處", regionNumber = "04", deptName = "大里服務所", deptNumber = "0426", mapSeq = 1),
        RegionEntity(regionName = "第四區管理處", regionNumber = "04", deptName = "大雅營運所", deptNumber = "0455", mapSeq = 1),
        RegionEntity(regionName = "第四區管理處", regionNumber = "04", deptName = "東勢營運所", deptNumber = "0448", mapSeq = 1),
        RegionEntity(regionName = "第四區管理處", regionNumber = "04", deptName = "水里營運所", deptNumber = "0447", mapSeq = 1),
        RegionEntity(regionName = "第四區管理處", regionNumber = "04", deptName = "沙鹿營運所", deptNumber = "0454", mapSeq = 1),
        RegionEntity(regionName = "第四區管理處", regionNumber = "04", deptName = "清水營運所", deptNumber = "0442", mapSeq = 1),
        RegionEntity(regionName = "第四區管理處", regionNumber = "04", deptName = "港尾淨水場", deptNumber = "046203", mapSeq = 1),
        RegionEntity(regionName = "第四區管理處", regionNumber = "04", deptName = "烏日營運所", deptNumber = "0452", mapSeq = 1),
        RegionEntity(regionName = "第四區管理處", regionNumber = "04", deptName = "竹山營運所", deptNumber = "0446", mapSeq = 1),
        RegionEntity(regionName = "第四區管理處", regionNumber = "04", deptName = "草屯營運所", deptNumber = "0445", mapSeq = 1),
        RegionEntity(regionName = "第四區管理處", regionNumber = "04", deptName = "豐原服務所", deptNumber = "0423", mapSeq = 1),
        RegionEntity(regionName = "第四區管理處", regionNumber = "04", deptName = "豐原服務所", deptNumber = "0423", mapSeq = 2),
        RegionEntity(regionName = "第四區管理處", regionNumber = "04", deptName = "霧峰營運所", deptNumber = "0451", mapSeq = 1),
        RegionEntity(regionName = "第四區管理處", regionNumber = "04", deptName = "鯉魚潭給水廠", deptNumber = "0463", mapSeq = 1)
    )
