package com.sparkbyexamples.spark.streaming.kafka.json

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{col, from_json}
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}

object SparkStreamingProduceKafkaJson {

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
