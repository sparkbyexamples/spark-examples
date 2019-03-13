package com.sparkbyexamples.spark.kafka.json

import org.apache.spark.sql.SparkSession

object KafkaConsumerJson {
  def main(args:Array[String]): Unit = {


    val spark: SparkSession = SparkSession.builder().master("local[1]")
      .appName("SparkByExamples.com")
      .getOrCreate()

    val df = spark
      .readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "192.168.1.100:9092")
      .option("subscribe", "topic1")
      .load()

    df.printSchema()

    df.show()
  }
}
