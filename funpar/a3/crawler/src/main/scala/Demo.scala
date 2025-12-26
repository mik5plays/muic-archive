import org.jsoup._

object Demo {
  def printLinks(url: String) = {
    import collection.JavaConverters._
    val doc = Jsoup.connect(url).get

    val allLinks = doc.select("a[href]").asScala
                      .map(_.attr("abs:href"))

    allLinks.foreach { lnk =>
        println(lnk)  
    }
  }

  def printText(url: String) = {
    val doc = Jsoup.connect(url).get

    println(doc.text())
  }


  def main(args: Array[String]) = {
    val demoUrl = "https://en.wikipedia.org"
    val demoUrl2 = "https://cs.muic.mahidol.ac.th/courses/ooc/api/"

    printText(demoUrl2)
    printLinks(demoUrl2)
  }
}
