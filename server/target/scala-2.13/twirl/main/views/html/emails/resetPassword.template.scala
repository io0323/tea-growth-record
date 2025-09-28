
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

object resetPassword extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template1[String,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(resetUrl: String):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.1*/("""
"""),format.raw/*3.1*/("""<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>パスワードのリセット</title>
</head>
<body>
  <h1>パスワードのリセット</h1>
  <p>パスワードのリセットを受け付けました。</p>
  <p>以下のリンクをクリックして、新しいパスワードを設定してください。</p>
  <p><a href=""""),_display_(/*13.16*/resetUrl),format.raw/*13.24*/("""">パスワードをリセットする</a></p>
  <p>このリンクは24時間有効です。</p>
  <p>このメールに心当たりがない場合は、このメールを無視してください。</p>
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
                  SOURCE: app/views/emails/resetPassword.scala.html
                  HASH: 34b7015de989e7c77efb8d8626267561fc54d65d
                  MATRIX: 744->1|856->20|883->21|1116->227|1145->235
                  LINES: 21->1|26->2|27->3|37->13|37->13
                  -- GENERATED --
              */
          