
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

object dashboard extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template4[models.User,List[models.Tea],RequestHeader,MessagesProvider,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(user: models.User, teas: List[models.Tea])(implicit request: RequestHeader, messagesProvider: MessagesProvider):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.1*/("""
"""),_display_(/*3.2*/main("ダッシュボード")/*3.17*/ {_display_(Seq[Any](format.raw/*3.19*/("""
  """),format.raw/*4.3*/("""<div class="container mt-4">
    <div class="row">
      <div class="col-md-12">
        <h1>ようこそ、"""),_display_(/*7.19*/user/*7.23*/.usernameさん),format.raw/*7.34*/("""</h1>
        <p class="text-muted">"""),_display_(/*8.32*/user/*8.36*/.email),format.raw/*8.42*/("""</p>
      </div>
    </div>

    <div class="row mt-4">
      <div class="col-md-12">
        <div class="d-flex justify-content-between align-items-center">
          <h2>お茶の一覧</h2>
          <a href=""""),_display_(/*16.21*/routes/*16.27*/.HomeController.createTea()),format.raw/*16.54*/("""" class="btn btn-primary">
            <i class="fas fa-plus"></i> 新規登録
          </a>
        </div>
      </div>
    </div>

    <div class="row mt-4">
      <div class="col-md-12">
        """),_display_(/*25.10*/if(teas.isEmpty)/*25.26*/ {_display_(Seq[Any](format.raw/*25.28*/("""
          """),format.raw/*26.11*/("""<div class="alert alert-info">
            お茶が登録されていません。新規登録から始めましょう。
          </div>
        """)))}/*29.11*/else/*29.16*/{_display_(Seq[Any](format.raw/*29.17*/("""
          """),format.raw/*30.11*/("""<div class="row">
            """),_display_(/*31.14*/for(tea <- teas) yield /*31.30*/ {_display_(Seq[Any](format.raw/*31.32*/("""
              """),format.raw/*32.15*/("""<div class="col-md-4 mb-4">
                <div class="card h-100">
                  """),_display_(/*34.20*/tea/*34.23*/.imageUrl.map/*34.36*/ { url =>_display_(Seq[Any](format.raw/*34.45*/("""
                    """),format.raw/*35.21*/("""<img src=""""),_display_(/*35.32*/url),format.raw/*35.35*/("""" class="card-img-top" alt=""""),_display_(/*35.64*/tea/*35.67*/.name),format.raw/*35.72*/("""">
                  """)))}),format.raw/*36.20*/("""
                  """),format.raw/*37.19*/("""<div class="card-body">
                    <h5 class="card-title">"""),_display_(/*38.45*/tea/*38.48*/.name),format.raw/*38.53*/("""</h5>
                    <p class="card-text">
                      <small class="text-muted">
                        <i class="fas fa-tag"></i> """),_display_(/*41.53*/tea/*41.56*/.teaType),format.raw/*41.64*/("""<br>
                        <i class="fas fa-map-marker-alt"></i> """),_display_(/*42.64*/tea/*42.67*/.origin),format.raw/*42.74*/("""<br>
                        <i class="fas fa-calendar"></i> """),_display_(/*43.58*/tea/*43.61*/.purchaseDate.toString("yyyy/MM/dd")),format.raw/*43.97*/("""<br>
                        <i class="fas fa-info-circle"></i> """),_display_(/*44.61*/tea/*44.64*/.status),format.raw/*44.71*/("""
                      """),format.raw/*45.23*/("""</small>
                    </p>
                    """),_display_(/*47.22*/tea/*47.25*/.description.map/*47.41*/ { desc =>_display_(Seq[Any](format.raw/*47.51*/("""
                      """),format.raw/*48.23*/("""<p class="card-text">"""),_display_(/*48.45*/desc),format.raw/*48.49*/("""</p>
                    """)))}),format.raw/*49.22*/("""
                    """),_display_(/*50.22*/if(tea.price.isDefined || tea.quantity.isDefined)/*50.71*/ {_display_(Seq[Any](format.raw/*50.73*/("""
                      """),format.raw/*51.23*/("""<p class="card-text">
                        <small class="text-muted">
                          """),_display_(/*53.28*/tea/*53.31*/.price.map/*53.41*/ { price =>_display_(Seq[Any](format.raw/*53.52*/("""
                            """),format.raw/*54.29*/("""<i class="fas fa-yen-sign"></i> """),_display_(/*54.62*/price),format.raw/*54.67*/("""
                          """)))}),format.raw/*55.28*/("""
                          """),_display_(/*56.28*/tea/*56.31*/.quantity.map/*56.44*/ { qty =>_display_(Seq[Any](format.raw/*56.53*/("""
                            """),_display_(/*57.30*/tea/*57.33*/.unit.map/*57.42*/ { unit =>_display_(Seq[Any](format.raw/*57.52*/("""
                              """),format.raw/*58.31*/("""<i class="fas fa-box"></i> """),_display_(/*58.59*/qty),format.raw/*58.62*/(""" """),_display_(/*58.64*/unit),format.raw/*58.68*/("""
                            """)))}),format.raw/*59.30*/("""
                          """)))}),format.raw/*60.28*/("""
                        """),format.raw/*61.25*/("""</small>
                      </p>
                    """)))}),format.raw/*63.22*/("""
                  """),format.raw/*64.19*/("""</div>
                  <div class="card-footer">
                    <div class="btn-group w-100">
                      <a href=""""),_display_(/*67.33*/routes/*67.39*/.HomeController.editTea(tea.id)),format.raw/*67.70*/("""" class="btn btn-outline-primary">
                        <i class="fas fa-edit"></i> 編集
                      </a>
                      <a href=""""),_display_(/*70.33*/routes/*70.39*/.HomeController.deleteTea(tea.id)),format.raw/*70.72*/("""" class="btn btn-outline-danger"
                         onclick="return confirm('このお茶を削除してもよろしいですか？')">
                        <i class="fas fa-trash"></i> 削除
                      </a>
                    </div>
                  </div>
                </div>
              </div>
            """)))}),format.raw/*78.14*/("""
          """),format.raw/*79.11*/("""</div>
        """)))}),format.raw/*80.10*/("""
      """),format.raw/*81.7*/("""</div>
    </div>
  </div>
""")))}),format.raw/*84.2*/(""" """))
      }
    }
  }

  def render(user:models.User,teas:List[models.Tea],request:RequestHeader,messagesProvider:MessagesProvider): play.twirl.api.HtmlFormat.Appendable = apply(user,teas)(request,messagesProvider)

  def f:((models.User,List[models.Tea]) => (RequestHeader,MessagesProvider) => play.twirl.api.HtmlFormat.Appendable) = (user,teas) => (request,messagesProvider) => apply(user,teas)(request,messagesProvider)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  SOURCE: app/views/dashboard.scala.html
                  HASH: 3c68b0538532960aae32cc22325cb72e9f65c401
                  MATRIX: 786->1|992->114|1019->116|1042->131|1081->133|1110->136|1235->235|1247->239|1278->250|1341->287|1353->291|1379->297|1610->501|1625->507|1673->534|1893->727|1918->743|1958->745|1997->756|2112->853|2125->858|2164->859|2203->870|2261->901|2293->917|2333->919|2376->934|2491->1022|2503->1025|2525->1038|2572->1047|2621->1068|2659->1079|2683->1082|2739->1111|2751->1114|2777->1119|2830->1141|2877->1160|2972->1228|2984->1231|3010->1236|3186->1385|3198->1388|3227->1396|3322->1464|3334->1467|3362->1474|3451->1536|3463->1539|3520->1575|3612->1640|3624->1643|3652->1650|3703->1673|3785->1728|3797->1731|3822->1747|3870->1757|3921->1780|3970->1802|3995->1806|4052->1832|4101->1854|4159->1903|4199->1905|4250->1928|4377->2028|4389->2031|4408->2041|4457->2052|4514->2081|4574->2114|4600->2119|4659->2147|4714->2175|4726->2178|4748->2191|4795->2200|4852->2230|4864->2233|4882->2242|4930->2252|4989->2283|5044->2311|5068->2314|5097->2316|5122->2320|5183->2350|5242->2378|5295->2403|5383->2460|5430->2479|5590->2612|5605->2618|5657->2649|5833->2798|5848->2804|5902->2837|6231->3135|6270->3146|6317->3162|6351->3169|6409->3197
                  LINES: 21->1|26->2|27->3|27->3|27->3|28->4|31->7|31->7|31->7|32->8|32->8|32->8|40->16|40->16|40->16|49->25|49->25|49->25|50->26|53->29|53->29|53->29|54->30|55->31|55->31|55->31|56->32|58->34|58->34|58->34|58->34|59->35|59->35|59->35|59->35|59->35|59->35|60->36|61->37|62->38|62->38|62->38|65->41|65->41|65->41|66->42|66->42|66->42|67->43|67->43|67->43|68->44|68->44|68->44|69->45|71->47|71->47|71->47|71->47|72->48|72->48|72->48|73->49|74->50|74->50|74->50|75->51|77->53|77->53|77->53|77->53|78->54|78->54|78->54|79->55|80->56|80->56|80->56|80->56|81->57|81->57|81->57|81->57|82->58|82->58|82->58|82->58|82->58|83->59|84->60|85->61|87->63|88->64|91->67|91->67|91->67|94->70|94->70|94->70|102->78|103->79|104->80|105->81|108->84
                  -- GENERATED --
              */
          