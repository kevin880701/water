package com.lhr.water.ui.form

import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.lhr.water.data.Form
import com.lhr.water.data.ItemDetail
import com.lhr.water.repository.FormRepository
import com.lhr.water.repository.RegionRepository
import com.lhr.water.repository.UserRepository
import com.lhr.water.room.RegionEntity
import com.lhr.water.room.CheckoutEntity
import com.lhr.water.room.FormEntity
import com.lhr.water.room.StorageEntity
import com.lhr.water.room.StorageRecordEntity
import com.lhr.water.ui.base.APP
import com.lhr.water.util.MapDataList
import com.lhr.water.util.formTypeMap
import com.lhr.water.util.fromTitleList
import com.lhr.water.util.getCurrentDate
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class FormViewModel(
    context: Context,
    var regionRepository: RegionRepository,
    var formRepository: FormRepository,
) : AndroidViewModel(context.applicationContext as APP) {

    // 篩選表單代號formNumber的String
    var searchFormNumber = MutableLiveData<String>("")
    // 篩選表單類別FormClass的List
    var filterList = MutableLiveData<ArrayList<String>>(ArrayList(fromTitleList))


    /**
     * 篩選表單內容
     */
    fun filterRecord(formList: ArrayList<FormEntity>, searchFormNumber: String, filterList: ArrayList<String>): ArrayList<FormEntity>? {
        return formList.filter { form ->
            // 根據 "FormClass" 判斷是否在 filterList 中
            val reportTitle = form.reportTitle.toString()
            val reportTitleFilterCondition = filterList.contains(reportTitle)
            val formNumber = form.formNumber.toString()

            // 如果搜尋框(EditText)中的文本不為空，則判斷 "formNumber" 是否包含該文本
            val editTextFilterCondition = if (searchFormNumber.isNotEmpty()) {
                formNumber.contains(searchFormNumber, ignoreCase = true)
            } else {
                true // 搜尋框(EditText)，不添加 formNumber 的篩選條件
            }
            reportTitleFilterCondition!! && editTextFilterCondition
        }?.toMutableList()!! as ArrayList<FormEntity>
    }
}