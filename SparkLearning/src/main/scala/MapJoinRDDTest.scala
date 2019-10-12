import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkContext, SparkConf}

import scala.collection.mutable.ArrayBuffer

/**
 * Created by Frank on 2019/10/11.
 * learn rdd join
 * https://blog.csdn.net/lsshlsw/article/details/50834858
 * https://blog.csdn.net/Utopia_1919/article/details/52038189
 */
object MapJoinRDDTest {
  def main(args: Array[String]) {
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    val conf=new SparkConf().setAppName("xx").setMaster("local[*]")
    val sc=new SparkContext(conf)
//    sc.setLogLevel("warn")

    val tempBig = sc.textFile("file:///C:\\\\Users\\\\Frank\\\\Desktop\\\\sparktest.txt").map(_.split(",")).map(k =>
      (k(0).toInt,k(1).toInt))
    val tempSmall = sc.textFile("file:///C:\\\\Users\\\\Frank\\\\Desktop\\\\sparktest2.txt").map(_.split(",")).map(k =>
      (k(0).toInt,k(1).toInt))

    val bc_tempSmall = sc.broadcast(tempSmall.collectAsMap()).value
    //mapPartitions
    val temp4 = tempBig.mapPartitions(iter => {
      val temp = ArrayBuffer[(Int,Int,Int)]()
      iter.foreach(k=>{
        if(bc_tempSmall.contains(k._1)){
          temp.+=((k._1,k._2,bc_tempSmall.getOrElse(k._1,0)))
        }
      })
      temp.iterator
    })
    temp4.foreach(println(_))
    //map
    val temp3 = tempBig.map(k=>{
      val temp = ArrayBuffer[(Int,Int,Int)]()
      if(bc_tempSmall.contains(k._1)){
        temp.+=((k._1,k._2,bc_tempSmall.getOrElse(k._1,0)))
      }
      temp
    })
    temp3.foreach(println(_))


    /**
     * map-side-join
     * 取出小表中出现的用户与大表关联后取出所需要的信息
     * */
    //部分人信息(身份证,姓名)
    val people_info = sc.parallelize(Array(("110","lsw"),("112","yyy"))).collectAsMap()
    //全国的学生详细信息(身份证,学校名称,学号...)
    val student_all = sc.parallelize(Array(("110","s1","211"),
      ("111","s2","222"),
      ("112","s3","233"),
      ("113","s2","244")))

    //将需要关联的小表进行广播
    val people_bc = sc.broadcast(people_info)

    /**
     * 使用mapPartition而不是用map，减少创建broadCastMap.value的空间消耗
     * 同时匹配不到的数据也不需要返回（）
     * */
    val res = student_all.mapPartitions(iter =>{
      val stuMap = people_bc.value
      val arrayBuffer = ArrayBuffer[(String,String,String)]()
      iter.foreach{case (idCard,school,sno) =>{
        if(stuMap.contains(idCard)){
          arrayBuffer.+= ((idCard, stuMap.getOrElse(idCard,""),school))
        }
      }}
      arrayBuffer.iterator
    })
    res.foreach(println)

    /**
     * 使用另一种方式实现
     * 使用for的守卫
     * */
    val res1 = student_all.mapPartitions(iter => {
      val stuMap = people_bc.value
      for{
        (idCard, school, sno) <- iter
        if(stuMap.contains(idCard))
      } yield (idCard, stuMap.getOrElse(idCard,""),school)
    })

    res1.foreach(println)

  }
}
