import org.specs2.mutable._
import play.api.Play.current
import play.api.db.slick.DB
import play.api.test.Helpers._
import play.api.test._

class IsolationSpec extends Specification {
  
  import models._

  "An item " should {

    "be inserted during the first test case" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        DB.withSession{ implicit s =>
          val baselineId = Baselines.insert(Baseline(99,  "de",2000, "super"))
          val Some(baseline) = Baselines.findById(baselineId)
          baseline.name must equalTo("de")
        }
      }
    }

    "and not exist in the second test case" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        DB.withSession{ implicit s =>

          val baseline = Baselines.findByName("de")
          baseline must have size (0)
        }
      }
    }
  }

}