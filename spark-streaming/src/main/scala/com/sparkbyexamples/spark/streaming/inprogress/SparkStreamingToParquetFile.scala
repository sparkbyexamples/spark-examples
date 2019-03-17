package com.sparkbyexamples.spark.streaming.inprogress

import org.apache.spark.sql.SparkSession

object SparkStreamingToParquetFile_ {

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

    //Parquet doesn't support complete mode hence,
    //we can't write aggregated output
    df.writeStream
      .format("parquet")
      .outputMode("append")
      .option("path","c:/tmp/spark_out/parquet")
      .option("checkpointLocation", "c:/tmp/checkpoint")
      .start()
      .awaitTermination()


  }
}
