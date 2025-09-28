
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

object teaForm extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template4[Form[models.Tea],play.filters.csrf.CSRF.Token,Request[AnyContent],MessagesProvider,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(form: Form[models.Tea], csrfToken: play.filters.csrf.CSRF.Token)(implicit request: Request[AnyContent], messagesProvider: MessagesProvider):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.1*/("""
"""),format.raw/*3.1*/("""<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>お茶の登録 - お茶の成長記録</title>
    <link rel="stylesheet" href=""""),_display_(/*9.35*/routes/*9.41*/.Assets.versioned("stylesheets/main.css")),format.raw/*9.82*/("""">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href=""""),_display_(/*15.44*/routes/*15.50*/.HomeController.dashboard()),format.raw/*15.77*/("""">お茶の成長記録</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <a class="nav-link" href=""""),_display_(/*22.52*/routes/*22.58*/.HomeController.profile()),format.raw/*22.83*/("""">プロフィール</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href=""""),_display_(/*25.52*/routes/*25.58*/.HomeController.logout()),format.raw/*25.82*/("""">ログアウト</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card">
                    <div class="card-header">
                        <h1 class="h4 mb-0">お茶の登録</h1>
                    </div>
                    <div class="card-body">
                        """),_display_(/*40.26*/request/*40.33*/.flash.get("error").map/*40.56*/ { error =>_display_(Seq[Any](format.raw/*40.67*/("""
                            """),format.raw/*41.29*/("""<div class="alert alert-danger">"""),_display_(/*41.62*/error),format.raw/*41.67*/("""</div>
                        """)))}),format.raw/*42.26*/("""

                        """),_display_(/*44.26*/helper/*44.32*/.form(action = routes.HomeController.createTea())/*44.81*/ {_display_(Seq[Any](format.raw/*44.83*/("""
                            """),_display_(/*45.30*/helper/*45.36*/.CSRF.formField),format.raw/*45.51*/("""

                            """),format.raw/*47.29*/("""<div class="row">
                                <div class="col-md-6 mb-3">
                                    """),_display_(/*49.38*/helper/*49.44*/.inputText(form("name"), Symbol("class") -> "form-control", Symbol("_label") -> "名前")),format.raw/*49.129*/("""
                                """),format.raw/*50.33*/("""</div>
                                <div class="col-md-6 mb-3">
                                    """),_display_(/*52.38*/helper/*52.44*/.inputText(form("teaType"), Symbol("class") -> "form-control", Symbol("_label") -> "種類")),format.raw/*52.132*/("""
                                """),format.raw/*53.33*/("""</div>
                            </div>

                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    """),_display_(/*58.38*/helper/*58.44*/.inputText(form("origin"), Symbol("class") -> "form-control", Symbol("_label") -> "産地")),format.raw/*58.131*/("""
                                """),format.raw/*59.33*/("""</div>
                                <div class="col-md-6 mb-3">
                                    """),_display_(/*61.38*/helper/*61.44*/.inputDate(form("purchaseDate"), Symbol("class") -> "form-control", Symbol("_label") -> "購入日")),format.raw/*61.138*/("""
                                """),format.raw/*62.33*/("""</div>
                            </div>

                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    """),_display_(/*67.38*/helper/*67.44*/.select(form("status"), options = Seq("未開封" -> "未開封", "開封済み" -> "開封済み", "飲み終わり" -> "飲み終わり"), Symbol("class") -> "form-control", Symbol("_label") -> "状態")),format.raw/*67.197*/("""
                                """),format.raw/*68.33*/("""</div>
                                <div class="col-md-6 mb-3">
                                    """),_display_(/*70.38*/helper/*70.44*/.inputText(form("price"), Symbol("class") -> "form-control", Symbol("_label") -> "価格")),format.raw/*70.130*/("""
                                """),format.raw/*71.33*/("""</div>
                            </div>

                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    """),_display_(/*76.38*/helper/*76.44*/.inputText(form("quantity"), Symbol("class") -> "form-control", Symbol("_label") -> "数量")),format.raw/*76.133*/("""
                                """),format.raw/*77.33*/("""</div>
                                <div class="col-md-6 mb-3">
                                    """),_display_(/*79.38*/helper/*79.44*/.inputText(form("unit"), Symbol("class") -> "form-control", Symbol("_label") -> "単位")),format.raw/*79.129*/("""
                                """),format.raw/*80.33*/("""</div>
                            </div>

                            <div class="mb-3">
                                """),_display_(/*84.34*/helper/*84.40*/.textarea(form("description"), Symbol("class") -> "form-control", Symbol("_label") -> "説明")),format.raw/*84.131*/("""
                            """),format.raw/*85.29*/("""</div>

                            <div class="mb-3">
                                """),_display_(/*88.34*/helper/*88.40*/.inputText(form("imageUrl"), Symbol("class") -> "form-control", Symbol("_label") -> "画像URL")),format.raw/*88.132*/("""
                            """),format.raw/*89.29*/("""</div>

                            <div class="d-grid gap-2">
                                <button type="submit" class="btn btn-primary">保存</button>
                                <a href=""""),_display_(/*93.43*/routes/*93.49*/.HomeController.dashboard()),format.raw/*93.76*/("""" class="btn btn-outline-secondary">キャンセル</a>
                            </div>
                        """)))}),format.raw/*95.26*/("""
                    """),format.raw/*96.21*/("""</div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> """))
      }
    }
  }

  def render(form:Form[models.Tea],csrfToken:play.filters.csrf.CSRF.Token,request:Request[AnyContent],messagesProvider:MessagesProvider): play.twirl.api.HtmlFormat.Appendable = apply(form,csrfToken)(request,messagesProvider)

  def f:((Form[models.Tea],play.filters.csrf.CSRF.Token) => (Request[AnyContent],MessagesProvider) => play.twirl.api.HtmlFormat.Appendable) = (form,csrfToken) => (request,messagesProvider) => apply(form,csrfToken)(request,messagesProvider)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  SOURCE: app/views/teaForm.scala.html
                  HASH: 02bec3baaf65e5f110b137286721d738ca303991
                  MATRIX: 807->1|1041->142|1068->143|1305->354|1319->360|1380->401|1669->664|1684->670|1732->697|2172->1110|2187->1116|2233->1141|2392->1273|2407->1279|2452->1303|2934->1758|2950->1765|2982->1788|3031->1799|3088->1828|3148->1861|3174->1866|3237->1898|3291->1925|3306->1931|3364->1980|3404->1982|3461->2012|3476->2018|3512->2033|3570->2063|3712->2178|3727->2184|3834->2269|3895->2302|4026->2406|4041->2412|4151->2500|4212->2533|4425->2719|4440->2725|4549->2812|4610->2845|4741->2949|4756->2955|4872->3049|4933->3082|5146->3268|5161->3274|5336->3427|5397->3460|5528->3564|5543->3570|5651->3656|5712->3689|5925->3875|5940->3881|6051->3970|6112->4003|6243->4107|6258->4113|6365->4198|6426->4231|6576->4354|6591->4360|6704->4451|6761->4480|6876->4568|6891->4574|7005->4666|7062->4695|7284->4890|7299->4896|7347->4923|7484->5029|7533->5050
                  LINES: 21->1|26->2|27->3|33->9|33->9|33->9|39->15|39->15|39->15|46->22|46->22|46->22|49->25|49->25|49->25|64->40|64->40|64->40|64->40|65->41|65->41|65->41|66->42|68->44|68->44|68->44|68->44|69->45|69->45|69->45|71->47|73->49|73->49|73->49|74->50|76->52|76->52|76->52|77->53|82->58|82->58|82->58|83->59|85->61|85->61|85->61|86->62|91->67|91->67|91->67|92->68|94->70|94->70|94->70|95->71|100->76|100->76|100->76|101->77|103->79|103->79|103->79|104->80|108->84|108->84|108->84|109->85|112->88|112->88|112->88|113->89|117->93|117->93|117->93|119->95|120->96
                  -- GENERATED --
              */
          