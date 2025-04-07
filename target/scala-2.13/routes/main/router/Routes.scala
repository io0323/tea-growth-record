// @GENERATOR:play-routes-compiler
// @SOURCE:conf/routes

package router

import play.core.routing._
import play.core.routing.HandlerInvokerFactory._

import play.api.mvc._

import _root_.controllers.Assets.Asset
import _root_.models.TeaType
import _root_.models.TeaStatus

class Routes(
  override val errorHandler: play.api.http.HttpErrorHandler, 
  // @LINE:7
  HomeController_4: controllers.HomeController,
  // @LINE:10
  TeaController_0: controllers.TeaController,
  // @LINE:19
  AuthController_2: controllers.AuthController,
  // @LINE:30
  Assets_3: controllers.Assets,
  // @LINE:33
  ErrorController_1: controllers.ErrorController,
  val prefix: String
) extends GeneratedRouter {

   @javax.inject.Inject()
   def this(errorHandler: play.api.http.HttpErrorHandler,
    // @LINE:7
    HomeController_4: controllers.HomeController,
    // @LINE:10
    TeaController_0: controllers.TeaController,
    // @LINE:19
    AuthController_2: controllers.AuthController,
    // @LINE:30
    Assets_3: controllers.Assets,
    // @LINE:33
    ErrorController_1: controllers.ErrorController
  ) = this(errorHandler, HomeController_4, TeaController_0, AuthController_2, Assets_3, ErrorController_1, "/")

  def withPrefix(addPrefix: String): Routes = {
    val prefix = play.api.routing.Router.concatPrefix(addPrefix, this.prefix)
    router.RoutesPrefix.setPrefix(prefix)
    new Routes(errorHandler, HomeController_4, TeaController_0, AuthController_2, Assets_3, ErrorController_1, prefix)
  }

  private[this] val defaultPrefix: String = {
    if (this.prefix.endsWith("/")) "" else "/"
  }

  def documentation = List(
    ("""GET""", this.prefix, """controllers.HomeController.index()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """teas""", """controllers.TeaController.index()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """teas/new""", """controllers.TeaController.create()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """teas""", """controllers.TeaController.save()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """teas/""" + "$" + """id<[^/]+>""", """controllers.TeaController.show(id:Long)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """teas/""" + "$" + """id<[^/]+>/edit""", """controllers.TeaController.edit(id:Long)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """teas/""" + "$" + """id<[^/]+>""", """controllers.TeaController.update(id:Long)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """teas/""" + "$" + """id<[^/]+>/delete""", """controllers.TeaController.delete(id:Long)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """login""", """controllers.AuthController.login()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """login""", """controllers.AuthController.doLogin()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """logout""", """controllers.AuthController.logout()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """register""", """controllers.AuthController.register()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """register""", """controllers.AuthController.doRegister()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """password""", """controllers.TeaController.password()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """password""", """controllers.TeaController.updatePassword()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """assets/""" + "$" + """file<.+>""", """controllers.Assets.versioned(path:String = "/public", file:Asset)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """error/forbidden""", """controllers.ErrorController.forbidden()"""),
    Nil
  ).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
    case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
    case l => s ++ l.asInstanceOf[List[(String,String,String)]]
  }}


  // @LINE:7
  private[this] lazy val controllers_HomeController_index0_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix)))
  )
  private[this] lazy val controllers_HomeController_index0_invoker = createInvoker(
    HomeController_4.index(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.HomeController",
      "index",
      Nil,
      "GET",
      this.prefix + """""",
      """ Home page""",
      Seq()
    )
  )

  // @LINE:10
  private[this] lazy val controllers_TeaController_index1_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("teas")))
  )
  private[this] lazy val controllers_TeaController_index1_invoker = createInvoker(
    TeaController_0.index(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.TeaController",
      "index",
      Nil,
      "GET",
      this.prefix + """teas""",
      """ Tea management""",
      Seq()
    )
  )

  // @LINE:11
  private[this] lazy val controllers_TeaController_create2_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("teas/new")))
  )
  private[this] lazy val controllers_TeaController_create2_invoker = createInvoker(
    TeaController_0.create(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.TeaController",
      "create",
      Nil,
      "GET",
      this.prefix + """teas/new""",
      """""",
      Seq()
    )
  )

  // @LINE:12
  private[this] lazy val controllers_TeaController_save3_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("teas")))
  )
  private[this] lazy val controllers_TeaController_save3_invoker = createInvoker(
    TeaController_0.save(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.TeaController",
      "save",
      Nil,
      "POST",
      this.prefix + """teas""",
      """""",
      Seq()
    )
  )

  // @LINE:13
  private[this] lazy val controllers_TeaController_show4_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("teas/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val controllers_TeaController_show4_invoker = createInvoker(
    TeaController_0.show(fakeValue[Long]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.TeaController",
      "show",
      Seq(classOf[Long]),
      "GET",
      this.prefix + """teas/""" + "$" + """id<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:14
  private[this] lazy val controllers_TeaController_edit5_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("teas/"), DynamicPart("id", """[^/]+""",true), StaticPart("/edit")))
  )
  private[this] lazy val controllers_TeaController_edit5_invoker = createInvoker(
    TeaController_0.edit(fakeValue[Long]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.TeaController",
      "edit",
      Seq(classOf[Long]),
      "GET",
      this.prefix + """teas/""" + "$" + """id<[^/]+>/edit""",
      """""",
      Seq()
    )
  )

  // @LINE:15
  private[this] lazy val controllers_TeaController_update6_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("teas/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val controllers_TeaController_update6_invoker = createInvoker(
    TeaController_0.update(fakeValue[Long]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.TeaController",
      "update",
      Seq(classOf[Long]),
      "POST",
      this.prefix + """teas/""" + "$" + """id<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:16
  private[this] lazy val controllers_TeaController_delete7_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("teas/"), DynamicPart("id", """[^/]+""",true), StaticPart("/delete")))
  )
  private[this] lazy val controllers_TeaController_delete7_invoker = createInvoker(
    TeaController_0.delete(fakeValue[Long]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.TeaController",
      "delete",
      Seq(classOf[Long]),
      "POST",
      this.prefix + """teas/""" + "$" + """id<[^/]+>/delete""",
      """""",
      Seq()
    )
  )

  // @LINE:19
  private[this] lazy val controllers_AuthController_login8_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("login")))
  )
  private[this] lazy val controllers_AuthController_login8_invoker = createInvoker(
    AuthController_2.login(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.AuthController",
      "login",
      Nil,
      "GET",
      this.prefix + """login""",
      """ Authentication""",
      Seq()
    )
  )

  // @LINE:20
  private[this] lazy val controllers_AuthController_doLogin9_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("login")))
  )
  private[this] lazy val controllers_AuthController_doLogin9_invoker = createInvoker(
    AuthController_2.doLogin(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.AuthController",
      "doLogin",
      Nil,
      "POST",
      this.prefix + """login""",
      """""",
      Seq()
    )
  )

  // @LINE:21
  private[this] lazy val controllers_AuthController_logout10_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("logout")))
  )
  private[this] lazy val controllers_AuthController_logout10_invoker = createInvoker(
    AuthController_2.logout(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.AuthController",
      "logout",
      Nil,
      "GET",
      this.prefix + """logout""",
      """""",
      Seq()
    )
  )

  // @LINE:22
  private[this] lazy val controllers_AuthController_register11_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("register")))
  )
  private[this] lazy val controllers_AuthController_register11_invoker = createInvoker(
    AuthController_2.register(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.AuthController",
      "register",
      Nil,
      "GET",
      this.prefix + """register""",
      """""",
      Seq()
    )
  )

  // @LINE:23
  private[this] lazy val controllers_AuthController_doRegister12_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("register")))
  )
  private[this] lazy val controllers_AuthController_doRegister12_invoker = createInvoker(
    AuthController_2.doRegister(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.AuthController",
      "doRegister",
      Nil,
      "POST",
      this.prefix + """register""",
      """""",
      Seq()
    )
  )

  // @LINE:26
  private[this] lazy val controllers_TeaController_password13_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("password")))
  )
  private[this] lazy val controllers_TeaController_password13_invoker = createInvoker(
    TeaController_0.password(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.TeaController",
      "password",
      Nil,
      "GET",
      this.prefix + """password""",
      """ Password management""",
      Seq()
    )
  )

  // @LINE:27
  private[this] lazy val controllers_TeaController_updatePassword14_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("password")))
  )
  private[this] lazy val controllers_TeaController_updatePassword14_invoker = createInvoker(
    TeaController_0.updatePassword(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.TeaController",
      "updatePassword",
      Nil,
      "POST",
      this.prefix + """password""",
      """""",
      Seq()
    )
  )

  // @LINE:30
  private[this] lazy val controllers_Assets_versioned15_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("assets/"), DynamicPart("file", """.+""",false)))
  )
  private[this] lazy val controllers_Assets_versioned15_invoker = createInvoker(
    Assets_3.versioned(fakeValue[String], fakeValue[Asset]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Assets",
      "versioned",
      Seq(classOf[String], classOf[Asset]),
      "GET",
      this.prefix + """assets/""" + "$" + """file<.+>""",
      """ Map static resources from the /public folder to the /assets URL path""",
      Seq()
    )
  )

  // @LINE:33
  private[this] lazy val controllers_ErrorController_forbidden16_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("error/forbidden")))
  )
  private[this] lazy val controllers_ErrorController_forbidden16_invoker = createInvoker(
    ErrorController_1.forbidden(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ErrorController",
      "forbidden",
      Nil,
      "GET",
      this.prefix + """error/forbidden""",
      """ Error pages""",
      Seq()
    )
  )


  def routes: PartialFunction[RequestHeader, Handler] = {
  
    // @LINE:7
    case controllers_HomeController_index0_route(params@_) =>
      call { 
        controllers_HomeController_index0_invoker.call(HomeController_4.index())
      }
  
    // @LINE:10
    case controllers_TeaController_index1_route(params@_) =>
      call { 
        controllers_TeaController_index1_invoker.call(TeaController_0.index())
      }
  
    // @LINE:11
    case controllers_TeaController_create2_route(params@_) =>
      call { 
        controllers_TeaController_create2_invoker.call(TeaController_0.create())
      }
  
    // @LINE:12
    case controllers_TeaController_save3_route(params@_) =>
      call { 
        controllers_TeaController_save3_invoker.call(TeaController_0.save())
      }
  
    // @LINE:13
    case controllers_TeaController_show4_route(params@_) =>
      call(params.fromPath[Long]("id", None)) { (id) =>
        controllers_TeaController_show4_invoker.call(TeaController_0.show(id))
      }
  
    // @LINE:14
    case controllers_TeaController_edit5_route(params@_) =>
      call(params.fromPath[Long]("id", None)) { (id) =>
        controllers_TeaController_edit5_invoker.call(TeaController_0.edit(id))
      }
  
    // @LINE:15
    case controllers_TeaController_update6_route(params@_) =>
      call(params.fromPath[Long]("id", None)) { (id) =>
        controllers_TeaController_update6_invoker.call(TeaController_0.update(id))
      }
  
    // @LINE:16
    case controllers_TeaController_delete7_route(params@_) =>
      call(params.fromPath[Long]("id", None)) { (id) =>
        controllers_TeaController_delete7_invoker.call(TeaController_0.delete(id))
      }
  
    // @LINE:19
    case controllers_AuthController_login8_route(params@_) =>
      call { 
        controllers_AuthController_login8_invoker.call(AuthController_2.login())
      }
  
    // @LINE:20
    case controllers_AuthController_doLogin9_route(params@_) =>
      call { 
        controllers_AuthController_doLogin9_invoker.call(AuthController_2.doLogin())
      }
  
    // @LINE:21
    case controllers_AuthController_logout10_route(params@_) =>
      call { 
        controllers_AuthController_logout10_invoker.call(AuthController_2.logout())
      }
  
    // @LINE:22
    case controllers_AuthController_register11_route(params@_) =>
      call { 
        controllers_AuthController_register11_invoker.call(AuthController_2.register())
      }
  
    // @LINE:23
    case controllers_AuthController_doRegister12_route(params@_) =>
      call { 
        controllers_AuthController_doRegister12_invoker.call(AuthController_2.doRegister())
      }
  
    // @LINE:26
    case controllers_TeaController_password13_route(params@_) =>
      call { 
        controllers_TeaController_password13_invoker.call(TeaController_0.password())
      }
  
    // @LINE:27
    case controllers_TeaController_updatePassword14_route(params@_) =>
      call { 
        controllers_TeaController_updatePassword14_invoker.call(TeaController_0.updatePassword())
      }
  
    // @LINE:30
    case controllers_Assets_versioned15_route(params@_) =>
      call(Param[String]("path", Right("/public")), params.fromPath[Asset]("file", None)) { (path, file) =>
        controllers_Assets_versioned15_invoker.call(Assets_3.versioned(path, file))
      }
  
    // @LINE:33
    case controllers_ErrorController_forbidden16_route(params@_) =>
      call { 
        controllers_ErrorController_forbidden16_invoker.call(ErrorController_1.forbidden())
      }
  }
}
