package com.sparkbyexamples.spark.streaming

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{explode, split}

object SparkStreamingKafkaConsumer {

  def main(args: Array[String]): Unit = {

    val spark:SparkSession = SparkSession.builder()
      .master("local[3]")
      .appName("SparkByExample")
      .getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")

    val df = spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "192.168.1.100:9092")
      .option("subscribe", "topic_text6")
      //.option("subscribePattern", "topic.*")
      .option("startingOffsets", "earliest") // Other possible values assign and latest
      .load()

    df.printSchema()

    val groupCount = df.select(explode(split(df("value")," ")).alias("word"))
      .groupBy("word").count()

    groupCount.writeStream
      .format("console")
      .outputMode("complete")
      .start()
      .awaitTermination()

  }
}
