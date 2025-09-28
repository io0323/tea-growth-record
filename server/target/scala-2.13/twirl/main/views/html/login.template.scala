
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
/*1.2*/import forms.Forms.LoginData

object login extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template3[Form[LoginData],RequestHeader,Messages,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*2.2*/(loginForm: Form[LoginData])(implicit request: RequestHeader, messages: Messages):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*3.1*/("""
"""),_display_(/*4.2*/main("ログイン")/*4.14*/ {_display_(Seq[Any](format.raw/*4.16*/("""
  """),format.raw/*5.3*/("""<div class="container">
    <div class="panel">
      <h1>ログイン</h1>

      """),_display_(/*9.8*/request/*9.15*/.flash.get("success").map/*9.40*/ { message =>_display_(Seq[Any](format.raw/*9.53*/("""
        """),format.raw/*10.9*/("""<div class="alert alert-success">"""),_display_(/*10.43*/message),format.raw/*10.50*/("""</div>
      """)))}),format.raw/*11.8*/("""

      """),_display_(/*13.8*/request/*13.15*/.flash.get("error").map/*13.38*/ { message =>_display_(Seq[Any](format.raw/*13.51*/("""
        """),format.raw/*14.9*/("""<div class="alert alert-danger">"""),_display_(/*14.42*/message),format.raw/*14.49*/("""</div>
      """)))}),format.raw/*15.8*/("""

      """),_display_(/*17.8*/helper/*17.14*/.form(routes.AuthController.loginSubmit())/*17.56*/ {_display_(Seq[Any](format.raw/*17.58*/("""
        """),_display_(/*18.10*/helper/*18.16*/.CSRF.formField),format.raw/*18.31*/("""

        """),_display_(/*20.10*/helper/*20.16*/.inputText(
          loginForm("email"),
          Symbol("label") -> "メールアドレス",
          Symbol("type") -> "email",
          Symbol("class") -> "form-control",
          Symbol("required") -> true
        )),format.raw/*26.10*/("""

        """),_display_(/*28.10*/helper/*28.16*/.inputPassword(
          loginForm("password"),
          Symbol("label") -> "パスワード",
          Symbol("class") -> "form-control",
          Symbol("required") -> true
        )),format.raw/*33.10*/("""

        """),format.raw/*35.9*/("""<button type="submit" class="btn btn-primary">ログイン</button>
      """)))}),format.raw/*36.8*/("""

      """),format.raw/*38.7*/("""<hr>

      <p>アカウントをお持ちでない方は<a href=""""),_display_(/*40.34*/routes/*40.40*/.AuthController.register()),format.raw/*40.66*/("""">こちら</a>から登録してください。</p>
    </div>
  </div>
""")))}),format.raw/*43.2*/(""" """))
      }
    }
  }

  def render(loginForm:Form[LoginData],request:RequestHeader,messages:Messages): play.twirl.api.HtmlFormat.Appendable = apply(loginForm)(request,messages)

  def f:((Form[LoginData]) => (RequestHeader,Messages) => play.twirl.api.HtmlFormat.Appendable) = (loginForm) => (request,messages) => apply(loginForm)(request,messages)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  SOURCE: app/views/login.scala.html
                  HASH: 7c3bca2362bf693f56b10c0c7317e5184947358e
                  MATRIX: 432->1|797->31|972->113|999->115|1019->127|1058->129|1087->132|1188->208|1203->215|1236->240|1286->253|1322->262|1383->296|1411->303|1455->317|1490->326|1506->333|1538->356|1589->369|1625->378|1685->411|1713->418|1757->432|1792->441|1807->447|1858->489|1898->491|1935->501|1950->507|1986->522|2024->533|2039->539|2270->749|2308->760|2323->766|2522->944|2559->954|2656->1021|2691->1029|2757->1068|2772->1074|2819->1100|2895->1146
                  LINES: 17->1|22->2|27->3|28->4|28->4|28->4|29->5|33->9|33->9|33->9|33->9|34->10|34->10|34->10|35->11|37->13|37->13|37->13|37->13|38->14|38->14|38->14|39->15|41->17|41->17|41->17|41->17|42->18|42->18|42->18|44->20|44->20|50->26|52->28|52->28|57->33|59->35|60->36|62->38|64->40|64->40|64->40|67->43
                  -- GENERATED --
              */
          