
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

object editTea extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template5[models.Tea,Form[models.Forms.UpdateTeaData],RequestHeader,Messages,Flash,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(tea: models.Tea, teaForm: Form[models.Forms.UpdateTeaData])(implicit request: RequestHeader, messages: Messages, flash: Flash):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {
/*3.2*/import helper._
/*4.2*/import java.time.format.DateTimeFormatter
/*5.2*/import java.util.Locale
/*6.2*/import models.TeaType
/*7.2*/import models.TeaStatus
/*8.2*/import play.filters.csrf.CSRF


Seq[Any](format.raw/*2.1*/("""
"""),format.raw/*9.1*/("""
"""),_display_(/*10.2*/main("お茶の編集")/*10.15*/ {_display_(Seq[Any](format.raw/*10.17*/("""
  """),format.raw/*11.3*/("""<div class="container mx-auto px-4 py-8">
    <h1 class="text-3xl font-bold mb-8">お茶の編集</h1>

    """),_display_(/*14.6*/flash/*14.11*/.get("error").map/*14.28*/ { message =>_display_(Seq[Any](format.raw/*14.41*/("""
      """),format.raw/*15.7*/("""<div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative mb-4" role="alert">
        <span class="block sm:inline">"""),_display_(/*16.40*/message),format.raw/*16.47*/("""</span>
      </div>
    """)))}),format.raw/*18.6*/("""

    """),_display_(/*20.6*/helper/*20.12*/.form(action = routes.TeaController.update(tea.id.get), Symbol("class") -> "space-y-6")/*20.99*/ {_display_(Seq[Any](format.raw/*20.101*/("""
      """),_display_(/*21.8*/CSRF/*21.12*/.formField),format.raw/*21.22*/("""

      """),format.raw/*23.7*/("""<div class="space-y-4">
        """),_display_(/*24.10*/helper/*24.16*/.inputText(
          teaForm("name"),
          Symbol("class") -> "mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-300 focus:ring focus:ring-indigo-200 focus:ring-opacity-50",
          Symbol("_label") -> "名前",
          Symbol("_help") -> ""
        )),format.raw/*29.10*/("""

        """),format.raw/*31.9*/("""<div class="form-group">
          <label for="teaType" class="block text-sm font-medium text-gray-700">種類</label>
          <select name="teaType" id="teaType" class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-300 focus:ring focus:ring-indigo-200 focus:ring-opacity-50">
            """),_display_(/*34.14*/for(teaType <- TeaType.values.toList.sortBy(_.toString)) yield /*34.70*/ {_display_(Seq[Any](format.raw/*34.72*/("""
              """),format.raw/*35.15*/("""<option value=""""),_display_(/*35.31*/teaType),format.raw/*35.38*/("""" """),_display_(/*35.41*/if(teaForm("teaType").value.contains(teaType.toString))/*35.96*/{_display_(Seq[Any](format.raw/*35.97*/("""selected""")))}),format.raw/*35.106*/(""">"""),_display_(/*35.108*/{TeaType.TeaTypeOps(teaType).displayName}),format.raw/*35.149*/("""</option>
            """)))}),format.raw/*36.14*/("""
          """),format.raw/*37.11*/("""</select>
        </div>

        <div class="form-group">
          <label for="status" class="block text-sm font-medium text-gray-700">状態</label>
          <select name="status" id="status" class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-300 focus:ring focus:ring-indigo-200 focus:ring-opacity-50">
            """),_display_(/*43.14*/for(status <- TeaStatus.values.toList.sortBy(_.toString)) yield /*43.71*/ {_display_(Seq[Any](format.raw/*43.73*/("""
              """),format.raw/*44.15*/("""<option value=""""),_display_(/*44.31*/status),format.raw/*44.37*/("""" """),_display_(/*44.40*/if(teaForm("status").value.contains(status.toString))/*44.93*/{_display_(Seq[Any](format.raw/*44.94*/("""selected""")))}),format.raw/*44.103*/(""">"""),_display_(/*44.105*/{TeaStatus.TeaStatusOps(status).displayName}),format.raw/*44.149*/("""</option>
            """)))}),format.raw/*45.14*/("""
          """),format.raw/*46.11*/("""</select>
        </div>

        """),_display_(/*49.10*/helper/*49.16*/.inputDate(
          teaForm("purchaseDate"),
          Symbol("class") -> "mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-300 focus:ring focus:ring-indigo-200 focus:ring-opacity-50",
          Symbol("_label") -> "購入日",
          Symbol("_help") -> ""
        )),format.raw/*54.10*/("""

        """),_display_(/*56.10*/helper/*56.16*/.inputText(
          teaForm("price"),
          Symbol("type") -> "number",
          Symbol("step") -> "0.01",
          Symbol("class") -> "mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-300 focus:ring focus:ring-indigo-200 focus:ring-opacity-50",
          Symbol("_label") -> "価格（円）",
          Symbol("_help") -> ""
        )),format.raw/*63.10*/("""

        """),_display_(/*65.10*/helper/*65.16*/.inputText(
          teaForm("weight"),
          Symbol("type") -> "number",
          Symbol("step") -> "0.01",
          Symbol("class") -> "mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-300 focus:ring focus:ring-indigo-200 focus:ring-opacity-50",
          Symbol("_label") -> "重量（g）",
          Symbol("_help") -> ""
        )),format.raw/*72.10*/("""

        """),_display_(/*74.10*/helper/*74.16*/.textarea(
          teaForm("notes"),
          Symbol("class") -> "mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-300 focus:ring focus:ring-indigo-200 focus:ring-opacity-50",
          Symbol("_label") -> "メモ",
          Symbol("_help") -> "",
          Symbol("rows") -> "3"
        )),format.raw/*80.10*/("""
      """),format.raw/*81.7*/("""</div>

      <div class="flex justify-between">
        <button type="submit" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline">
          更新
        </button>
        <a href=""""),_display_(/*87.19*/routes/*87.25*/.TeaController.show(tea.id.get)),format.raw/*87.56*/("""" class="bg-gray-500 hover:bg-gray-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline">
          キャンセル
        </a>
      </div>
    """)))}),format.raw/*91.6*/("""
  """),format.raw/*92.3*/("""</div>
""")))}),format.raw/*93.2*/(""" """))
      }
    }
  }

  def render(tea:models.Tea,teaForm:Form[models.Forms.UpdateTeaData],request:RequestHeader,messages:Messages,flash:Flash): play.twirl.api.HtmlFormat.Appendable = apply(tea,teaForm)(request,messages,flash)

  def f:((models.Tea,Form[models.Forms.UpdateTeaData]) => (RequestHeader,Messages,Flash) => play.twirl.api.HtmlFormat.Appendable) = (tea,teaForm) => (request,messages,flash) => apply(tea,teaForm)(request,messages,flash)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  SOURCE: app/views/editTea.scala.html
                  HASH: dad3511bffe84d1d23e14d52485fff59bc121779
                  MATRIX: 1000->1|1200->131|1223->148|1272->191|1303->216|1332->239|1363->264|1421->129|1448->294|1476->296|1498->309|1538->311|1568->314|1693->413|1707->418|1733->435|1784->448|1818->455|1989->599|2017->606|2073->632|2106->639|2121->645|2217->732|2258->734|2292->742|2305->746|2336->756|2371->764|2431->797|2446->803|2749->1085|2786->1095|3130->1412|3202->1468|3242->1470|3285->1485|3328->1501|3356->1508|3386->1511|3450->1566|3489->1567|3530->1576|3560->1578|3623->1619|3677->1642|3716->1653|4091->2001|4164->2058|4204->2060|4247->2075|4290->2091|4317->2097|4347->2100|4409->2153|4448->2154|4489->2163|4519->2165|4585->2209|4639->2232|4678->2243|4740->2278|4755->2284|5067->2575|5105->2586|5120->2592|5501->2952|5539->2963|5554->2969|5936->3330|5974->3341|5989->3347|6325->3662|6359->3669|6632->3915|6647->3921|6699->3952|6896->4119|6926->4122|6964->4130
                  LINES: 28->1|31->3|32->4|33->5|34->6|35->7|36->8|39->2|40->9|41->10|41->10|41->10|42->11|45->14|45->14|45->14|45->14|46->15|47->16|47->16|49->18|51->20|51->20|51->20|51->20|52->21|52->21|52->21|54->23|55->24|55->24|60->29|62->31|65->34|65->34|65->34|66->35|66->35|66->35|66->35|66->35|66->35|66->35|66->35|66->35|67->36|68->37|74->43|74->43|74->43|75->44|75->44|75->44|75->44|75->44|75->44|75->44|75->44|75->44|76->45|77->46|80->49|80->49|85->54|87->56|87->56|94->63|96->65|96->65|103->72|105->74|105->74|111->80|112->81|118->87|118->87|118->87|122->91|123->92|124->93
                  -- GENERATED --
              */
          