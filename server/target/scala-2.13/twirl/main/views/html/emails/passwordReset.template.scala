
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

object passwordReset extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template1[String,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(resetUrl: String):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.1*/("""
"""),format.raw/*3.1*/("""<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>パスワードリセット</title>
  </head>
  <body>
    <h1>パスワードリセット</h1>
    <p>パスワードリセットのリクエストを受け付けました。</p>
    <p>以下のリンクをクリックして、新しいパスワードを設定してください：</p>
    <p><a href=""""),_display_(/*13.18*/resetUrl),format.raw/*13.26*/("""">"""),_display_(/*13.29*/resetUrl),format.raw/*13.37*/("""</a></p>
    <p>このリンクは24時間のみ有効です。</p>
    <p>パスワードリセットをリクエストしていない場合は、このメールを無視してください。</p>
    <hr>
    <p>お茶の木成長記録</p>
  </body>
</html> """))
      }
    }
  }

  def render(resetUrl:String): play.twirl.api.HtmlFormat.Appendable = apply(resetUrl)

  def f:((String) => play.twirl.api.HtmlFormat.Appendable) = (resetUrl) => apply(resetUrl)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  SOURCE: app/views/emails/passwordReset.scala.html
                  HASH: 3c52dcbba4504fe76645b8079ffa6a82539f14bf
                  MATRIX: 744->1|856->20|883->21|1137->248|1166->256|1196->259|1225->267
                  LINES: 21->1|26->2|27->3|37->13|37->13|37->13|37->13
                  -- GENERATED --
              */
          