import scala.concurrent.Future
import scala.concurrent.Promise
import scala.concurrent.ExecutionContext.Implicits.global

object DataCrunch {

  trait DataProvider {
    def get(onSuccess: Seq[String] => Unit,
            onFailure: () => Unit): Unit
  }


  object LoremIpsum extends DataProvider {
    override def get(onSuccess: Seq[String] => Unit,
            onFailure: () => Unit): Unit = {
      val lorem =
        """Lorem ipsum dolor sit amet, consectetur adipiscing elit.
        Cras nec sagittis justo. Nullam dignissim ultricies velit a tempus.
        Aenean pharetra semper elit eu luctus. Fusce maximus lacus eget magna
        ultricies, ac suscipit justo lobortis. Nullam pellentesque lectus
        at tellus gravida rhoncus. Nam augue tortor, rutrum et eleifend id,
        luctus ut turpis. Vivamus nec sodales augue.

        Suspendisse non erat diam. Mauris hendrerit neque at sem laoreet
          vehicula. Sed aliquam urna a efficitur tincidunt. In non purus
        tincidunt, molestie velit vulputate, mollis nisl. Pellentesque
        rhoncus molestie bibendum. Etiam sit amet felis a orci fermentum
        tempor. Duis ante lacus, luctus ac scelerisque eget, viverra ut felis."""
      onSuccess(lorem.split("\n"))
    }
  }

  object FailedSample extends DataProvider {
    override def get(onSuccess: Seq[String] => Unit,
                     onFailure: () => Unit): Unit = {
      onFailure()
    }
  }

  // This returns a DataProvider that feeds the "consumer" all the lines from a
  // file indicated by filename.
  def FileSource(filename: String): DataProvider = new DataProvider {
    override def get(onSuccess: Seq[String] => Unit, onFailure: () => Unit): Unit = {
      try {
        val lines = io.Source.fromFile(filename)
          .getLines
          .toVector
        onSuccess(lines)
      }
      catch {
        case _: Throwable => onFailure()
      }
    }
  }

  def dataProviderFuture(dp: DataProvider): Future[Seq[String]] = {
    val promise = Promise[Seq[String]]()

    dp.get(
      data => promise.success(data), // the result of dp.get()
      () => promise.failure(new Exception("Failed")) // otherwise, throw an exception
    )

    promise.future

  }

  def highestFreq(linesFut: Future[Seq[String]]): Future[(String, Double)] = {
    def topFreq(xs: Seq[String]): (String, Int) = {
      xs.groupBy(identity)
        .view.mapValues(_.size).toMap
        .toList
        .maxBy { case (_, freq) => freq }
    }

    linesFut.map { lines =>
      val allWords = lines.flatMap(_.split("\\s+").filter(_.nonEmpty))
      val (mostFrequentWord, frequency) = topFreq(allWords.toList)
      (mostFrequentWord, (frequency.toDouble / allWords.length))
    }
  }

  def main(args: Array[String]) = {
    // Example of how DataProvider is typically used. Comment out the block of code
    // below so it doesn't print some random garbage.

//    def funcOnSuccess(lines: Seq[String]) = lines.foreach(println(_))
//    def funcOnFailure() = println("Failed")
//    val sampleProvider = LoremIpsum

    val test1 = highestFreq(dataProviderFuture(FileSource("testLorem")))
    val test2 = highestFreq(dataProviderFuture(FileSource("testMyOwn")))
    val test3 = highestFreq(dataProviderFuture(FileSource("testFail"))) // a file that does not exist

    test1.onComplete {
      case scala.util.Success((w, f)) => println(s"Word: $w, Popularity Function: $f")
      case scala.util.Failure(e) => println(e.getMessage)
    }

    test2.onComplete {
      case scala.util.Success((w, f)) => println(s"Word: $w, Popularity Function: $f")
      case scala.util.Failure(e) => println(e.getMessage)
    }

    test3.onComplete {
      case scala.util.Success((w, f)) => println(s"Word: $w, Popularity Function: $f")
      case scala.util.Failure(e) => println(e.getMessage)
    }

  }

}
