
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

object error500 extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template3[RequestHeader,Messages,Flash,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/()(implicit request: RequestHeader, messages: Messages, flash: Flash):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.1*/("""
"""),_display_(/*3.2*/main("500 Internal Server Error")/*3.35*/ {_display_(Seq[Any](format.raw/*3.37*/("""
  """),format.raw/*4.3*/("""<div class="container mx-auto px-4 py-8">
    <div class="bg-white shadow-lg rounded-lg overflow-hidden">
      <div class="p-6">
        <h1 class="text-2xl font-bold mb-4">500 Internal Server Error</h1>
        <p class="mb-4">サーバーでエラーが発生しました。</p>
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
                  SOURCE: app/views/error/error500.scala.html
                  HASH: 19dc3fcbdb832acfea07d8cf29b8e86c8bbd14fd
                  MATRIX: 963->1|1126->71|1153->73|1194->106|1233->108|1262->111|1556->379|1570->385|1612->407|1776->541
                  LINES: 28->1|33->2|34->3|34->3|34->3|35->4|40->9|40->9|40->9|46->15
                  -- GENERATED --
              */
          