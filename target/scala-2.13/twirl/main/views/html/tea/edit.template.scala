
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

object edit extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template4[models.Tea,Form[models.Forms.UpdateTeaData],RequestHeader,Messages,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(tea: models.Tea, form: Form[models.Forms.UpdateTeaData])(implicit request: RequestHeader, messages: Messages):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.1*/("""
"""),_display_(/*3.2*/main("お茶を編集")/*3.15*/ {_display_(Seq[Any](format.raw/*3.17*/("""
  """),format.raw/*4.3*/("""<div class="container mx-auto px-4 py-8">
    <h1 class="text-3xl font-bold mb-8">お茶を編集</h1>

    """),_display_(/*7.6*/helper/*7.12*/.form(routes.TeaController.update(tea.id.get), Symbol("class") -> "space-y-4")/*7.90*/ {_display_(Seq[Any](format.raw/*7.92*/("""
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
          更新
        </button>
        <a href=""""),_display_(/*66.19*/routes/*66.25*/.TeaController.show(tea.id.get)),format.raw/*66.56*/("""" class="bg-gray-500 hover:bg-gray-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline">
          キャンセル
        </a>
      </div>
    """)))}),format.raw/*70.6*/("""
  """),format.raw/*71.3*/("""</div>
""")))}),format.raw/*72.2*/(""" """))
      }
    }
  }

  def render(tea:models.Tea,form:Form[models.Forms.UpdateTeaData],request:RequestHeader,messages:Messages): play.twirl.api.HtmlFormat.Appendable = apply(tea,form)(request,messages)

  def f:((models.Tea,Form[models.Forms.UpdateTeaData]) => (RequestHeader,Messages) => play.twirl.api.HtmlFormat.Appendable) = (tea,form) => (request,messages) => apply(tea,form)(request,messages)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  SOURCE: app/views/tea/edit.scala.html
                  HASH: df599d9cfafa03c059d390692d60826cbf36f312
                  MATRIX: 995->1|1199->112|1226->114|1247->127|1286->129|1315->132|1439->231|1453->237|1539->315|1578->317|1611->325|1625->331|1660->346|1695->355|1710->361|2010->641|2045->650|2060->656|2436->1012|2471->1021|2486->1027|2863->1384|2898->1393|2913->1399|3222->1688|3257->1697|3272->1703|3574->1985|3609->1994|3624->2000|3926->2282|3961->2291|3976->2297|4309->2610|4344->2618|4603->2850|4618->2856|4670->2887|4867->3054|4897->3057|4935->3065
                  LINES: 28->1|33->2|34->3|34->3|34->3|35->4|38->7|38->7|38->7|38->7|39->8|39->8|39->8|41->10|41->10|46->15|48->17|48->17|54->23|56->25|56->25|62->31|64->33|64->33|69->38|71->40|71->40|76->45|78->47|78->47|83->52|85->54|85->54|91->60|93->62|97->66|97->66|97->66|101->70|102->71|103->72
                  -- GENERATED --
              */
          