package com.lhr.water.data

val fakerUserInfo = """
{
  "code": 0,
  "message": "成功",
  "data": {
    "userInfo": {
      "deptAno": "0D60",
      "userId": "123"
    }
  }
}
"""

val upData = """
    {
  "code": 200,
  "message": "Success",
  "data": {
    "list": {
      "delivery": [
        {
  "ano": "ANO123",
  "applyNo": "APPLY456",
  "contact": "John Doe",
  "contactPhone": "123-456-7890",
  "contractNumber": "CONTRACT789",
  "date": "2024-05-22",
  "dealStatus": "待處理",
  "deliveryDate": "2024-05-25",
  "deliverylocation": "Some Location",
  "deptName": "Some Department",
  "extendDate1": "",
  "extendDate2": "",
  "extendDate3": "",
  "extendNo1": "",
  "extendNo2": "",
  "extendNo3": "",
  "formNumber": "FORM001",
  "formId": 789,
  "projectNumber": "PROJECT123",
  "receiptDept": "Receiving Department",
  "reportId": "REPORT456",
  "isCreateRNumber": "0",
  "reportTitle": "交貨通知單",
  "sumAddition": "Some Addition",
  "underwriter": "Some Underwriter",
  "updatedAt": "2024-05-22",
  "itemDetail": [
    {
      "itemId": 1,
      "number": "1",
      "itemNo": "ITEM001",
      "batch": "BATCH001",
      "no": "NO001",
      "materialNumber": "M001",
      "materialName": "水管",
      "materialSpec": "5吋",
      "materialUnit": "根",
      "requestQuantity": "10",
      "approvedQuantity": "8",
      "price": "10.00",
      "amount": "80.00",
      "updatedAt": "2024-05-22",
      "deliveryStatus": "DELIVERED"
    },
    {
      "itemId": 2,
      "number": "2",
      "itemNo": "ITEM002",
      "batch": "BATCH002",
      "no": "NO002",
      "materialNumber": "M002",
      "materialName": "水管2",
      "materialSpec": "5吋",
      "materialUnit": "根",
      "requestQuantity": "15",
      "approvedQuantity": "12",
      "price": "12.00",
      "amount": "144.00",
      "updatedAt": "2024-05-22",
      "deliveryStatus": "PENDING"
    }
  ]
}
      ],
      "transfer": [
        {
  "contact": "John Doe",
  "contactPhone": "123-456-7890",
  "date": "2024-05-22",
  "dealStatus": "待處理",
  "formNumber": "FORM004",
  "formId": 789,
  "originalVoucherNumber": "VOUCHER789",
  "receivingApplyTransferDate": "2024-05-25",
  "receivingApplyTransferNumber": "APPLY789",
  "receivingDept": "Receiving Department",
  "receivingLocation": "Receiving Location",
  "receivingTransferDate": "2024-05-26",
  "receivingTransferNumber": "TRANSFER789",
  "reportId": 790,
  "reportTitle": "材料調撥單",
  "requiredDate": "2024-05-30",
  "isCreateRNumber": "0",
  "transferDescription": "Some transfer description",
  "transferringDept": "Transferring Department",
  "transferringDeptAno": "DEPT789",
  "transferringTransferDate": "2024-05-27",
  "transferringTransferNumber": "TRANSFER123",
  "transferStatus": "撥方已送出",
  "updatedAt": "2024-05-22",
  "itemDetail": [
    {
      "itemId": 5,
      "number": "1",
      "materialNumber": "M001",
      "materialName": "水管",
      "materialSpec": "5吋",
      "materialUnit": "根",
      "requestQuantity": "20",
      "approvedQuantity": "18",
      "approvalResult": "Some approval result",
      "applyNumber": "APPLY001",
      "updatedAt": "2024-05-22",
      "transferStatus": "單據提出"
    }
  ]
}
      ],
      "receive": [
        {
  "accountingSubject": "Some Accounting Subject",
  "ano": "ANO456",
  "caseNumber": "CASE789",
  "costAllocationUnit": "Cost Allocation Unit",
  "date": "2024-05-22",
  "dealStatus": "待處理",
  "formNumber": "FORM002",
  "formId": 791,
  "issuingUnit": "Issuing Unit",
  "originalVoucherNumber": "VOUCHER123",
  "pickingDate": "2024-05-25",
  "pickingDept": "Picking Department",
  "projectName": "Some Project",
  "isCreateRNumber": "0",
  "projectNumber": "PROJECT456",
  "reportId": "REPORT789",
  "reportTitle": "材料領料單",
  "systemCode": "SYSTEM001",
  "updatedAt": "2024-05-22",
  "usageCode": "USAGE001",
  "itemDetail": [
    {
      "number": "1",
      "itemId": 3,
      "materialNumber": "M001",
      "materialName": "水管",
      "materialSpec": "5吋",
      "materialUnit": "根",
      "requestQuantity": "20",
      "approvedQuantity": "18",
      "price": "15.00",
      "itemCost": "270.00",
      "updatedAt": "2024-05-22"
    }
  ]
}
      ],
      "return": [
        {
  "accountingSubject": "Some Accounting Subject",
  "ano": "ANO789",
  "costAllocationUnit": "Cost Allocation Unit",
  "date": "2024-05-22",
  "dealStatus": "待處理",
  "formNumber": "FORM003",
  "formId": 793,
  "leadDept": "Lead Department",
  "leadNumber": "LEAD001",
  "originalVoucherNumber": "VOUCHER456",
  "projectName": "Some Project",
  "isCreateRNumber": "0",
  "projectNumber": "PROJECT789",
  "receiptDept": "Receipt Department",
  "receivedDate": "2024-05-25",
  "reportId": "REPORT123",
  "reportTitle": "材料退料單",
  "returnDept": "Return Department",
  "systemCode": "SYSTEM002",
  "updatedAt": "2024-05-22",
  "usageCode": "USAGE002",
  "itemDetail": [
    {
      "itemId": 4,
      "number": "1",
      "materialNumber": "M001",
      "materialName": "水管",
      "materialSpec": "5吋",
      "materialUnit": "根",
      "requestQuantity": "30",
      "approvedQuantity": "25",
      "price": "20.00",
      "amount": "500.00",
      "updatedAt": "2024-05-22"
    }
  ]
}
      ],
      "inventory": [
        {
          "formId": 795,
          "formNumber": "M156325",
          "dealStatus": "待處理",
          "reportId": "S615612",
          "reportTitle": "材料盤點單",
          "date": "105/04/05",
          "dealTime": "113/03/09",
          "inventoryUnit": "單位代號",
          "deptName": "單位名稱",
          "seq": "次數",
          "materialNumber": "M001",
          "materialName": "水管",
          "materialSpec": "5吋",
          "materialUnit": "根",
          "defaultQuantity": 3,
          "actualQuantity": 3,
          "checkDate": "盤點日期",
          "lastUseDate": "最後使用日期",
          "approvedDate": "區處審核日期 ",
          "updatedAt": "2024-05-22-17-30-20"
        }
      ],
      "whseStrg": [
            {
        "storageId": 1,
        "storageWarehouseDeptAno": "0349",
        "storageWarehouseSeq": 1,
        "storageName": "儲櫃1",
        "storageX": 100,
        "storageY": 100
    },
    {
        "storageId": 2,
        "storageWarehouseDeptAno": "0D60",
        "storageWarehouseSeq": 2,
        "storageName": "儲櫃2",
        "storageX": 200,
        "storageY": 200
    },
    {
        "storageId": 3,
        "storageWarehouseDeptAno": "0D60",
        "storageWarehouseSeq": 1,
        "storageName": "儲櫃3",
        "storageX": 300,
        "storageY": 300
    },
    {
        "storageId": 4,
        "storageWarehouseDeptAno": "0D60",
        "storageWarehouseSeq": 1,
        "storageName": "儲櫃4",
        "storageX": 400,
        "storageY": 400
    }
      ],
      "invtStrg": [
        {
        "storageId": 1,
        "storageFromType": "1",
        "storageFromNo": "M0001",
        "storageMaterialName": "材料1",
        "storageMaterialNo": "1",
        "InvtStat": "2",
        "storageUserId": "U0001",
        "storageMaterialQuantity": "10",
        "createdAt": "2024-05-07-18-26-31",
        "storageArrivalId": "A001",
        "itemId": 0
    },
    {
        "storageId": 1,
        "storageFromType": "5",
        "storageFromNo": "M0002",
        "storageMaterialName": "材料1",
        "storageMaterialNo": "1",
        "InvtStat": "3",
        "storageUserId": "U0001",
        "storageMaterialQuantity": "7",
        "createdAt": "2024-05-07-18-26-31",
        "storageArrivalId": "A001",
        "itemId": 0
    },
    {
        "storageId": 1,
        "storageFromType": "1",
        "storageFromNo": "M0003",
        "storageMaterialName": "材料3",
        "storageMaterialNo": "3",
        "InvtStat": "1",
        "storageUserId": "U0001",
        "storageMaterialQuantity": "4",
        "createdAt": "2024-05-07-18-26-31",
        "storageArrivalId": "A001",
        "itemId": 0
    },
    {
        "storageId": 3,
        "storageFromType": "1",
        "storageFromNo": "M165651",
        "storageMaterialName": "水管",
        "storageMaterialNo": "M001",
        "InvtStat": "2",
        "storageUserId": "U0001",
        "storageMaterialQuantity": "20",
        "createdAt": "2024-05-07-18-26-31",
        "storageArrivalId": "A001",
        "itemId": 0
    },
    {
        "storageId": 4,
        "storageFromType": "1",
        "storageFromNo": "M165651",
        "storageMaterialName": "水管2",
        "storageMaterialNo": "M002",
        "InvtStat": "2",
        "storageUserId": "U0001",
        "storageMaterialQuantity": "20",
        "createdAt": "2024-05-07-18-26-31",
        "storageArrivalId": "A001",
        "itemId": 0
    },
    {
        "storageId": 3,
        "storageFromType": "1",
        "storageFromNo": "M165651",
        "storageMaterialName": "水管",
        "storageMaterialNo": "M001",
        "InvtStat": "2",
        "storageUserId": "U0001",
        "storageMaterialQuantity": "2",
        "createdAt": "2024-05-07-18-26-31",
        "storageArrivalId": "A001",
        "itemId": 0
    }
      ],
      "checkoutForm": [
            {
        "storageInventoryId": 1,
        "storageMaterialName": "材料1",
        "storageMaterialNo": "1",
        "storageMaterialQuantity": 10,
        "storageArrivalTime": "2024-05-07-18-26-31",
        "checkoutTime": "2024-05-01-00-00-00"
    },
    {
        "storageInventoryId": 1,
        "storageMaterialName": "材料2",
        "storageMaterialNo": "2",
        "storageMaterialQuantity": 10,
        "storageArrivalTime": "2024-05-07-18-26-31",
        "checkoutTime": "2024-05-01-00-00-00"
    },
    {
        "storageInventoryId": 1,
        "storageMaterialName": "材料3",
        "storageMaterialNo": "3",
        "storageMaterialQuantity": 10,
        "storageArrivalTime": "2024-05-07-18-26-31",
        "checkoutTime": "2024-05-01-00-00-00"
    },
    {
        "storageInventoryId": 1,
        "storageMaterialName": "材料4",
        "storageMaterialNo": "4",
        "storageMaterialQuantity": 10,
        "storageArrivalTime": "2024-05-07-18-26-31",
        "checkoutTime": "2024-04-01-00-00-00"
    }
      ]
    }
  }
}
"""