
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

object show extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template3[models.Tea,RequestHeader,Messages,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(tea: models.Tea)(implicit request: RequestHeader, messages: Messages):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.1*/("""
"""),_display_(/*3.2*/main("お茶の詳細")/*3.15*/ {_display_(Seq[Any](format.raw/*3.17*/("""
  """),format.raw/*4.3*/("""<div class="container mx-auto px-4 py-8">
    <div class="bg-white shadow overflow-hidden sm:rounded-lg">
      <div class="px-4 py-5 sm:px-6">
        <h1 class="text-3xl font-bold leading-6 text-gray-900">"""),_display_(/*7.65*/tea/*7.68*/.name),format.raw/*7.73*/("""</h1>
        <p class="mt-1 max-w-2xl text-sm text-gray-500">お茶の詳細情報</p>
      </div>
      <div class="border-t border-gray-200">
        <dl>
          <div class="bg-gray-50 px-4 py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
            <dt class="text-sm font-medium text-gray-500">種類</dt>
            <dd class="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2">"""),_display_(/*14.75*/tea/*14.78*/.teaType.displayName),format.raw/*14.98*/("""</dd>
          </div>
          <div class="bg-white px-4 py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
            <dt class="text-sm font-medium text-gray-500">状態</dt>
            <dd class="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2">"""),_display_(/*18.75*/tea/*18.78*/.status.displayName),format.raw/*18.97*/("""</dd>
          </div>
          <div class="bg-gray-50 px-4 py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
            <dt class="text-sm font-medium text-gray-500">購入日</dt>
            <dd class="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2">"""),_display_(/*22.75*/tea/*22.78*/.purchaseDate),format.raw/*22.91*/("""</dd>
          </div>
          <div class="bg-white px-4 py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
            <dt class="text-sm font-medium text-gray-500">価格</dt>
            <dd class="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2">"""),_display_(/*26.75*/tea/*26.78*/.price.map(p => s"${p}円").getOrElse("未設定")),format.raw/*26.120*/("""</dd>
          </div>
          <div class="bg-gray-50 px-4 py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
            <dt class="text-sm font-medium text-gray-500">量</dt>
            <dd class="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2">"""),_display_(/*30.75*/tea/*30.78*/.amount.map(a => s"${a}g").getOrElse("未設定")),format.raw/*30.121*/("""</dd>
          </div>
          <div class="bg-white px-4 py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
            <dt class="text-sm font-medium text-gray-500">メモ</dt>
            <dd class="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2">"""),_display_(/*34.75*/tea/*34.78*/.memo.getOrElse("未設定")),format.raw/*34.100*/("""</dd>
          </div>
        </dl>
      </div>
    </div>

    <div class="mt-8 flex justify-between">
      <div>
        <a href=""""),_display_(/*42.19*/routes/*42.25*/.TeaController.edit(tea.id.get)),format.raw/*42.56*/("""" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline">
          編集
        </a>
      </div>
      <div>
        """),_display_(/*47.10*/helper/*47.16*/.form(routes.TeaController.delete(tea.id.get))/*47.62*/ {_display_(Seq[Any](format.raw/*47.64*/("""
          """),_display_(/*48.12*/helper/*48.18*/.CSRF.formField),format.raw/*48.33*/("""
          """),format.raw/*49.11*/("""<button type="submit" class="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline" onclick="return confirm('本当に削除しますか？')">
            削除
          </button>
        """)))}),format.raw/*52.10*/("""
      """),format.raw/*53.7*/("""</div>
      <div>
        <a href=""""),_display_(/*55.19*/routes/*55.25*/.TeaController.index()),format.raw/*55.47*/("""" class="bg-gray-500 hover:bg-gray-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline">
          一覧に戻る
        </a>
      </div>
    </div>
  </div>
""")))}),format.raw/*61.2*/(""" """))
      }
    }
  }

  def render(tea:models.Tea,request:RequestHeader,messages:Messages): play.twirl.api.HtmlFormat.Appendable = apply(tea)(request,messages)

  def f:((models.Tea) => (RequestHeader,Messages) => play.twirl.api.HtmlFormat.Appendable) = (tea) => (request,messages) => apply(tea)(request,messages)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  SOURCE: app/views/tea/show.scala.html
                  HASH: efeecd2ce48c816d6b710a0d0da7252c37b2a8a4
                  MATRIX: 962->1|1126->72|1153->74|1174->87|1213->89|1242->92|1476->300|1487->303|1512->308|1909->678|1921->681|1962->701|2235->947|2247->950|2287->969|2563->1218|2575->1221|2609->1234|2882->1480|2894->1483|2958->1525|3232->1772|3244->1775|3309->1818|3582->2064|3594->2067|3638->2089|3801->2225|3816->2231|3868->2262|4075->2442|4090->2448|4145->2494|4185->2496|4224->2508|4239->2514|4275->2529|4314->2540|4566->2761|4600->2768|4664->2805|4679->2811|4722->2833|4935->3016
                  LINES: 28->1|33->2|34->3|34->3|34->3|35->4|38->7|38->7|38->7|45->14|45->14|45->14|49->18|49->18|49->18|53->22|53->22|53->22|57->26|57->26|57->26|61->30|61->30|61->30|65->34|65->34|65->34|73->42|73->42|73->42|78->47|78->47|78->47|78->47|79->48|79->48|79->48|80->49|83->52|84->53|86->55|86->55|86->55|92->61
                  -- GENERATED --
              */
          