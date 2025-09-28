
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

object show extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template3[models.Tea,RequestHeader,Messages,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(tea: models.Tea)(implicit request: RequestHeader, messages: Messages):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.1*/("""
"""),_display_(/*3.2*/main(tea.name)/*3.16*/ {_display_(Seq[Any](format.raw/*3.18*/("""
  """),format.raw/*4.3*/("""<div class="container">
    <div class="row mb-4">
      <div class="col">
        <h1>"""),_display_(/*7.14*/tea/*7.17*/.name),format.raw/*7.22*/("""</h1>
      </div>
      <div class="col text-end">
        <div class="btn-group">
          <a href=""""),_display_(/*11.21*/routes/*11.27*/.TeaController.edit(tea.id.get)),format.raw/*11.58*/("""" class="btn btn-outline-primary">
            編集
          </a>
          """),_display_(/*14.12*/helper/*14.18*/.form(routes.TeaController.delete(tea.id.get))/*14.64*/ {_display_(Seq[Any](format.raw/*14.66*/("""
            """),_display_(/*15.14*/helper/*15.20*/.CSRF.formField),format.raw/*15.35*/("""
            """),format.raw/*16.13*/("""<button type="submit" class="btn btn-outline-danger" onclick="return confirm('本当に削除しますか？');">
              削除
            </button>
          """)))}),format.raw/*19.12*/("""
        """),format.raw/*20.9*/("""</div>
      </div>
    </div>

    """),_display_(/*24.6*/request/*24.13*/.flash.get("success").map/*24.38*/ { message =>_display_(Seq[Any](format.raw/*24.51*/("""
      """),format.raw/*25.7*/("""<div class="alert alert-success">"""),_display_(/*25.41*/message),format.raw/*25.48*/("""</div>
    """)))}),format.raw/*26.6*/("""

    """),_display_(/*28.6*/request/*28.13*/.flash.get("error").map/*28.36*/ { message =>_display_(Seq[Any](format.raw/*28.49*/("""
      """),format.raw/*29.7*/("""<div class="alert alert-danger">"""),_display_(/*29.40*/message),format.raw/*29.47*/("""</div>
    """)))}),format.raw/*30.6*/("""

    """),format.raw/*32.5*/("""<div class="card mb-4">
      <div class="row g-0">
        <div class="col-md-4">
          """),_display_(/*35.12*/tea/*35.15*/.imageUrl.map/*35.28*/ { url =>_display_(Seq[Any](format.raw/*35.37*/("""
            """),format.raw/*36.13*/("""<img src=""""),_display_(/*36.24*/url),format.raw/*36.27*/("""" class="img-fluid rounded-start" alt=""""),_display_(/*36.67*/tea/*36.70*/.name),format.raw/*36.75*/("""">
          """)))}),format.raw/*37.12*/("""
        """),format.raw/*38.9*/("""</div>
        <div class="col-md-8">
          <div class="card-body">
            <h5 class="card-title">基本情報</h5>
            <dl class="row">
              <dt class="col-sm-3">種類</dt>
              <dd class="col-sm-9">"""),_display_(/*44.37*/tea/*44.40*/.teaType),format.raw/*44.48*/("""</dd>

              <dt class="col-sm-3">説明</dt>
              <dd class="col-sm-9">"""),_display_(/*47.37*/tea/*47.40*/.description),format.raw/*47.52*/("""</dd>

              <dt class="col-sm-3">在庫</dt>
              <dd class="col-sm-9">"""),_display_(/*50.37*/tea/*50.40*/.quantity),format.raw/*50.49*/(""" """),format.raw/*50.50*/("""g</dd>

              <dt class="col-sm-3">ステータス</dt>
              <dd class="col-sm-9">"""),_display_(/*53.37*/tea/*53.40*/.status),format.raw/*53.47*/("""</dd>

              <dt class="col-sm-3">登録日</dt>
              <dd class="col-sm-9">"""),_display_(/*56.37*/tea/*56.40*/.createdAt.format("yyyy/MM/dd")),format.raw/*56.71*/("""</dd>

              <dt class="col-sm-3">更新日</dt>
              <dd class="col-sm-9">"""),_display_(/*59.37*/tea/*59.40*/.updatedAt.format("yyyy/MM/dd")),format.raw/*59.71*/("""</dd>
            </dl>
          </div>
        </div>
      </div>
    </div>

    <div class="text-center">
      <a href=""""),_display_(/*67.17*/routes/*67.23*/.TeaController.index()),format.raw/*67.45*/("""" class="btn btn-secondary">
        一覧に戻る
      </a>
    </div>
  </div>
""")))}),format.raw/*72.2*/(""" """))
      }
    }
  }

  def render(tea:models.Tea,request:RequestHeader,messages:Messages): play.twirl.api.HtmlFormat.Appendable = apply(tea)(request,messages)

  def f:((models.Tea) => (RequestHeader,Messages) => play.twirl.api.HtmlFormat.Appendable) = (tea) => (request,messages) => apply(tea)(request,messages)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  SOURCE: app/views/show.scala.html
                  HASH: 0cb29174d15c91f855561f97ddd39726cad9cf2b
                  MATRIX: 755->1|919->72|946->74|968->88|1007->90|1036->93|1150->181|1161->184|1186->189|1317->293|1332->299|1384->330|1487->406|1502->412|1557->458|1597->460|1638->474|1653->480|1689->495|1730->508|1905->652|1941->661|2004->698|2020->705|2054->730|2105->743|2139->750|2200->784|2228->791|2270->803|2303->810|2319->817|2351->840|2402->853|2436->860|2496->893|2524->900|2566->912|2599->918|2720->1012|2732->1015|2754->1028|2801->1037|2842->1050|2880->1061|2904->1064|2971->1104|2983->1107|3009->1112|3054->1126|3090->1135|3342->1360|3354->1363|3383->1371|3496->1457|3508->1460|3541->1472|3654->1558|3666->1561|3696->1570|3725->1571|3842->1661|3854->1664|3882->1671|3996->1758|4008->1761|4060->1792|4174->1879|4186->1882|4238->1913|4392->2040|4407->2046|4450->2068|4555->2143
                  LINES: 21->1|26->2|27->3|27->3|27->3|28->4|31->7|31->7|31->7|35->11|35->11|35->11|38->14|38->14|38->14|38->14|39->15|39->15|39->15|40->16|43->19|44->20|48->24|48->24|48->24|48->24|49->25|49->25|49->25|50->26|52->28|52->28|52->28|52->28|53->29|53->29|53->29|54->30|56->32|59->35|59->35|59->35|59->35|60->36|60->36|60->36|60->36|60->36|60->36|61->37|62->38|68->44|68->44|68->44|71->47|71->47|71->47|74->50|74->50|74->50|74->50|77->53|77->53|77->53|80->56|80->56|80->56|83->59|83->59|83->59|91->67|91->67|91->67|96->72
                  -- GENERATED --
              */
          