import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession

import scala.math._

/**
 * Created by Frank on 2018/8/20.
 */
object SparkPi {
  def main(args: Array[String]) {
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    val spark = SparkSession
      .builder
      .master("local[*]")
      .appName("Spark Pi")
      .getOrCreate()
    val slices = if (args.length > 0) args(0).toInt else 2
    val n = math.min(100000L * slices, Int.MaxValue).toInt // avoid overflow
    val count = spark.sparkContext.parallelize(1 until n, slices).map { i =>
        val x = random * 2 - 1
        val y = random * 2 - 1
        if (x * x + y * y <= 1) 1 else 0
      }.reduce(_ + _)
    println(s"Pi is roughly ${4.0 * count / (n - 1)}")
    spark.stop()
  }
}
//random返回[0,1)之间的随机数
//x，y取值范围在[-1,1)之间
//对应半径为1的圆和其外接正方形（边长为2 ，面积为4）
//正方形面积为s1=4，圆面积s2=πr^2=π
//在正方形中不断随机选点，选n个点，落在圆内的为count个
//则s2/s1=count/n,即s2=s1*count/n, s2即为π值