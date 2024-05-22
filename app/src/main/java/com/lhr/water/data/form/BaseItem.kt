package com.lhr.water.data.form

open class BaseItem(
){

    @Transient
    open val id: String = ""

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

    open fun getRequestQuantity(): Int {
        return 0
    }

    open fun getApprovedQuantity(): Int {
        return 0
    }

    open fun setApprovedQuantity(quantity: String){
    }
}