package com.sparkbyexamples.spark.streaming.batch

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.col
import org.apache.spark.sql.avro.to_avro
//https://spark.apache.org/docs/2.3.0/structured-streaming-kafka-integration.html
object SparkBatchProduceToKafkaAvro {

  def main(args: Array[String]): Unit = {

    val spark: SparkSession = SparkSession.builder()
      .master("local[1]")
      .appName("SparkByExample")
      .getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")

    /*
      Write Avro to Kafka
     */
    val data2 = Seq((1,"James ","","Smith",2018,1,"M",3000),
      (2,"Michael ","Rose","",2010,3,"M",4000),
      (3,"Robert ","","Williams",2010,3,"M",4000),
      (4,"Maria ","Anne","Jones",2005,5,"F",4000),
      (5,"Jen","Mary","Brown",2010,7,"",-1)
    )

    val columns = Seq("id","firstname","middlename","lastname","dob_year",
      "dob_month","gender","salary")
    import spark.sqlContext.implicits._
    val df2 = data2.toDF(columns:_*)

    df2.toJSON.select(to_avro(col("value")).as("value"))
      .write
      .format("kafka")
      .option("kafka.bootstrap.servers","192.168.1.100:9092")
      .option("topic","avro_topic1")
      .save()

  }
}
