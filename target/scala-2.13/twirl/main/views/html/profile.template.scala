
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

object profile extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template5[Form[models.Forms.UpdateProfileData],Form[models.Forms.PasswordUpdateData],RequestHeader,Messages,Flash,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(form: Form[models.Forms.UpdateProfileData], passwordForm: Form[models.Forms.PasswordUpdateData])(implicit request: RequestHeader, messages: Messages, flash: Flash):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.1*/("""
"""),_display_(/*3.2*/main("プロフィール")/*3.16*/ {_display_(Seq[Any](format.raw/*3.18*/("""
  """),format.raw/*4.3*/("""<div class="container mx-auto px-4 py-8">
    <div class="max-w-md mx-auto bg-white rounded-lg shadow-md p-6">
      <h1 class="text-2xl font-bold mb-6">プロフィール</h1>

      """),_display_(/*8.8*/if(flash.get("success").isDefined)/*8.42*/ {_display_(Seq[Any](format.raw/*8.44*/("""
        """),format.raw/*9.9*/("""<div class="bg-green-100 border border-green-400 text-green-700 px-4 py-3 rounded mb-4">
          """),_display_(/*10.12*/flash/*10.17*/.get("success")),format.raw/*10.32*/("""
        """),format.raw/*11.9*/("""</div>
      """)))}),format.raw/*12.8*/("""

      """),format.raw/*14.7*/("""<div class="space-y-6">
        <div>
          <h2 class="text-xl font-semibold mb-4">プロフィール情報</h2>
          """),_display_(/*17.12*/helper/*17.18*/.form(routes.TeaController.updateProfile(), Symbol("class") -> "space-y-4")/*17.93*/ {_display_(Seq[Any](format.raw/*17.95*/("""
            """),_display_(/*18.14*/helper/*18.20*/.CSRF.formField),format.raw/*18.35*/("""
            """),format.raw/*19.13*/("""<div>
              """),_display_(/*20.16*/helper/*20.22*/.inputText(form("name"), Symbol("class") -> "form-input w-full", Symbol("placeholder") -> "名前")),format.raw/*20.117*/("""
            """),format.raw/*21.13*/("""</div>
            <div>
              """),_display_(/*23.16*/helper/*23.22*/.inputText(form("email"), Symbol("class") -> "form-input w-full", Symbol("placeholder") -> "メールアドレス")),format.raw/*23.123*/("""
            """),format.raw/*24.13*/("""</div>
            <div>
              """),_display_(/*26.16*/helper/*26.22*/.inputPassword(form("password"), Symbol("class") -> "form-input w-full", Symbol("placeholder") -> "パスワード")),format.raw/*26.128*/("""
            """),format.raw/*27.13*/("""</div>
            <button type="submit" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">
              プロフィールを更新
            </button>
          """)))}),format.raw/*31.12*/("""
        """),format.raw/*32.9*/("""</div>

        <div>
          <h2 class="text-xl font-semibold mb-4">パスワード変更</h2>
          """),_display_(/*36.12*/helper/*36.18*/.form(routes.TeaController.updatePassword(), Symbol("class") -> "space-y-4")/*36.94*/ {_display_(Seq[Any](format.raw/*36.96*/("""
            """),_display_(/*37.14*/helper/*37.20*/.CSRF.formField),format.raw/*37.35*/("""
            """),format.raw/*38.13*/("""<div>
              """),_display_(/*39.16*/helper/*39.22*/.inputPassword(passwordForm("currentPassword"), Symbol("class") -> "form-input w-full", Symbol("placeholder") -> "現在のパスワード")),format.raw/*39.146*/("""
            """),format.raw/*40.13*/("""</div>
            <div>
              """),_display_(/*42.16*/helper/*42.22*/.inputPassword(passwordForm("newPassword"), Symbol("class") -> "form-input w-full", Symbol("placeholder") -> "新しいパスワード")),format.raw/*42.142*/("""
            """),format.raw/*43.13*/("""</div>
            <button type="submit" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">
              パスワードを変更
            </button>
          """)))}),format.raw/*47.12*/("""
        """),format.raw/*48.9*/("""</div>
      </div>
    </div>
  </div>
""")))}),format.raw/*52.2*/(""" """))
      }
    }
  }

  def render(form:Form[models.Forms.UpdateProfileData],passwordForm:Form[models.Forms.PasswordUpdateData],request:RequestHeader,messages:Messages,flash:Flash): play.twirl.api.HtmlFormat.Appendable = apply(form,passwordForm)(request,messages,flash)

  def f:((Form[models.Forms.UpdateProfileData],Form[models.Forms.PasswordUpdateData]) => (RequestHeader,Messages,Flash) => play.twirl.api.HtmlFormat.Appendable) = (form,passwordForm) => (request,messages,flash) => apply(form,passwordForm)(request,messages,flash)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  SOURCE: app/views/profile.scala.html
                  HASH: 25df7fa500637d7566c35fb7f6ffdfcf25b5373e
                  MATRIX: 1031->1|1289->166|1316->168|1338->182|1377->184|1406->187|1604->360|1646->394|1685->396|1720->405|1847->505|1861->510|1897->525|1933->534|1977->548|2012->556|2151->668|2166->674|2250->749|2290->751|2331->765|2346->771|2382->786|2423->799|2471->820|2486->826|2603->921|2644->934|2711->974|2726->980|2849->1081|2890->1094|2957->1134|2972->1140|3100->1246|3141->1259|3348->1435|3384->1444|3506->1539|3521->1545|3606->1621|3646->1623|3687->1637|3702->1643|3738->1658|3779->1671|3827->1692|3842->1698|3988->1822|4029->1835|4096->1875|4111->1881|4253->2001|4294->2014|4500->2189|4536->2198|4607->2239
                  LINES: 28->1|33->2|34->3|34->3|34->3|35->4|39->8|39->8|39->8|40->9|41->10|41->10|41->10|42->11|43->12|45->14|48->17|48->17|48->17|48->17|49->18|49->18|49->18|50->19|51->20|51->20|51->20|52->21|54->23|54->23|54->23|55->24|57->26|57->26|57->26|58->27|62->31|63->32|67->36|67->36|67->36|67->36|68->37|68->37|68->37|69->38|70->39|70->39|70->39|71->40|73->42|73->42|73->42|74->43|78->47|79->48|83->52
                  -- GENERATED --
              */
          