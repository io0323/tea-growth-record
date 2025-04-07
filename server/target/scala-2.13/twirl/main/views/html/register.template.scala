
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
/*1.2*/import forms.Forms.RegisterData

object register extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template3[Form[RegisterData],RequestHeader,Messages,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*2.2*/(registerForm: Form[RegisterData])(implicit request: RequestHeader, messages: Messages):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*3.1*/("""
"""),_display_(/*4.2*/main("新規登録")/*4.14*/ {_display_(Seq[Any](format.raw/*4.16*/("""
  """),format.raw/*5.3*/("""<div class="container">
    <div class="panel">
      <h1>新規登録</h1>

      """),_display_(/*9.8*/request/*9.15*/.flash.get("success").map/*9.40*/ { message =>_display_(Seq[Any](format.raw/*9.53*/("""
        """),format.raw/*10.9*/("""<div class="alert alert-success">"""),_display_(/*10.43*/message),format.raw/*10.50*/("""</div>
      """)))}),format.raw/*11.8*/("""

      """),_display_(/*13.8*/request/*13.15*/.flash.get("error").map/*13.38*/ { message =>_display_(Seq[Any](format.raw/*13.51*/("""
        """),format.raw/*14.9*/("""<div class="alert alert-danger">"""),_display_(/*14.42*/message),format.raw/*14.49*/("""</div>
      """)))}),format.raw/*15.8*/("""

      """),_display_(/*17.8*/helper/*17.14*/.form(routes.AuthController.registerSubmit())/*17.59*/ {_display_(Seq[Any](format.raw/*17.61*/("""
        """),_display_(/*18.10*/helper/*18.16*/.CSRF.formField),format.raw/*18.31*/("""

        """),_display_(/*20.10*/helper/*20.16*/.inputText(
          registerForm("name"),
          Symbol("label") -> "名前",
          Symbol("class") -> "form-control"
        )),format.raw/*24.10*/("""

        """),_display_(/*26.10*/helper/*26.16*/.inputText(
          registerForm("email"),
          Symbol("label") -> "メールアドレス",
          Symbol("type") -> "email",
          Symbol("class") -> "form-control",
          Symbol("required") -> true
        )),format.raw/*32.10*/("""

        """),_display_(/*34.10*/helper/*34.16*/.inputPassword(
          registerForm("password"),
          Symbol("label") -> "パスワード",
          Symbol("class") -> "form-control",
          Symbol("required") -> true
        )),format.raw/*39.10*/("""

        """),format.raw/*41.9*/("""<button type="submit" class="btn btn-primary">登録</button>
      """)))}),format.raw/*42.8*/("""

      """),format.raw/*44.7*/("""<hr>

      <p>すでにアカウントをお持ちの方は<a href=""""),_display_(/*46.35*/routes/*46.41*/.AuthController.login()),format.raw/*46.64*/("""">こちら</a>からログインしてください。</p>
    </div>
  </div>
""")))}),format.raw/*49.2*/(""" """))
      }
    }
  }

  def render(registerForm:Form[RegisterData],request:RequestHeader,messages:Messages): play.twirl.api.HtmlFormat.Appendable = apply(registerForm)(request,messages)

  def f:((Form[RegisterData]) => (RequestHeader,Messages) => play.twirl.api.HtmlFormat.Appendable) = (registerForm) => (request,messages) => apply(registerForm)(request,messages)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  SOURCE: app/views/register.scala.html
                  HASH: 275e0b3757383beaa00102059be3f2d80d13985f
                  MATRIX: 432->1|806->34|987->122|1014->124|1034->136|1073->138|1102->141|1203->217|1218->224|1251->249|1301->262|1337->271|1398->305|1426->312|1470->326|1505->335|1521->342|1553->365|1604->378|1640->387|1700->420|1728->427|1772->441|1807->450|1822->456|1876->501|1916->503|1953->513|1968->519|2004->534|2042->545|2057->551|2210->683|2248->694|2263->700|2497->913|2535->924|2550->930|2752->1111|2789->1121|2884->1186|2919->1194|2986->1234|3001->1240|3045->1263|3123->1311
                  LINES: 17->1|22->2|27->3|28->4|28->4|28->4|29->5|33->9|33->9|33->9|33->9|34->10|34->10|34->10|35->11|37->13|37->13|37->13|37->13|38->14|38->14|38->14|39->15|41->17|41->17|41->17|41->17|42->18|42->18|42->18|44->20|44->20|48->24|50->26|50->26|56->32|58->34|58->34|63->39|65->41|66->42|68->44|70->46|70->46|70->46|73->49
                  -- GENERATED --
              */
          