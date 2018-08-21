package quickstart

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession

/**
 * Created by Frank on 2018/8/21.
 */
object QuickStart {
  def main(args: Array[String]) {
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    val spark = SparkSession
      .builder
      .master("local[*]")
      .appName("Spark Pi")
      .getOrCreate()

    import spark.implicits._

    val textFile=spark.read.textFile("E:\\IdeaProjects\\SparkExercise\\datas\\add-plugin.md")

    println(textFile.count())   //读取的文件的行数

    println(textFile.first())

    val linesWithSpark=textFile.filter(line=> line.contains("You"))     //filter过滤

    println(linesWithSpark.count())
    linesWithSpark.collect().foreach(println _)

    val num= textFile.map(line => line.split(" ").size).reduce((a, b) => if (a > b) a else b)
    println(num)
  }

}
