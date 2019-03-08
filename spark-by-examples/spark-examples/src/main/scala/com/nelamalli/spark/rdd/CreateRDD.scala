package com.sparkbyexamples.spark.rdd

import org.apache.spark.sql.SparkSession

object CreateRDD {

  def main(args:Array[String]): Unit ={

    val spark:SparkSession = SparkSession.builder()
      .master("local[3]")
      .appName("SparkByExamples.com")
      .getOrCreate()

    //Create RDD from collection
    val rdd=spark.sparkContext.parallelize(
      Seq(("Java", 20000), ("Python", 100000), ("Scala", 3000))
    )

    //Create RDD from another RDD
    val rdd2 = rdd.map(row=>{
      (row._1,row._2+100)
    })

    rdd2.foreach(println)

  }
}
