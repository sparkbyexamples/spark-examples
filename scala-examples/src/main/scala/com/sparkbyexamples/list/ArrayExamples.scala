package com.sparkbyexamples.list

object ArrayExamples extends App {

  var list1 = Array("A","B")
  list1(1)="AA" //Errro
  val  list2 = list1.map(_.toLowerCase())

  println(list1.mkString(","))
  list1.foreach(f=>println(f))
  list1.foreach(println(_))

  println("Reading a value form Index :"+list1(1))
  println("Adding element 'C' to Arrays")

  //list1 += "C"    // re-assigning
  println(list1.mkString(","))

  println("Adding two Arrays")
  var list3 = list1 ++ list2

  println(list3.mkString(","))

  println("Adding literal to each element in Lists")
  val list4 = list1.map(f=>f+"->")
  println(list4.mkString(","))

  println("Convert all list elements to Int")
  val list5 = List("1","2","3","4","5")
  println(list5.map(f=>f.toInt).mkString(","))
}
