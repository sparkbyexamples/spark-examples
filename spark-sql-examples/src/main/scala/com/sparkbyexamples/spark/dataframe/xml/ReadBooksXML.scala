package com.sparkbyexamples.spark.dataframe.xml

import com.sparkbyexamples.spark.beans.BooksDiscounted
import org.apache.spark.sql.{DataFrame, Encoders, Row, SparkSession}

object ReadBooksXML {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local[1]")
      .appName("SparkByExample")
      .getOrCreate()

    import spark.implicits._

    val df = spark.read
      .format("com.databricks.spark.xml")
      .option("rowTag", "book")
      .load("src/main/resources/books.xml")


    df.foreach(row=>{
      println("ID:"+row.getAs("_id") +", Author: "+row.getAs("author"))
      println("ID:"+row(0) +", Author: "+row(1))
      println("ID:"+row.get(0) +", Author: "+row.get(1))
      println("ID:"+row.getString(0) +", Author: "+row.getString(1))
    })

    val row:Row = df.first()
    println("First Element => ID:"+row.getAs("_id") +", Author: "+row.getAs("author"))

    val rows:Array[Row]=df.collect()
    rows.foreach(row=>{
      println("Collect Elements => ID:"+row.getAs("_id") +", Author: "+row.getAs("author"))
    })

    // map on dataframe returns dataset
    val ds = df.map(f=>{
     BooksDiscounted(f.getAs("_id"),f.getAs("author"),f.getAs("description"),
       f.getAs("price"),f.getAs("publish_date"),f.getAs("title"), f.getDouble(4) - f.getDouble(4)*20/100)
    })
    ds.foreach(f=>{
      println("Price :"+f.price + ", Discounted Price :"+f.discountPrice)
    })

  }
}


