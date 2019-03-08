
import scala.io.Source

for(s<-Source.fromFile("pom.xml").getLines())
  println(s)