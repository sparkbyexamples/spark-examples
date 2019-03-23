package com.sparkbyexamples.spark.streaming.kafka.avro

import java.io.File
import java.nio.file.{Files, Paths}

import org.apache.avro.Schema
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.col
import org.apache.spark.sql.avro.from_avro
import org.apache.spark.sql.types.{IntegerType, StringType, StructType}

object KafkaConsumerAvro {
    def main(args: Array[String]): Unit = {

      val spark: SparkSession = SparkSession.builder()
        .master("local")
        .appName("SparkByExample")
        .getOrCreate()

      spark.sparkContext.setLogLevel("ERROR")

      val df = spark.readStream
        .format("kafka")
        .option("kafka.bootstrap.servers", "192.168.1.100:9092")
        .option("subscribe", "avro_topic9")
        .option("startingOffsets", "earliest") // From starting
        .load()

      df.printSchema()

      //df.show(false)
      //org.apache.spark.sql.AnalysisException: Queries with streaming sources must be executed with writeStream.start();;
      //org.apache.avro.SchemaParseException: Type not supported: struct

      //val jsonFormatSchema = new String(Files.readAllBytes(Paths.get("./src/main/resources/person.avsc")))

      val str = "{\n  \"type\": \"record\",\n  \"name\": \"Person\",\n  \"namespace\": \"com.sparkbyexamples\",\n  \"fields\": [\n    {\"name\": \"firstname\",\"type\": \"string\"}]}"
      val person = df//.selectExpr("CAST(value AS STRING)")
        .select(from_avro(col("value") , str).as("firstname"))
        //.select("data.*")

      /**
        *uncomment below code if you want to write it to console for testing.
        */
      person.writeStream
        .format("console")
        .outputMode("append")
        .start()
        .awaitTermination()

  }
}
