
package views.html

import _root_.play.twirl.api.TwirlFeatureImports._
import _root_.play.twirl.api.TwirlHelperImports._
import _root_.play.twirl.api.Html
import _root_.play.twirl.api.JavaScript
import _root_.play.twirl.api.Txt
import _root_.play.twirl.api.Xml
import models._
import controllers._
import play.api.i18n._
import views.html._
import play.api.templates.PlayMagic._
import play.api.mvc._
import play.api.data._
import models._
import forms._
import play.api.i18n.Messages
import play.api.mvc.RequestHeader
import play.filters.csrf.CSRF
import play.filters.csrf.CSRF.Token
import java.time.format.DateTimeFormatter

object createTea extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template4[Form[models.Forms.CreateTeaData],RequestHeader,Messages,Flash,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(form: Form[models.Forms.CreateTeaData])(implicit request: RequestHeader, messages: Messages, flash: Flash):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.1*/("""
"""),_display_(/*3.2*/main("お茶を登録")/*3.15*/ {_display_(Seq[Any](format.raw/*3.17*/("""
  """),format.raw/*4.3*/("""<div class="container mx-auto px-4 py-8">
    <h1 class="text-3xl font-bold mb-8">お茶を登録</h1>

    """),_display_(/*7.6*/helper/*7.12*/.form(action = routes.TeaController.save())/*7.55*/ {_display_(Seq[Any](format.raw/*7.57*/("""
      """),_display_(/*8.8*/helper/*8.14*/.CSRF.formField),format.raw/*8.29*/("""

      """),format.raw/*10.7*/("""<div class="mb-4">
        <label class="block text-gray-700 text-sm font-bold mb-2" for="name">
          名前
        </label>
        """),_display_(/*14.10*/helper/*14.16*/.inputText(form("name"), Symbol("class") -> "shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline")),format.raw/*14.185*/("""
        """),_display_(/*15.10*/form("name")/*15.22*/.error.map/*15.32*/ { error =>_display_(Seq[Any](format.raw/*15.43*/("""
          """),format.raw/*16.11*/("""<p class="text-red-500 text-xs italic">"""),_display_(/*16.51*/error/*16.56*/.message),format.raw/*16.64*/("""</p>
        """)))}),format.raw/*17.10*/("""
      """),format.raw/*18.7*/("""</div>

      <div class="mb-4">
        <label class="block text-gray-700 text-sm font-bold mb-2" for="teaType">
          種類
        </label>
        """),_display_(/*24.10*/helper/*24.16*/.select(
          form("teaType"),
          options = TeaType.values.map(t => t.toString -> t.toString).toSeq,
          Symbol("class") -> "shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
        )),format.raw/*28.10*/("""
        """),_display_(/*29.10*/form("teaType")/*29.25*/.error.map/*29.35*/ { error =>_display_(Seq[Any](format.raw/*29.46*/("""
          """),format.raw/*30.11*/("""<p class="text-red-500 text-xs italic">"""),_display_(/*30.51*/error/*30.56*/.message),format.raw/*30.64*/("""</p>
        """)))}),format.raw/*31.10*/("""
      """),format.raw/*32.7*/("""</div>

      <div class="mb-4">
        <label class="block text-gray-700 text-sm font-bold mb-2" for="status">
          状態
        </label>
        """),_display_(/*38.10*/helper/*38.16*/.select(
          form("status"),
          options = TeaStatus.values.map(s => s.toString -> s.toString).toSeq,
          Symbol("class") -> "shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
        )),format.raw/*42.10*/("""
        """),_display_(/*43.10*/form("status")/*43.24*/.error.map/*43.34*/ { error =>_display_(Seq[Any](format.raw/*43.45*/("""
          """),format.raw/*44.11*/("""<p class="text-red-500 text-xs italic">"""),_display_(/*44.51*/error/*44.56*/.message),format.raw/*44.64*/("""</p>
        """)))}),format.raw/*45.10*/("""
      """),format.raw/*46.7*/("""</div>

      <div class="mb-4">
        <label class="block text-gray-700 text-sm font-bold mb-2" for="purchaseDate">
          購入日
        </label>
        """),_display_(/*52.10*/helper/*52.16*/.inputDate(form("purchaseDate"), Symbol("class") -> "shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline")),format.raw/*52.193*/("""
        """),_display_(/*53.10*/form("purchaseDate")/*53.30*/.error.map/*53.40*/ { error =>_display_(Seq[Any](format.raw/*53.51*/("""
          """),format.raw/*54.11*/("""<p class="text-red-500 text-xs italic">"""),_display_(/*54.51*/error/*54.56*/.message),format.raw/*54.64*/("""</p>
        """)))}),format.raw/*55.10*/("""
      """),format.raw/*56.7*/("""</div>

      <div class="mb-4">
        <label class="block text-gray-700 text-sm font-bold mb-2" for="price">
          価格
        </label>
        """),_display_(/*62.10*/helper/*62.16*/.inputText(form("price"), Symbol("class") -> "shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline")),format.raw/*62.186*/("""
        """),_display_(/*63.10*/form("price")/*63.23*/.error.map/*63.33*/ { error =>_display_(Seq[Any](format.raw/*63.44*/("""
          """),format.raw/*64.11*/("""<p class="text-red-500 text-xs italic">"""),_display_(/*64.51*/error/*64.56*/.message),format.raw/*64.64*/("""</p>
        """)))}),format.raw/*65.10*/("""
      """),format.raw/*66.7*/("""</div>

      <div class="mb-4">
        <label class="block text-gray-700 text-sm font-bold mb-2" for="weight">
          重量
        </label>
        """),_display_(/*72.10*/helper/*72.16*/.inputText(form("weight"), Symbol("class") -> "shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline")),format.raw/*72.187*/("""
        """),_display_(/*73.10*/form("weight")/*73.24*/.error.map/*73.34*/ { error =>_display_(Seq[Any](format.raw/*73.45*/("""
          """),format.raw/*74.11*/("""<p class="text-red-500 text-xs italic">"""),_display_(/*74.51*/error/*74.56*/.message),format.raw/*74.64*/("""</p>
        """)))}),format.raw/*75.10*/("""
      """),format.raw/*76.7*/("""</div>

      <div class="mb-4">
        <label class="block text-gray-700 text-sm font-bold mb-2" for="notes">
          メモ
        </label>
        """),_display_(/*82.10*/helper/*82.16*/.textarea(form("notes"), Symbol("class") -> "shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline")),format.raw/*82.185*/("""
        """),_display_(/*83.10*/form("notes")/*83.23*/.error.map/*83.33*/ { error =>_display_(Seq[Any](format.raw/*83.44*/("""
          """),format.raw/*84.11*/("""<p class="text-red-500 text-xs italic">"""),_display_(/*84.51*/error/*84.56*/.message),format.raw/*84.64*/("""</p>
        """)))}),format.raw/*85.10*/("""
      """),format.raw/*86.7*/("""</div>

      <div class="flex items-center justify-between">
        <button class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline" type="submit">
          登録
        </button>
        <a href=""""),_display_(/*92.19*/routes/*92.25*/.TeaController.index()),format.raw/*92.47*/("""" class="text-blue-500 hover:text-blue-700">
          一覧に戻る
        </a>
      </div>
    """)))}),format.raw/*96.6*/("""
  """),format.raw/*97.3*/("""</div>
""")))}),format.raw/*98.2*/(""" """))
      }
    }
  }

  def render(form:Form[models.Forms.CreateTeaData],request:RequestHeader,messages:Messages,flash:Flash): play.twirl.api.HtmlFormat.Appendable = apply(form)(request,messages,flash)

  def f:((Form[models.Forms.CreateTeaData]) => (RequestHeader,Messages,Flash) => play.twirl.api.HtmlFormat.Appendable) = (form) => (request,messages,flash) => apply(form)(request,messages,flash)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  SOURCE: app/views/createTea.scala.html
                  HASH: 2503fad227aabd99c32e5ab33b1c7a5217d744f1
                  MATRIX: 991->1|1192->109|1219->111|1240->124|1279->126|1308->129|1432->228|1446->234|1497->277|1536->279|1569->287|1583->293|1618->308|1653->316|1816->452|1831->458|2022->627|2059->637|2080->649|2099->659|2148->670|2187->681|2254->721|2268->726|2297->734|2342->748|2376->755|2556->908|2571->914|2868->1190|2905->1200|2929->1215|2948->1225|2997->1236|3036->1247|3103->1287|3117->1292|3146->1300|3191->1314|3225->1321|3404->1473|3419->1479|3717->1756|3754->1766|3777->1780|3796->1790|3845->1801|3884->1812|3951->1852|3965->1857|3994->1865|4039->1879|4073->1886|4259->2045|4274->2051|4473->2228|4510->2238|4539->2258|4558->2268|4607->2279|4646->2290|4713->2330|4727->2335|4756->2343|4801->2357|4835->2364|5013->2515|5028->2521|5220->2691|5257->2701|5279->2714|5298->2724|5347->2735|5386->2746|5453->2786|5467->2791|5496->2799|5541->2813|5575->2820|5754->2972|5769->2978|5962->3149|5999->3159|6022->3173|6041->3183|6090->3194|6129->3205|6196->3245|6210->3250|6239->3258|6284->3272|6318->3279|6496->3430|6511->3436|6702->3605|6739->3615|6761->3628|6780->3638|6829->3649|6868->3660|6935->3700|6949->3705|6978->3713|7023->3727|7057->3734|7343->3993|7358->3999|7401->4021|7523->4113|7553->4116|7591->4124
                  LINES: 28->1|33->2|34->3|34->3|34->3|35->4|38->7|38->7|38->7|38->7|39->8|39->8|39->8|41->10|45->14|45->14|45->14|46->15|46->15|46->15|46->15|47->16|47->16|47->16|47->16|48->17|49->18|55->24|55->24|59->28|60->29|60->29|60->29|60->29|61->30|61->30|61->30|61->30|62->31|63->32|69->38|69->38|73->42|74->43|74->43|74->43|74->43|75->44|75->44|75->44|75->44|76->45|77->46|83->52|83->52|83->52|84->53|84->53|84->53|84->53|85->54|85->54|85->54|85->54|86->55|87->56|93->62|93->62|93->62|94->63|94->63|94->63|94->63|95->64|95->64|95->64|95->64|96->65|97->66|103->72|103->72|103->72|104->73|104->73|104->73|104->73|105->74|105->74|105->74|105->74|106->75|107->76|113->82|113->82|113->82|114->83|114->83|114->83|114->83|115->84|115->84|115->84|115->84|116->85|117->86|123->92|123->92|123->92|127->96|128->97|129->98
                  -- GENERATED --
              */
          