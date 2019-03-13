package com.sparkbyexamples.spark.streaming.kafka.avro

import org.apache.spark.sql.SparkSession

object KafkaConsumerAvro {
  def main(args:Array[String]): Unit = {

    val spark: SparkSession = SparkSession.builder()
      .master("local[1]")
      .appName("SparkByExamples.com")
      .getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")

    val df = spark
      .readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "192.168.1.100:9092")
      .option("subscribe", "text_topic6")
      .load()

    df.printSchema()

    df.selectExpr("CAST(value AS STRING)" ).writeStream
      .format("console")
      .outputMode("append")
      .option("truncate",false)
      //.option("newRows",30)
      .start()
      .awaitTermination()
  }
}
