package com.sparkbyexamples.spark.kafka

import java.nio.file.{Files, Paths}

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.avro._
import org.apache.spark.sql.functions._
object KafkaProduceAvro {

  def main(args:Array[String]): Unit ={

    val spark: SparkSession = SparkSession.builder().master("local[1]")
      .appName("SparkByExamples.com")
      .getOrCreate()

    val data = Seq((1,"James ","","Smith",2018,1,"M",3000),
      (2,"Michael ","Rose","",2010,3,"M",4000),
      (3,"Robert ","","Williams",2010,3,"M",4000),
      (4,"Maria ","Anne","Jones",2005,5,"F",4000),
      (5,"Jen","Mary","Brown",2010,7,"",-1)
    )

    val columns = Seq("id","firstname","middlename","lastname","dob_year",
      "dob_month","gender","salary")
    import spark.sqlContext.implicits._
    val df = data.toDF(columns:_*)

    df.map(f=>{
      (f.getAs[Integer]("id"),
    })

//    // `from_avro` requires Avro schema in JSON string format.
//    val jsonFormatSchema = new String(Files.readAllBytes(Paths.get("src/main/resources/person.avsc")))
//
//    val df = spark
//      .readStream
//      .format("kafka")
//      .option("kafka.bootstrap.servers", "host1:port1,host2:port2")
//      .option("subscribe", "topic1")
//      .load()
//
//    // 1. Decode the Avro data into a struct;
//    // 2. Filter by column `favorite_color`;
//    // 3. Encode the column `name` in Avro format.
//    val output = df
//      .select(from_avro(col("value"), jsonFormatSchema) as "user")
//      .where(col("user.favorite_color") === "red")
//      .select(to_avro(col("user.name")) as "value")

    val query = output
      .writeStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "host1:port1,host2:port2")
      .option("topic", "topic2")
      .start()

  }
}
