
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

object profile extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template3[Form[forms.Forms.UpdateProfileData],RequestHeader,Messages,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(profileForm: Form[forms.Forms.UpdateProfileData])(implicit request: RequestHeader, messages: Messages):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.1*/("""
"""),_display_(/*3.2*/main("プロフィール")/*3.16*/ {_display_(Seq[Any](format.raw/*3.18*/("""
  """),format.raw/*4.3*/("""<div class="container">
    <div class="panel">
      <h1>プロフィール</h1>

      """),_display_(/*8.8*/request/*8.15*/.flash.get("success").map/*8.40*/ { message =>_display_(Seq[Any](format.raw/*8.53*/("""
        """),format.raw/*9.9*/("""<div class="alert alert-success">"""),_display_(/*9.43*/message),format.raw/*9.50*/("""</div>
      """)))}),format.raw/*10.8*/("""

      """),_display_(/*12.8*/request/*12.15*/.flash.get("error").map/*12.38*/ { message =>_display_(Seq[Any](format.raw/*12.51*/("""
        """),format.raw/*13.9*/("""<div class="alert alert-danger">"""),_display_(/*13.42*/message),format.raw/*13.49*/("""</div>
      """)))}),format.raw/*14.8*/("""

      """),_display_(/*16.8*/helper/*16.14*/.form(routes.UserController.updateProfile())/*16.58*/ {_display_(Seq[Any](format.raw/*16.60*/("""
        """),_display_(/*17.10*/helper/*17.16*/.CSRF.formField),format.raw/*17.31*/("""

        """),_display_(/*19.10*/helper/*19.16*/.inputText(
          profileForm("name"),
          Symbol("label") -> "名前",
          Symbol("class") -> "form-control"
        )),format.raw/*23.10*/("""

        """),_display_(/*25.10*/helper/*25.16*/.inputText(
          profileForm("email"),
          Symbol("label") -> "メールアドレス",
          Symbol("type") -> "email",
          Symbol("class") -> "form-control",
          Symbol("required") -> true
        )),format.raw/*31.10*/("""

        """),format.raw/*33.9*/("""<button type="submit" class="btn btn-primary">更新</button>
      """)))}),format.raw/*34.8*/("""

      """),format.raw/*36.7*/("""<hr>

      <h2>パスワードの変更</h2>

      """),_display_(/*40.8*/helper/*40.14*/.form(routes.UserController.changePassword())/*40.59*/ {_display_(Seq[Any](format.raw/*40.61*/("""
        """),_display_(/*41.10*/helper/*41.16*/.CSRF.formField),format.raw/*41.31*/("""

        """),_display_(/*43.10*/helper/*43.16*/.inputPassword(
          forms.Forms.changePasswordForm("currentPassword"),
          Symbol("label") -> "現在のパスワード",
          Symbol("class") -> "form-control",
          Symbol("required") -> true
        )),format.raw/*48.10*/("""

        """),_display_(/*50.10*/helper/*50.16*/.inputPassword(
          forms.Forms.changePasswordForm("newPassword"),
          Symbol("label") -> "新しいパスワード",
          Symbol("class") -> "form-control",
          Symbol("required") -> true
        )),format.raw/*55.10*/("""

        """),_display_(/*57.10*/helper/*57.16*/.inputPassword(
          forms.Forms.changePasswordForm("confirmPassword"),
          Symbol("label") -> "新しいパスワード（確認）",
          Symbol("class") -> "form-control",
          Symbol("required") -> true
        )),format.raw/*62.10*/("""

        """),format.raw/*64.9*/("""<button type="submit" class="btn btn-primary">変更</button>
      """)))}),format.raw/*65.8*/("""
    """),format.raw/*66.5*/("""</div>
  </div>
""")))}),format.raw/*68.2*/(""" """))
      }
    }
  }

  def render(profileForm:Form[forms.Forms.UpdateProfileData],request:RequestHeader,messages:Messages): play.twirl.api.HtmlFormat.Appendable = apply(profileForm)(request,messages)

  def f:((Form[forms.Forms.UpdateProfileData]) => (RequestHeader,Messages) => play.twirl.api.HtmlFormat.Appendable) = (profileForm) => (request,messages) => apply(profileForm)(request,messages)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  SOURCE: app/views/profile.scala.html
                  HASH: 9f7509f9deab6848eb9def31952c20f5177cd581
                  MATRIX: 783->1|980->105|1007->107|1029->121|1068->123|1097->126|1200->204|1215->211|1248->236|1298->249|1333->258|1393->292|1420->299|1464->313|1499->322|1515->329|1547->352|1598->365|1634->374|1694->407|1722->414|1766->428|1801->437|1816->443|1869->487|1909->489|1946->499|1961->505|1997->520|2035->531|2050->537|2202->668|2240->679|2255->685|2488->897|2525->907|2620->972|2655->980|2719->1018|2734->1024|2788->1069|2828->1071|2865->1081|2880->1087|2916->1102|2954->1113|2969->1119|3199->1328|3237->1339|3252->1345|3478->1550|3516->1561|3531->1567|3765->1780|3802->1790|3897->1855|3929->1860|3976->1877
                  LINES: 21->1|26->2|27->3|27->3|27->3|28->4|32->8|32->8|32->8|32->8|33->9|33->9|33->9|34->10|36->12|36->12|36->12|36->12|37->13|37->13|37->13|38->14|40->16|40->16|40->16|40->16|41->17|41->17|41->17|43->19|43->19|47->23|49->25|49->25|55->31|57->33|58->34|60->36|64->40|64->40|64->40|64->40|65->41|65->41|65->41|67->43|67->43|72->48|74->50|74->50|79->55|81->57|81->57|86->62|88->64|89->65|90->66|92->68
                  -- GENERATED --
              */
          