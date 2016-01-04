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


  "BaseValues" should {


    "be inserted" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        DB.withSession{ implicit s =>
          val baseValueId = BaseValues.insert(BaseValue(99, 1, "Verwaltung", 7000, "description1"))
          val Some(baseval) = BaseValues.findById(baseValueId)
          baseval.category must equalTo("Verwaltung")
        }
      }
    }
  }


  "A User" should {

    "be retrieved by profile" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        DB.withSession{ implicit s =>
          val Some(user) = Users.findByProfile("googleplus.com")
          Users.listAll.length must be_==(1)
          user.profile must equalTo("googleplus.com")
        }
      }
    }

    "be inserted" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        DB.withSession{ implicit s =>
          Users.insert(User(99,"twitter.com"))
          val Some(user) = Users.findById(2)
          user.profile must equalTo("twitter.com")
        }
      }
    }

    "be inserted2" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        DB.withSession{ implicit s =>

          val user = Users.findById(2)
          user must equalTo(None)
        }
      }
    }

    "be anonymized" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        DB.withSession{ implicit s =>
          Users.anonymize(1) must be_==(1)
          val Some(user) = Users.findById(1)
          user.profile must be_==("anonymous")
        }
      }
    }

    "be unchangeable" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        DB.withSession{ implicit s =>
          Users.listAll.length must be_==(1)
          Users.findByProfile("facebook.com") must beNone
        }
      }
    }
  }
}