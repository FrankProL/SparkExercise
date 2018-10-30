import java.util.{Calendar, Date}

import org.joda.time.DateTime

/**
 * Created by Frank on 2018/9/11.
 */
object DateTimeTest {
  val dayFormat = new java.text.SimpleDateFormat("yyyyMMdd")

  def getDateBefore(date: Date, num: Int, flag: String): String = {
    val now = Calendar.getInstance()
    now.setTime(date)
    now.set(Calendar.DATE, now.get(Calendar.DATE) - num)
    flag match {
      case "DATE" => dayFormat.format(now.getTime())
      case "TIMESTAMP" => (now.getTime().getTime / 1000).toString
    }
  }

  def calculateDate(time: DateTime, direct: String, formater: String = "NONE") = {
    val v = direct.substring(1, direct.length - 1).toInt
    var timeStr = ""
    val caluTime = direct match {
      case x if (x.startsWith("-") && x.endsWith("y")) => time.minusYears(v)
      case x if (x.startsWith("-") && x.endsWith("M")) => time.minusMonths(v)
      case x if (x.startsWith("-") && x.endsWith("d")) => time.minusDays(v)
      case x if (x.startsWith("-") && x.endsWith("h")) => time.minusHours(v)
      case x if (x.startsWith("-") && x.endsWith("m")) => time.minusMinutes(v)
      case x if (x.startsWith("-") && x.endsWith("s")) => time.minusSeconds(v)
      case x if (x.startsWith("-") && x.endsWith("w")) => time.minusWeeks(v)
      case x if (x.startsWith("+") && x.endsWith("y")) => time.plusYears(v)
      case x if (x.startsWith("+") && x.endsWith("M")) => time.plusMonths(v)
      case x if (x.startsWith("+") && x.endsWith("d")) => time.plusDays(v)
      case x if (x.startsWith("+") && x.endsWith("h")) => time.plusHours(v)
      case x if (x.startsWith("+") && x.endsWith("m")) => time.plusSeconds(v)
      case x if (x.startsWith("+") && x.endsWith("s")) => time.plusSeconds(v)
      case x if (x.startsWith("+") && x.endsWith("w")) => time.plusWeeks(v)
//      case _ => Exception.invalidTimeOrFormater(formater); time
    }

    if(formater.equals("NONE") || formater.trim.isEmpty)
      caluTime
    else
      caluTime.toString(formater)
  }

  //获取给定日期之后num天的日期
  def getDateAfter(dateStr: String, num: Int): String = {
    val dayFormat = new java.text.SimpleDateFormat("yyyyMMdd")
    val date:Date = dayFormat.parse(dateStr)
    val tmpDate=Calendar.getInstance()
    tmpDate.setTime(date)
    tmpDate.add(Calendar.DAY_OF_MONTH,num)
    dayFormat.format(tmpDate.getTime())
  }

  //计算当前时间距离次日凌晨的时长(毫秒数)
  def resetTime = {
    val now = new Date()
    val tomorrowMidnight = new Date(now.getYear,now.getMonth,now.getDate+1)
    tomorrowMidnight.getTime - now.getTime
  }

  // 计算当前时间距离次日零点的时长（毫秒）
  def resetTime1 = {
    val now = new Date()
    val todayEnd = Calendar.getInstance
    todayEnd.set(Calendar.HOUR_OF_DAY, 23) // Calendar.HOUR 12小时制
    todayEnd.set(Calendar.MINUTE, 59)
    todayEnd.set(Calendar.SECOND, 59)
    todayEnd.set(Calendar.MILLISECOND, 999)
    todayEnd.getTimeInMillis - now.getTime
  }

  def main(args: Array[String]) {
    val startTime="20180101"

    val date:Date = dayFormat.parse(startTime)
    println("date:  "+date)
    println("getDateBefore -3:  "+getDateBefore(date, -3, "DATE"))

    val three=Calendar.getInstance()
    three.setTime(date)
    three.add(Calendar.DAY_OF_MONTH,3)
    println("Calendar add 3:  "+dayFormat.format(three.getTime()))

    println("calculateDate 10days :   "+calculateDate(new DateTime(), "+10d", "yyyyMMdd"))

    println("getDateAfter 3:  "+getDateAfter("20190820",3))

    println("输出起始日期到结束日期之间的每一天：")
    var cycDate=startTime
    while (cycDate.compareTo("20180231") <= 0) {
      cycDate=getDateAfter(cycDate,1)
      println(cycDate)
    }
  }
}
