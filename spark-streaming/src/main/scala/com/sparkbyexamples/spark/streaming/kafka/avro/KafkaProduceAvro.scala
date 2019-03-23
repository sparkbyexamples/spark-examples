package com.sparkbyexamples.spark.streaming.kafka.avro

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{col, from_json,to_json,struct}
import org.apache.spark.sql.avro.to_avro
import org.apache.spark.sql.types.{IntegerType, StringType, StructType}

object KafkaProduceAvro {

  def main(args: Array[String]): Unit = {

    val spark: SparkSession = SparkSession.builder()
      .master("local[3]")
      .appName("SparkByExample")
      .getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")

    val df = spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "192.168.1.100:9092")
      .option("subscribe", "json_topic")
      .option("startingOffsets", "earliest") // From starting
      .load()

    df.printSchema()

    //df.show(false)
    //org.apache.spark.sql.AnalysisException: Queries with streaming sources must be executed with writeStream.start();;

    val schema = new StructType()
      .add("id",IntegerType)
      .add("firstname",StringType)
      .add("middlename",StringType)
      .add("lastname",StringType)
      .add("dob_year",IntegerType)
      .add("dob_month",IntegerType)
      .add("gender",StringType)
      .add("salary",IntegerType)

    val person = df.selectExpr("CAST(value AS STRING)") // First convert binary to string
      .select(from_json(col("value"), schema).as("data"))
      .select("data.*")

    person.printSchema()
    /**
      *uncomment below code if you want to write it to console for testing.
      */
//    person.select(to_json(struct("id","firstname","middlename","lastname","dob_year","dob_month","gender","salary")).as("value"))
//      .writeStream
//        .format("console")
//        .outputMode("append")
//        .start()
//        .awaitTermination()

    /**
      * First cast Kafka Binary tp String
      * Convert
      * Convert String to Avro
      */

    person.select(to_avro(col("firstname")) as "value")
      .writeStream
      .format("kafka")
      .outputMode("append")
      .option("kafka.bootstrap.servers", "192.168.1.100:9092")
      .option("topic", "avro_topic9")
      .option("checkpointLocation","c:/tmp")
      .start()
      .awaitTermination()
  }

}
