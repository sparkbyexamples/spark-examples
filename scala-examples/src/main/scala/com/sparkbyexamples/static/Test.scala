package com.sparkbyexamples

object Test {

  def main(args:Array[String]): Unit ={

    val O:Option[Any] = None

    StaticExample.singletone match {
      case None => println ("nome")
      case Some(_) => println(StaticExample.singletone.get)
    }

    StaticExample.create()
    println(StaticExample.objectNamePublic)

    StaticExample.singletone match {
      case None => println ("nome")
      case _ => println(StaticExample.singletone.get)
    }

    val staticExample: StaticExample = new StaticExample("My Name is Naveen")
    val staticExample2: StaticExample = new StaticExample("My Name is Prabha")
    println("staticExample.getClassName() : "+staticExample.getClassName())
    println("staticExample2.getClassName() : "+staticExample2.getClassName())

    println("staticExample.getValue() : "+staticExample.getValue())

    println( StaticExample.getClassName(staticExample))


    staticExample.printObjectName()

  }
}
