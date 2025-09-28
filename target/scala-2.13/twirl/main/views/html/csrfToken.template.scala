
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

object csrfToken extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template3[RequestHeader,Messages,Flash,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/()(implicit request: RequestHeader, messages: Messages, flash: Flash):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.1*/("""
"""),_display_(/*3.2*/helper/*3.8*/.CSRF.formField),format.raw/*3.23*/(""" """))
      }
    }
  }

  def render(request:RequestHeader,messages:Messages,flash:Flash): play.twirl.api.HtmlFormat.Appendable = apply()(request,messages,flash)

  def f:(() => (RequestHeader,Messages,Flash) => play.twirl.api.HtmlFormat.Appendable) = () => (request,messages,flash) => apply()(request,messages,flash)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  SOURCE: app/views/csrfToken.scala.html
                  HASH: 864089b567351908aa154f407d8a3645dc293240
                  MATRIX: 958->1|1121->71|1148->73|1161->79|1196->94
                  LINES: 28->1|33->2|34->3|34->3|34->3
                  -- GENERATED --
              */
          