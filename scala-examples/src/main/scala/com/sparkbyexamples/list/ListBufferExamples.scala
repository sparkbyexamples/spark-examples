package com.sparkbyexamples.list

import scala.collection.mutable

object ListBufferExamples {

  def main(args: Array[String]): Unit = {

    var list1 = mutable.ListBuffer("A","B")
    list1(0)="C"
    list1(1)="D"
    // list1 = "C" :: list1  //  Error
    //list1 = list1 + "" //Error
    //list1 ++= "E" //Error
    // list1 = list1 + "C"// Error
    list1 += "B"

    list1.append("A")

    println("Modify an element on list")
    list1.foreach(println(_))

    println("Create list2 from list1")
    var list2 = list1.map(_.toLowerCase())
    list2.appendAll(list1)
    list2.foreach(println(_))

    println("Add list to existing list")
    list2 ++= list1
    //val list7 = list1 ::: list2 //Error
    list2.foreach(println(_))

    println("Merge list1 & list2 and create list3")
    //val list3 = list1 ::: list2 // Error
    val list3 = list1 ++ list2
    list3.foreach(println(_))

    //Converts list to map
    println("Convert list to map")
    val list4 = list1.map(f=>(f,f.toLowerCase()))
    list4.foreach(f=>println(f._1+f._2))

    //A ListBuffer is like an array buffer except that it uses a linked list internally instead of an array
    // val list3 = mutable.ListBuffer("A","B")


  }
}
