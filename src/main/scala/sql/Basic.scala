package sql

import org.apache.spark.sql.SparkSession
import org.apache.log4j.{Level, Logger}

/**
 * Created by Frank on 2018/8/20.
 */
object Basic {
  def main(args: Array[String]) {
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    val spark = SparkSession
      .builder()
      .master("local[*]")
      .appName("Spark SQL basic example")
      .config("spark.some.config.option", "some-value")
      .getOrCreate()

    val df = spark.read.json("E:\\IdeaProjects\\test123\\datas\\zips.json")

    df.show()
    spark.close()
  }
}
