package com.sparkbyexamples.spark.dataframe.avro

import java.nio.file.{Files, Paths}

import org.apache.spark.sql.types.{IntegerType, StringType, StructType}
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.avro.from_avro
import org.apache.spark.sql.avro.to_avro

object AvroExample {

  def main(args: Array[String]): Unit = {


    val spark: SparkSession = SparkSession.builder()
      .master("local[1]")
      .appName("SparkByExamples.com")
      .getOrCreate()

    // `from_avro` requires Avro schema in JSON string format.
    val jsonFormatSchema = new String(Files.readAllBytes(Paths.get("./examples/src/main/resources/user.avsc")))

    val df = spark
      .readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "host1:port1,host2:port2")
      .option("subscribe", "topic1")
      .load()

    // 1. Decode the Avro data into a struct;
    // 2. Filter by column `favorite_color`;
    // 3. Encode the column `name` in Avro format.
    val output = df
      .select(from_avro(col("value"), jsonFormatSchema) as 'user)
      .where("user.favorite_color == \"red\"")
      .select(to_avro(col("user.name")) as "value")

    val query = output
      .writeStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "host1:port1,host2:port2")
      .option("topic", "topic2")
      .start()
  }
}
