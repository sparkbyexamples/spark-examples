import com.experian.edf.oxygen.utils.JsonUtils
import com.google.gson.JsonObject

//Set by default immutable
var s1 = Set("one","two","three","four")
//s1(0)="1" - this statement fails
s1.foreach(println)

for(s<-s1)
    println(s)

 s1 += "zero"

for(s<-s1)
  println(s)

//Map

var m1 = Map("a"->"A","b"->"B")

val str = "{"+Map("ss" -> "yy", "aa" -> "bb").map{case (k, v) => "\""+k + "\":" + v}.mkString(",") + "}"


println("----->"+str)
val str1 = m1.foreach(m=> ("--->"+m._1 + ","+m._2))
println("----->"+str1)
val s5 = m1.keySet

for(s<-s5)
  println(m1(s))

println(m1.contains("a"))

m1.foreach(a=>println(a._1 +","+a._2))

for(m<-m1)
  printf(m._1,m._2)

for((a,b)<-m1){
  printf("Key %s , value %s -", a,b)
}