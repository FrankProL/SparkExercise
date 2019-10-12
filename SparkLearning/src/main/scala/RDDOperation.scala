import org.apache.spark.{SparkContext, SparkConf}

/**
 * Created by Frank on 2019/10/12.
 * RDD
 */
object RDDOperation {
  def main(args: Array[String]) {
    val conf=new SparkConf().setAppName("xx").setMaster("local[*]")
    val sc=new SparkContext(conf)

    val rdd1 = sc.parallelize(List("a", "b", "c"))
    val rdd2 = sc.parallelize(List("e", "d","c"))

    //求并集，union
    rdd1.union(rdd2).collect

    rdd1.union(rdd2).distinct.collect

    //求交集，intersection
    rdd1.intersection(rdd2).collect

    //求差集，subtract
    rdd1.subtract(rdd2).collect
  }
}
