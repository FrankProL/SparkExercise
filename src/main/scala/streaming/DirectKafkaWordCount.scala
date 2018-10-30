package streaming

/**
 * Created by Frank on 2018/8/21.
 */
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka010._
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer

/**
 * Consumes messages from one or more topics in Kafka and does wordcount.
 * Usage: DirectKafkaWordCount <brokers> <topics>
 * <brokers> is a list of one or more Kafka brokers
 * <topics> is a list of one or more kafka topics to consume from
 *
 * Example:
 * $ bin/run-example streaming.DirectKafkaWordCount broker1-host:port,broker2-host:port \
 * topic1,topic2
 */
object DirectKafkaWordCount {
  def main(args: Array[String]) {
    if (args.length < 2) {
      System.err.println(
        s"""
           |Usage: DirectKafkaWordCount <brokers> <topics>
           |  <brokers> is a list of one or more Kafka brokers
           |  <topics> is a list of one or more kafka topics to consume from
           |
        """.stripMargin)
      System.exit(1)
    }

    //    StreamingExamples.setStreamingLogLevels()

    val Array(brokers, topics) = args
    System.setProperty("HADOOP_USER_NAME", "kzcq")
    System.setProperty("user.name", "kzcq")
    //System.setProperty("hadoop.home.dir", "E:\\hadoop-2.6.0-cdh5.9.0")
    //System.setProperty("SPARK_LOCAL_IP","172.23.11.152")
    //System.setProperty("spark.driver.host","172.23.11.152")

    // Create context with 2 second batch interval
    /*val sparkConf = new SparkConf().setMaster("local[*]").setAppName("DirectKafkaWordCount")*/
    val sparkConf = new SparkConf()
      .setAppName("DirectKafkaWordCount")
      .setMaster("spark://lg-11-152.ko.cn:7077")
      .setJars(List("file:///D:/testscala/out/artifacts/testscala_jar/testscala.jar"))  //用于本地提交集群运行
      .set("spark.executor.cores","2")
    val ssc = new StreamingContext(sparkConf, Seconds(5))
    // Create direct kafka stream with brokers and topics
    val topicsSet = topics.split(",").toSet
    /*val kafkaParams = Map[String, String]("metadata.broker.list" -> brokers,"bootstrap.servers"->"172.23.11.150:9092")*/
    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> "172.23.11.150:9092",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "use_a_separate_group_id_for_each_stream0",
      "auto.offset.reset" -> "earliest",
      "enable.auto.commit" -> (false: java.lang.Boolean)
    )
    val messages = KafkaUtils.createDirectStream[String, String](
      ssc,
      LocationStrategies.PreferConsistent,
      ConsumerStrategies.Subscribe[String, String](topicsSet, kafkaParams))

    messages.map(record => (record.key, record.value))
    /*

        val offsetRanges = Array(
          // topic, partition, inclusive starting offset, exclusive ending offset
          OffsetRange("test", 0, 0, 100),
          OffsetRange("test", 1, 0, 100)
        )

        val rdd = KafkaUtils.createRDD[String, String](ssc.sparkContext, kafkaParams, offsetRanges, LocationStrategies.PreferConsistent)
    */


    // Get the lines, split them into words, count the words and print
    val lines = messages.map(_.value)
    lines.saveAsTextFiles("hdfs://lg-11-152.ko.cn:9000/user/kzcq/data_spark/regist/")
    //    val words = lines.flatMap(_.split("}"))
    //    val wordCounts = words.map(x => (x, 1L)).reduceByKey(_ + _)
    //    wordCounts.print()

    // Start the computation
    ssc.start()
    ssc.awaitTermination()
  }
}

// scalastyle:on println
