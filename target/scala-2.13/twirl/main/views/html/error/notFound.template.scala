
package views.html.error

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

object notFound extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template3[RequestHeader,Messages,Flash,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/()(implicit request: RequestHeader, messages: Messages, flash: Flash):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.1*/("""
"""),_display_(/*3.2*/main("404 Not Found")/*3.23*/ {_display_(Seq[Any](format.raw/*3.25*/("""
  """),format.raw/*4.3*/("""<div class="container mx-auto px-4 py-8">
    <div class="bg-white shadow-lg rounded-lg overflow-hidden">
      <div class="p-6">
        <h1 class="text-2xl font-bold mb-4">404 Not Found</h1>
        <p class="mb-4">ページが見つかりません。</p>
        <a href=""""),_display_(/*9.19*/routes/*9.25*/.TeaController.index()),format.raw/*9.47*/("""" class="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600">
          ホームに戻る
        </a>
      </div>
    </div>
  </div>
""")))}),format.raw/*15.2*/(""" """))
      }
    }
  }

  def render(request:RequestHeader,messages:Messages,flash:Flash): play.twirl.api.HtmlFormat.Appendable = apply()(request,messages,flash)

  def f:(() => (RequestHeader,Messages,Flash) => play.twirl.api.HtmlFormat.Appendable) = () => (request,messages,flash) => apply()(request,messages,flash)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  SOURCE: app/views/error/notFound.scala.html
                  HASH: 249f0deded0d69b8bab93b6d8a5d4aa7ce020fcc
                  MATRIX: 963->1|1126->71|1153->73|1182->94|1221->96|1250->99|1528->351|1542->357|1584->379|1748->513
                  LINES: 28->1|33->2|34->3|34->3|34->3|35->4|40->9|40->9|40->9|46->15
                  -- GENERATED --
              */
          