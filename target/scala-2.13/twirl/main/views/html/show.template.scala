
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

object show extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template4[Tea,RequestHeader,Messages,Flash,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(tea: Tea)(implicit request: RequestHeader, messages: Messages, flash: Flash):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {
/*3.2*/import java.time.format.DateTimeFormatter
/*4.2*/import java.util.Locale


Seq[Any](format.raw/*2.1*/("""
"""),format.raw/*5.1*/("""
"""),_display_(/*6.2*/main(s"${tea.name}の詳細")/*6.25*/ {_display_(Seq[Any](format.raw/*6.27*/("""
  """),format.raw/*7.3*/("""<div class="container mx-auto px-4 py-8">
    <div class="flex justify-between items-center mb-8">
      <h1 class="text-3xl font-bold">"""),_display_(/*9.39*/tea/*9.42*/.name),format.raw/*9.47*/(""" """),format.raw/*9.48*/("""の詳細</h1>
      <div>
        <a href=""""),_display_(/*11.19*/routes/*11.25*/.TeaController.edit(tea.id.get)),format.raw/*11.56*/("""" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline mr-2">
          編集
        </a>
        """),_display_(/*14.10*/helper/*14.16*/.form(action = routes.TeaController.delete(tea.id.get), Symbol("style") -> "display: inline")/*14.109*/ {_display_(Seq[Any](format.raw/*14.111*/("""
          """),_display_(/*15.12*/CSRF/*15.16*/.formField),format.raw/*15.26*/("""
          """),format.raw/*16.11*/("""<button type="submit" class="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline" onclick="return confirm('本当に削除しますか？');">
            削除
          </button>
        """)))}),format.raw/*19.10*/("""
      """),format.raw/*20.7*/("""</div>
    </div>

    """),_display_(/*23.6*/flash/*23.11*/.get("success").map/*23.30*/ { message =>_display_(Seq[Any](format.raw/*23.43*/("""
      """),format.raw/*24.7*/("""<div class="bg-green-100 border border-green-400 text-green-700 px-4 py-3 rounded relative mb-4" role="alert">
        <span class="block sm:inline">"""),_display_(/*25.40*/message),format.raw/*25.47*/("""</span>
      </div>
    """)))}),format.raw/*27.6*/("""

    """),format.raw/*29.5*/("""<div class="bg-white shadow overflow-hidden sm:rounded-lg">
      <div class="px-4 py-5 border-b border-gray-200 sm:px-6">
        <h3 class="text-lg leading-6 font-medium text-gray-900">基本情報</h3>
      </div>
      <div class="px-4 py-5 sm:p-0">
        <dl>
          <div class="sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6 sm:py-5">
            <dt class="text-sm font-medium text-gray-500">種類</dt>
            <dd class="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2">"""),_display_(/*37.75*/tea/*37.78*/.teaType.displayName),format.raw/*37.98*/("""</dd>
          </div>
          <div class="sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6 sm:py-5">
            <dt class="text-sm font-medium text-gray-500">状態</dt>
            <dd class="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2">"""),_display_(/*41.75*/tea/*41.78*/.status.displayName),format.raw/*41.97*/("""</dd>
          </div>
          <div class="sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6 sm:py-5">
            <dt class="text-sm font-medium text-gray-500">購入日</dt>
            <dd class="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2">"""),_display_(/*45.75*/tea/*45.78*/.purchaseDate.format(DateTimeFormatter.ofPattern("yyyy年MM月dd日", Locale.JAPAN))),format.raw/*45.156*/("""</dd>
          </div>
          <div class="sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6 sm:py-5">
            <dt class="text-sm font-medium text-gray-500">価格</dt>
            <dd class="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2">"""),_display_(/*49.75*/tea/*49.78*/.price.map(p => s"${p}円").getOrElse("未設定")),format.raw/*49.120*/("""</dd>
          </div>
          <div class="sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6 sm:py-5">
            <dt class="text-sm font-medium text-gray-500">重量</dt>
            <dd class="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2">"""),_display_(/*53.75*/tea/*53.78*/.weight.map(w => s"${w}g").getOrElse("未設定")),format.raw/*53.121*/("""</dd>
          </div>
          <div class="sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6 sm:py-5">
            <dt class="text-sm font-medium text-gray-500">メモ</dt>
            <dd class="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2">"""),_display_(/*57.75*/tea/*57.78*/.notes.getOrElse("未設定")),format.raw/*57.101*/("""</dd>
          </div>
        </dl>
      </div>
    </div>

    <div class="mt-8">
      <a href=""""),_display_(/*64.17*/routes/*64.23*/.TeaController.index()),format.raw/*64.45*/("""" class="text-blue-600 hover:text-blue-900">
        ← 一覧に戻る
      </a>
    </div>
  </div>
""")))}),format.raw/*69.2*/(""" """))
      }
    }
  }

  def render(tea:Tea,request:RequestHeader,messages:Messages,flash:Flash): play.twirl.api.HtmlFormat.Appendable = apply(tea)(request,messages,flash)

  def f:((Tea) => (RequestHeader,Messages,Flash) => play.twirl.api.HtmlFormat.Appendable) = (tea) => (request,messages,flash) => apply(tea)(request,messages,flash)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  SOURCE: app/views/show.scala.html
                  HASH: 89f6bfe66d9c95c3dadc128c7e5758303a2274d1
                  MATRIX: 957->1|1107->81|1156->124|1208->79|1235->148|1262->150|1293->173|1332->175|1361->178|1524->315|1535->318|1560->323|1588->324|1654->363|1669->369|1721->400|1908->560|1923->566|2026->659|2067->661|2106->673|2119->677|2150->687|2189->698|2442->920|2476->927|2526->951|2540->956|2568->975|2619->988|2653->995|2830->1145|2858->1152|2914->1178|2947->1184|3446->1656|3458->1659|3499->1679|3761->1914|3773->1917|3813->1936|4076->2172|4088->2175|4188->2253|4450->2488|4462->2491|4526->2533|4788->2768|4800->2771|4865->2814|5127->3049|5139->3052|5184->3075|5312->3176|5327->3182|5370->3204|5493->3297
                  LINES: 28->1|31->3|32->4|35->2|36->5|37->6|37->6|37->6|38->7|40->9|40->9|40->9|40->9|42->11|42->11|42->11|45->14|45->14|45->14|45->14|46->15|46->15|46->15|47->16|50->19|51->20|54->23|54->23|54->23|54->23|55->24|56->25|56->25|58->27|60->29|68->37|68->37|68->37|72->41|72->41|72->41|76->45|76->45|76->45|80->49|80->49|80->49|84->53|84->53|84->53|88->57|88->57|88->57|95->64|95->64|95->64|100->69
                  -- GENERATED --
              */
          