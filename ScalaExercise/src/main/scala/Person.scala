/**
 * Created by Frank on 2017/2/27.
 */
class Person {
  var name:String="jj"
  val age:Int=1

  //定义方法
  //如果一个方法没有参数，而且不能更改属性（不会产生副作用），那么参数可以省略
  //如果有更改的时候，（）不要省略
  def eat():Unit={
    println(name+" eat ....")
  }

  def watchFootball(teamName:String):Unit={
    println(name+" watch "+teamName)
    println(s"${name} watch ${teamName}")
  }
}
