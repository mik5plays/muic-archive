import java.security.KeyStore.TrustedCertificateEntry
import scala.annotation.tailrec

object DateUtil extends App {
  type Date = (Int, Int, Int)

  def isLeap(y: Int): Boolean = (y % 400 == 0) || (y % 4 == 0 && y % 100 != 0)

  def isOlder(x: Date, y: Date): Boolean = {
    if (y._3 > x._3) { true }
    else if (y._3 == x._3 && y._2 > x._2) { true }
    else if (y._3 == x._3 && y._2 == x._2 && y._1 > x._1) { true }
    else { false }
  }

  def numberInMonth(xs: List[Date], month: Int): Int =
    xs.count{ case (_, m, _) => m == month }

  def numberInMonths(xs: List[Date], months: List[Int]): Int =
    xs.count{ case (_, m, _) => months.contains(m) }

  def datesInMonth(xs: List[Date], month: Int): List[Date] =
    xs.filter(date => date._2 == month)

  def datesInMonths(xs: List[Date], months: List[Int]): List[Date] =
    xs.filter(date => months.contains(date._2))

  def dateToString(d: Date): String = {
    val Months = List("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December")
    d match {
      case (day, month, year) => day.toString + "-" + Months(month - 1) + "-" + year
    }
  }

  def whatMonth(n: Int, yr: Int): Int = {
    val Days = Vector(0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
    val DaysLeap: Vector[Int] = if (isLeap(yr)) {
      Days.updated(2, 29)
    } else {
      Days
    }

    @tailrec
    def findMonth(day: Int, month: Int): Int = {
      val days = DaysLeap(month)
      if (day <= days) {
        month
      } else {
        findMonth(day - days, month + 1)
      }
    }

    findMonth(n, 1)
  }

  def oldest(dates: List[Date]): Option[Date] = {
    if (dates.isEmpty) {
      None
    } else {
      val oldest = dates.tail.foldLeft(dates.head)( (acc, curr) => if (isOlder(curr, acc)) curr else acc )
      Some(oldest)
    }
  }

  def isReasonableDate(d: Date): Boolean = {
    val Days = Vector(0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
    val DaysLeap: Vector[Int] = if (isLeap(d._3)) {
      Days.updated(2, 29)
    } else {
      Days
    }
    val reasonableYear: Boolean = (d._3 > 0) && (d._3 <= 3000)
    val reasonableMonth: Boolean = (d._2 > 0) && (d._2 <= 12)
    val reasonableDay: Boolean = ((d._1 > 0) && (d._1 <= 31)) && (d._1 <= DaysLeap(d._2))
    reasonableDay && reasonableMonth && reasonableYear
  }
}
