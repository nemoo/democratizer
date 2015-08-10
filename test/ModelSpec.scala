package test

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
  
  "An Item" should {
    
    "be retrieved by color" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        DB.withSession{ implicit s =>
          val Some(task) = Tasks.findByColor("blue")
          Tasks.count must be_==(5)
          task.color must equalTo("blue")            
        }        
      }
    }
    
    "be inserted" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        DB.withSession{ implicit s =>
          Tasks.insert(Task(99,"black", 1))
          val Some(item) = Tasks.findByColor("black")      
          item.color must equalTo("black")  
          Tasks.count must be_==(6)
        }
      }
    }
    
    
    "be unchangeable" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        DB.withSession{ implicit s =>
          Tasks.count must be_==(5)
          Tasks.findByColor("cyan") must beNone      
        }
      }
    }   
    
    "be selectable distinct" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        DB.withSession{ implicit s =>
          val results = Tasks.distinctTest
          results.map(x => println(x.name))
          results must have size(1)      
        }
      }
    }       
    
  }


  "Baselines" should {

    "be retrieved by name" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        DB.withSession{ implicit s =>
          val baseline = Baselines.findByName("aut")
          Baselines.listAll.length must be_==(1)
          baseline must equalTo(List(Baseline(1, "aut", 1000, "wow")))
        }
      }
    }

    "be inserted" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        DB.withSession{ implicit s =>
          Baselines.insert(Baseline(99, "de",2000, "super"))
          val Some(baseline) = Baselines.findById(2)
          baseline.name must equalTo("de")
          Baselines.listAll.length must be_==(2)
        }
      }
    }

    "be unchangeable" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        DB.withSession{ implicit s =>
          Baselines.listAll.length must be_==(1)
          Baselines.findByName("ch") must beEmpty
        }
      }
    }

    /*
    "be selectable distinct" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        DB.withSession{ implicit s =>
          val results = Tasks.distinctTest
          results.map(x => println(x.name))
          results must have size(1)
        }
      }
    }*/

  }


  "BaseValues" should {

    "be retrieved by Baseline" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        DB.withSession{ implicit s =>
          val baseval = BaseValues.findByBaseline(1)
          BaseValues.listAll.length must be_==(3)
          baseval must equalTo(List(
            BaseValue(1, 1, "Soziales", 500),
            BaseValue(2, 1, "Rüstung", 300),
            BaseValue(3, 1, "Wirtschaft", 4000)))
        }
      }
    }

    "be inserted" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        DB.withSession{ implicit s =>
          BaseValues.insert(BaseValue(99, 1, "Verwaltung", 7000))
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

    /*
    "be selectable distinct" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        DB.withSession{ implicit s =>
          val results = Tasks.distinctTest
          results.map(x => println(x.name))
          results must have size(1)
        }
      }
    }*/

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

    /*
    "be selectable distinct" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        DB.withSession{ implicit s =>
          val results = Tasks.distinctTest
          results.map(x => println(x.name))
          results must have size(1)
        }
      }
    }*/

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
          votes must equalTo(List(Vote(1,1,1,new DateTime(2014,12,3,21,4))))
        }
      }
    }

    "be retrieved by User" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        DB.withSession{ implicit s =>
          val votes = Votes.findByUser(1)
          Votes.listAll.length must be_==(1)
          votes must equalTo(List(Vote(1,1,1,new DateTime(2014,12,3,21,4))))
        }
      }
    }

    "be inserted" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        DB.withSession{ implicit s =>
          Votes.insert(Vote(99,1,1,new DateTime(2013,8,1,21,44)))
          val Some(vote) = Votes.findById(2)
          vote.timestamp must equalTo(new DateTime(2013,8,1,21,44))
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

    /*
    "be selectable distinct" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        DB.withSession{ implicit s =>
          val results = Tasks.distinctTest
          results.map(x => println(x.name))
          results must have size(1)
        }
      }
    }*/

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

    /*
    "be selectable distinct" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        DB.withSession{ implicit s =>
          val results = Tasks.distinctTest
          results.map(x => println(x.name))
          results must have size(1)
        }
      }
    }*/

  }


}