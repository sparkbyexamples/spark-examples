//package com.sparkbyexamples.kafka.avro
//
//import java.util.Properties
//
//import org.apache.avro.Schema
//import org.apache.avro.generic.GenericRecord
//import org.apache.avro.generic.GenericRecordBuilder
//import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}
//import java.io.File
//
//
//
//object PersonKafkaProducerAvroRegistry_ {
//
//  def main(args: Array[String]): Unit = {
//
//    val props = new Properties()
//    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
//    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,"io.confluent.kafka.serializers.StringSerializer.class")
//    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,"io.confluent.kafka.serializers.KafkaAvroSerializer.class");
//    props.put("schema.registry.url", "http://localhost:8081");
//    val producer = new KafkaProducer[Object, Object](props);
//
//    val key = "key1";
//
//
//    val parser = new Schema.Parser();
//    val schema = parser.parse(new File("src/main/resources/person.avsc"));
//    val genericRecordBuilder = new GenericRecordBuilder(schema)
//
//
//    val avroPerson = genericRecordBuilder
//      .set("firstName", "My First Name")
//      .set("lastName", "My last Name")
//      .set("birthDate", "My Date of Birth")
//      .build()
//
//    val record:ProducerRecord[Object, Object] = new ProducerRecord[Object, Object]("avro_topic", key, avroPerson);
//
//    producer.send(record);
//
//  }
//}
