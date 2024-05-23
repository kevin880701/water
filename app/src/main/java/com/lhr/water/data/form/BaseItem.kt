package com.lhr.water.data.form

open class BaseItem(
){

    @Transient
    open val itemId: Int = 0

    @Transient
    open val number: String = ""

    @Transient
    open val materialUnit: String = ""

    @Transient
    open val materialNumber: String = ""

    @Transient
    open val materialName: String = ""

    @Transient
    open val materialSpec: String = ""

    @Transient
    open val requestQuantity: String = ""

    @Transient
    open var approvedQuantity: String = ""

    @Transient
    open val updatedAt: String = ""

}