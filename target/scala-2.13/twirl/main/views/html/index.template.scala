
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

object index extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template4[Seq[Tea],RequestHeader,Messages,Flash,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(teas: Seq[Tea])(implicit request: RequestHeader, messages: Messages, flash: Flash):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {
/*3.2*/import java.time.format.DateTimeFormatter
/*4.2*/import java.util.Locale
/*5.2*/import models.TeaType
/*6.2*/import models.TeaStatus
/*7.2*/import play.filters.csrf.CSRF


Seq[Any](format.raw/*2.1*/("""
"""),format.raw/*8.1*/("""
"""),_display_(/*9.2*/main("お茶一覧")/*9.14*/ {_display_(Seq[Any](format.raw/*9.16*/("""
  """),format.raw/*10.3*/("""<div class="container mx-auto px-4 py-8">
    <div class="flex justify-between items-center mb-8">
      <h1 class="text-3xl font-bold">お茶一覧</h1>
      <a href=""""),_display_(/*13.17*/routes/*13.23*/.TeaController.create()),format.raw/*13.46*/("""" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline">
        新規登録
      </a>
    </div>

    """),_display_(/*18.6*/flash/*18.11*/.get("success").map/*18.30*/ { message =>_display_(Seq[Any](format.raw/*18.43*/("""
      """),format.raw/*19.7*/("""<div class="bg-green-100 border border-green-400 text-green-700 px-4 py-3 rounded relative mb-4" role="alert">
        <span class="block sm:inline">"""),_display_(/*20.40*/message),format.raw/*20.47*/("""</span>
      </div>
    """)))}),format.raw/*22.6*/("""

    """),_display_(/*24.6*/if(flash.get("error").isDefined)/*24.38*/ {_display_(Seq[Any](format.raw/*24.40*/("""
      """),format.raw/*25.7*/("""<div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative mb-4" role="alert">
        <span class="block sm:inline">"""),_display_(/*26.40*/flash/*26.45*/.get("error")),format.raw/*26.58*/("""</span>
      </div>
    """)))}),format.raw/*28.6*/("""

    """),_display_(/*30.6*/if(teas.isEmpty)/*30.22*/ {_display_(Seq[Any](format.raw/*30.24*/("""
      """),format.raw/*31.7*/("""<div class="bg-yellow-100 border-l-4 border-yellow-500 text-yellow-700 p-4" role="alert">
        <p>お茶が登録されていません。新規登録ボタンから登録してください。</p>
      </div>
    """)))}/*34.7*/else/*34.12*/{_display_(Seq[Any](format.raw/*34.13*/("""
      """),format.raw/*35.7*/("""<div class="bg-white shadow-md rounded my-6">
        <table class="min-w-full">
          <thead>
            <tr class="bg-gray-100">
              <th class="px-6 py-3 border-b border-gray-300 text-left text-xs leading-4 font-medium text-gray-500 uppercase tracking-wider">名前</th>
              <th class="px-6 py-3 border-b border-gray-300 text-left text-xs leading-4 font-medium text-gray-500 uppercase tracking-wider">種類</th>
              <th class="px-6 py-3 border-b border-gray-300 text-left text-xs leading-4 font-medium text-gray-500 uppercase tracking-wider">状態</th>
              <th class="px-6 py-3 border-b border-gray-300 text-left text-xs leading-4 font-medium text-gray-500 uppercase tracking-wider">購入日</th>
              <th class="px-6 py-3 border-b border-gray-300"></th>
            </tr>
          </thead>
          <tbody class="bg-white">
          """),_display_(/*47.12*/for(tea <- teas) yield /*47.28*/ {_display_(Seq[Any](format.raw/*47.30*/("""
            """),format.raw/*48.13*/("""<tr>
              <td class="px-6 py-4 border-b border-gray-300">
                <a href=""""),_display_(/*50.27*/routes/*50.33*/.TeaController.show(tea.id.get)),format.raw/*50.64*/("""" class="text-blue-600 hover:text-blue-900">"""),_display_(/*50.109*/tea/*50.112*/.name),format.raw/*50.117*/("""</a>
              </td>
              <td class="px-6 py-4 border-b border-gray-300">"""),_display_(/*52.63*/{TeaType.TeaTypeOps(tea.teaType).displayName}),format.raw/*52.108*/("""</td>
              <td class="px-6 py-4 border-b border-gray-300">"""),_display_(/*53.63*/{TeaStatus.TeaStatusOps(tea.status).displayName}),format.raw/*53.111*/("""</td>
              <td class="px-6 py-4 border-b border-gray-300">"""),_display_(/*54.63*/tea/*54.66*/.purchaseDate.format(DateTimeFormatter.ofPattern("yyyy年MM月dd日", Locale.JAPAN))),format.raw/*54.144*/("""</td>
              <td class="px-6 py-4 border-b border-gray-300 text-right">
                <a href=""""),_display_(/*56.27*/routes/*56.33*/.TeaController.edit(tea.id.get)),format.raw/*56.64*/("""" class="text-blue-600 hover:text-blue-900 mr-4">編集</a>
                """),_display_(/*57.18*/helper/*57.24*/.form(action = routes.TeaController.delete(tea.id.get), Symbol("style") -> "display: inline")/*57.117*/ {_display_(Seq[Any](format.raw/*57.119*/("""
                  """),_display_(/*58.20*/CSRF/*58.24*/.formField),format.raw/*58.34*/("""
                  """),format.raw/*59.19*/("""<button type="submit" class="text-red-600 hover:text-red-900" onclick="return confirm('本当に削除しますか？');">
                    削除
                  </button>
                """)))}),format.raw/*62.18*/("""
              """),format.raw/*63.15*/("""</td>
            </tr>
          """)))}),format.raw/*65.12*/("""
          """),format.raw/*66.11*/("""</tbody>
        </table>
      </div>
    """)))}),format.raw/*69.6*/("""
  """),format.raw/*70.3*/("""</div>
""")))}),format.raw/*71.2*/(""" """))
      }
    }
  }

  def render(teas:Seq[Tea],request:RequestHeader,messages:Messages,flash:Flash): play.twirl.api.HtmlFormat.Appendable = apply(teas)(request,messages,flash)

  def f:((Seq[Tea]) => (RequestHeader,Messages,Flash) => play.twirl.api.HtmlFormat.Appendable) = (teas) => (request,messages,flash) => apply(teas)(request,messages,flash)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  SOURCE: app/views/index.scala.html
                  HASH: be7494093e597a16d685095b3b1f8efad257418f
                  MATRIX: 963->1|1119->87|1168->130|1199->155|1228->178|1259->203|1317->85|1344->233|1371->235|1391->247|1430->249|1460->252|1649->414|1664->420|1708->443|1895->604|1909->609|1937->628|1988->641|2022->648|2199->798|2227->805|2283->831|2316->838|2357->870|2397->872|2431->879|2602->1023|2616->1028|2650->1041|2706->1067|2739->1074|2764->1090|2804->1092|2838->1099|3011->1255|3024->1260|3063->1261|3097->1268|4003->2147|4035->2163|4075->2165|4116->2178|4236->2271|4251->2277|4303->2308|4376->2353|4389->2356|4416->2361|4530->2448|4597->2493|4692->2561|4762->2609|4857->2677|4869->2680|4969->2758|5101->2863|5116->2869|5168->2900|5268->2973|5283->2979|5386->3072|5427->3074|5474->3094|5487->3098|5518->3108|5565->3127|5767->3298|5810->3313|5876->3348|5915->3359|5989->3403|6019->3406|6057->3414
                  LINES: 28->1|31->3|32->4|33->5|34->6|35->7|38->2|39->8|40->9|40->9|40->9|41->10|44->13|44->13|44->13|49->18|49->18|49->18|49->18|50->19|51->20|51->20|53->22|55->24|55->24|55->24|56->25|57->26|57->26|57->26|59->28|61->30|61->30|61->30|62->31|65->34|65->34|65->34|66->35|78->47|78->47|78->47|79->48|81->50|81->50|81->50|81->50|81->50|81->50|83->52|83->52|84->53|84->53|85->54|85->54|85->54|87->56|87->56|87->56|88->57|88->57|88->57|88->57|89->58|89->58|89->58|90->59|93->62|94->63|96->65|97->66|100->69|101->70|102->71
                  -- GENERATED --
              */
          