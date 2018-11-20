/**
 * Created by Frank on 2018/11/9.
 * accr保存每次累乘的结果
 */
object FactorialTest {

  def factorial(n:Int):Int={
    def loop(n:Int,accr:Int):Int={
      if(n<=0) accr
      else loop(n-1,n*accr)
    }
    loop(n,1)
  }

  def main(args: Array[String]) {
    print(factorial(3))
  }
}
