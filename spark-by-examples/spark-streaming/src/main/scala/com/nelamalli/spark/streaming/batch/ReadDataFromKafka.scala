package com.sparkbyexamples.spark.streaming.batch

import org.apache.spark.sql.SparkSession

object ReadDataFromKafka {

  def main(args: Array[String]): Unit = {

    val spark: SparkSession = SparkSession.builder()
      .master("local[1]")
      .appName("https://SparkByExamples.com")
      .getOrCreate()

    val df = spark
      .read
      .format("kafka")
      .option("kafka.bootstrap.servers", "192.168.1.100:9092")
      .option("subscribe", "text_topic")
      .load()

    df.printSchema()

    val df2 = df.selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)","topic")
    df2.show(false)
  }
}
