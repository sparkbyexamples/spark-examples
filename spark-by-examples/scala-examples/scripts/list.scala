//Lists are immutable like java String
var l1:List[String] = List[String]("1","2")
//l1(0)="one" - this statement fails
 l1=List("one","two","three")
val l2=l1
l2.foreach(l=>println(l))
l2.foreach(println)
for(i<-l2)
    println(i)
for(i<-0 to 2)
    println(l2(i))
val l3 = "zero" :: l2

l3.foreach(l=>println(l))

println("Concatenated List")
val l4 = "4" :: "5" :: "6" :: Nil

val l5 = l3 ::: l4

for(l<-l5)
    println(l)
val l6 = List("will","wall","until")
println("All containsl letter l :" + l6.forall(l=>l.endsWith("l")))

val l7 = l6.sortWith((a,b)=>a.charAt(0) > b.charAt(0))
println("After Sorting")
l7.foreach(println)



