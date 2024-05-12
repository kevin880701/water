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
    "formNumber": "M165651",
    "dealStatus": "待處理",
    "reportId": "M165615",
    "id": 2662,
    "reportTitle": "交貨通知單",
    "date": "102/02/03",
    "dealTime": "113/02/29",
    "ano": "435",
    "underwriter": "GRAVE",
    "deliveryDay": "交貨期限",
    "contractNumber": "165165",
    "deliveryDate": "113/03/25",
    "extendDate1": "",
    "extendDate2": "",
    "extendDate3": "",
    "receiptDept": "第一區",
    "deliverylocation": "文山所",
    "deliveryStatus": "",
    "extendNo1": "",
    "extendNo2": "",
    "extendNo3": "",
    "projectNumber": "169156",
    "contact": "王曉明",
    "contactPhone": "0956321543",
    "applyNo": "A16541569",
    "itemDetail": [
      {
        "number": "01",
        "itemNo": "01",
        "batch": "01",
        "no": "01",
        "materialNumber": "M001",
        "materialName": "水管",
        "materialSpec": "5吋",
        "materialUnit": "根",
        "deliveryQuantity": 20,
        "receivedQuantity": 10,
        "price": "505",
        "itemCost": "505"
      },
      {
        "number": "02",
        "itemNo": "02",
        "batch": "02",
        "no": "02",
        "materialNumber": "M002",
        "materialName": "水管2",
        "materialSpec": "5吋",
        "materialUnit": "根",
        "deliveryQuantity": 20,
        "receivedQuantity": 10,
        "price": "505",
        "itemCost": "505"
      }
    ],
    "sumAddition": "1000"
  }
      ],
      "transfer": [
        {
          "formNumber": "M156618",
          "reportId": "S615615",
          "reportTitle": "材料調撥單",
          "dealStatus": "待處理",
          "date": "105/04/01",
          "id": 1233,
          "dealTime": "113/03/07",
          "transferringDept": "撥方單位",
          "receivingDept": "第一區",
          "receivingLocation": "文山所",
          "receivingTransferDate": "112/07/02",
          "receivingApplyTransferDate": "收方撥入日期",
          "receivingApplyTransferNumber": "收方請撥單號",
          "receivingTransferNumber": "收方調撥單號",
          "requiredDate": "需用日期",
          "transferringTransferNumber": "撥方調撥單號",
          "originalVoucherNumber": "原憑證字號",
          "transferringTransferDate": "撥方調撥日期",
          "contact": "聯絡人",
          "contactPhone": "電話",
          "transferDescription": "調撥或用途說明",
          "itemDetail": [
            {
              "number": "01",
              "materialNumber": "M001",
              "materialName": "水管",
              "materialSpec": "5吋",
              "materialUnit": "根",
              "actualQuantity": 20,
              "applyNumber": 20,
              "allocatedQuantity": 20,
              "receivedQuantity": 20
            },
            {
              "number": "02",
              "materialNumber": "M002",
              "materialName": "水管2",
              "materialSpec": "5吋",
              "materialUnit": "根",
              "actualQuantity": 20,
              "applyNumber": 20,
              "allocatedQuantity": 20,
              "receivedQuantity": 20
            }
          ]
        }
      ],
      "receive": [
        {
          "formNumber": "M1566132",
          "dealStatus": "待處理",
          "reportId": "M165617",
          "id": 615,
          "reportTitle": "材料領料單",
          "date": "104/03/06",
          "dealTime": "113/03/06",
          "issuingUnit": "發料單位",
          "pickingDate": "領料日期",
          "pickingDept": "領料單位",
          "originalVoucherNumber": "原憑証字號",
          "costAllocationUnit": "成本分攤單位",
          "accountingSubject": "會計科目",
          "systemCode": "系統代號",
          "usageCode": "用途代號",
          "projectNumber": "工程編號",
          "projectName": "工程名稱",
          "caseNumber": "案號",
          "itemDetail": [
            {
              "number": "01",
              "materialNumber": "M001",
              "materialName": "水管",
              "materialSpec": "5吋",
              "materialUnit": "根",
              "requestedQuantity": 10,
              "actualQuantity": 10,
              "price": "505",
              "itemCost": null
            },
            {
              "number": "02",
              "materialNumber": "M002",
              "materialName": "水管2",
              "materialSpec": "5吋",
              "materialUnit": "根",
              "requestedQuantity": 7,
              "actualQuantity": 50,
              "price": "505",
              "itemCost": null
            }
          ]
        }
      ],
      "return": [
        {
          "formNumber": "M1566742",
          "dealStatus": "待處理",
          "reportId": "M165616",
          "id": 195,
          "reportTitle": "材料退料單",
          "date": "104/03/05",
          "dealTime": "113/03/05",
          "receiptDept": "收料單位",
          "leadNumber": "領調單號",
          "leadDept": "領調單位",
          "receivedDate": "實收日期",
          "accountingSubject": "會計科目",
          "costAllocationUnit": "成本分攤單位",
          "returnDept": "退料單位",
          "systemCode": "系統代號",
          "usageCode": "用途代號",
          "projectNumber": "工程編號",
          "projectName": "工程名稱",
          "originalVoucherNumber": "原憑證字號",
          "itemDetail": [
            {
              "number": "01",
              "materialNumber": "M001",
              "materialName": "水管",
              "materialSpec": "5吋",
              "materialUnit": "根",
              "returnedQuantity": 10,
              "receivedQuantity": 10,
              "stockingPrice": "505",
              "returnAmt": "505"
            },
            {
              "number": "02",
              "materialNumber": "M002",
              "materialName": "水管2",
              "materialSpec": "5吋",
              "materialUnit": "根",
              "returnedQuantity": 5,
              "receivedQuantity": 5,
              "stockingPrice": "505",
              "returnAmt": "505"
            }
          ]
        }
      ],
      "inventory": [
        {
          "id": 1233,
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
          "actualQuantity": 0,
          "checkDate": "盤點日期",
          "lastUseDate": "最後使用日期",
          "approvedDate": "區處審核日期 "
        }
      ],
      "whseStrg": [
            {
        "storageId": 1,
        "storageWarehouseDeptAno": "0D60",
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
        "storageFromType": 1,
        "storageFromNo": "M0001",
        "storageMaterialName": "材料1",
        "storageMaterialNo": "1",
        "storageArrivalTime": "2024-05-07-18-26-31",
        "storageDepartureTime": "2024-05-07-18-26-31",
        "InvtStat": 2,
        "storageUserId": "U0001",
        "storageMaterialQuantity": 10,
        "createdAt": "2024-05-07-18-26-31"
    },
    {
        "storageId": 1,
        "storageFromType": 5,
        "storageFromNo": "M0002",
        "storageMaterialName": "材料1",
        "storageMaterialNo": "1",
        "storageArrivalTime": "2024-05-07-18-26-31",
        "storageDepartureTime": "2024-05-07-18-26-31",
        "InvtStat": 3,
        "storageUserId": "U0001",
        "storageMaterialQuantity": 7,
        "createdAt": "2024-05-07-18-26-31"
    },
    {
        "storageId": 1,
        "storageFromType": 1,
        "storageFromNo": "M0003",
        "storageMaterialName": "材料3",
        "storageMaterialNo": "3",
        "storageArrivalTime": "2024-05-07-18-26-31",
        "storageDepartureTime": "2024-05-07-18-26-31",
        "InvtStat": 1,
        "storageUserId": "U0001",
        "storageMaterialQuantity": 4,
        "createdAt": "2024-05-07-18-26-31"
    },
    {
        "storageId": 3,
        "storageFromType": 1,
        "storageFromNo": "M165651",
        "storageMaterialName": "水管",
        "storageMaterialNo": "M001",
        "storageArrivalTime": "2024-05-07-18-26-31",
        "storageDepartureTime": "2024-05-07-18-26-31",
        "InvtStat": 2,
        "storageUserId": "U0001",
        "storageMaterialQuantity": 20,
        "createdAt": "2024-05-07-18-26-31"
    },
    {
        "storageId": 4,
        "storageFromType": 1,
        "storageFromNo": "M165651",
        "storageMaterialName": "水管2",
        "storageMaterialNo": "M002",
        "storageArrivalTime": "2024-05-07-18-26-31",
        "storageDepartureTime": "2024-05-07-18-26-31",
        "InvtStat": 2,
        "storageUserId": "U0001",
        "storageMaterialQuantity": 20,
        "createdAt": "2024-05-07-18-26-31"
    },
    {
        "storageId": 3,
        "storageFromType": 1,
        "storageFromNo": "M165651",
        "storageMaterialName": "水管",
        "storageMaterialNo": "M001",
        "storageArrivalTime": "2024-05-06-18-26-31",
        "storageDepartureTime": "2024-05-06-18-26-31",
        "InvtStat": 2,
        "storageUserId": "U0001",
        "storageMaterialQuantity": 2,
        "createdAt": "2024-05-07-18-26-31"
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
}"""