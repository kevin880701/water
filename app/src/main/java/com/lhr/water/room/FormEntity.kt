package com.lhr.water.room

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.lhr.water.data.form.BaseForm
import com.lhr.water.data.form.DeliveryForm
import com.lhr.water.data.form.ReceiveForm
import com.lhr.water.data.form.ReturnForm
import com.lhr.water.data.form.TransferForm
import java.io.Serializable


@Entity(
    tableName = SqlModel.FORM_TABLE_NAME,
    indices = [Index(value = [SqlModel.formNumber], unique = true)]
)
class FormEntity(
    formNumber: String,
    dealStatus: String,
    reportId: String,
    reportTitle: String,
    date: String,
    formContent: String,
    isCreateRNumber: String,
    isUpdate: Boolean = true,
) : Serializable {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = SqlModel.formNumber, typeAffinity = ColumnInfo.TEXT)
    var formNumber = formNumber

    @ColumnInfo(name = SqlModel.dealStatus, typeAffinity = ColumnInfo.TEXT)
    var dealStatus = dealStatus

    @ColumnInfo(name = SqlModel.reportId, typeAffinity = ColumnInfo.TEXT)
    var reportId = reportId

    @ColumnInfo(name = SqlModel.reportTitle, typeAffinity = ColumnInfo.TEXT)
    var reportTitle = reportTitle

    @ColumnInfo(name = SqlModel.date, typeAffinity = ColumnInfo.TEXT)
    var date = date

    @ColumnInfo(name = SqlModel.formContent, typeAffinity = ColumnInfo.TEXT)
    var formContent = formContent

    @ColumnInfo(name = "isUpdate", typeAffinity = ColumnInfo.INTEGER)
    var isUpdate = isUpdate

    @ColumnInfo(name = "isCreateRNumber", typeAffinity = ColumnInfo.TEXT)
    var isCreateRNumber = isCreateRNumber

    fun parseBaseForm(): BaseForm {
        return when (reportTitle) {
            "交貨通知單" -> Gson().fromJson(formContent, DeliveryForm::class.java)
            "材料領料單" -> Gson().fromJson(formContent, ReceiveForm::class.java)
            "材料調撥單" -> Gson().fromJson(formContent, TransferForm::class.java)
            "材料退料單" -> Gson().fromJson(formContent, ReturnForm::class.java)
            else -> Gson().fromJson(formContent, DeliveryForm::class.java) //要改成null
        }
    }

    companion object {
        fun convertFormToFormEntities(formList: List<BaseForm>): List<FormEntity> {
            val gson = Gson()
            val formEntities = ArrayList<FormEntity>()
            for (form in formList) {
                val formContentJson = gson.toJson(form)
                val formEntity = FormEntity(
                    form.formNumber,
                    form.dealStatus,
                    form.reportId,
                    form.reportTitle,
                    form.date,
                    formContentJson,
                    form.isCreateRNumber
                )
                formEntities.add(formEntity)
            }
            return formEntities
        }
    }
}