/**
 * Created by Frank on 2018/11/30.
 */
object ClosureTest {
  def sum(arr:Array[Int]): Int ={
    arr.reduce(_+_)
  }

  def lazy_sum(arr:Array[Int]): Int ={
    var sum=sum(arr)
    sum
  }

  def main(args: Array[String]) {
    val arr=Array(1,2,3,4,5)
    print(sum(arr))

  }
}
