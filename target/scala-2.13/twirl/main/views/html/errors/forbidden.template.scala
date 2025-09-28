
package views.html.errors

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

object forbidden extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template2[RequestHeader,Messages,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/()(implicit request: RequestHeader, messages: Messages):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.1*/("""
"""),_display_(/*3.2*/main("アクセス権限エラー")/*3.19*/ {_display_(Seq[Any](format.raw/*3.21*/("""
  """),format.raw/*4.3*/("""<div class="container mx-auto px-4 py-8">
    <div class="bg-red-100 border-l-4 border-red-500 text-red-700 p-4" role="alert">
      <p class="font-bold">アクセス権限エラー</p>
      <p>このページにアクセスする権限がありません。</p>
    </div>
    <div class="mt-4">
      <a href=""""),_display_(/*10.17*/routes/*10.23*/.TeaController.index()),format.raw/*10.45*/("""" class="text-blue-600 hover:text-blue-900">
        ホームに戻る
      </a>
    </div>
  </div>
""")))}),format.raw/*15.2*/(""" """))
      }
    }
  }

  def render(request:RequestHeader,messages:Messages): play.twirl.api.HtmlFormat.Appendable = apply()(request,messages)

  def f:(() => (RequestHeader,Messages) => play.twirl.api.HtmlFormat.Appendable) = () => (request,messages) => apply()(request,messages)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  SOURCE: app/views/errors/forbidden.scala.html
                  HASH: 70a4a3f3096a518cbf0c77a08c466a811697ad95
                  MATRIX: 959->1|1108->57|1135->59|1160->76|1199->78|1228->81|1508->334|1523->340|1566->362|1688->454
                  LINES: 28->1|33->2|34->3|34->3|34->3|35->4|41->10|41->10|41->10|46->15
                  -- GENERATED --
              */
          