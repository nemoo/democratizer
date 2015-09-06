import org.joda.time.DateTime
import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._
import play.api.db.slick.DB
import play.api.Play.current

class ModelSpec extends Specification {
  
  import models._

  // -- Date helpers
  
  def dateIs(date: java.util.Date, str: String) = new java.text.SimpleDateFormat("yyyy-MM-dd").format(date) == str
  
  // --

  "Baselines" should {

    "be inserted" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        DB.withSession{ implicit s =>
          val baselineId = Baselines.insert(Baseline(99,  "de",2000, "super"))
          val Some(baseline) = Baselines.findById(baselineId)
          baseline.name must equalTo("de")
        }
      }
    }

    "be retrieved by name" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        DB.withSession{ implicit s =>
          Baselines.insert(Baseline(99,  "dexx",2000, "super"))
          val baseline = Baselines.findByName("dexx")
          baseline.head.description must equalTo("super")
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