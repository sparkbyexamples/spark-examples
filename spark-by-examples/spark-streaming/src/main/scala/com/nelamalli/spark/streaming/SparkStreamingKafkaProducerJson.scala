package com.sparkbyexamples.spark.streaming

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{col, from_json}
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}

object SparkStreamingKafkaProducerJson {

  def main(args: Array[String]): Unit = {

    val spark: SparkSession = SparkSession.builder()
      .master("local[3]")
      .appName("SparkByExample")
      .getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")

    val df = spark.readStream
      .option("header", "true")
      .option("maxFilesPerTrigger", 3)
      .text("c:/tmp/stream_folder")

    df.printSchema()

    // Convert Json string to Object
    val schema = StructType(
      List(
        StructField("RecordNumber", IntegerType, true),
        StructField("Zipcode", StringType, true),
        StructField("ZipCodeType", StringType, true),
        StructField("City", StringType, true),
        StructField("State", StringType, true),
        StructField("LocationType", StringType, true),
        StructField("Lat", StringType, true),
        StructField("Long", StringType, true),
        StructField("Xaxis", StringType, true),
        StructField("Yaxis", StringType, true),
        StructField("Zaxis", StringType, true),
        StructField("WorldRegion", StringType, true),
        StructField("Country", StringType, true),
        StructField("LocationText", StringType, true),
        StructField("Location", StringType, true),
        StructField("Decommisioned", StringType, true)
      )
    )

    val zipcodeDF = df.select(
      from_json(col("value").cast("string"), schema).alias("zipcode")).select("zipcode.*")

    zipcodeDF.printSchema()

    zipcodeDF.selectExpr("CAST(RecordNumber AS STRING) AS key", "to_json(struct(*)) AS value")
      .writeStream
      .format("kafka")
      .outputMode("append")
      .option("kafka.bootstrap.servers", "192.168.1.100:9092")
      .option("topic", "topic_text")
      .option("checkpointLocation", "c:/tmp/checkpoint")
      .start()
      .awaitTermination()


  }
}
