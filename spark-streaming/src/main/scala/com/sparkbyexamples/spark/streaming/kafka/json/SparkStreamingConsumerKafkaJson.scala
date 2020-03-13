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
        .option("kafka.bootstrap.servers", "172.21.0.1:9092")
        .option("subscribe", "gofu-sync_sources")
        .option("startingOffsets", "earliest") // From starting
        .load()

    df.printSchema()

    //df.show(false)
    //org.apache.spark.sql.AnalysisException: Queries with streaming sources must be executed with writeStream.start();;

    val schema = new StructType()
      .add("id",IntegerType)
      .add("type",StringType)
      .add("goapotik_merchant_code",StringType)
      .add("product_code",StringType)
      .add("product_name",StringType)
      .add("price",LongType)
      .add("qty",IntegerType)
      .add("source_update_date",LongType)
      .add("batch_code",StringType)
      .add("additional_json",StringType)
      .add("created_by",IntegerType)
      .add("updated_by",IntegerType)
      .add("created_at",LongType)
      .add("updated_at",LongType)

    val person = df.selectExpr("CAST(value AS STRING)")
    .select(from_json(col("value"), schema).as("data"))
      .select("data.*")

    /**
     *uncomment below code if you want to write it to console for testing.
     */
    val query = person.writeStream
      .format("console")
      .outputMode("append")
      .start()
      .awaitTermination()

    /**
      *uncomment below code if you want to write it to kafka topic.
      */
 /*   df.selectExpr("CAST(id AS STRING) AS key", "to_json(struct(*)) AS value")
      .writeStream
      .format("kafka")
      .outputMode("append")
      .option("kafka.bootstrap.servers", "172.21.0.1:9092")
      .option("topic", "gofu-sync_sources")
      .start()
      .awaitTermination()
*/

  }
}
