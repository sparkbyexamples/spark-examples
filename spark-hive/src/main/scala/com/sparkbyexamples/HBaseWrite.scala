//package com.sparkbyexamples
//
//import org.apache.spark.sql.SparkSession
//
//object HBaseWrite {
//
//  def main(args: Array[String]): Unit = {
//
//    val spark:SparkSession = SparkSession.builder()
//      .master("local[3]")
//      .appName("SparkByExample")
//      .getOrCreate()
//
//    //Chaining multiple options
//    val df = spark.read.
//      options(Map("inferSchema"->"true","sep"->",","header"->"true"))
//      .csv("src/main/resources/zipcodes.csv")
//    df.show(false)
//    df.printSchema()
//
//    def catalog = s"""{
//                     |"table":{"namespace":"default", "name":"Zipcode"},
//                     |"rowkey":"key",
//                     |"columns":{
//                     |"RecordNumber":{"cf":"rowkey", "col":"RecordNumber", "type":"string"},
//                     |"Zipcode":{"cf":"ZipcodeCF", "col":"Zipcode", "type":"string"},
//                     |"ZipCodeType":{"cf":"ZipcodeCF", "col":"ZipCodeType", "type":"string"},
//                     |"City":{"cf":"ZipcodeCF", "col":"City", "type":"string"},
//                     |"State":{"cf":"ZipcodeCF", "col":"State", "type":"string"},
//                     |"LocationType":{"cf":"ZipcodeCF", "col":"LocationType", "type":"string"},
//                     |"Lat":{"cf":"ZipcodeCF", "col":"Lat", "type":"string"},
//                     |"Long":{"cf":"ZipcodeCF", "col":"Long", "type":"string"},
//                     |"Xaxis":{"cf":"ZipcodeCF", "col":"Xaxis", "type":"string"},
//                     |"Yaxis":{"cf":"ZipcodeCF", "col":"Yaxis", "type":"string"},
//                     |"Zaxis":{"cf":"ZipcodeCF", "col":"Zaxis", "type":"string"},
//                     |"WorldRegion":{"cf":"ZipcodeCF", "col":"WorldRegion", "type":"string"},
//                     |"Country":{"cf":"ZipcodeCF", "col":"Country", "type":"string"},
//                     |"LocationText":{"cf":"ZipcodeCF", "col":"LocationText", "type":"string"}
//                     |}
//                     |}""".stripMargin
//
//    df.write
//      .option(HBaseTableCatalog.tableCatalog, catalog)
//      .option(HBaseTableCatalog.newTable, "5")
//      .format("org.apache.spark.sql.execution.datasources.hbase")
//      .save()
//
//  }
//}
