
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

object notFound extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template2[RequestHeader,Messages,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(implicit request: RequestHeader, messages: Messages):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.1*/("""
"""),_display_(/*3.2*/main("404 Not Found")/*3.23*/ {_display_(Seq[Any](format.raw/*3.25*/("""
  """),format.raw/*4.3*/("""<div class="container mx-auto px-4 py-8">
    <div class="text-center">
      <h1 class="text-4xl font-bold text-gray-900 mb-4">404 Not Found</h1>
      <p class="text-gray-600 mb-8">"""),_display_(/*7.38*/messages("error.notFound")),format.raw/*7.64*/("""</p>
      <a href=""""),_display_(/*8.17*/routes/*8.23*/.TeaController.index()),format.raw/*8.45*/("""" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">
        """),_display_(/*9.10*/messages("common.backToHome")),format.raw/*9.39*/("""
      """),format.raw/*10.7*/("""</a>
    </div>
  </div>
""")))}),format.raw/*13.2*/(""" """))
      }
    }
  }

  def render(request:RequestHeader,messages:Messages): play.twirl.api.HtmlFormat.Appendable = apply(request,messages)

  def f:((RequestHeader,Messages) => play.twirl.api.HtmlFormat.Appendable) = (request,messages) => apply(request,messages)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  SOURCE: app/views/errors/notFound.scala.html
                  HASH: f6dba5cd68478121eb926265f3a8eee0cc2dfefb
                  MATRIX: 958->1|1105->55|1132->57|1161->78|1200->80|1229->83|1439->267|1485->293|1532->314|1546->320|1588->342|1703->431|1752->460|1786->467|1842->493
                  LINES: 28->1|33->2|34->3|34->3|34->3|35->4|38->7|38->7|39->8|39->8|39->8|40->9|40->9|41->10|44->13
                  -- GENERATED --
              */
          