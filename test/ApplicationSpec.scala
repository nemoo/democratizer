import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._

class ApplicationSpec extends Specification {

  // -- Date helpers
  
  def dateIs(date: java.util.Date, str: String) = new java.text.SimpleDateFormat("yyyy-MM-dd").format(date) == str
  
  // --
  
  "Application" should {

    "return ok" in {
      
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        val result = controllers.Application.ui(FakeRequest())

        status(result) must equalTo(OK)
      }

    }

  }
  
}