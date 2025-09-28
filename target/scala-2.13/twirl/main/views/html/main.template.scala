
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

object main extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template3[String,Html,RequestHeader,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(title: String)(content: Html)(implicit request: RequestHeader):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.1*/("""
"""),format.raw/*3.1*/("""<!DOCTYPE html>
<html lang="ja">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>"""),_display_(/*8.13*/title),format.raw/*8.18*/(""" """),format.raw/*8.19*/("""- お茶成長記録</title>
    <link rel="stylesheet" media="screen" href=""""),_display_(/*9.50*/routes/*9.56*/.Assets.versioned("stylesheets/main.css")),format.raw/*9.97*/("""">
    <link rel="shortcut icon" type="image/png" href=""""),_display_(/*10.55*/routes/*10.61*/.Assets.versioned("images/favicon.png")),format.raw/*10.100*/("""">
  </head>
  <body class="bg-gray-100">
    <nav class="bg-white shadow-lg">
      <div class="max-w-7xl mx-auto px-4">
        <div class="flex justify-between h-16">
          <div class="flex">
            <div class="flex-shrink-0 flex items-center">
              <a href=""""),_display_(/*18.25*/routes/*18.31*/.TeaController.index()),format.raw/*18.53*/("""" class="text-xl font-bold text-gray-800">
                お茶成長記録
              </a>
            </div>
          </div>
          <div class="flex items-center">
            """),_display_(/*24.14*/request/*24.21*/.session.get("userId").map/*24.47*/ { _ =>_display_(Seq[Any](format.raw/*24.54*/("""
              """),format.raw/*25.15*/("""<a href=""""),_display_(/*25.25*/routes/*25.31*/.TeaController.create()),format.raw/*25.54*/("""" class="text-gray-600 hover:text-gray-900 px-3 py-2 rounded-md text-sm font-medium">
                新規登録
              </a>
              <a href=""""),_display_(/*28.25*/routes/*28.31*/.AuthController.logout()),format.raw/*28.55*/("""" class="text-gray-600 hover:text-gray-900 px-3 py-2 rounded-md text-sm font-medium">
                ログアウト
              </a>
            """)))}/*31.14*/.getOrElse/*31.24*/ {_display_(Seq[Any](format.raw/*31.26*/("""
              """),format.raw/*32.15*/("""<a href=""""),_display_(/*32.25*/routes/*32.31*/.AuthController.login()),format.raw/*32.54*/("""" class="text-gray-600 hover:text-gray-900 px-3 py-2 rounded-md text-sm font-medium">
                ログイン
              </a>
              <a href=""""),_display_(/*35.25*/routes/*35.31*/.AuthController.register()),format.raw/*35.57*/("""" class="text-gray-600 hover:text-gray-900 px-3 py-2 rounded-md text-sm font-medium">
                新規登録
              </a>
            """)))}),format.raw/*38.14*/("""
          """),format.raw/*39.11*/("""</div>
        </div>
      </div>
    </nav>

    <main class="container mx-auto px-4 py-8">
      """),_display_(/*45.8*/content),format.raw/*45.15*/("""
    """),format.raw/*46.5*/("""</main>

    <footer class="bg-white shadow-lg mt-8">
      <div class="max-w-7xl mx-auto py-4 px-4">
        <p class="text-center text-gray-500 text-sm">
          © 2024 お茶成長記録. All rights reserved.
        </p>
      </div>
    </footer>
  </body>
</html> """))
      }
    }
  }

  def render(title:String,content:Html,request:RequestHeader): play.twirl.api.HtmlFormat.Appendable = apply(title)(content)(request)

  def f:((String) => (Html) => (RequestHeader) => play.twirl.api.HtmlFormat.Appendable) = (title) => (content) => (request) => apply(title)(content)(request)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  SOURCE: app/views/main.scala.html
                  HASH: 4081444837347dea01af52618662329a1c31ea2b
                  MATRIX: 950->1|1107->65|1134->66|1314->220|1339->225|1367->226|1459->292|1473->298|1534->339|1618->396|1633->402|1694->441|2002->722|2017->728|2060->750|2263->926|2279->933|2314->959|2359->966|2402->981|2439->991|2454->997|2498->1020|2675->1170|2690->1176|2735->1200|2894->1340|2913->1350|2953->1352|2996->1367|3033->1377|3048->1383|3092->1406|3269->1556|3284->1562|3331->1588|3501->1727|3540->1738|3667->1839|3695->1846|3727->1851
                  LINES: 28->1|33->2|34->3|39->8|39->8|39->8|40->9|40->9|40->9|41->10|41->10|41->10|49->18|49->18|49->18|55->24|55->24|55->24|55->24|56->25|56->25|56->25|56->25|59->28|59->28|59->28|62->31|62->31|62->31|63->32|63->32|63->32|63->32|66->35|66->35|66->35|69->38|70->39|76->45|76->45|77->46
                  -- GENERATED --
              */
          