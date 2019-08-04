package com.sparkbyexamples

class StaticExample(className1:String) {

  private val className = className1

  def printObjectName(): Unit ={
    println(StaticExample.objectName);
  }

  def getValue():String = {
    return StaticExample.objectName
  }
  def getClassName():String = {
    return className
  }


}

object StaticExample {

  private val objectName = " class name Static Example"
  val objectNamePublic = "public variable"
  var singletone:Option[String] = None

  def create(): Unit ={
    if(singletone == None){
      singletone = Some("value")
    }
  }

  def getClassName(staticExample:StaticExample) : String = {
    return staticExample.className
  }
}
