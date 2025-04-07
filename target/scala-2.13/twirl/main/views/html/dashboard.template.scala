
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

object dashboard extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template5[models.User,Seq[models.Tea],RequestHeader,Messages,Flash,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(user: models.User, teas: Seq[models.Tea])(implicit request: RequestHeader, messages: Messages, flash: Flash):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {
/*3.2*/import java.time.format.DateTimeFormatter
/*4.2*/import java.util.Locale


Seq[Any](format.raw/*2.1*/("""
"""),format.raw/*5.1*/("""
"""),_display_(/*6.2*/main("ダッシュボード")/*6.17*/ {_display_(Seq[Any](format.raw/*6.19*/("""
  """),format.raw/*7.3*/("""<div class="container mx-auto px-4 py-8">
    <h1 class="text-3xl font-bold mb-8">お茶一覧</h1>

    """),_display_(/*10.6*/flash/*10.11*/.get("success").map/*10.30*/ { message =>_display_(Seq[Any](format.raw/*10.43*/("""
      """),format.raw/*11.7*/("""<div class="bg-green-100 border border-green-400 text-green-700 px-4 py-3 rounded relative mb-4" role="alert">
        <span class="block sm:inline">"""),_display_(/*12.40*/message),format.raw/*12.47*/("""</span>
      </div>
    """)))}),format.raw/*14.6*/("""

    """),format.raw/*16.5*/("""<div class="mb-4">
      <a href=""""),_display_(/*17.17*/routes/*17.23*/.TeaController.create()),format.raw/*17.46*/("""" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">
        新規登録
      </a>
    </div>

    <div class="bg-white shadow-md rounded my-6">
      <table class="min-w-full">
        <thead>
          <tr class="bg-gray-100">
            <th class="text-left py-3 px-4 font-semibold text-gray-600">名前</th>
            <th class="text-left py-3 px-4 font-semibold text-gray-600">種類</th>
            <th class="text-left py-3 px-4 font-semibold text-gray-600">状態</th>
            <th class="text-left py-3 px-4 font-semibold text-gray-600">購入日</th>
            <th class="text-left py-3 px-4 font-semibold text-gray-600">価格</th>
            <th class="text-left py-3 px-4 font-semibold text-gray-600">重量</th>
            <th class="text-left py-3 px-4 font-semibold text-gray-600">操作</th>
          </tr>
        </thead>
        <tbody>
        """),_display_(/*36.10*/for(tea <- teas) yield /*36.26*/ {_display_(Seq[Any](format.raw/*36.28*/("""
          """),format.raw/*37.11*/("""<tr class="border-b hover:bg-gray-50">
            <td class="py-3 px-4">"""),_display_(/*38.36*/tea/*38.39*/.name),format.raw/*38.44*/("""</td>
            <td class="py-3 px-4">"""),_display_(/*39.36*/tea/*39.39*/.teaType.toString),format.raw/*39.56*/("""</td>
            <td class="py-3 px-4">"""),_display_(/*40.36*/tea/*40.39*/.status.toString),format.raw/*40.55*/("""</td>
            <td class="py-3 px-4">"""),_display_(/*41.36*/tea/*41.39*/.purchaseDate.format(DateTimeFormatter.ofPattern("yyyy年MM月dd日", Locale.JAPAN))),format.raw/*41.117*/("""</td>
            <td class="py-3 px-4">"""),_display_(/*42.36*/tea/*42.39*/.price.map(_.toString + "円").getOrElse("未設定")),format.raw/*42.84*/("""</td>
            <td class="py-3 px-4">"""),_display_(/*43.36*/tea/*43.39*/.weight.map(_.toString + "g").getOrElse("未設定")),format.raw/*43.85*/("""</td>
            <td class="py-3 px-4">
              <a href=""""),_display_(/*45.25*/routes/*45.31*/.TeaController.show(tea.id.get)),format.raw/*45.62*/("""" class="text-blue-500 hover:text-blue-700 mr-4">
                詳細
              </a>
              <a href=""""),_display_(/*48.25*/routes/*48.31*/.TeaController.edit(tea.id.get)),format.raw/*48.62*/("""" class="text-green-500 hover:text-green-700 mr-4">
                編集
              </a>
              """),_display_(/*51.16*/helper/*51.22*/.form(action = routes.TeaController.delete(tea.id.get), Symbol("style") -> "display: inline;")/*51.116*/ {_display_(Seq[Any](format.raw/*51.118*/("""
                """),_display_(/*52.18*/helper/*52.24*/.CSRF.formField),format.raw/*52.39*/("""
                """),format.raw/*53.17*/("""<button type="submit" class="text-red-500 hover:text-red-700" onclick="return confirm('本当に削除しますか？');">
                  削除
                </button>
              """)))}),format.raw/*56.16*/("""
            """),format.raw/*57.13*/("""</td>
          </tr>
        """)))}),format.raw/*59.10*/("""
        """),format.raw/*60.9*/("""</tbody>
      </table>
    </div>
  </div>
""")))}))
      }
    }
  }

  def render(user:models.User,teas:Seq[models.Tea],request:RequestHeader,messages:Messages,flash:Flash): play.twirl.api.HtmlFormat.Appendable = apply(user,teas)(request,messages,flash)

  def f:((models.User,Seq[models.Tea]) => (RequestHeader,Messages,Flash) => play.twirl.api.HtmlFormat.Appendable) = (user,teas) => (request,messages,flash) => apply(user,teas)(request,messages,flash)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  SOURCE: app/views/dashboard.scala.html
                  HASH: 89ff569c0ca15257762748eb09620d06f11c7e20
                  MATRIX: 986->1|1168->113|1217->156|1269->111|1296->180|1323->182|1346->197|1385->199|1414->202|1538->300|1552->305|1580->324|1631->337|1665->344|1842->494|1870->501|1926->527|1959->533|2021->568|2036->574|2080->597|2976->1466|3008->1482|3048->1484|3087->1495|3188->1569|3200->1572|3226->1577|3294->1618|3306->1621|3344->1638|3412->1679|3424->1682|3461->1698|3529->1739|3541->1742|3641->1820|3709->1861|3721->1864|3787->1909|3855->1950|3867->1953|3934->1999|4026->2064|4041->2070|4093->2101|4232->2213|4247->2219|4299->2250|4431->2355|4446->2361|4550->2455|4591->2457|4636->2475|4651->2481|4687->2496|4732->2513|4928->2678|4969->2691|5031->2722|5067->2731
                  LINES: 28->1|31->3|32->4|35->2|36->5|37->6|37->6|37->6|38->7|41->10|41->10|41->10|41->10|42->11|43->12|43->12|45->14|47->16|48->17|48->17|48->17|67->36|67->36|67->36|68->37|69->38|69->38|69->38|70->39|70->39|70->39|71->40|71->40|71->40|72->41|72->41|72->41|73->42|73->42|73->42|74->43|74->43|74->43|76->45|76->45|76->45|79->48|79->48|79->48|82->51|82->51|82->51|82->51|83->52|83->52|83->52|84->53|87->56|88->57|90->59|91->60
                  -- GENERATED --
              */
          