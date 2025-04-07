
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

object index extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template3[Seq[models.Tea],RequestHeader,Messages,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(teas: Seq[models.Tea])(implicit request: RequestHeader, messages: Messages):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.1*/("""
"""),_display_(/*3.2*/main("お茶一覧")/*3.14*/ {_display_(Seq[Any](format.raw/*3.16*/("""
  """),format.raw/*4.3*/("""<div class="container">
    <div class="row mb-4">
      <div class="col">
        <h1>お茶一覧</h1>
      </div>
      <div class="col text-end">
        <a href=""""),_display_(/*10.19*/routes/*10.25*/.TeaController.create()),format.raw/*10.48*/("""" class="btn btn-primary">
          新しいお茶を登録
        </a>
      </div>
    </div>

    """),_display_(/*16.6*/request/*16.13*/.flash.get("success").map/*16.38*/ { message =>_display_(Seq[Any](format.raw/*16.51*/("""
      """),format.raw/*17.7*/("""<div class="alert alert-success">"""),_display_(/*17.41*/message),format.raw/*17.48*/("""</div>
    """)))}),format.raw/*18.6*/("""

    """),_display_(/*20.6*/request/*20.13*/.flash.get("error").map/*20.36*/ { message =>_display_(Seq[Any](format.raw/*20.49*/("""
      """),format.raw/*21.7*/("""<div class="alert alert-danger">"""),_display_(/*21.40*/message),format.raw/*21.47*/("""</div>
    """)))}),format.raw/*22.6*/("""

    """),_display_(/*24.6*/if(teas.isEmpty)/*24.22*/ {_display_(Seq[Any](format.raw/*24.24*/("""
      """),format.raw/*25.7*/("""<div class="alert alert-info">
        まだお茶が登録されていません。新しいお茶を登録してみましょう。
      </div>
    """)))}/*28.7*/else/*28.12*/{_display_(Seq[Any](format.raw/*28.13*/("""
      """),format.raw/*29.7*/("""<div class="row row-cols-1 row-cols-md-3 g-4">
        """),_display_(/*30.10*/for(tea <- teas) yield /*30.26*/ {_display_(Seq[Any](format.raw/*30.28*/("""
          """),format.raw/*31.11*/("""<div class="col">
            <div class="card h-100">
              """),_display_(/*33.16*/tea/*33.19*/.imageUrl.map/*33.32*/ { url =>_display_(Seq[Any](format.raw/*33.41*/("""
                """),format.raw/*34.17*/("""<img src=""""),_display_(/*34.28*/url),format.raw/*34.31*/("""" class="card-img-top" alt=""""),_display_(/*34.60*/tea/*34.63*/.name),format.raw/*34.68*/("""">
              """)))}),format.raw/*35.16*/("""
              """),format.raw/*36.15*/("""<div class="card-body">
                <h5 class="card-title">"""),_display_(/*37.41*/tea/*37.44*/.name),format.raw/*37.49*/("""</h5>
                <p class="card-text">
                  <small class="text-muted">"""),_display_(/*39.46*/tea/*39.49*/.teaType),format.raw/*39.57*/("""</small>
                </p>
                <p class="card-text">"""),_display_(/*41.39*/tea/*41.42*/.description),format.raw/*41.54*/("""</p>
                <p class="card-text">
                  <small class="text-muted">在庫: """),_display_(/*43.50*/tea/*43.53*/.quantity),format.raw/*43.62*/(""" """),format.raw/*43.63*/("""g</small>
                </p>
                <div class="btn-group">
                  <a href=""""),_display_(/*46.29*/routes/*46.35*/.TeaController.show(tea.id.get)),format.raw/*46.66*/("""" class="btn btn-outline-primary">
                    詳細
                  </a>
                  <a href=""""),_display_(/*49.29*/routes/*49.35*/.TeaController.edit(tea.id.get)),format.raw/*49.66*/("""" class="btn btn-outline-secondary">
                    編集
                  </a>
                  """),_display_(/*52.20*/helper/*52.26*/.form(routes.TeaController.delete(tea.id.get))/*52.72*/ {_display_(Seq[Any](format.raw/*52.74*/("""
                    """),_display_(/*53.22*/helper/*53.28*/.CSRF.formField),format.raw/*53.43*/("""
                    """),format.raw/*54.21*/("""<button type="submit" class="btn btn-outline-danger" onclick="return confirm('本当に削除しますか？');">
                      削除
                    </button>
                  """)))}),format.raw/*57.20*/("""
                """),format.raw/*58.17*/("""</div>
              </div>
              <div class="card-footer">
                <small class="text-muted">
                  登録日: """),_display_(/*62.25*/tea/*62.28*/.createdAt.format("yyyy/MM/dd")),format.raw/*62.59*/("""
                """),format.raw/*63.17*/("""</small>
              </div>
            </div>
          </div>
        """)))}),format.raw/*67.10*/("""
      """),format.raw/*68.7*/("""</div>
    """)))}),format.raw/*69.6*/("""
  """),format.raw/*70.3*/("""</div>
""")))}),format.raw/*71.2*/(""" """))
      }
    }
  }

  def render(teas:Seq[models.Tea],request:RequestHeader,messages:Messages): play.twirl.api.HtmlFormat.Appendable = apply(teas)(request,messages)

  def f:((Seq[models.Tea]) => (RequestHeader,Messages) => play.twirl.api.HtmlFormat.Appendable) = (teas) => (request,messages) => apply(teas)(request,messages)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  SOURCE: app/views/index.scala.html
                  HASH: 015666c2ac234939fbc3570a1d60efa0ac7c0ae9
                  MATRIX: 761->1|931->78|958->80|978->92|1017->94|1046->97|1234->258|1249->264|1293->287|1408->376|1424->383|1458->408|1509->421|1543->428|1604->462|1632->469|1674->481|1707->488|1723->495|1755->518|1806->531|1840->538|1900->571|1928->578|1970->590|2003->597|2028->613|2068->615|2102->622|2209->712|2222->717|2261->718|2295->725|2378->781|2410->797|2450->799|2489->810|2586->880|2598->883|2620->896|2667->905|2712->922|2750->933|2774->936|2830->965|2842->968|2868->973|2917->991|2960->1006|3051->1070|3063->1073|3089->1078|3205->1167|3217->1170|3246->1178|3341->1246|3353->1249|3386->1261|3505->1353|3517->1356|3547->1365|3576->1366|3702->1465|3717->1471|3769->1502|3905->1611|3920->1617|3972->1648|4101->1750|4116->1756|4171->1802|4211->1804|4260->1826|4275->1832|4311->1847|4360->1868|4559->2036|4604->2053|4766->2188|4778->2191|4830->2222|4875->2239|4981->2314|5015->2321|5057->2333|5087->2336|5125->2344
                  LINES: 21->1|26->2|27->3|27->3|27->3|28->4|34->10|34->10|34->10|40->16|40->16|40->16|40->16|41->17|41->17|41->17|42->18|44->20|44->20|44->20|44->20|45->21|45->21|45->21|46->22|48->24|48->24|48->24|49->25|52->28|52->28|52->28|53->29|54->30|54->30|54->30|55->31|57->33|57->33|57->33|57->33|58->34|58->34|58->34|58->34|58->34|58->34|59->35|60->36|61->37|61->37|61->37|63->39|63->39|63->39|65->41|65->41|65->41|67->43|67->43|67->43|67->43|70->46|70->46|70->46|73->49|73->49|73->49|76->52|76->52|76->52|76->52|77->53|77->53|77->53|78->54|81->57|82->58|86->62|86->62|86->62|87->63|91->67|92->68|93->69|94->70|95->71
                  -- GENERATED --
              */
          