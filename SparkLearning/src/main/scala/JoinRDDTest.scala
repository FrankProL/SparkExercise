import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Created by Frank on 2019/10/11.
 * learn test
 */
object JoinRDDTest {

  def main(args: Array[String]) {
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    val conf =new SparkConf().setAppName("testwordcoun").setMaster("local[*]")
    val sc=new SparkContext(conf)
//    sc.setLogLevel("WARN")
//    val texts=sc.textFile("file:///C:\\Users\\Frank\\Desktop\\sparktest.txt")
//    println(texts.count())

    /*两个RDD按key合并(join算子和cogroup算子)*/
    val idName = sc.parallelize(Array((1, "zhangsan"), (2, "lisi"), (3, "wangwu")))
    val idAge = sc.parallelize(Array((1, 30), (2, 29), (4, 21)))
    val idScore = sc.parallelize(Array((1, 100), (2, 90), (2, 95)))

    println("================cogroup :")
    idName.cogroup(idAge).collect().foreach(println)

    println("================join :")
    idName.fullOuterJoin(idAge).collect().foreach(println)

    println("================cogroup ,有重复id时 ：")
    idName.cogroup(idScore).collect().foreach(println)

    println("================join ,有重复id时 ：")
    idName.fullOuterJoin(idScore).collect().foreach(println)

    // 第一种写法Array(Tuple(String, Tuple))
    val rdd1 = sc.makeRDD(Array(("1", ("Spark", "beijing")), ("2", ("Hadoop", "shanghai"))), 2)
    // 注意键如果是Tuple是不行的，rdd进行join的key是String等基础数据类型，不能是Tuple等容器类,如下
    // val rdd1 = sc.makeRDD(Array((("1001","type1"), ("Spark", "beijing")), (("1002", "type2"), ("Hadoop", "shanghai"))), 2)

    val rdd2 = sc.makeRDD(Array(("1", "30K"), ("2", "15K"), ("3", "25K"), ("5", "10K")), 2)

    println("//下面做Join操作，预期要得到（1,×）、（2,×）、（3,×）")
    val joinRDD = rdd1.join(rdd2).collect.foreach(println)
    println("//下面做leftOutJoin操作，预期要得到（1,×）、（2,×）、（3,×）、(4,×）")
    val leftJoinRDD = rdd1.leftOuterJoin(rdd2).collect.foreach(println)
    println("//下面做rightOutJoin操作，预期要得到（1,×）、（2,×）、（3,×）、(5,×）")
    val rightJoinRDD = rdd1.rightOuterJoin(rdd2).collect.foreach(println)


    val temp1 = sc.textFile("file:///C:\\\\Users\\\\Frank\\\\Desktop\\\\sparktest.txt").map(_.split(",")).map(k =>
      (k(0).toInt,k(1).toInt))
    val temp2 = sc.textFile("file:///C:\\\\Users\\\\Frank\\\\Desktop\\\\sparktest2.txt").map(_.split(",")).map(k =>
      (k(0).toInt,k(1).toInt))

    val temp4 = temp1.join(temp2).map(k => {
      (k._1,k._2._1,k._2._2)
    })
    temp4.foreach(println(_))

    sc.stop()
  }

}
