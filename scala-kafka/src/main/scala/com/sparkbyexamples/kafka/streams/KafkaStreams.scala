//package com.sparkbyexamples.kafka.streams
//
//import java.util.Properties
//import java.util.concurrent.TimeUnit
//
//import org.apache.kafka.streams.scala.StreamsBuilder
//import org.apache.kafka.streams.scala.kstream.KStream
//import org.apache.kafka.streams.{KafkaStreams, StreamsConfig}
//
///**
//  * Demonstrates how to perform simple, state-less transformations via map functions.
//  * Similar to [[]] but in Scala.
//  *
//  * Use cases include e.g. basic data sanitization, data anonymization by obfuscating sensitive data
//  * fields (such as personally identifiable information aka PII).  This specific example reads
//  * incoming text lines and converts each text line to all-uppercase.
//  *
//  *
//  * HOW TO RUN THIS EXAMPLE
//  *
//  * 1) Start Zookeeper and Kafka.
//  * Please refer to <a href='http://docs.confluent.io/current/quickstart.html#quickstart'>QuickStart</a>.
//  *
//  * 2) Create the input and output topics used by this example.
//  *
//  * {{{
//  * $ bin/kafka-topics --create --topic TextLinesTopic --zookeeper localhost:2181 --partitions 1 --replication-factor 1
//  * $ bin/kafka-topics --create --topic UppercasedTextLinesTopic --zookeeper localhost:2181 --partitions 1 --replication-factor 1
//  * $ bin/kafka-topics --create --topic OriginalAndUppercasedTopic --zookeeper localhost:2181 --partitions 1 --replication-factor 1
//  * }}}
//  *
//  * Note: The above commands are for the Confluent Platform. For Apache Kafka it should be
//  * `bin/kafka-topics.sh ...`.
//  *
//  * 3) Start this example application either in your IDE or on the command line.
//  *
//  * If via the command line please refer to
//  * <a href='https://github.com/confluentinc/kafka-streams-examples#packaging-and-running'>Packaging</a>.
//  * Once packaged you can then run:
//  *
//  * {{{
//  * $ java -cp target/kafka-streams-examples-5.0.0-SNAPSHOT-standalone.jar io.confluent.examples.streams.MapFunctionScalaExample
//  * }}}
//  *
//  * 4) Write some input data to the source topics (e.g. via `kafka-console-producer`.  The already
//  * running example application (step 3) will automatically process this input data and write the
//  * results to the output topics.
//  *
//  * {{{
//  * # Start the console producer.  You can then enter input data by writing some line of text,
//  * # followed by ENTER:
//  * #
//  * #   hello kafka streams<ENTER>
//  * #   all streams lead to kafka<ENTER>
//  * #
//  * # Every line you enter will become the value of a single Kafka message.
//  * $ bin/kafka-console-producer --broker-list localhost:9092 --topic TextLinesTopic
//  * }}}
//  *
//  * 5) Inspect the resulting data in the output topics, e.g. via `kafka-console-consumer`.
//  *
//  * {{{
//  * $ bin/kafka-console-consumer --bootstrap-server localhost:9092 --topic UppercasedTextLinesTopic --from-beginning
//  * }}}
//  *
//  * You should see output data similar to:
//  * {{{
//  * HELLO KAFKA STREAMS
//  * ALL STREAMS LEAD TO KAFKA
//  * }}}
//  *
//  * {{{
//  * $ bin/kafka-console-consumer --bootstrap-server localhost:9092 --topic OriginalAndUppercasedTopic --from-beginning --property print.key=true
//  * }}}
//  *
//  * You should see output data similar to:
//  * {{{
//  * hello kafka streams	HELLO KAFKA STREAMS
//  * all streams lead to kafka	ALL STREAMS LEAD TO KAFKA
//  * }}}
//  *
//  * 6) Once you're done with your experiments, you can stop this example via `Ctrl-C`.  If needed,
//  * also stop the Kafka broker (`Ctrl-C`), and only then stop the ZooKeeper instance (`Ctrl-C`).
//  */
//object KafkaStreams_ extends App {
//
//  import org.apache.kafka.streams.scala.ImplicitConversions._
//  import org.apache.kafka.streams.scala.Serdes._
//
//  val config: Properties = {
//    val p = new Properties()
//    p.put(StreamsConfig.APPLICATION_ID_CONFIG, "map-function-scala-example")
//    val bootstrapServers = if (args.length > 0) args(0) else "192.168.1.100:9092,192.168.1.141:9092,192.168.1.113:9092,192.168.1.118:90922"
//    p.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers)
//    p
//  }
//
//  val builder = new StreamsBuilder
//  val textLines: KStream[Array[Byte], String] = builder.stream[Array[Byte], String]("TextLinesTopic")
//
//  // Variant 1: using `mapValues`
//  val uppercasedWithMapValues: KStream[Array[Byte], String] = textLines.mapValues(_.toUpperCase())
//  uppercasedWithMapValues.to("UppercasedTextLinesTopic")
//
//  // Variant 2: using `map`, modify both key and value
//  val originalAndUppercased: KStream[String, String] = textLines.map((_, value) => (value, value.toUpperCase()))
//
//  // Write the results to a new Kafka topic "OriginalAndUppercasedTopic".
//  originalAndUppercased.to("OriginalAndUppercasedTopic")
//
//  val streams: KafkaStreams = new KafkaStreams(builder.build(), config)
//  streams.start()
//
//  sys.ShutdownHookThread {
//    streams.close(10, TimeUnit.SECONDS)
//  }
//
//}
