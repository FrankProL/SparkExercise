import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkContext, SparkConf}

/**
 * Created by Frank on 2018/8/20.
 */
object SparkWordCount {
  def main(args: Array[String]) {
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)

    val conf = new SparkConf().setAppName("WordCount").setMaster("local[*]")
    val sc = new SparkContext(conf)
    val texts = sc.textFile("file:///C:\\Users\\Frank\\Desktop\\sparktest.txt")
    val wordCount = texts.flatMap(line => line.split(",")).map(word => (word, 1)).reduceByKey((a, b) => a + b)
    wordCount.foreach(println _)
    sc.stop()
  }
}
