package com.sparkbyexamples.kafka.jackson
import java.util.Properties
import com.sparkbyexamples.kafka.beans.User
import org.apache.kafka.clients.consumer.KafkaConsumer
import scala.collection.JavaConverters._
object KafkaConsumerWithUserObject extends App {
  val prop:Properties = new Properties()
  prop.put("group.id", "test")
  prop.put("bootstrap.servers","192.168.1.100:9092")
  prop.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer")
  prop.put("value.deserializer","com.sparkbyexamples.kafka.jackson.UserDeserializer")
  prop.put("enable.auto.commit", "true")
  prop.put("auto.commit.interval.ms", "1000")
  val consumer = new KafkaConsumer[String,User](prop)
  val topics = List("user_user")
  try{
    consumer.subscribe(topics.asJava)
    while(true){
      val records = consumer.poll(10)
      for(record<-records.asScala){
        println("Topic: "+record.topic()+", Key: "+record.key() +", Value: "+record.value().getName +
          ", Offset: "+record.offset() +", Partition: "+record.partition())
      }
    }
  }catch{
    case e:Exception => e.printStackTrace()
  }finally {
    consumer.close()
  }
}
