package com.sparkbyexamples.kafka.jackson
import java.util.Properties

import com.sparkbyexamples.kafka.beans.User
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.apache.kafka.common.serialization.StringSerializer
object KafkaProducerWithUserObject {
  val props:Properties = new Properties()
  props.put("bootstrap.servers","192.168.1.100:9092")
  props.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer","com.sparkbyexamples.kafka.jackson.UserSerializer")
  props.put("acks","all")
  val producer = new KafkaProducer[String, User](props)
  try{
    for(i <- 0 to 100) {
      val user = new User("My Name - "+i,i)
      val record = new ProducerRecord[String, User]("user_topic",i.toString,user)
      val metadata = producer.send(record)
      printf(s"sent record(key=%s value=%s) " +
        "meta(partition=%d, offset=%d)\n",
        record.key(), record.value(), metadata.get().partition(),
        metadata.get().offset());
    }

  }catch{
    case e:Exception => e.printStackTrace()
  }finally {
    producer.close()
  }
}
