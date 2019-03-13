package com.sparkbyexamples.spark.streaming.batch

import java.io.File

import org.apache.avro.Schema
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.col
import org.apache.spark.sql.avro.from_avro

//https://spark.apache.org/docs/2.3.0/structured-streaming-kafka-integration.html
object SparkBatchConsumeFromKafkaAvro {

  def main(args: Array[String]): Unit = {

    val spark: SparkSession = SparkSession.builder()
      .master("local[1]")
      .appName("https://SparkByExamples.com")
      .getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")

    val df = spark
      .read
      .format("kafka")
      .option("kafka.bootstrap.servers", "192.168.1.100:9092")
      .option("subscribe", "avro_topic1")
      .load()

    df.printSchema()

    /*
     Displays Data in Binary
     */

    val schemaAvro = new Schema.Parser()
      .parse(new File("src/main/resources/person.avsc"))

    /*
     Displays Data in String
     */
    val df2 = df.select(from_avro(col("value"),schemaAvro.toString ).as("value"))
        .selectExpr("CAST(value AS STRING)")

    df2.select("value.gender").show()
  }
}
