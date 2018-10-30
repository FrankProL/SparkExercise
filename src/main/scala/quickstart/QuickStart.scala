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

    // For implicit conversions like converting RDDs to DataFrames
    import spark.implicits._        //使用隐式转换，下面的map函数需要使用

    val textFile=spark.read.textFile("E:\\IdeaProjects\\SparkExercise\\datas\\add-plugin.md")

    println(textFile.count())   //读取的文件的行数

    println(textFile.first())

    val linesWithSpark=textFile.filter(line=> line.contains("You"))     //filter过滤

    println(linesWithSpark.count())
    linesWithSpark.collect().foreach(println _)

    /* to find the line with the most words */
    val num= textFile.map(_.split(" ").size).reduce((a, b) => if (a > b) a else b)
    //val num= textFile.map(line => line.split(" ").size).reduce((a, b) => if (a > b) a else b)
    /* use Math.max() function to make this code easier to understand */
    //import java.lang.Math
    //val num = textFile.map(line => line.split(" ").size).reduce((a, b) => Math.max(a, b))
    println(num)

    /*
    Spark can implement MapReduce flows easily, word count    _.toLowerCase   identity
     */
    val wordCounts = textFile.flatMap(line => line.split(" ")).groupByKey(_.toLowerCase).count()
    //val wordCounts = textFile.flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_)
    wordCounts.collect().foreach(println _)
  }

}
