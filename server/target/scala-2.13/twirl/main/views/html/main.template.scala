
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

object main extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template3[String,Html,RequestHeader,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(title: String)(content: Html)(implicit request: RequestHeader):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.1*/("""
"""),format.raw/*3.1*/("""<!DOCTYPE html>
<html lang="ja">
  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>"""),_display_(/*8.13*/title),format.raw/*8.18*/(""" """),format.raw/*8.19*/("""- Tea Growth Record</title>
    <link rel="stylesheet" href=""""),_display_(/*9.35*/routes/*9.41*/.Assets.versioned("stylesheets/main.css")),format.raw/*9.82*/("""">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css">
  </head>
  <body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
      <div class="container">
        <a class="navbar-brand" href=""""),_display_(/*15.40*/routes/*15.46*/.HomeController.index()),format.raw/*15.69*/("""">Tea Growth Record</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
          <ul class="navbar-nav ms-auto">
            """),_display_(/*21.14*/request/*21.21*/.session.get("userId").map/*21.47*/ { _ =>_display_(Seq[Any](format.raw/*21.54*/("""
              """),format.raw/*22.15*/("""<li class="nav-item">
                <a class="nav-link" href=""""),_display_(/*23.44*/routes/*23.50*/.TeaController.index()),format.raw/*23.72*/("""">お茶一覧</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href=""""),_display_(/*26.44*/routes/*26.50*/.UserController.profile()),format.raw/*26.75*/("""">プロフィール</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href=""""),_display_(/*29.44*/routes/*29.50*/.AuthController.logout()),format.raw/*29.74*/("""">ログアウト</a>
              </li>
            """)))}/*31.14*/.getOrElse/*31.24*/ {_display_(Seq[Any](format.raw/*31.26*/("""
              """),format.raw/*32.15*/("""<li class="nav-item">
                <a class="nav-link" href=""""),_display_(/*33.44*/routes/*33.50*/.AuthController.login()),format.raw/*33.73*/("""">ログイン</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href=""""),_display_(/*36.44*/routes/*36.50*/.AuthController.register()),format.raw/*36.76*/("""">新規登録</a>
              </li>
            """)))}),format.raw/*38.14*/("""
          """),format.raw/*39.11*/("""</ul>
        </div>
      </div>
    </nav>

    <main class="container mt-4">
      """),_display_(/*45.8*/content),format.raw/*45.15*/("""
    """),format.raw/*46.5*/("""</main>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
  </body>
</html> 
</html> """))
      }
    }
  }

  def render(title:String,content:Html,request:RequestHeader): play.twirl.api.HtmlFormat.Appendable = apply(title)(content)(request)

  def f:((String) => (Html) => (RequestHeader) => play.twirl.api.HtmlFormat.Appendable) = (title) => (content) => (request) => apply(title)(content)(request)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  SOURCE: app/views/main.scala.html
                  HASH: 11033cf0482efdc04988e4edca02b72e3a90f9d0
                  MATRIX: 747->1|904->65|931->66|1113->222|1138->227|1166->228|1254->290|1268->296|1329->337|1616->598|1631->604|1675->627|2021->946|2037->953|2072->979|2117->986|2160->1001|2252->1066|2267->1072|2310->1094|2447->1204|2462->1210|2508->1235|2647->1347|2662->1353|2707->1377|2771->1422|2790->1432|2830->1434|2873->1449|2965->1514|2980->1520|3024->1543|3161->1653|3176->1659|3223->1685|3298->1729|3337->1740|3450->1827|3478->1834|3510->1839
                  LINES: 21->1|26->2|27->3|32->8|32->8|32->8|33->9|33->9|33->9|39->15|39->15|39->15|45->21|45->21|45->21|45->21|46->22|47->23|47->23|47->23|50->26|50->26|50->26|53->29|53->29|53->29|55->31|55->31|55->31|56->32|57->33|57->33|57->33|60->36|60->36|60->36|62->38|63->39|69->45|69->45|70->46
                  -- GENERATED --
              */
          