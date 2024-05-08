package com.lhr.water.data

import com.lhr.water.room.CheckoutEntity
import com.lhr.water.room.StorageEntity
import com.lhr.water.room.StorageRecordEntity


var fakerStorageEntities: ArrayList<StorageEntity> = arrayListOf(
    StorageEntity(
        storageId = 1,
        deptNumber = "0D60",
        mapSeq = 1,
        storageName = "儲櫃1",
        storageX = 100,
        storageY = 100,
    ),
    StorageEntity(
        storageId = 2,
        deptNumber = "0D60",
        mapSeq = 2,
        storageName = "儲櫃2",
        storageX = 200,
        storageY = 200,
    ),
    StorageEntity(
        storageId = 3,
        deptNumber = "0D60",
        mapSeq = 1,
        storageName = "儲櫃3",
        storageX = 300,
        storageY = 300,
    ),
    StorageEntity(
        storageId = 4,
        deptNumber = "0D60",
        mapSeq = 1,
        storageName = "儲櫃4",
        storageX = 400,
        storageY = 400,
    )
)

var fakerCheckoutEntities: ArrayList<CheckoutEntity> = arrayListOf(
    CheckoutEntity(
        storageId = 1,
        materialName = "材料1",
        materialNumber = "1",
        quantity = 10,
        inputTime = "2024-05-07-18-26-31",
        checkoutTime = "2024-05-01-00-00-00",
    ),
    CheckoutEntity(
        storageId = 1,
        materialName = "材料2",
        materialNumber = "2",
        quantity = 10,
        inputTime = "2024-05-07-18-26-31",
        checkoutTime = "2024-05-01-00-00-00",
    ),
    CheckoutEntity(
        storageId = 1,
        materialName = "材料3",
        materialNumber = "3",
        quantity = 10,
        inputTime = "2024-05-07-18-26-31",
        checkoutTime = "2024-05-01-00-00-00",
    ),
    CheckoutEntity(
        storageId = 1,
        materialName = "材料4",
        materialNumber = "4",
        quantity = 10,
        inputTime = "2024-05-07-18-26-31",
        checkoutTime = "2024-04-01-00-00-00",
    )
)


var fakerStorageRecordEntities: ArrayList<StorageRecordEntity> = arrayListOf(
    StorageRecordEntity(
        storageId = 1,
        formType = 1,
        formNumber = "M0001",
        materialName = "材料1",
        materialNumber = "1",
        InvtStat = 2,
        userId = "U0001",
        quantity = 10,
        date = "2024-05-07-18-26-31",
    ),
    StorageRecordEntity(
        storageId = 1,
        formType = 5,
        formNumber = "M0002",
        materialName = "材料1",
        materialNumber = "1",
        InvtStat = 3,
        userId = "U0001",
        quantity = 7,
        date = "2024-05-07-18-26-31",
    ),
    StorageRecordEntity(
        storageId = 1,
        formType = 1,
        formNumber = "M0003",
        materialName = "材料3",
        materialNumber = "3",
        InvtStat = 1,
        userId = "U0001",
        quantity = 4,
        date = "2024-05-07-18-26-31",
    ),
)