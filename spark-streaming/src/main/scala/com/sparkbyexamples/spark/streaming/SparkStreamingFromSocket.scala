package com.sparkbyexamples.spark.streaming

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{explode, split}

object SparkStreamingFromSocket {

  def main(args: Array[String]): Unit = {

    val spark:SparkSession = SparkSession.builder()
      .master("local[3]")
      .appName("SparkByExample")
      .getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")

    val df = spark.readStream
      .format("socket")
      .option("host","localhost")
      .option("port","9090")
      .load()

    val wordsDF = df.select(explode(split(df("value")," ")).alias("word"))

    val count = wordsDF.groupBy("word").count()

    val query = count.writeStream
      .format("console")
      .outputMode("complete")
      .start()
    query.awaitTermination()

  }
}
