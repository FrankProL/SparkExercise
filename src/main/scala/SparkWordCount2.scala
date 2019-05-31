import org.apache.spark.{SparkConf, SparkContext}

object SparkWordCount2 {
  //  Logger.getLogger("org").setLevel(Level.WARN)
  def FILE_NAME: String = "word_count_results_"

  def main(args: Array[String]) {
    println("start")
    //System.setProperty("HADOOP_USER_NAME", "kzcq")
    //System.setProperty("user.name", "kzcq")
    val ayyaylib = new Array[String](1);
    ayyaylib(0) = "file:///E:/IdeaProjects/testscala/out/artifacts/testscala_jar/testscala.jar";
    //val conf = new SparkConf().setAppName("WordCount").setMaster("spark://lg-11-152.ko.cn:7077").setJars(ayyaylib)
    val conf = new SparkConf().setAppName("WordCount").setMaster("local[*]").setJars(ayyaylib)

    val sc = new SparkContext(conf)
    //val texts = sc.textFile("hdfs://lg-11-152.ko.cn:9000//user/kzcq/mysql/rr.txt")
    val texts = sc.textFile("file:///C:\\Users\\Frank\\Desktop\\sparktest.txt")
    val wordCount = texts.flatMap(line => line.split(" ")).map(word => (word, 1)).reduceByKey((a, b) => a + b)
    wordCount.foreach(println)
    println(wordCount)

        //val texts = sc.textFile("hdfs://lg-11-152.ko.cn:9000/user/kzcq/mysql/*.txt")
        //val wordCount = texts.flatMap(line => line.split(" ")).map(word => (word, 1)).reduceByKey((a, b) => a + b)
        //wordCount.collect().foreach(println)
    sc.stop()
    println("Hello World")

  }

}
