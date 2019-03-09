package com.sparkbyexamples.kafka.jackson

import java.util

import com.sparkbyexamples.kafka.beans.User
import org.apache.kafka.common.serialization.Deserializer
import org.codehaus.jackson.map.ObjectMapper

class UserDeserializer extends Deserializer[User] {
  override def configure(map: util.Map[String, _], b: Boolean): Unit = {
  }

  override def deserialize(s: String, bytes: Array[Byte]): User = {
    val mapper = new ObjectMapper()
    val user = mapper.readValue(bytes, classOf[User])
    user
  }

  override def close(): Unit = {
  }
}
