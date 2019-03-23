package com.sparkbyexamples.spark.streaming.kafka.json

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{col, from_json}
import org.apache.spark.sql.types.{IntegerType, StringType, StructType}

object SparkStreamingConsumerKafkaJson {

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

    val person = df.selectExpr("CAST(value AS STRING)")
    .select(from_json(col("value"), schema).as("data"))
      .select("data.*")

    /**
     *uncomment below code if you want to write it to console for testing.
     */
//    val query = person.writeStream
//      .format("console")
//      .outputMode("append")
//      .start()
//      .awaitTermination()

    /**
      *uncomment below code if you want to write it to kafka topic.
      */
    df.selectExpr("CAST(id AS STRING) AS key", "to_json(struct(*)) AS value")
      .writeStream
      .format("kafka")
      .outputMode("append")
      .option("kafka.bootstrap.servers", "192.168.1.100:9092")
      .option("topic", "josn_data_topic")
      .start()
      .awaitTermination()


  }
}
