package com.lhr.water.data.form

open class BaseItem(
){

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
}