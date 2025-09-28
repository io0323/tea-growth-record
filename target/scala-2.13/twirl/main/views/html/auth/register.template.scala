
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

object register extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template4[Form[models.Forms.RegisterData],RequestHeader,Messages,Flash,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(form: Form[models.Forms.RegisterData])(implicit request: RequestHeader, messages: Messages, flash: Flash):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.1*/("""
"""),_display_(/*3.2*/main("新規登録")/*3.14*/ {_display_(Seq[Any](format.raw/*3.16*/("""
  """),format.raw/*4.3*/("""<div class="container mt-5">
    <div class="row justify-content-center">
      <div class="col-md-6">
        <div class="card">
          <div class="card-header">
            <h2>新規登録</h2>
          </div>
          <div class="card-body">
            """),_display_(/*12.14*/helper/*12.20*/.form(action = routes.AuthController.doRegister())/*12.70*/ {_display_(Seq[Any](format.raw/*12.72*/("""
              """),_display_(/*13.16*/helper/*13.22*/.CSRF.formField),format.raw/*13.37*/("""

              """),_display_(/*15.16*/if(form.hasGlobalErrors)/*15.40*/ {_display_(Seq[Any](format.raw/*15.42*/("""
                """),format.raw/*16.17*/("""<div class="alert alert-danger">
                  """),_display_(/*17.20*/form/*17.24*/.globalError.map(_.message)),format.raw/*17.51*/("""
                """),format.raw/*18.17*/("""</div>
              """)))}),format.raw/*19.16*/("""

              """),format.raw/*21.15*/("""<div class="mb-3">
                <label for="name" class="form-label">名前</label>
                <input type="text" name="name" id="name" value=""""),_display_(/*23.66*/form("name")/*23.78*/.value),format.raw/*23.84*/("""" class="form-control" required>
                """),_display_(/*24.18*/form("name")/*24.30*/.error.map/*24.40*/ { error =>_display_(Seq[Any](format.raw/*24.51*/("""
                  """),format.raw/*25.19*/("""<div class="text-danger">"""),_display_(/*25.45*/error/*25.50*/.message),format.raw/*25.58*/("""</div>
                """)))}),format.raw/*26.18*/("""
              """),format.raw/*27.15*/("""</div>

              <div class="mb-3">
                <label for="email" class="form-label">メールアドレス</label>
                <input type="email" name="email" id="email" value=""""),_display_(/*31.69*/form("email")/*31.82*/.value),format.raw/*31.88*/("""" class="form-control" required>
                """),_display_(/*32.18*/form("email")/*32.31*/.error.map/*32.41*/ { error =>_display_(Seq[Any](format.raw/*32.52*/("""
                  """),format.raw/*33.19*/("""<div class="text-danger">"""),_display_(/*33.45*/error/*33.50*/.message),format.raw/*33.58*/("""</div>
                """)))}),format.raw/*34.18*/("""
              """),format.raw/*35.15*/("""</div>

              <div class="mb-3">
                <label for="password" class="form-label">パスワード</label>
                <input type="password" name="password" id="password" class="form-control" required>
                """),_display_(/*40.18*/form("password")/*40.34*/.error.map/*40.44*/ { error =>_display_(Seq[Any](format.raw/*40.55*/("""
                  """),format.raw/*41.19*/("""<div class="text-danger">"""),_display_(/*41.45*/error/*41.50*/.message),format.raw/*41.58*/("""</div>
                """)))}),format.raw/*42.18*/("""
              """),format.raw/*43.15*/("""</div>

              <div class="mb-3">
                <label for="confirmPassword" class="form-label">パスワード（確認）</label>
                <input type="password" name="confirmPassword" id="confirmPassword" class="form-control" required>
                """),_display_(/*48.18*/form("confirmPassword")/*48.41*/.error.map/*48.51*/ { error =>_display_(Seq[Any](format.raw/*48.62*/("""
                  """),format.raw/*49.19*/("""<div class="text-danger">"""),_display_(/*49.45*/error/*49.50*/.message),format.raw/*49.58*/("""</div>
                """)))}),format.raw/*50.18*/("""
              """),format.raw/*51.15*/("""</div>

              <div class="d-grid gap-2">
                <button type="submit" class="btn btn-primary">登録</button>
                <a href=""""),_display_(/*55.27*/routes/*55.33*/.AuthController.login()),format.raw/*55.56*/("""" class="btn btn-link">ログインはこちら</a>
              </div>
            """)))}),format.raw/*57.14*/("""
          """),format.raw/*58.11*/("""</div>
        </div>
      </div>
    </div>
  </div>
""")))}),format.raw/*63.2*/(""" """))
      }
    }
  }

  def render(form:Form[models.Forms.RegisterData],request:RequestHeader,messages:Messages,flash:Flash): play.twirl.api.HtmlFormat.Appendable = apply(form)(request,messages,flash)

  def f:((Form[models.Forms.RegisterData]) => (RequestHeader,Messages,Flash) => play.twirl.api.HtmlFormat.Appendable) = (form) => (request,messages,flash) => apply(form)(request,messages,flash)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  SOURCE: app/views/auth/register.scala.html
                  HASH: 4c2cb54744c51081f7d1d9d0fad004773d564655
                  MATRIX: 994->1|1194->108|1221->110|1241->122|1280->124|1309->127|1592->383|1607->389|1666->439|1706->441|1749->457|1764->463|1800->478|1844->495|1877->519|1917->521|1962->538|2041->590|2054->594|2102->621|2147->638|2200->660|2244->676|2419->824|2440->836|2467->842|2544->892|2565->904|2584->914|2633->925|2680->944|2733->970|2747->975|2776->983|2831->1007|2874->1022|3080->1201|3102->1214|3129->1220|3206->1270|3228->1283|3247->1293|3296->1304|3343->1323|3396->1349|3410->1354|3439->1362|3494->1386|3537->1401|3793->1630|3818->1646|3837->1656|3886->1667|3933->1686|3986->1712|4000->1717|4029->1725|4084->1749|4127->1764|4408->2018|4440->2041|4459->2051|4508->2062|4555->2081|4608->2107|4622->2112|4651->2120|4706->2144|4749->2159|4925->2308|4940->2314|4984->2337|5085->2407|5124->2418|5210->2474
                  LINES: 28->1|33->2|34->3|34->3|34->3|35->4|43->12|43->12|43->12|43->12|44->13|44->13|44->13|46->15|46->15|46->15|47->16|48->17|48->17|48->17|49->18|50->19|52->21|54->23|54->23|54->23|55->24|55->24|55->24|55->24|56->25|56->25|56->25|56->25|57->26|58->27|62->31|62->31|62->31|63->32|63->32|63->32|63->32|64->33|64->33|64->33|64->33|65->34|66->35|71->40|71->40|71->40|71->40|72->41|72->41|72->41|72->41|73->42|74->43|79->48|79->48|79->48|79->48|80->49|80->49|80->49|80->49|81->50|82->51|86->55|86->55|86->55|88->57|89->58|94->63
                  -- GENERATED --
              */
          