
package views.html.tea

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

object index extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template3[Seq[models.Tea],RequestHeader,Messages,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(teas: Seq[models.Tea])(implicit request: RequestHeader, messages: Messages):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.1*/("""
"""),_display_(/*3.2*/main("お茶一覧")/*3.14*/ {_display_(Seq[Any](format.raw/*3.16*/("""
  """),format.raw/*4.3*/("""<div class="container mx-auto px-4 py-8">
    <div class="flex justify-between items-center mb-8">
      <h1 class="text-3xl font-bold">お茶一覧</h1>
      <a href=""""),_display_(/*7.17*/routes/*7.23*/.TeaController.create()),format.raw/*7.46*/("""" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline">
        新規登録
      </a>
    </div>

    <div class="bg-white shadow-md rounded my-6">
      <table class="min-w-full">
        <thead>
          <tr class="bg-gray-100">
            <th class="px-6 py-3 border-b border-gray-300 text-left text-xs leading-4 font-medium text-gray-500 uppercase tracking-wider">名前</th>
            <th class="px-6 py-3 border-b border-gray-300 text-left text-xs leading-4 font-medium text-gray-500 uppercase tracking-wider">種類</th>
            <th class="px-6 py-3 border-b border-gray-300 text-left text-xs leading-4 font-medium text-gray-500 uppercase tracking-wider">状態</th>
            <th class="px-6 py-3 border-b border-gray-300 text-left text-xs leading-4 font-medium text-gray-500 uppercase tracking-wider">購入日</th>
            <th class="px-6 py-3 border-b border-gray-300 text-left text-xs leading-4 font-medium text-gray-500 uppercase tracking-wider">価格</th>
            <th class="px-6 py-3 border-b border-gray-300 text-left text-xs leading-4 font-medium text-gray-500 uppercase tracking-wider">量</th>
            <th class="px-6 py-3 border-b border-gray-300"></th>
          </tr>
        </thead>
        <tbody>
        """),_display_(/*26.10*/for(tea <- teas) yield /*26.26*/ {_display_(Seq[Any](format.raw/*26.28*/("""
          """),format.raw/*27.11*/("""<tr>
            <td class="px-6 py-4 border-b border-gray-300">"""),_display_(/*28.61*/tea/*28.64*/.name),format.raw/*28.69*/("""</td>
            <td class="px-6 py-4 border-b border-gray-300">"""),_display_(/*29.61*/tea/*29.64*/.teaType.displayName),format.raw/*29.84*/("""</td>
            <td class="px-6 py-4 border-b border-gray-300">"""),_display_(/*30.61*/tea/*30.64*/.status.displayName),format.raw/*30.83*/("""</td>
            <td class="px-6 py-4 border-b border-gray-300">"""),_display_(/*31.61*/tea/*31.64*/.purchaseDate),format.raw/*31.77*/("""</td>
            <td class="px-6 py-4 border-b border-gray-300">"""),_display_(/*32.61*/tea/*32.64*/.price.map(p => s"${p}円").getOrElse("未設定")),format.raw/*32.106*/("""</td>
            <td class="px-6 py-4 border-b border-gray-300">"""),_display_(/*33.61*/tea/*33.64*/.amount.map(a => s"${a}g").getOrElse("未設定")),format.raw/*33.107*/("""</td>
            <td class="px-6 py-4 border-b border-gray-300 text-right">
              <a href=""""),_display_(/*35.25*/routes/*35.31*/.TeaController.show(tea.id.get)),format.raw/*35.62*/("""" class="text-blue-500 hover:text-blue-700">
                詳細
              </a>
            </td>
          </tr>
        """)))}),format.raw/*40.10*/("""
        """),format.raw/*41.9*/("""</tbody>
      </table>
    </div>
  </div>
""")))}),format.raw/*45.2*/(""" """))
      }
    }
  }

  def render(teas:Seq[models.Tea],request:RequestHeader,messages:Messages): play.twirl.api.HtmlFormat.Appendable = apply(teas)(request,messages)

  def f:((Seq[models.Tea]) => (RequestHeader,Messages) => play.twirl.api.HtmlFormat.Appendable) = (teas) => (request,messages) => apply(teas)(request,messages)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  SOURCE: app/views/tea/index.scala.html
                  HASH: ad339b9533b34a2bff5c502b30eba7a54cd3ba9d
                  MATRIX: 968->1|1138->78|1165->80|1185->92|1224->94|1253->97|1441->259|1455->265|1498->288|2814->1577|2846->1593|2886->1595|2925->1606|3017->1671|3029->1674|3055->1679|3148->1745|3160->1748|3201->1768|3294->1834|3306->1837|3346->1856|3439->1922|3451->1925|3485->1938|3578->2004|3590->2007|3654->2049|3747->2115|3759->2118|3824->2161|3952->2262|3967->2268|4019->2299|4176->2425|4212->2434|4287->2479
                  LINES: 28->1|33->2|34->3|34->3|34->3|35->4|38->7|38->7|38->7|57->26|57->26|57->26|58->27|59->28|59->28|59->28|60->29|60->29|60->29|61->30|61->30|61->30|62->31|62->31|62->31|63->32|63->32|63->32|64->33|64->33|64->33|66->35|66->35|66->35|71->40|72->41|76->45
                  -- GENERATED --
              */
          