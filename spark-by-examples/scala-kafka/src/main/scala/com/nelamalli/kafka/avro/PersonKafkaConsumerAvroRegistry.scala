//package com.sparkbyexamples.kafka.avro
//
//import java.util
//import java.util.Properties
//
//import org.apache.kafka.clients.consumer.{ConsumerConfig, KafkaConsumer}
//
//import scala.collection.JavaConversions._
//
//object PersonKafkaConsumerAvroRegistry_ {
//
//  def main(args: Array[String]): Unit = {
//
//    val props = new Properties()
//    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
//    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,"io.confluent.kafka.serializers.StringDeserializer.class")
//    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,"io.confluent.kafka.serializers.KafkaAvroDeserializer.class")
//
//    props.put(ConsumerConfig.GROUP_ID_CONFIG, "group1")
//    props.put("schema.registry.url", "http://localhost:8081");
//
//    val topic = "avro_topic"
//    val consumer = new KafkaConsumer[String, String](props)
//    consumer.subscribe(util.Arrays.asList(topic))
//    while ({true}) {
//      val records = consumer.poll(100)
//
//      for (record <- records) {
//        //System.out.printf("offset = %d, key = %s, value = %s \n", record.offset, record.key, record.value)
//      }
//    }
//  }
//}