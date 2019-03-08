package com.sparkbyexamples.spark.dataframe.avro

import org.apache.spark.sql.types.{IntegerType, StringType, StructType}
import org.apache.spark.sql.{Row, SparkSession}

object AvroExample {

  def main(args: Array[String]): Unit = {


    val spark: SparkSession = SparkSession.builder()
      .master("local[1]")
      .appName("SparkByExamples.com")
      .getOrCreate()

    val data = Seq(Row(Row("James ","","Smith"),"36636","M",3000),
      Row(Row("Michael ","Rose",""),"40288","M",4000),
      Row(Row("Robert ","","Williams"),"42114","M",4000),
      Row(Row("Maria ","Anne","Jones"),"39192","F",4000),
      Row(Row("Jen","Mary","Brown"),"","F",-1)
    )

    val schema = new StructType()
      .add("name",new StructType()
        .add("firstname",StringType)
        .add("middlename",StringType)
        .add("lastname",StringType))
      .add("dob",StringType)
      .add("gender",StringType)
      .add("salary",IntegerType)

    val df = spark.createDataFrame(spark.sparkContext.parallelize(data),schema)
    //val readFromHf = spark.read.format("com.databricks.spark.avro")
    // .load("C:\\Users\\a03078a\\DataFabric\\Workspace\\bureau-australia-app\\acceptance\\accepted\\TEL_11102018_0011111_MVP_TEST.csv.6508434824099390398\\part-00000-b34d116a-87ae-4c76-b0c5-ebdeb8147116-c000.avro")
    //readFromHf.printSchema()

    df.write.format("avro").save("C:/tmp/spark_out/avro/namesAndFavColors.avro")
    // df.write.format("org.apache.spark.sql.avro").save("C:/tmp/spark_out/avro/namesAndFavColors.avro")


  }
}
