
package views.html.tea

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

object create extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template3[Form[models.Forms.CreateTeaData],RequestHeader,Messages,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(form: Form[models.Forms.CreateTeaData])(implicit request: RequestHeader, messages: Messages):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.1*/("""
"""),_display_(/*3.2*/main("お茶を登録")/*3.15*/ {_display_(Seq[Any](format.raw/*3.17*/("""
  """),format.raw/*4.3*/("""<div class="container mx-auto px-4 py-8">
    <h1 class="text-3xl font-bold mb-8">お茶を登録</h1>

    """),_display_(/*7.6*/helper/*7.12*/.form(routes.TeaController.save(), Symbol("class") -> "space-y-4")/*7.78*/ {_display_(Seq[Any](format.raw/*7.80*/("""
      """),_display_(/*8.8*/helper/*8.14*/.CSRF.formField),format.raw/*8.29*/("""

      """),_display_(/*10.8*/helper/*10.14*/.inputText(
        form("name"),
        Symbol("class") -> "form-input mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-300 focus:ring focus:ring-indigo-200 focus:ring-opacity-50",
        Symbol("_label") -> "名前",
        Symbol("_help") -> ""
      )),format.raw/*15.8*/("""

      """),_display_(/*17.8*/helper/*17.14*/.select(
        form("teaType"),
        models.TeaType.values.map(t => t.toString -> t.displayName).toSeq,
        Symbol("class") -> "form-select mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-300 focus:ring focus:ring-indigo-200 focus:ring-opacity-50",
        Symbol("_label") -> "種類",
        Symbol("_help") -> ""
      )),format.raw/*23.8*/("""

      """),_display_(/*25.8*/helper/*25.14*/.select(
        form("status"),
        models.TeaStatus.values.map(s => s.toString -> s.displayName).toSeq,
        Symbol("class") -> "form-select mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-300 focus:ring focus:ring-indigo-200 focus:ring-opacity-50",
        Symbol("_label") -> "状態",
        Symbol("_help") -> ""
      )),format.raw/*31.8*/("""

      """),_display_(/*33.8*/helper/*33.14*/.inputDate(
        form("purchaseDate"),
        Symbol("class") -> "form-input mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-300 focus:ring focus:ring-indigo-200 focus:ring-opacity-50",
        Symbol("_label") -> "購入日",
        Symbol("_help") -> ""
      )),format.raw/*38.8*/("""

      """),_display_(/*40.8*/helper/*40.14*/.inputText(
        form("price"),
        Symbol("class") -> "form-input mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-300 focus:ring focus:ring-indigo-200 focus:ring-opacity-50",
        Symbol("_label") -> "価格",
        Symbol("_help") -> "円"
      )),format.raw/*45.8*/("""

      """),_display_(/*47.8*/helper/*47.14*/.inputText(
        form("amount"),
        Symbol("class") -> "form-input mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-300 focus:ring focus:ring-indigo-200 focus:ring-opacity-50",
        Symbol("_label") -> "量",
        Symbol("_help") -> "g"
      )),format.raw/*52.8*/("""

      """),_display_(/*54.8*/helper/*54.14*/.textarea(
        form("memo"),
        Symbol("class") -> "form-textarea mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-300 focus:ring focus:ring-indigo-200 focus:ring-opacity-50",
        Symbol("_label") -> "メモ",
        Symbol("_help") -> "",
        Symbol("rows") -> "3"
      )),format.raw/*60.8*/("""

      """),format.raw/*62.7*/("""<div class="flex justify-between">
        <button type="submit" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline">
          登録
        </button>
        <a href=""""),_display_(/*66.19*/routes/*66.25*/.TeaController.index()),format.raw/*66.47*/("""" class="bg-gray-500 hover:bg-gray-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline">
          キャンセル
        </a>
      </div>
    """)))}),format.raw/*70.6*/("""
  """),format.raw/*71.3*/("""</div>
""")))}),format.raw/*72.2*/(""" """))
      }
    }
  }

  def render(form:Form[models.Forms.CreateTeaData],request:RequestHeader,messages:Messages): play.twirl.api.HtmlFormat.Appendable = apply(form)(request,messages)

  def f:((Form[models.Forms.CreateTeaData]) => (RequestHeader,Messages) => play.twirl.api.HtmlFormat.Appendable) = (form) => (request,messages) => apply(form)(request,messages)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  SOURCE: app/views/tea/create.scala.html
                  HASH: e3c486dbf9baf010eabb72a4f334ff6db7cce0fb
                  MATRIX: 986->1|1173->95|1200->97|1221->110|1260->112|1289->115|1413->214|1427->220|1501->286|1540->288|1573->296|1587->302|1622->317|1657->326|1672->332|1972->612|2007->621|2022->627|2398->983|2433->992|2448->998|2825->1355|2860->1364|2875->1370|3184->1659|3219->1668|3234->1674|3536->1956|3571->1965|3586->1971|3888->2253|3923->2262|3938->2268|4271->2581|4306->2589|4565->2821|4580->2827|4623->2849|4820->3016|4850->3019|4888->3027
                  LINES: 28->1|33->2|34->3|34->3|34->3|35->4|38->7|38->7|38->7|38->7|39->8|39->8|39->8|41->10|41->10|46->15|48->17|48->17|54->23|56->25|56->25|62->31|64->33|64->33|69->38|71->40|71->40|76->45|78->47|78->47|83->52|85->54|85->54|91->60|93->62|97->66|97->66|97->66|101->70|102->71|103->72
                  -- GENERATED --
              */
          