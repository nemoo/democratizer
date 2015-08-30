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


  "Votes" should {

    "be retrieved by Baseline and User" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        DB.withSession{ implicit s =>
          val Some(vote) = Votes.findByBaselineAndUser(1,1)
          Votes.listAll.length must be_==(1)
          vote.baseline must equalTo(1)
          vote.user must equalTo(1)
        }
      }
    }

    "be retrieved by Baseline" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        DB.withSession{ implicit s =>
          val votes = Votes.findByBaseline(1)
          Votes.listAll.length must be_==(1)
          votes must equalTo(List(Votes.findById(1).get))
        }
      }
    }

    "be retrieved by User" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        DB.withSession{ implicit s =>
          val votes = Votes.findByUser(1)
          Votes.listAll.length must be_==(1)
          votes must equalTo(List(Votes.findById(1).get))
        }
      }
    }

    "be inserted" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        DB.withSession{ implicit s =>
          Votes.insert(Vote(99,1,1,new DateTime(2013,8,1,21,44)))
          val Some(vote) = Votes.findById(2)
          vote must equalTo(Votes.findById(2).get)
          Votes.listAll.length must be_==(2)
        }
      }
    }

    "be unchangeable" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        DB.withSession{ implicit s =>
          Votes.listAll.length must be_==(1)
          Votes.findByBaseline(2) must beEmpty
          Votes.findByUser(2) must beEmpty
        }
      }
    }

  }


  "VoteValues" should {

    "be inserted" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        DB.withSession{ implicit s =>
          VoteValues.insert(VoteValue(99,1,1,600))
          val Some(voteval) = VoteValues.findById(4)
          voteval.delta must be_==(600)
        }
      }
    }
    
    "be updated" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        DB.withSession{ implicit s =>
          VoteValues.update(2,-400) must be_==(1)
          val Some(voteval) = VoteValues.findById(2)
          VoteValues.listAll.length must be_==(3)
          voteval.delta must be_==(-400)
        }
      }
    }

  }


}