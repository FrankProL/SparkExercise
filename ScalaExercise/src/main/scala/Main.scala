/**
 * Created by Frank on 2018/11/20.
 */
object Main extends App{
  class A {
    def this(f: String) = {
      this()
      this.f = f
    }
    var f: String = _
    override def toString = s"A(${f})"
  }
  val m = Map(1 -> new A("a"))
  println("-------------------------")
  println(s"m=$m")
  println("-------------------------")
  val m1 = m.mapValues{a=>a.f = "b";a}
  println(s"m=$m")
  println(s"m1=$m1")
  m1.foreach(_._2.f = "c")
  println(s"m1=$m1")
  println("-------------------------")
  val m2 = m.transform{(k,v)=>v.f = "d";v}
  println(s"m=$m")
  println(s"m2=$m2")
  println("-------------------------")
}
