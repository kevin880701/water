package com.lhr.water.data

data class DeliveryData(
    val formClass: String,
    val formStatus: Int = 0,
    val formContent: FormContent
)
data class FormContent(
    val applyNo: String,
    val contact: String,
    val contactPhone: String,
    val contractNumber: String,
    val date: String,
    val deliveryDate: String,
    val deliveryLocation: String,
    val deliveryNumber: String,
    val extendDate1: String,
    val extendDate2: String,
    val extendDate3: String,
    val extendNo1: String,
    val extendNo2: String,
    val extendNo3: String,
    val formGoodInfo: List<FormGoodInfo>,
    val projectNumber: String,
    val receiptDept: String,
    val reportId: String,
    val reportTitle: String,
    val sumAddition: String,
    val underwriter: String
)
