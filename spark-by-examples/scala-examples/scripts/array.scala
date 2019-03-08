// Arrays are mutable
println("Start")

var j=0
while(j<args.length){
  println(args(j) +","+args.apply(j))
  j+=1
}

args.foreach(s=>println(s))
args.foreach(println)
for(i<-args){
  println(i);booleanArrayOps()
}

for(i<-1 to 2){
  println(args(i))
}
val arr1=Array("one","two","three")
println("Count: "+arr1.count(a=>a.length==5))

val arr:Array[String]=new Array[String](3);
arr(0)="1one"
arr(1)="2two"
arr.update(2,"3three");
println(arr.dropRight(2).length)




