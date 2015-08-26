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

    "be retrieved by name" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        DB.withSession{ implicit s =>
          val baseline = Baselines.findByName("aut")
          Baselines.listAll.length must be_==(2)
          baseline must equalTo(List(Baseline(1, "aut", 1000, "Minions ipsum aute jeje daa occaecat wiiiii bappleees. Voluptate reprehenderit tatata bala tu ex bananaaaa poopayee ullamco poopayee sit amet occaecat ullamco. Dolore la bodaaa aliquip tatata bala tu. Esse ut qui sed magna bappleees veniam elit ex reprehenderit pepete. Dolor esse daa hahaha. Jeje incididunt aliquip veniam bappleees voluptate gelatooo daa consequat adipisicing. Consectetur wiiiii chasy eiusmod gelatooo tank yuuu! Hahaha qui tank yuuu! Ullamco commodo ad aliqua ex nostrud occaecat aute uuuhhh.")))
        }
      }
    }

    "be inserted" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        DB.withSession{ implicit s =>
          Baselines.insert(Baseline(99, "de",2000, "super"))
          val Some(baseline) = Baselines.findById(3)
          baseline.name must equalTo("de")
          Baselines.listAll.length must be_==(3)
        }
      }
    }

    "be unchangeable" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        DB.withSession{ implicit s =>
          Baselines.listAll.length must be_==(2)
          Baselines.findByName("ch") must beEmpty
        }
      }
    }

  }


  "BaseValues" should {

    "be retrieved by Baseline" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        DB.withSession{ implicit s =>
          val baseval = BaseValues.findByBaseline(1)
          BaseValues.listAll.length must be_==(3)
          baseval must equalTo(List(
            BaseValue(1, 1, "Soziales", 500, "des"),
            BaseValue(2, 1, "Rüstung", 300, "descr"),
            BaseValue(3, 1, "Wirtschaft", 4000, "descrip")))
        }
      }
    }

    "be inserted" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        DB.withSession{ implicit s =>
          BaseValues.insert(BaseValue(99, 1, "Verwaltung", 7000, "description1"))
          val Some(baseval) = BaseValues.findById(4)
          baseval.category must equalTo("Verwaltung")
          BaseValues.listAll.length must be_==(4)
        }
      }
    }

    "be unchangeable" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        DB.withSession{ implicit s =>
          BaseValues.listAll.length must be_==(3)
          BaseValues.findByBaseline(2) must beEmpty
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
          Users.listAll.length must be_==(2)
        }
      }
    }

    "be anonymized" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        DB.withSession{ implicit s =>
          Users.anonymize(1) must be_==(1)
          val Some(user) = Users.findById(1)
          Users.listAll.length must be_==(1)
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

    "be retrieved by Basevalue and Vote" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        DB.withSession{ implicit s =>
          val Some(voteval) = VoteValues.findByBaseValueAndVote(3,1)
          VoteValues.listAll.length must be_==(3)
          voteval.delta must be_==(-300)
        }
      }
    }

    "be retrieved by BaseValue" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        DB.withSession{ implicit s =>
          val votevals = VoteValues.findByBaseValue(2)
          VoteValues.listAll.length must be_==(3)
          votevals must equalTo(List(VoteValue(2,2,1,-200)))
        }
      }
    }

    "be retrieved by Vote" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        DB.withSession{ implicit s =>
          val votevals = VoteValues.findByVote(1)
          VoteValues.listAll.length must be_==(3)
          votevals must equalTo(List(
            VoteValue(1,1,1,20),
            VoteValue(2,2,1,-200),
            VoteValue(3,3,1,-300)))

        }
      }
    }

    "be inserted" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        DB.withSession{ implicit s =>
          VoteValues.insert(VoteValue(99,1,1,600))
          val Some(voteval) = VoteValues.findById(4)
          voteval.delta must be_==(600)
          VoteValues.listAll.length must be_==(4)
        }
      }
    }

    "be unchangeable" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        DB.withSession{ implicit s =>
          VoteValues.listAll.length must be_==(3)
          VoteValues.findByBaseValue(4) must beEmpty
          VoteValues.findByVote(2) must beEmpty
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