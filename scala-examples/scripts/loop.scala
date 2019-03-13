import java.io.File

import scala.io.Source

var files = (new File(".")).listFiles()
for(file <- files if file.getName.endsWith(".scala") if file.getName.contains("loop"))
  println(file)

for{
  file <- files
  if file.getName.endsWith(".scala")
  if file.getName.contains("test")
}println(file)

def lines(fileName:String):Array[String]={
  Source.fromFile(fileName).getLines().toArray
}

def readFiles(): Unit ={

  val files = new File(".").listFiles()
  for{
    file<-files
    if file.getName.endsWith(".scala")
    line<-lines(file.getName)
    trimLine = line.trim
    if trimLine.contains("println")
  }println(s"$file lines "+ trimLine)

}
readFiles()

