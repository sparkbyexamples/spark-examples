package com.sparkbyexamples.spark.kafka.json

import org.apache.spark.sql.SparkSession

object KafkaProduceJson {

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

    val ds = df.toJSON
    ds.printSchema()

    val query = ds
      .writeStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "192.168.1.100:9092")
      .option("topic", "text_topic")
      .start()

  }
}
