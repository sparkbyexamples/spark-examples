package com.sparkbyexamples.spark.streaming.inprogress

import org.apache.spark.sql.SparkSession

object SparkStreamingToJDBC_ {

  def main(args: Array[String]): Unit = {

    val spark: SparkSession = SparkSession.builder()
      .master("local[3]")
      .appName("SparkByExample")
      .getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")

    val df = spark.readStream
      .format("socket")
      .option("host","localhost")
      .option("port","9090")
      .load()

    df.printSchema()

  }
}
