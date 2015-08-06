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


  "A Baseline" should {

    "be retrieved by name" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        DB.withSession{ implicit s =>
          val Some(baseline) = Baselines.findByName("aut")
          Baselines.all.length must be_==(1)
          baseline.name must equalTo("aut")
        }
      }
    }

    "be retrieved by year" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        DB.withSession{ implicit s =>
          val Some(baseline) = Baselines.findByYear(new DateTime(2014, 12, 4, 0, 0))
          Baselines.all.length must be_==(1)
          baseline.year must equalTo(new DateTime(2014, 12, 4, 0, 0))
        }
      }
    }

    "be inserted" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {

        DB.withSession{ implicit s =>
          Baselines.insert(Baseline(99, "de", new DateTime(2013, 5, 2, 0, 0), "super"))

          val Some(baseline) = Baselines.findById(2)
          baseline.name must equalTo("de")
          Baselines.all.length must be_==(2)
        }
      }
    }


    "be unchangeable" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {

        DB.withSession{ implicit s =>
          Baselines.all.length must be_==(1)
          Baselines.findByName("ch") must beNone
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


  "A BaseValue" should {

    "be retrieved by Baseline" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        DB.withSession{ implicit s =>
          val Some(baseval) = BaseValues.findByBaseline(1)
          BaseValues.all.length must be_==(3)
          baseval.category must equalTo("Soziales")
        }
      }
    }

    "be inserted" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {

        DB.withSession{ implicit s =>
          BaseValues.insert(BaseValue(99, 1, "Verwaltung", 7000))

          val Some(baseval) = BaseValues.findById(4)
          baseval.category must equalTo("Verwaltung")
          BaseValues.all.length must be_==(4)
        }
      }
    }

    "be unchangeable" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {

        DB.withSession{ implicit s =>
          BaseValues.all.length must be_==(3)
          BaseValues.findByBaseline(2) must beNone
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