import org.apache.spark.sql.{SparkSession, SQLContext}
import org.apache.spark.{SparkContext, SparkConf}

/**
 * Created by Frank on 2018/9/8.
 * 使用spark1.x的API，测试Dataset和DataFrame
 */
object DatasetDataFrameTest {
  def main(args: Array[String]) {
    /*  //spark 1.x api 创建sc、sqlContext
    val conf = new SparkConf().setAppName("SparkJoins").setMaster("local")
    val sc = new SparkContext(conf);
    val sqlContext = new SQLContext(sc);
    */

    val spark=SparkSession.builder().master("local[*]").appName("spark2->sqlContext").getOrCreate()
    // SparkSession can do everything SQLContext can do but if necessary the SQLContext can be accessed as follows
    val sqlContext = spark.sqlContext

    import spark.implicits._
    val ds = sqlContext.read.text("E:\\IdeaProjects\\SparkExercise\\datas\\zips.json").as[String]
    val result= ds
      .flatMap(_.split(" "))
      .filter(_ != "")
      .toDF()
//    .groupBy($"value")
//    .agg(count("*") as "numOccurances")
//    .orderby($"numOccurances" desc)
    result.show()

    val wordCount= ds
      .flatMap(_.split(" "))
      .filter(_ != "")
      .groupBy()    //groupBy(_.toLowerCase())
      .count()
    wordCount.show()
  }
}
