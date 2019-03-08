import org.apache.spark.sql.SparkSession

object SparkTest{

  def main(args:Array[String]): Unit ={

    val sparkSession = SparkSession.builder().appName("Naveen").master("local[1]").getOrCreate();

    println("APP Name :"+sparkSession.sparkContext.appName);
    println("Deploy Mode :"+sparkSession.sparkContext.deployMode);
    println("Master :"+sparkSession.sparkContext.master);

  }
}