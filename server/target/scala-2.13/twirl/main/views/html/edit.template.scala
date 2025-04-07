
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

object edit extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template4[models.Tea,Form[forms.Forms.UpdateTeaData],RequestHeader,Messages,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(tea: models.Tea, teaForm: Form[forms.Forms.UpdateTeaData])(implicit request: RequestHeader, messages: Messages):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.1*/("""
"""),_display_(/*3.2*/main(s"${tea.name}の編集")/*3.25*/ {_display_(Seq[Any](format.raw/*3.27*/("""
  """),format.raw/*4.3*/("""<div class="container">
    <div class="row mb-4">
      <div class="col">
        <h1>"""),_display_(/*7.14*/tea/*7.17*/.nameの編集),format.raw/*7.25*/("""</h1>
      </div>
    </div>

    """),_display_(/*11.6*/request/*11.13*/.flash.get("success").map/*11.38*/ { message =>_display_(Seq[Any](format.raw/*11.51*/("""
      """),format.raw/*12.7*/("""<div class="alert alert-success">"""),_display_(/*12.41*/message),format.raw/*12.48*/("""</div>
    """)))}),format.raw/*13.6*/("""

    """),_display_(/*15.6*/request/*15.13*/.flash.get("error").map/*15.36*/ { message =>_display_(Seq[Any](format.raw/*15.49*/("""
      """),format.raw/*16.7*/("""<div class="alert alert-danger">"""),_display_(/*16.40*/message),format.raw/*16.47*/("""</div>
    """)))}),format.raw/*17.6*/("""

    """),_display_(/*19.6*/helper/*19.12*/.form(routes.TeaController.update(tea.id.get))/*19.58*/ {_display_(Seq[Any](format.raw/*19.60*/("""
      """),_display_(/*20.8*/helper/*20.14*/.CSRF.formField),format.raw/*20.29*/("""

      """),_display_(/*22.8*/helper/*22.14*/.inputText(
        teaForm("name"),
        Symbol("label") -> "名前",
        Symbol("class") -> "form-control",
        Symbol("required") -> true
      )),format.raw/*27.8*/("""

      """),_display_(/*29.8*/helper/*29.14*/.select(
        teaForm("teaTypeId"),
        options = models.TeaType.values.map(t => t.id.toString -> t.name),
        Symbol("label") -> "種類",
        Symbol("class") -> "form-control",
        Symbol("required") -> true
      )),format.raw/*35.8*/("""

      """),_display_(/*37.8*/helper/*37.14*/.textarea(
        teaForm("description"),
        Symbol("label") -> "説明",
        Symbol("class") -> "form-control",
        Symbol("rows") -> 3
      )),format.raw/*42.8*/("""

      """),_display_(/*44.8*/helper/*44.14*/.inputText(
        teaForm("quantity"),
        Symbol("label") -> "在庫 (g)",
        Symbol("type") -> "number",
        Symbol("class") -> "form-control",
        Symbol("required") -> true,
        Symbol("min") -> 0
      )),format.raw/*51.8*/("""

      """),_display_(/*53.8*/helper/*53.14*/.select(
        teaForm("status"),
        options = models.TeaStatusType.values.map(s => s.toString -> s.toString),
        Symbol("label") -> "ステータス",
        Symbol("class") -> "form-control",
        Symbol("required") -> true
      )),format.raw/*59.8*/("""

      """),format.raw/*61.7*/("""<div class="mb-3">
        <label class="form-label">画像</label>
        """),_display_(/*63.10*/tea/*63.13*/.imageUrl.map/*63.26*/ { url =>_display_(Seq[Any](format.raw/*63.35*/("""
          """),format.raw/*64.11*/("""<div class="mb-2">
            <img src=""""),_display_(/*65.24*/url),format.raw/*65.27*/("""" class="img-thumbnail" style="max-width: 200px;" alt=""""),_display_(/*65.83*/tea/*65.86*/.name),format.raw/*65.91*/("""">
          </div>
        """)))}),format.raw/*67.10*/("""
        """),format.raw/*68.9*/("""<input type="file" name="image" class="form-control" accept="image/*">
      </div>

      <div class="text-center">
        <button type="submit" class="btn btn-primary">更新</button>
        <a href=""""),_display_(/*73.19*/routes/*73.25*/.TeaController.show(tea.id.get)),format.raw/*73.56*/("""" class="btn btn-secondary">キャンセル</a>
      </div>
    """)))}),format.raw/*75.6*/("""
  """),format.raw/*76.3*/("""</div>
""")))}),format.raw/*77.2*/(""" """))
      }
    }
  }

  def render(tea:models.Tea,teaForm:Form[forms.Forms.UpdateTeaData],request:RequestHeader,messages:Messages): play.twirl.api.HtmlFormat.Appendable = apply(tea,teaForm)(request,messages)

  def f:((models.Tea,Form[forms.Forms.UpdateTeaData]) => (RequestHeader,Messages) => play.twirl.api.HtmlFormat.Appendable) = (tea,teaForm) => (request,messages) => apply(tea,teaForm)(request,messages)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  SOURCE: app/views/edit.scala.html
                  HASH: e2d19bc5a68a15389c8312c03e10b42a659d0de5
                  MATRIX: 787->1|993->114|1020->116|1051->139|1090->141|1119->144|1233->232|1244->235|1272->243|1334->279|1350->286|1384->311|1435->324|1469->331|1530->365|1558->372|1600->384|1633->391|1649->398|1681->421|1732->434|1766->441|1826->474|1854->481|1896->493|1929->500|1944->506|1999->552|2039->554|2073->562|2088->568|2124->583|2159->592|2174->598|2349->753|2384->762|2399->768|2651->1000|2686->1009|2701->1015|2875->1169|2910->1178|2925->1184|3172->1411|3207->1420|3222->1426|3481->1665|3516->1673|3616->1746|3628->1749|3650->1762|3697->1771|3736->1782|3805->1824|3829->1827|3912->1883|3924->1886|3950->1891|4010->1920|4046->1929|4274->2130|4289->2136|4341->2167|4427->2223|4457->2226|4495->2234
                  LINES: 21->1|26->2|27->3|27->3|27->3|28->4|31->7|31->7|31->7|35->11|35->11|35->11|35->11|36->12|36->12|36->12|37->13|39->15|39->15|39->15|39->15|40->16|40->16|40->16|41->17|43->19|43->19|43->19|43->19|44->20|44->20|44->20|46->22|46->22|51->27|53->29|53->29|59->35|61->37|61->37|66->42|68->44|68->44|75->51|77->53|77->53|83->59|85->61|87->63|87->63|87->63|87->63|88->64|89->65|89->65|89->65|89->65|89->65|91->67|92->68|97->73|97->73|97->73|99->75|100->76|101->77
                  -- GENERATED --
              */
          