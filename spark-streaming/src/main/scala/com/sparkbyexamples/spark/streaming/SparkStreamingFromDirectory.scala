package com.sparkbyexamples.spark.streaming

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{col, get_json_object}

object SparkStreamingFromDirectory {

  def main(args: Array[String]): Unit = {

    val spark:SparkSession = SparkSession.builder()
      .master("local[3]")
      .appName("SparkByExample")
      .getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")

    val df = spark.readStream
      .option("header","true")
      .option("maxFilesPerTrigger",3)
      .text("c:/tmp/stream_folder")

    df.printSchema()

    val groupDF = df.select(
      get_json_object(col("value").cast("string"),"$.Zipcode")
        .alias("Zipcode")).groupBy("Zipcode").count()

    groupDF.printSchema()

    groupDF.writeStream
      .format("console")
      .outputMode("complete")
      .option("truncate",false)
      .option("newRows",30)
      .start()
      .awaitTermination()


  }
}
