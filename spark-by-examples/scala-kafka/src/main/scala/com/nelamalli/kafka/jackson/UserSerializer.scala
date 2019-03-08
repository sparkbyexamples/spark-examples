package com.sparkbyexamples.kafka.jackson

import java.util

import com.sparkbyexamples.kafka.beans.User
import org.apache.kafka.common.serialization.Serializer
import org.codehaus.jackson.map.ObjectMapper

class UserSerializer extends Serializer[User]{

  override def configure(map: util.Map[String, _], b: Boolean): Unit = {
  }

  override def serialize(s: String, t: User): Array[Byte] = {
    if(t==null)
      null
    else
     {
       val objectMapper = new ObjectMapper()
       objectMapper.writeValueAsString(t).getBytes
     }
  }

  override def close(): Unit = {
  }
}
