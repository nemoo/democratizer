import java.text.SimpleDateFormat
import org.joda.time.DateTime
import play.api._
import models._
import play.api.db.slick._
import play.api.Play.current

object Global extends GlobalSettings {

  override def onStart(app: Application) {
    InitialData.insert()
  }

}

/**
 * Initial set of data to be imported
 * in the sample application.
 */
object InitialData {

  val sdf = new SimpleDateFormat("yyyy-MM-dd")

  def insert() {
    DB.withSession { implicit s: Session =>

      if (Baselines.listAll.isEmpty && BaseValues.listAll.isEmpty && Users.listAll.isEmpty && Votes.listAll.isEmpty && VoteValues.listAll.isEmpty) {

        val result =
          """
1	ALLGEMEINE ÖFFENTLICHE VERWALTUNG	22.703	22.963	23.34	22.652
1.1	Oberste Regierungs- und Verwaltungsstellen und gesetzgebende Organe, Finanz- und Steuerverwaltung, auswärtige Angelegenheiten	7.367	7.662	8.143	7.544
1.2	Wirtschaftshilfe für das Ausland	389	420	457	474
1.3	Allgemeine Dienste	3.871	3.844	3.858	3.955
1.4	Grundlagenforschung	1.447	1.431	1.548	1.594
1.5	Angewandte Forschung und experimentelle Entwicklung im Bereich allgemeine öffentliche Verwaltung	2	1	0	0
1.6	Allgemeine öffentliche Verwaltung, a.n.g.	99	103	141	132
1.7	Staatsschuldentransaktionen	9.281	9.346	8.995	8.716
1.8	Allgemeine Transfers zwischen verschiedenen staatlichen Ebenen	246	155	197	236
2	VERTEIDIGUNG	1.908	1.872	2.004	1.911
2.1	Militärische Verteidigung	1.749	1.709	1.83	1.73
2.2	Zivile Verteidigung	6	9	7	10
2.3	Militärische Hilfe für das Ausland	-	-	-	-
2.4	Angewandte Forschung und experimentelle Entwicklung im Bereich Verteidigung	2	2	1	1
2.5	Verteidigung, a.n.g.	150	152	167	170
3	ÖFFENTLICHE ORDNUNG UND SICHERHEIT	4.056	4.208	4.219	4.393
3.1	Polizei	2.071	2.126	2.123	2.197
3.2	Feuerwehr	513	551	555	569
3.3	Gerichte	833	869	886	971
3.4	Strafvollzug	400	423	424	436
3.5	Angewandte Forschung und experimentelle Entwicklung im Bereich öffentliche Ordnung und Sicherheit	59	60	62	63
3.6	Öffentliche Ordnung und Sicherheit, a.n.g.	180	179	169	157
4	WIRTSCHAFTLICHE ANGELEGENHEITEN	18.933	19.552	18.204	23.35
4.1	Allgemeine Angelegenheiten der Wirtschaft und des Arbeitsmarktes	4.953	5.816	6.058	8.936
4.2	Land- und Forstwirtschaft, Fischerei und Jagd	1.289	1.16	1.209	1.211
4.3	Brennstoffe und Energie	55	36	19	14
4.4	Bergbau, Herstellung von Waren und Bauwesen	66	64	86	36
4.5	Verkehr	9.454	9.096	9.693	9.767
4.6	Nachrichtenübermittlung	147	128	-1.891	136
4.7	Andere Wirtschaftsbereiche	533	528	526	560
4.8	Angewandte Forschung und experimentelle Entwicklung im Bereich wirtschaftliche Angelegenheiten	2.372	2.657	2.432	2.618
4.9	Wirtschaftliche Angelegenheiten, a.n.g.	65	68	72	72
5	UMWELTSCHUTZ	1.473	1.535	1.572	1.574
5.1	Abfallwirtschaft	154	151	150	163
5.2	Abwasserwirtschaft	389	414	410	403
5.3	Beseitigung von Umweltverunreinigungen	615	651	701	676
5.4	Arten- und Landschaftsschutz	62	54	53	61
5.5	Angewandte Forschung und experimentelle Entwicklung im Bereich Umweltschutz	81	81	81	86
5.6	Umweltschutz, a.n.g.	173	184	176	186
6	WOHNUNGSWESEN UND KOMMUNALE GEMEINSCHAFTSDIENSTE	1.241	1.227	1.174	1.238
6.1	Wohnungswesen	790	756	697	732
6.2	Raumplanung	175	175	169	184
6.3	Wasserversorgung	64	69	69	69
6.4	Straßenbeleuchtung	171	185	196	210
6.5	Angewandte Forschung und experimentelle Entwicklung im Bereich Wohnungswesen und kommunale Gemeinschaftsdienste	41	41	43	44
6.6	Wohnungswesen und kommunale Gemeinschaftsdienste, a.n.g.	-	-	-	-
7	GESUNDHEITSWESEN	24.033	24.755	25.345	26.096
7.1	Medizinische Erzeugnisse, Geräte und Ausrüstungen	3.35	3.426	3.469	3.672
7.2	Ambulante Behandlung	4.362	4.511	4.705	4.831
7.3	Stationäre Behandlung	13.333	13.717	14.032	14.359
7.4	Öffentlicher Gesundheitsdienst	472	508	520	557
7.5	Angewandte Forschung und experimentelle Entwicklung im Bereich Gesundheitswesen	1.585	1.599	1.634	1.674
7.6	Gesundheitswesen, a.n.g.	932	995	985	1.004
8	FREIZEITGESTALTUNG, SPORT, KULTUR UND RELIGION	2.889	2.915	2.955	3.004
8.1	Freizeitgestaltung und Sport	828	852	849	863
8.2	Kultur	1.768	1.805	1.811	1.856
8.3	Rundfunk- und Verlagswesen	95	66	78	47
8.4	Religiöse und andere Gemeinschaftsangelegenheiten	130	122	157	169
8.5	Angewandte Forschung und experimentelle Entwicklung im Bereich Freizeitgestaltung, Sport, Kultur und Religion	67	67	57	58
8.6	Freizeitgestaltung, Sport, Kultur und Religion, a.n.g.	1	2	2	11
9	BILDUNGSWESEN	15.437	15.883	16.278	16.426
9.1	Elementar- und Primärbereich	4.233	4.31	4.452	4.613
9.2	Sekundarbereich	7.078	7.232	7.354	7.344
9.3	Post-sekundarer, nicht-tertiärer Bereich	45	46	44	36
9.4	Tertiärbereich	2.327	2.547	2.707	2.579
9.5	Nicht-zuordenbares Bildungswesen	693	727	724	733
9.6	Hilfsdienstleistungen für das Bildungswesen	655	686	696	744
9.7	Angewandte Forschung und experimentelle Entwicklung im Bereich Bildungswesen	108	117	111	113
9.8	Bildungswesen, a.n.g.	297	218	191	265
10	SOZIALE SICHERUNG	64.133	66.557	68.961	71.293
10.1	Krankheit und Erwerbsunfähigkeit	6.125	6.26	6.42	6.558
10.2	Alter	38.52	40.292	41.848	43.535
10.3	Hinterbliebene	4.622	4.758	4.867	4.96
10.4	Familien und Kinder	7.506	7.554	7.658	7.68
10.5	Arbeitslosigkeit	3.97	4.159	4.544	4.758
10.6	Wohnraum	262	235	213	206
10.7	Soziale Hilfe, a.n.g.	2.437	2.654	2.772	2.95
10.8	Angewandte Forschung und experimentelle Entwicklung im Bereich Soziale Sicherung	39	40	40	41
10.9	Soziale Sicherung, a.n.g.	654	605	599	605
          """.split('\n')
          .toList
          .map{string =>
            string.split("\t").toList}

        val rawData: List[(String, Int)] = result.map{list =>
          list match {
            case List(_,category: String,_,_,_,lastyear: String) =>
              val amount = lastyear.trim
                .replace("-","0")
                .replace(".","")
                .toInt
              (category, amount)
            case _ => ("none", 0)
          }
        }


        val base1 = Baselines.insert(Baseline(0, "Staatsausgaben Österreich 2014", 171936000, ""))
        rawData.foreach{x =>
          BaseValues.insert(BaseValue(0,base1,x._1,x._2,""))}

        val base2 = Baselines.insert(Baseline(0, "Österreich 2001", 1000, "Minions ipsum hahaha bappleees chasy wiiiii jeje. Po kass bananaaaa para tú me want bananaaa! Poulet tikka masala tank yuuu! Me want bananaaa! baboiii chasy tatata bala tu wiiiii wiiiii jeje tatata bala tu. Para tú me want bananaaa! Para tú ti aamoo! Hana dul sae hahaha hana dul sae la bodaaa ti aamoo! Poulet tikka masala. Gelatooo gelatooo hana dul sae ti aamoo! Gelatooo tulaliloo jeje. Po kass underweaaar tank yuuu! Jeje. Underweaaar tank yuuu! Jiji belloo! Bee do bee do bee do tank yuuu! Belloo! Butt pepete. Pepete uuuhhh belloo! Bananaaaa poopayee uuuhhh. Uuuhhh wiiiii butt baboiii hahaha jeje poopayee ti aamoo! Tulaliloo la bodaaa. "))


        val baseval1 = BaseValues.insert(BaseValue(0, base1, "Soziales", 500, "des"))
        val baseval2 = BaseValues.insert(BaseValue(0, base1, "Rüstung", 300, "descr"))
        val baseval3 = BaseValues.insert(BaseValue(0, base1, "Wirtschaft", 4000, "descrip"))

        val user1 = Users.insert(User(0,"googleplus.com"))
        val vote1 = Votes.insert(Vote(0,base1,user1,new DateTime(2014,12,3,21,4)))

        Seq(
          VoteValue(0,baseval1,vote1,20),
          VoteValue(0,baseval2,vote1,-200),
          VoteValue(0,baseval3,vote1,-300)).foreach(VoteValues.insert)

      }
    }
  }
}