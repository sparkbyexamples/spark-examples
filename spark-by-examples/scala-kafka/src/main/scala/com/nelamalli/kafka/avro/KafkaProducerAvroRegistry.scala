//package com.sparkbyexamples.kafka.avro
//
//import java.util.Properties
//
//import org.apache.avro.Schema
//import org.apache.avro.generic.{GenericData, GenericRecord}
//import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}
//
//object KafkaProducerAvroRegistry_ {
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
//    val userSchema = "{\"type\":\"record\"," +
//      "\"name\":\"myrecord\"," +
//      "\"fields\":[{\"name\":\"f1\",\"type\":\"string\"}]}";
//    val parser = new Schema.Parser();
//    val schema = parser.parse(userSchema);
//    val avroRecord:GenericRecord = new GenericData.Record(schema);
//    avroRecord.put("f1", "value1");
//
//    val record:ProducerRecord[Object, Object] = new ProducerRecord[Object, Object]("avro_topic", key, avroRecord);
//
//    producer.send(record);
//
//  }
//}
