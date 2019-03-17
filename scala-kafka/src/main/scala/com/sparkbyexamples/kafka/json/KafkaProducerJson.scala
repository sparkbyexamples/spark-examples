//package com.sparkbyexamples.kafka.json
//
//import java.util.Properties
//
//import org.apache.kafka.clients.producer.KafkaProducer
//
//object KafkaProducerJson {
//
//  def main(args: Array[String]): Unit = {
//
//    val props:Properties = new Properties()
//    props.put("bootstrap.servers","192.168.1.128:9092")
//    props.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer")
//    props.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer")
//    props.put("acks","all")
//
//    val producer = new KafkaProducer[String, String](props)
//    val topic = "text_topic"
//
//
//  }
//}
