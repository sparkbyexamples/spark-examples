package com.sparkbyexamples.spark.dataframe.avro

import java.io.File

import com.databricks.spark.avro._
import org.apache.avro.Schema
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{SaveMode, SparkSession}

/**
  * Spark Avro library example
  * Avro schema example
  * Avro file format
  *
  */
object AvroUsingDataBricks {

  def main(args: Array[String]): Unit = {


    val spark: SparkSession = SparkSession.builder()
      .master("local[1]")
      .appName("SparkByExamples.com")
      .getOrCreate()

    val data = Seq(("James ","","Smith",2018,1,"M",3000),
      ("Michael ","Rose","",2010,3,"M",4000),
      ("Robert ","","Williams",2010,3,"M",4000),
      ("Maria ","Anne","Jones",2005,5,"F",4000),
      ("Jen","Mary","Brown",2010,7,"",-1)
      )

    val columns = Seq("firstname","middlename","lastname","dob_year",
      "dob_month","gender","salary")
    import spark.sqlContext.implicits._
    val df = data.toDF(columns:_*)

    /**
      * Write Avro File
      */
    df.write
      .mode(SaveMode.Overwrite)
      .avro("C:/tmp/spark_out/avro/person.avro")

    //Alternatively you can specify the format to use instead:
    df.write.format("com.databricks.spark.avro")
      .mode(SaveMode.Overwrite)
      .save("C:/tmp/spark_out/avro/person2.avro")

    /**
      * Read Avro File
      */
    val readDF = spark.read.avro("C:/tmp/spark_out/avro/person.avro")
    //Alternatively you can specify the format to use instead:

    val readDF2 = spark.read
      .format("com.databricks.spark.avro")
      .load("C:/tmp/spark_out/avro/person2.avro")

    /**
      * Write Partition
      */
    df.write.partitionBy("dob_year","dob_month")
      .mode(SaveMode.Overwrite)
      .avro("C:/tmp/spark_out/avro/person_partition.avro")

    /**
      * Reading Partition Data
      */
    spark.read
      .avro("C:/tmp/spark_out/avro/person_partition.avro")
      .where(col("dob_year") === 2010)
      .show()

    /**
      * Namespace
      */
    val name = "AvroTest"
    val namespace = "com.sparkbyexamples.spark"
    val parameters = Map("recordName" -> name, "recordNamespace" -> namespace)
    df.write.options(parameters)
      .mode(SaveMode.Overwrite)
      .avro("C:/tmp/spark_out/avro/person_namespace.avro")

    /**
      * Explicit schema
      */
    val schemaAvro = new Schema.Parser()
      .parse(new File("src/main/resources/person.avsc"))

    spark
      .read
      .format("com.databricks.spark.avro")
      .option("avroSchema", schemaAvro.toString)
      .load("C:/tmp/spark_out/avro/person.avro")
      .show()

    /**
      * Spark SQL
      */
    println("From Table")
    spark.sqlContext.sql("CREATE TEMPORARY VIEW PERSON USING com.databricks.spark.avro OPTIONS (path \"C:/tmp/spark_out/avro/person.avro\")")
    spark.sqlContext.sql("SELECT * FROM PERSON").show()
  }
}
