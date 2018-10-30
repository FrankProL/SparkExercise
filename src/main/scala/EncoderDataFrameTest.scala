import org.apache.spark.sql.SparkSession

/**
 * Created by Frank on 2018/9/10.
 */
class EncoderDataFrameTest {
  def main(args: Array[String]) {
    val spark= SparkSession.builder().appName("encoder_test").master("local")

  }
}
