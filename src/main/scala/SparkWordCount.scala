import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkContext, SparkConf}

/**
 * Created by Frank on 2018/8/20.
 */
object SparkWordCount {
  def main(args: Array[String]) {
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)

    //本地提交远程集群运行，设置hadoop和spark用户名
    //System.setProperty("HADOOP_USER_NAME", "kzcq")
    //System.setProperty("user.name", "kzcq")
    //本地提交远程集群运行，指定集群master和jar包的本地路径
    //val conf = new SparkConf().setAppName("WordCount").setMaster("spark://lg-11-152.ko.cn:7077").setJars(ayyaylib)

    val conf = new SparkConf().setAppName("WordCount").setMaster("local[*]")
    val sc = new SparkContext(conf)
    val texts = sc.textFile("file:///C:\\Users\\Frank\\Desktop\\sparktest.txt")
    val wordCount = texts.flatMap(line => line.split(",")).map(word => (word, 1)).reduceByKey((a, b) => a + b)
    //val result = srcData.flatMap(_.split("\\s+")).map((_,1)).reduceByKey(_+_)
    wordCount.foreach(println _)
    sc.stop()
  }
}
