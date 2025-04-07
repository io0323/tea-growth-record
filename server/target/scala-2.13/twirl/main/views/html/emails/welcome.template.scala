
package views.html.emails

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

object welcome extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template1[String,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(name: String):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.1*/("""
"""),format.raw/*3.1*/("""<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>ようこそ Tea Growth Record へ</title>
  </head>
  <body>
    <h1>ようこそ Tea Growth Record へ</h1>
    <p>"""),_display_(/*11.9*/{name}),format.raw/*11.15*/("""様</p>
    <p>Tea Growth Record へのご登録ありがとうございます。</p>
    <p>このサービスでは、お茶の購入記録や飲用記録を管理することができます。</p>
    <p>早速、お気に入りのお茶を登録してみましょう。</p>
    <p>ご不明な点がございましたら、お気軽にお問い合わせください。</p>
    <p>今後ともTea Growth Recordをよろしくお願いいたします。</p>
  </body>
</html> """))
      }
    }
  }

  def render(name:String): play.twirl.api.HtmlFormat.Appendable = apply(name)

  def f:((String) => play.twirl.api.HtmlFormat.Appendable) = (name) => apply(name)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  SOURCE: app/views/emails/welcome.scala.html
                  HASH: 018d9c5d8f7874b3b8d3f0e982d88bafec38503a
                  MATRIX: 738->1|846->16|873->17|1067->185|1094->191
                  LINES: 21->1|26->2|27->3|35->11|35->11
                  -- GENERATED --
              */
          