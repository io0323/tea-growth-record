
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

object showTea extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template4[models.Tea,RequestHeader,Messages,Flash,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(tea: models.Tea)(implicit request: RequestHeader, messages: Messages, flash: Flash):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.1*/("""
"""),_display_(/*3.2*/main("お茶の詳細")/*3.15*/ {_display_(Seq[Any](format.raw/*3.17*/("""
  """),format.raw/*4.3*/("""<div class="container mx-auto px-4 py-8">
    <div class="flex justify-between items-center mb-8">
      <h1 class="text-3xl font-bold">お茶の詳細</h1>
      <div class="space-x-4">
        <a href=""""),_display_(/*8.19*/routes/*8.25*/.TeaController.edit(tea.id)),format.raw/*8.52*/("""" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline">
          編集
        </a>
        <a href=""""),_display_(/*11.19*/routes/*11.25*/.TeaController.index()),format.raw/*11.47*/("""" class="bg-gray-500 hover:bg-gray-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline">
          一覧に戻る
        </a>
      </div>
    </div>

    <div class="bg-white shadow-md rounded-lg overflow-hidden">
      <div class="px-6 py-4">
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div>
            <h2 class="text-lg font-semibold text-gray-700">基本情報</h2>
            <dl class="mt-2 space-y-2">
              <div>
                <dt class="text-sm font-medium text-gray-500">名前</dt>
                <dd class="mt-1 text-sm text-gray-900">"""),_display_(/*25.57*/tea/*25.60*/.name),format.raw/*25.65*/("""</dd>
              </div>
              <div>
                <dt class="text-sm font-medium text-gray-500">種類</dt>
                <dd class="mt-1 text-sm text-gray-900">"""),_display_(/*29.57*/tea/*29.60*/.teaType),format.raw/*29.68*/("""</dd>
              </div>
              <div>
                <dt class="text-sm font-medium text-gray-500">状態</dt>
                <dd class="mt-1 text-sm text-gray-900">"""),_display_(/*33.57*/tea/*33.60*/.status),format.raw/*33.67*/("""</dd>
              </div>
            </dl>
          </div>
          <div>
            <h2 class="text-lg font-semibold text-gray-700">購入情報</h2>
            <dl class="mt-2 space-y-2">
              <div>
                <dt class="text-sm font-medium text-gray-500">購入日</dt>
                <dd class="mt-1 text-sm text-gray-900">"""),_display_(/*42.57*/tea/*42.60*/.purchaseDate),format.raw/*42.73*/("""</dd>
              </div>
              <div>
                <dt class="text-sm font-medium text-gray-500">価格</dt>
                <dd class="mt-1 text-sm text-gray-900">"""),_display_(/*46.57*/tea/*46.60*/.price),format.raw/*46.66*/("""</dd>
              </div>
              <div>
                <dt class="text-sm font-medium text-gray-500">重量</dt>
                <dd class="mt-1 text-sm text-gray-900">"""),_display_(/*50.57*/tea/*50.60*/.weight),format.raw/*50.67*/("""</dd>
              </div>
            </dl>
          </div>
        </div>
        <div class="mt-6">
          <h2 class="text-lg font-semibold text-gray-700">メモ</h2>
          <div class="mt-2 text-sm text-gray-900">
            """),_display_(/*58.14*/tea/*58.17*/.notes.getOrElse("メモはありません。")),format.raw/*58.46*/("""
          """),format.raw/*59.11*/("""</div>
        </div>
      </div>
    </div>
  </div>
""")))}),format.raw/*64.2*/(""" """))
      }
    }
  }

  def render(tea:models.Tea,request:RequestHeader,messages:Messages,flash:Flash): play.twirl.api.HtmlFormat.Appendable = apply(tea)(request,messages,flash)

  def f:((models.Tea) => (RequestHeader,Messages,Flash) => play.twirl.api.HtmlFormat.Appendable) = (tea) => (request,messages,flash) => apply(tea)(request,messages,flash)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  SOURCE: app/views/showTea.scala.html
                  HASH: de53549b8998f37b97fef133e2b2914b25c6188d
                  MATRIX: 967->1|1145->86|1172->88|1193->101|1232->103|1261->106|1482->301|1496->307|1543->334|1734->498|1749->504|1792->526|2419->1126|2431->1129|2457->1134|2657->1307|2669->1310|2698->1318|2898->1491|2910->1494|2938->1501|3300->1836|3312->1839|3346->1852|3546->2025|3558->2028|3585->2034|3785->2207|3797->2210|3825->2217|4086->2451|4098->2454|4148->2483|4187->2494|4273->2550
                  LINES: 28->1|33->2|34->3|34->3|34->3|35->4|39->8|39->8|39->8|42->11|42->11|42->11|56->25|56->25|56->25|60->29|60->29|60->29|64->33|64->33|64->33|73->42|73->42|73->42|77->46|77->46|77->46|81->50|81->50|81->50|89->58|89->58|89->58|90->59|95->64
                  -- GENERATED --
              */
          