package com.sparkbyexamples.spark.streaming.inprogress

import org.apache.spark.sql.SparkSession
object SparkStreamingKafkaUserObject {

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

    df.writeStream
      .format("kafka")
      .outputMode("append")
      .option("kafka.bootstrap.servers", "192.168.1.100:9092")
      .option("topic", "topic_text")
      .option("checkpointLocation", "c:/tmp/checkpoint")
      .start()
      .awaitTermination()
  }
}
