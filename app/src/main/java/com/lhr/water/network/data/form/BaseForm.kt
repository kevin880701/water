package com.lhr.water.network.data.form

import com.google.gson.annotations.SerializedName

open class BaseForm(
){

    @Transient
    open val formNumber: String = ""

    @Transient
    open val dealStatus: String = ""

    @Transient
    open val reportId: String = ""

    @Transient
    open val reportTitle: String = ""

    @Transient
    open val dealTime: String = ""

    @Transient
    open val date: String = ""
}