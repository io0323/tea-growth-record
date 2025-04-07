
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

object createTea extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template3[Form[forms.Forms.CreateTeaData],RequestHeader,MessagesProvider,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(form: Form[forms.Forms.CreateTeaData])(implicit request: RequestHeader, messagesProvider: MessagesProvider):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.1*/("""
"""),_display_(/*3.2*/main("お茶の新規登録")/*3.17*/ {_display_(Seq[Any](format.raw/*3.19*/("""
  """),format.raw/*4.3*/("""<div class="container mt-4">
    <div class="row">
      <div class="col-md-8 offset-md-2">
        <h1>お茶の新規登録</h1>

        """),_display_(/*9.10*/request/*9.17*/.flash.get("success").map/*9.42*/ { message =>_display_(Seq[Any](format.raw/*9.55*/("""
          """),format.raw/*10.11*/("""<div class="alert alert-success">"""),_display_(/*10.45*/message),format.raw/*10.52*/("""</div>
        """)))}),format.raw/*11.10*/("""

        """),_display_(/*13.10*/request/*13.17*/.flash.get("error").map/*13.40*/ { message =>_display_(Seq[Any](format.raw/*13.53*/("""
          """),format.raw/*14.11*/("""<div class="alert alert-danger">"""),_display_(/*14.44*/message),format.raw/*14.51*/("""</div>
        """)))}),format.raw/*15.10*/("""

        """),_display_(/*17.10*/helper/*17.16*/.form(routes.HomeController.doCreateTea(), Symbol("class") -> "mt-4")/*17.85*/ {_display_(Seq[Any](format.raw/*17.87*/("""
          """),_display_(/*18.12*/helper/*18.18*/.CSRF.formField),format.raw/*18.33*/("""

          """),format.raw/*20.11*/("""<div class="form-group">
            """),_display_(/*21.14*/helper/*21.20*/.inputText(
              form("name"),
              Symbol("class") -> "form-control",
              Symbol("_label") -> "名前",
              Symbol("_help") -> "お茶の名前を入力してください"
            )),format.raw/*26.14*/("""
          """),format.raw/*27.11*/("""</div>

          <div class="form-group">
            """),_display_(/*30.14*/helper/*30.20*/.inputText(
              form("teaType"),
              Symbol("class") -> "form-control",
              Symbol("_label") -> "種類",
              Symbol("_help") -> "お茶の種類を入力してください"
            )),format.raw/*35.14*/("""
          """),format.raw/*36.11*/("""</div>

          <div class="form-group">
            """),_display_(/*39.14*/helper/*39.20*/.inputText(
              form("origin"),
              Symbol("class") -> "form-control",
              Symbol("_label") -> "産地",
              Symbol("_help") -> "お茶の産地を入力してください"
            )),format.raw/*44.14*/("""
          """),format.raw/*45.11*/("""</div>

          <div class="form-group">
            """),_display_(/*48.14*/helper/*48.20*/.inputDate(
              form("purchaseDate"),
              Symbol("class") -> "form-control",
              Symbol("_label") -> "購入日",
              Symbol("_help") -> "お茶の購入日を選択してください"
            )),format.raw/*53.14*/("""
          """),format.raw/*54.11*/("""</div>

          <div class="form-group">
            """),_display_(/*57.14*/helper/*57.20*/.select(
              form("status"),
              options = Seq(
                "未開封" -> "未開封",
                "開封済み" -> "開封済み",
                "飲み終わり" -> "飲み終わり"
              ),
              Symbol("class") -> "form-control",
              Symbol("_label") -> "状態",
              Symbol("_help") -> "お茶の現在の状態を選択してください"
            )),format.raw/*67.14*/("""
          """),format.raw/*68.11*/("""</div>

          <div class="form-group">
            """),_display_(/*71.14*/helper/*71.20*/.textarea(
              form("description"),
              Symbol("class") -> "form-control",
              Symbol("rows") -> 3,
              Symbol("_label") -> "説明",
              Symbol("_help") -> "お茶についての説明を入力してください（任意）"
            )),format.raw/*77.14*/("""
          """),format.raw/*78.11*/("""</div>

          <div class="form-group">
            """),_display_(/*81.14*/helper/*81.20*/.inputText(
              form("price"),
              Symbol("class") -> "form-control",
              Symbol("type") -> "number",
              Symbol("_label") -> "価格",
              Symbol("_help") -> "お茶の価格を入力してください（任意）"
            )),format.raw/*87.14*/("""
          """),format.raw/*88.11*/("""</div>

          <div class="form-group">
            """),_display_(/*91.14*/helper/*91.20*/.inputText(
              form("quantity"),
              Symbol("class") -> "form-control",
              Symbol("type") -> "number",
              Symbol("step") -> "0.1",
              Symbol("_label") -> "数量",
              Symbol("_help") -> "お茶の数量を入力してください（任意）"
            )),format.raw/*98.14*/("""
          """),format.raw/*99.11*/("""</div>

          <div class="form-group">
            """),_display_(/*102.14*/helper/*102.20*/.inputText(
              form("unit"),
              Symbol("class") -> "form-control",
              Symbol("_label") -> "単位",
              Symbol("_help") -> "数量の単位を入力してください（任意）"
            )),format.raw/*107.14*/("""
          """),format.raw/*108.11*/("""</div>

          <div class="form-group">
            """),_display_(/*111.14*/helper/*111.20*/.inputText(
              form("imageUrl"),
              Symbol("class") -> "form-control",
              Symbol("_label") -> "画像URL",
              Symbol("_help") -> "お茶の画像URLを入力してください（任意）"
            )),format.raw/*116.14*/("""
          """),format.raw/*117.11*/("""</div>

          <div class="form-group">
            <button type="submit" class="btn btn-primary">
              <i class="fas fa-save"></i> 保存
            </button>
            <a href=""""),_display_(/*123.23*/routes/*123.29*/.HomeController.dashboard()),format.raw/*123.56*/("""" class="btn btn-secondary">
              <i class="fas fa-times"></i> キャンセル
            </a>
          </div>
        """)))}),format.raw/*127.10*/("""
      """),format.raw/*128.7*/("""</div>
    </div>
  </div>
""")))}),format.raw/*131.2*/(""" """))
      }
    }
  }

  def render(form:Form[forms.Forms.CreateTeaData],request:RequestHeader,messagesProvider:MessagesProvider): play.twirl.api.HtmlFormat.Appendable = apply(form)(request,messagesProvider)

  def f:((Form[forms.Forms.CreateTeaData]) => (RequestHeader,MessagesProvider) => play.twirl.api.HtmlFormat.Appendable) = (form) => (request,messagesProvider) => apply(form)(request,messagesProvider)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  SOURCE: app/views/createTea.scala.html
                  HASH: 2edef600e36dae6d8447dc425db4f5d125d6833a
                  MATRIX: 789->1|991->110|1018->112|1041->127|1080->129|1109->132|1262->259|1277->266|1310->291|1360->304|1399->315|1460->349|1488->356|1535->372|1573->383|1589->390|1621->413|1672->426|1711->437|1771->470|1799->477|1846->493|1884->504|1899->510|1977->579|2017->581|2056->593|2071->599|2107->614|2147->626|2212->664|2227->670|2440->862|2479->873|2562->929|2577->935|2793->1130|2832->1141|2915->1197|2930->1203|3145->1397|3184->1408|3267->1464|3282->1470|3505->1672|3544->1683|3627->1739|3642->1745|4004->2086|4043->2097|4126->2153|4141->2159|4403->2400|4442->2411|4525->2467|4540->2473|4800->2712|4839->2723|4922->2779|4937->2785|5239->3066|5278->3077|5362->3133|5378->3139|5596->3335|5636->3346|5720->3402|5736->3408|5964->3614|6004->3625|6223->3816|6239->3822|6288->3849|6441->3970|6476->3977|6535->4005
                  LINES: 21->1|26->2|27->3|27->3|27->3|28->4|33->9|33->9|33->9|33->9|34->10|34->10|34->10|35->11|37->13|37->13|37->13|37->13|38->14|38->14|38->14|39->15|41->17|41->17|41->17|41->17|42->18|42->18|42->18|44->20|45->21|45->21|50->26|51->27|54->30|54->30|59->35|60->36|63->39|63->39|68->44|69->45|72->48|72->48|77->53|78->54|81->57|81->57|91->67|92->68|95->71|95->71|101->77|102->78|105->81|105->81|111->87|112->88|115->91|115->91|122->98|123->99|126->102|126->102|131->107|132->108|135->111|135->111|140->116|141->117|147->123|147->123|147->123|151->127|152->128|155->131
                  -- GENERATED --
              */
          