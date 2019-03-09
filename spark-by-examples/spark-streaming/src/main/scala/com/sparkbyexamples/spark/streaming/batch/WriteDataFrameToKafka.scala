package com.sparkbyexamples.spark.streaming.batch
import org.apache.spark.sql.SparkSession
object WriteDataFrameToKafka {

  def main(args: Array[String]): Unit = {

    val spark: SparkSession = SparkSession.builder()
      .master("local[1]")
      .appName("SparkByExample")
      .getOrCreate()

    val data = Seq (("iphone", "2007"),("iphone 3G","2008"),
      ("iphone 3GS","2009"),
      ("iphone 4","2010"),
      ("iphone 4S","2011"),
      ("iphone 5","2012"))

    val df = spark.createDataFrame(data).toDF("key","value")
    // since we are reading from a file which is already in text, selectExpr is optional.
    // If the bytes of the Kafka records represent UTF8 strings,
    // we can simply use a cast to convert the binary data into the correct type.
    df//.selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)")
      .write
      .format("kafka")
      .option("kafka.bootstrap.servers","192.168.1.100:9092")
      .option("topic","text_topic")
      .save()
  }
}
