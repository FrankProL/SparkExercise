/**
 * Created by Frank on 2019/4/25.
 * 转换为字节数组，通过与操作，将特殊字符置0，然后替换为空
 */
class RemoveFourChar {

  def evalute(str: String):String={
    val conbyte=str.getBytes()
    for (i<-0 until conbyte.length ){
      if ((conbyte(i)&0xf8)==0xf0){
        for (j<- 0 until 4) {
          conbyte(i + j)=0x30
        }
      }
    }
    val newStr = new String(conbyte)
    newStr.replaceAll("0000","")
  }
}

object RemoveFourChar{
  def main(args: Array[String]) {
    val rf=new RemoveFourChar
    println(rf.evalute("nihao ..  hell 你好"))
    println(rf.evalute("ff的范德萨分😁😁😁😁Llfldakf;dsk。f😁😁😁😁😁😁😁😁😁😁😁😁daslfjdsa;lfkjd😁sd'j'l'f'k'd'j'sa'l'k"))
  }
}
