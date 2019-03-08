import scala.collection.mutable

class CheckSumAccumulator{

  private var sum = 0

  def add(b:Byte): Unit ={
    sum+=b
  }

  def calc(): Int ={
    ~(sum & 0XFF)+1
  }
}

object CheckSumAccumulator123{
  private val cache = mutable.Map.empty[String,Int]

  def calculate(str:String): Int ={

    if(cache.contains(str))
        cache(str)
    else{
      val csum = new CheckSumAccumulator();
      for(c<-str)
        csum.add(c.toByte)
      cache += (str -> csum.calc())
      csum.calc()
    }
  }
}

println(CheckSumAccumulator123.calculate("Naveen"))
println(CheckSumAccumulator123.calculate("Praveen"))
println(CheckSumAccumulator123.calculate("Naveen"))
