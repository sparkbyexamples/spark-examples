package com.sparkbyexamples.spark.dataframe.xml

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{IntegerType, StringType, StructType}

object PersonsXML {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local[1]")
      .appName("SparkByExample")
      .getOrCreate()

    /*
    Read XML File
     */
    val df = spark.read
      .format("xml")
      .option("rowTag", "person")
      .load("src/main/resources/persons.xml")

    df.printSchema()

    df.show()
//    val schema = new StructType()
//      .add("_id",IntegerType)
//      .add("firstname",StringType)
//      .add("middlename",StringType)
//      .add("lastname",StringType)
//      .add("dob_year",IntegerType)
//      .add("dob_month",IntegerType)
//      .add("gender",StringType)
//      .add("salary",IntegerType)
//
//    val df2 = spark.read
//      .format("xml")
//      .option("rowTag", "person")
//      .schema(schema)
//      .load("src/main/resources/persons.xml")

//    df.foreach(row=>{
//      println("ID:"+row.getAs("_id") )
//      println("ID:"+row(0))
//      println("ID:"+row.get(0))
//     // println("ID:"+row.getString(0))
//    })
//
//    df.write
//      .option("rootTag", "persons")
//      .option("rowTag", "person")
//      //.xml("src/main/resources/persons_new.xml")

  }
}


