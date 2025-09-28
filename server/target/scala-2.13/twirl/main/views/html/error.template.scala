
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

object error extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template3[Int,String,RequestHeader,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(statusCode: Int, message: String)(implicit request: RequestHeader):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.1*/("""
"""),format.raw/*3.1*/("""<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>エラー - お茶の木成長記録</title>
    <style>
        body """),format.raw/*10.14*/("""{"""),format.raw/*10.15*/("""
            """),format.raw/*11.13*/("""font-family: 'Helvetica Neue', Arial, sans-serif;
            line-height: 1.6;
            margin: 0;
            padding: 20px;
            background-color: #f5f5f5;
            display: flex;
            align-items: center;
            justify-content: center;
            min-height: 100vh;
        """),format.raw/*20.9*/("""}"""),format.raw/*20.10*/("""
        """),format.raw/*21.9*/(""".container """),format.raw/*21.20*/("""{"""),format.raw/*21.21*/("""
            """),format.raw/*22.13*/("""max-width: 600px;
            margin: 0 auto;
            padding: 40px;
            background: white;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            text-align: center;
        """),format.raw/*29.9*/("""}"""),format.raw/*29.10*/("""
        """),format.raw/*30.9*/("""h1 """),format.raw/*30.12*/("""{"""),format.raw/*30.13*/("""
            """),format.raw/*31.13*/("""color: #333;
            margin-bottom: 20px;
            font-size: 2em;
        """),format.raw/*34.9*/("""}"""),format.raw/*34.10*/("""
        """),format.raw/*35.9*/(""".error-code """),format.raw/*35.21*/("""{"""),format.raw/*35.22*/("""
            """),format.raw/*36.13*/("""font-size: 4em;
            color: #dc3545;
            margin-bottom: 20px;
        """),format.raw/*39.9*/("""}"""),format.raw/*39.10*/("""
        """),format.raw/*40.9*/(""".error-message """),format.raw/*40.24*/("""{"""),format.raw/*40.25*/("""
            """),format.raw/*41.13*/("""color: #666;
            margin-bottom: 30px;
        """),format.raw/*43.9*/("""}"""),format.raw/*43.10*/("""
        """),format.raw/*44.9*/(""".back-link """),format.raw/*44.20*/("""{"""),format.raw/*44.21*/("""
            """),format.raw/*45.13*/("""display: inline-block;
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            transition: background-color 0.3s;
        """),format.raw/*52.9*/("""}"""),format.raw/*52.10*/("""
        """),format.raw/*53.9*/(""".back-link:hover """),format.raw/*53.26*/("""{"""),format.raw/*53.27*/("""
            """),format.raw/*54.13*/("""background-color: #45a049;
        """),format.raw/*55.9*/("""}"""),format.raw/*55.10*/("""
    """),format.raw/*56.5*/("""</style>
</head>
<body>
    <div class="container">
        <div class="error-code">"""),_display_(/*60.34*/statusCode),format.raw/*60.44*/("""</div>
        <h1>エラーが発生しました</h1>
        <div class="error-message">"""),_display_(/*62.37*/message),format.raw/*62.44*/("""</div>
        <a href=""""),_display_(/*63.19*/routes/*63.25*/.HomeController.index()),format.raw/*63.48*/("""" class="back-link">ホームに戻る</a>
    </div>
</body>
</html> """))
      }
    }
  }

  def render(statusCode:Int,message:String,request:RequestHeader): play.twirl.api.HtmlFormat.Appendable = apply(statusCode,message)(request)

  def f:((Int,String) => (RequestHeader) => play.twirl.api.HtmlFormat.Appendable) = (statusCode,message) => (request) => apply(statusCode,message)(request)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  SOURCE: app/views/error.scala.html
                  HASH: 932ded09ca13f619508aed6b48ebd05f312240d4
                  MATRIX: 747->1|908->69|935->70|1164->271|1193->272|1234->285|1566->590|1595->591|1631->600|1670->611|1699->612|1740->625|1997->855|2026->856|2062->865|2093->868|2122->869|2163->882|2272->964|2301->965|2337->974|2377->986|2406->987|2447->1000|2559->1085|2588->1086|2624->1095|2667->1110|2696->1111|2737->1124|2818->1178|2847->1179|2883->1188|2922->1199|2951->1200|2992->1213|3261->1455|3290->1456|3326->1465|3371->1482|3400->1483|3441->1496|3503->1531|3532->1532|3564->1537|3676->1622|3707->1632|3805->1703|3833->1710|3885->1735|3900->1741|3944->1764
                  LINES: 21->1|26->2|27->3|34->10|34->10|35->11|44->20|44->20|45->21|45->21|45->21|46->22|53->29|53->29|54->30|54->30|54->30|55->31|58->34|58->34|59->35|59->35|59->35|60->36|63->39|63->39|64->40|64->40|64->40|65->41|67->43|67->43|68->44|68->44|68->44|69->45|76->52|76->52|77->53|77->53|77->53|78->54|79->55|79->55|80->56|84->60|84->60|86->62|86->62|87->63|87->63|87->63
                  -- GENERATED --
              */
          