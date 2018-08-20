package sql

import org.apache.spark.sql.SparkSession

/**
 * Created by Frank on 2018/8/20.
 */
class Basic {
  def main(args: Array[String]) {
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
