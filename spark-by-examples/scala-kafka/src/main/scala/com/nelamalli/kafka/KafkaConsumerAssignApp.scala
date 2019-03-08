package com.sparkbyexamples.kafka

import java.util
import java.util.Properties
import java.util.regex.Pattern

import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.TopicPartition

import scala.collection.JavaConverters._

object KafkaConsumerAssignApp {

  def main(args: Array[String]): Unit = {

    val prop:Properties = new Properties()
    prop.put("bootstrap.servers","192.168.1.100:9092")
    prop.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer")
    prop.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer")

    val consumer = new KafkaConsumer(prop)

    val tp1 = new TopicPartition("topic_text",1)
    val tp2 = new TopicPartition("my_topic_partition",1)

    val topics = List[TopicPartition](tp1,tp2)
    consumer.assign(topics.asJava)
    while(true){

      val records = consumer.poll(10)
      for(record<-records.asScala){

        println("Key: "+record.key() +", Value: "+record.value() +", Offset: "+record.offset() )

      }
    }

    consumer.close()// close in finally block
  }
}
