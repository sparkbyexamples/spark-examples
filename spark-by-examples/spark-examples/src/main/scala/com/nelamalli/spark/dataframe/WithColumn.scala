package com.sparkbyexamples.spark.dataframe

import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types.{IntegerType, StringType, StructType}
import org.apache.spark.sql.functions._
object WithColumn {

  def main(args:Array[String]):Unit= {

    val spark: SparkSession = SparkSession.builder()
      .master("local[1]")
      .appName("SparkByExamples.com")
      .getOrCreate()

//    val filePath="C://000_Projects/opt/BigData/zipcodes.csv"
//    val dfText = spark.read.text(filePath)
//    dfText.show()
//
//    //reading as a CSV will separate the columns
//    val dfcsv = spark.read.csv(filePath)
//    dfcsv.show()
//
//    //Adding an option
//    spark.read.option("inferSchema","true").csv(filePath)
//
//    //Chaining multiple options
//    val df2 = spark.read.options(Map("inferSchema"->"true","sep"->",","header"->"true")).csv(filePath)
//    df2.show(false)
//    df2.printSchema()

    val data = Seq(Row(Row("James ","","Smith"),"36636","M","3000"),
      Row(Row("Michael ","Rose",""),"40288","M","4000"),
      Row(Row("Robert ","","Williams"),"42114","M","4000"),
      Row(Row("Maria ","Anne","Jones"),"39192","F","4000"),
      Row(Row("Jen","Mary","Brown"),"","F","-1")
    )

    val schema = new StructType()
      .add("name",new StructType()
        .add("firstname",StringType)
        .add("middlename",StringType)
        .add("lastname",StringType))
      .add("dob",StringType)
      .add("gender",StringType)
      .add("salary",StringType)

    val df2 = spark.createDataFrame(spark.sparkContext.parallelize(data),schema)

    //Change the column data type
    df2.withColumn("salary",df2("salary").cast("Integer"))

    //Derive a new column from existing
    val df4=df2.withColumn("CopiedColumn",df2("salary")* -1)
    df4.show(2)

    //Transforming existing column
    val df5 = df2.withColumn("salary",df2("salary")*100)
    df5.show(2)

    //You can also chain withColumn to change multiple columns

    //Renaming a column. remember it returns a new RDD
    val df3=df2.withColumnRenamed("gender","sex")
    df3.printSchema()

    //Droping a column
    val df6=df4.drop("CopiedColumn")
    println(df6.columns.contains("CopiedColumn"))

    //Adding a literal value
    df2.withColumn("Country", lit("USA")).printSchema()
  }
}
