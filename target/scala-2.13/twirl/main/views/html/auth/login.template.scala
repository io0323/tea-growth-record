
package views.html.auth

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

object login extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template4[Form[models.Forms.LoginData],RequestHeader,Messages,Flash,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(form: Form[models.Forms.LoginData])(implicit request: RequestHeader, messages: Messages, flash: Flash):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.1*/("""
"""),_display_(/*3.2*/main("ログイン")/*3.14*/ {_display_(Seq[Any](format.raw/*3.16*/("""
  """),format.raw/*4.3*/("""<div class="container mt-5">
    <div class="row justify-content-center">
      <div class="col-md-6">
        <div class="card">
          <div class="card-header">
            <h2>ログイン</h2>
          </div>
          <div class="card-body">
            """),_display_(/*12.14*/helper/*12.20*/.form(action = routes.AuthController.doLogin())/*12.67*/ {_display_(Seq[Any](format.raw/*12.69*/("""
              """),_display_(/*13.16*/helper/*13.22*/.CSRF.formField),format.raw/*13.37*/("""

              """),_display_(/*15.16*/if(form.hasGlobalErrors)/*15.40*/ {_display_(Seq[Any](format.raw/*15.42*/("""
                """),format.raw/*16.17*/("""<div class="alert alert-danger">
                  """),_display_(/*17.20*/form/*17.24*/.globalError.map(_.message)),format.raw/*17.51*/("""
                """),format.raw/*18.17*/("""</div>
              """)))}),format.raw/*19.16*/("""

              """),format.raw/*21.15*/("""<div class="mb-3">
                <label for="email" class="form-label">メールアドレス</label>
                <input type="email" name="email" id="email" value=""""),_display_(/*23.69*/form("email")/*23.82*/.value),format.raw/*23.88*/("""" class="form-control" required>
                """),_display_(/*24.18*/form("email")/*24.31*/.error.map/*24.41*/ { error =>_display_(Seq[Any](format.raw/*24.52*/("""
                  """),format.raw/*25.19*/("""<div class="text-danger">"""),_display_(/*25.45*/error/*25.50*/.message),format.raw/*25.58*/("""</div>
                """)))}),format.raw/*26.18*/("""
              """),format.raw/*27.15*/("""</div>

              <div class="mb-3">
                <label for="password" class="form-label">パスワード</label>
                <input type="password" name="password" id="password" class="form-control" required>
                """),_display_(/*32.18*/form("password")/*32.34*/.error.map/*32.44*/ { error =>_display_(Seq[Any](format.raw/*32.55*/("""
                  """),format.raw/*33.19*/("""<div class="text-danger">"""),_display_(/*33.45*/error/*33.50*/.message),format.raw/*33.58*/("""</div>
                """)))}),format.raw/*34.18*/("""
              """),format.raw/*35.15*/("""</div>

              <div class="d-grid gap-2">
                <button type="submit" class="btn btn-primary">ログイン</button>
                <a href=""""),_display_(/*39.27*/routes/*39.33*/.AuthController.register()),format.raw/*39.59*/("""" class="btn btn-link">新規登録はこちら</a>
              </div>
            """)))}),format.raw/*41.14*/("""
          """),format.raw/*42.11*/("""</div>
        </div>
      </div>
    </div>
  </div>
""")))}),format.raw/*47.2*/(""" """))
      }
    }
  }

  def render(form:Form[models.Forms.LoginData],request:RequestHeader,messages:Messages,flash:Flash): play.twirl.api.HtmlFormat.Appendable = apply(form)(request,messages,flash)

  def f:((Form[models.Forms.LoginData]) => (RequestHeader,Messages,Flash) => play.twirl.api.HtmlFormat.Appendable) = (form) => (request,messages,flash) => apply(form)(request,messages,flash)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  SOURCE: app/views/auth/login.scala.html
                  HASH: 53572d3d97b0192740e58e308246161393a1ac4f
                  MATRIX: 988->1|1185->105|1212->107|1232->119|1271->121|1300->124|1583->380|1598->386|1654->433|1694->435|1737->451|1752->457|1788->472|1832->489|1865->513|1905->515|1950->532|2029->584|2042->588|2090->615|2135->632|2188->654|2232->670|2416->827|2438->840|2465->846|2542->896|2564->909|2583->919|2632->930|2679->949|2732->975|2746->980|2775->988|2830->1012|2873->1027|3129->1256|3154->1272|3173->1282|3222->1293|3269->1312|3322->1338|3336->1343|3365->1351|3420->1375|3463->1390|3641->1541|3656->1547|3703->1573|3804->1643|3843->1654|3929->1710
                  LINES: 28->1|33->2|34->3|34->3|34->3|35->4|43->12|43->12|43->12|43->12|44->13|44->13|44->13|46->15|46->15|46->15|47->16|48->17|48->17|48->17|49->18|50->19|52->21|54->23|54->23|54->23|55->24|55->24|55->24|55->24|56->25|56->25|56->25|56->25|57->26|58->27|63->32|63->32|63->32|63->32|64->33|64->33|64->33|64->33|65->34|66->35|70->39|70->39|70->39|72->41|73->42|78->47
                  -- GENERATED --
              */
          