
class Rational(n:Int,d:Int){
  require(d!=0)
  val number:Int = n
  val denom:Int = d
  override def toString() = n+"/"+d
  def this(n:Int)=this(n,1)

  def add(that:Rational): Rational ={
      new Rational(number*that.denom + that.number*denom,denom*that.denom)
  }

  def +(that:Rational): Rational ={
    new Rational(number*that.denom + that.number*denom,denom*that.denom)
  }
}

val a = new Rational(1,2)
val b = new Rational(2,3)
val c = a+b
println(c)