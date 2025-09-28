
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

object password extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template4[Form[models.Forms.PasswordUpdateData],RequestHeader,Messages,Flash,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(form: Form[models.Forms.PasswordUpdateData])(implicit request: RequestHeader, messages: Messages, flash: Flash):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.1*/("""
"""),_display_(/*3.2*/main("パスワード変更")/*3.17*/ {_display_(Seq[Any](format.raw/*3.19*/("""
  """),format.raw/*4.3*/("""<div class="container mx-auto px-4 py-8">
    <div class="max-w-md mx-auto bg-white rounded-lg shadow-md p-6">
      <h1 class="text-2xl font-bold mb-6">パスワード変更</h1>

      """),_display_(/*8.8*/if(flash.get("success").isDefined)/*8.42*/ {_display_(Seq[Any](format.raw/*8.44*/("""
        """),format.raw/*9.9*/("""<div class="bg-green-100 border border-green-400 text-green-700 px-4 py-3 rounded mb-4">
          """),_display_(/*10.12*/flash/*10.17*/.get("success")),format.raw/*10.32*/("""
        """),format.raw/*11.9*/("""</div>
      """)))}),format.raw/*12.8*/("""

      """),_display_(/*14.8*/if(form.hasGlobalErrors)/*14.32*/ {_display_(Seq[Any](format.raw/*14.34*/("""
        """),format.raw/*15.9*/("""<div class="bg-red-100 border-l-4 border-red-500 text-red-700 p-4 mb-4" role="alert">
          """),_display_(/*16.12*/for(error <- form.globalErrors) yield /*16.43*/ {_display_(Seq[Any](format.raw/*16.45*/("""
            """),format.raw/*17.13*/("""<p>"""),_display_(/*17.17*/error/*17.22*/.message),format.raw/*17.30*/("""</p>
          """)))}),format.raw/*18.12*/("""
        """),format.raw/*19.9*/("""</div>
      """)))}),format.raw/*20.8*/("""

      """),_display_(/*22.8*/helper/*22.14*/.form(action = routes.TeaController.updatePassword(), Symbol("class") -> "form")/*22.94*/ {_display_(Seq[Any](format.raw/*22.96*/("""
        """),_display_(/*23.10*/helper/*23.16*/.CSRF.formField),format.raw/*23.31*/("""
        """),format.raw/*24.9*/("""<div class="mb-6">
          """),_display_(/*25.12*/helper/*25.18*/.inputPassword(form("currentPassword"), Symbol("class") -> "form-input w-full", Symbol("placeholder") -> "現在のパスワード")),format.raw/*25.134*/("""
          """),_display_(/*26.12*/form("currentPassword")/*26.35*/.error.map/*26.45*/ { error =>_display_(Seq[Any](format.raw/*26.56*/("""
            """),format.raw/*27.13*/("""<p class="text-red-500 text-xs italic">"""),_display_(/*27.53*/error/*27.58*/.message),format.raw/*27.66*/("""</p>
          """)))}),format.raw/*28.12*/("""
        """),format.raw/*29.9*/("""</div>
        <div class="mb-6">
          """),_display_(/*31.12*/helper/*31.18*/.inputPassword(form("newPassword"), Symbol("class") -> "form-input w-full", Symbol("placeholder") -> "新しいパスワード")),format.raw/*31.130*/("""
          """),_display_(/*32.12*/form("newPassword")/*32.31*/.error.map/*32.41*/ { error =>_display_(Seq[Any](format.raw/*32.52*/("""
            """),format.raw/*33.13*/("""<p class="text-red-500 text-xs italic">"""),_display_(/*33.53*/error/*33.58*/.message),format.raw/*33.66*/("""</p>
          """)))}),format.raw/*34.12*/("""
        """),format.raw/*35.9*/("""</div>
        <div class="mb-6">
          """),_display_(/*37.12*/helper/*37.18*/.inputPassword(form("confirmPassword"), Symbol("class") -> "form-input w-full", Symbol("placeholder") -> "新しいパスワード（確認）")),format.raw/*37.138*/("""
          """),_display_(/*38.12*/form("confirmPassword")/*38.35*/.error.map/*38.45*/ { error =>_display_(Seq[Any](format.raw/*38.56*/("""
            """),format.raw/*39.13*/("""<p class="text-red-500 text-xs italic">"""),_display_(/*39.53*/error/*39.58*/.message),format.raw/*39.66*/("""</p>
          """)))}),format.raw/*40.12*/("""
        """),format.raw/*41.9*/("""</div>
        <div class="flex items-center justify-between">
          <button class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline" type="submit">
            変更
          </button>
          <a href=""""),_display_(/*46.21*/routes/*46.27*/.TeaController.index()),format.raw/*46.49*/("""" class="inline-block align-baseline font-bold text-sm text-blue-500 hover:text-blue-800">
            キャンセル
          </a>
        </div>
      """)))}),format.raw/*50.8*/("""
    """),format.raw/*51.5*/("""</div>
  </div>
""")))}),format.raw/*53.2*/(""" """))
      }
    }
  }

  def render(form:Form[models.Forms.PasswordUpdateData],request:RequestHeader,messages:Messages,flash:Flash): play.twirl.api.HtmlFormat.Appendable = apply(form)(request,messages,flash)

  def f:((Form[models.Forms.PasswordUpdateData]) => (RequestHeader,Messages,Flash) => play.twirl.api.HtmlFormat.Appendable) = (form) => (request,messages,flash) => apply(form)(request,messages,flash)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  SOURCE: app/views/password.scala.html
                  HASH: 058fdf205cc8d56c81f692b5291aea2802d2da7a
                  MATRIX: 995->1|1201->114|1228->116|1251->131|1290->133|1319->136|1518->310|1560->344|1599->346|1634->355|1761->455|1775->460|1811->475|1847->484|1891->498|1926->507|1959->531|1999->533|2035->542|2159->639|2206->670|2246->672|2287->685|2318->689|2332->694|2361->702|2408->718|2444->727|2488->741|2523->750|2538->756|2627->836|2667->838|2704->848|2719->854|2755->869|2791->878|2848->908|2863->914|3001->1030|3040->1042|3072->1065|3091->1075|3140->1086|3181->1099|3248->1139|3262->1144|3291->1152|3338->1168|3374->1177|3446->1222|3461->1228|3595->1340|3634->1352|3662->1371|3681->1381|3730->1392|3771->1405|3838->1445|3852->1450|3881->1458|3928->1474|3964->1483|4036->1528|4051->1534|4193->1654|4232->1666|4264->1689|4283->1699|4332->1710|4373->1723|4440->1763|4454->1768|4483->1776|4530->1792|4566->1801|4861->2069|4876->2075|4919->2097|5095->2243|5127->2248|5174->2265
                  LINES: 28->1|33->2|34->3|34->3|34->3|35->4|39->8|39->8|39->8|40->9|41->10|41->10|41->10|42->11|43->12|45->14|45->14|45->14|46->15|47->16|47->16|47->16|48->17|48->17|48->17|48->17|49->18|50->19|51->20|53->22|53->22|53->22|53->22|54->23|54->23|54->23|55->24|56->25|56->25|56->25|57->26|57->26|57->26|57->26|58->27|58->27|58->27|58->27|59->28|60->29|62->31|62->31|62->31|63->32|63->32|63->32|63->32|64->33|64->33|64->33|64->33|65->34|66->35|68->37|68->37|68->37|69->38|69->38|69->38|69->38|70->39|70->39|70->39|70->39|71->40|72->41|77->46|77->46|77->46|81->50|82->51|84->53
                  -- GENERATED --
              */
          