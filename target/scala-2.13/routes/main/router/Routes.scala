// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/io/Projects/GitHub/tea-growth-record/conf/routes
// @DATE:Wed Oct 01 20:12:34 JST 2025

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
  Assets_3: controllers.Assets,
  // @LINE:10
  HomeController_4: controllers.HomeController,
  // @LINE:13
  AuthController_2: controllers.AuthController,
  // @LINE:20
  TeaController_0: controllers.TeaController,
  // @LINE:34
  ErrorController_1: controllers.ErrorController,
  val prefix: String
) extends GeneratedRouter {

   @javax.inject.Inject()
   def this(errorHandler: play.api.http.HttpErrorHandler,
    // @LINE:7
    Assets_3: controllers.Assets,
    // @LINE:10
    HomeController_4: controllers.HomeController,
    // @LINE:13
    AuthController_2: controllers.AuthController,
    // @LINE:20
    TeaController_0: controllers.TeaController,
    // @LINE:34
    ErrorController_1: controllers.ErrorController
  ) = this(errorHandler, Assets_3, HomeController_4, AuthController_2, TeaController_0, ErrorController_1, "/")

  def withPrefix(addPrefix: String): Routes = {
    val prefix = play.api.routing.Router.concatPrefix(addPrefix, this.prefix)
    router.RoutesPrefix.setPrefix(prefix)
    new Routes(errorHandler, Assets_3, HomeController_4, AuthController_2, TeaController_0, ErrorController_1, prefix)
  }

  private[this] val defaultPrefix: String = {
    if (this.prefix.endsWith("/")) "" else "/"
  }

  def documentation = List(
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """assets/""" + "$" + """file<.+>""", """controllers.Assets.versioned(path:String = "/public", file:Asset)"""),
    ("""GET""", this.prefix, """controllers.HomeController.index()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """login""", """controllers.AuthController.login()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """login""", """controllers.AuthController.authenticate()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """register""", """controllers.AuthController.register()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """register""", """controllers.AuthController.create()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """logout""", """controllers.AuthController.logout()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """teas""", """controllers.TeaController.index()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """teas/new""", """controllers.TeaController.create()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """teas""", """controllers.TeaController.save()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """teas/""" + "$" + """id<[^/]+>""", """controllers.TeaController.show(id:Long)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """teas/""" + "$" + """id<[^/]+>/edit""", """controllers.TeaController.edit(id:Long)"""),
    ("""PUT""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """teas/""" + "$" + """id<[^/]+>""", """controllers.TeaController.update(id:Long)"""),
    ("""DELETE""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """teas/""" + "$" + """id<[^/]+>""", """controllers.TeaController.delete(id:Long)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """profile""", """controllers.TeaController.profile()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """profile""", """controllers.TeaController.updateProfile()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """profile/password""", """controllers.TeaController.updatePassword()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """error/forbidden""", """controllers.ErrorController.forbidden()"""),
    Nil
  ).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
    case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
    case l => s ++ l.asInstanceOf[List[(String,String,String)]]
  }}


  // @LINE:7
  private[this] lazy val controllers_Assets_versioned0_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("assets/"), DynamicPart("file", """.+""",false)))
  )
  private[this] lazy val controllers_Assets_versioned0_invoker = createInvoker(
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

  // @LINE:10
  private[this] lazy val controllers_HomeController_index1_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix)))
  )
  private[this] lazy val controllers_HomeController_index1_invoker = createInvoker(
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

  // @LINE:13
  private[this] lazy val controllers_AuthController_login2_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("login")))
  )
  private[this] lazy val controllers_AuthController_login2_invoker = createInvoker(
    AuthController_2.login(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.AuthController",
      "login",
      Nil,
      "GET",
      this.prefix + """login""",
      """ Authentication routes""",
      Seq()
    )
  )

  // @LINE:14
  private[this] lazy val controllers_AuthController_authenticate3_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("login")))
  )
  private[this] lazy val controllers_AuthController_authenticate3_invoker = createInvoker(
    AuthController_2.authenticate(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.AuthController",
      "authenticate",
      Nil,
      "POST",
      this.prefix + """login""",
      """""",
      Seq()
    )
  )

  // @LINE:15
  private[this] lazy val controllers_AuthController_register4_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("register")))
  )
  private[this] lazy val controllers_AuthController_register4_invoker = createInvoker(
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

  // @LINE:16
  private[this] lazy val controllers_AuthController_create5_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("register")))
  )
  private[this] lazy val controllers_AuthController_create5_invoker = createInvoker(
    AuthController_2.create(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.AuthController",
      "create",
      Nil,
      "POST",
      this.prefix + """register""",
      """""",
      Seq()
    )
  )

  // @LINE:17
  private[this] lazy val controllers_AuthController_logout6_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("logout")))
  )
  private[this] lazy val controllers_AuthController_logout6_invoker = createInvoker(
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

  // @LINE:20
  private[this] lazy val controllers_TeaController_index7_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("teas")))
  )
  private[this] lazy val controllers_TeaController_index7_invoker = createInvoker(
    TeaController_0.index(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.TeaController",
      "index",
      Nil,
      "GET",
      this.prefix + """teas""",
      """ Tea routes""",
      Seq()
    )
  )

  // @LINE:21
  private[this] lazy val controllers_TeaController_create8_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("teas/new")))
  )
  private[this] lazy val controllers_TeaController_create8_invoker = createInvoker(
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

  // @LINE:22
  private[this] lazy val controllers_TeaController_save9_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("teas")))
  )
  private[this] lazy val controllers_TeaController_save9_invoker = createInvoker(
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

  // @LINE:23
  private[this] lazy val controllers_TeaController_show10_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("teas/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val controllers_TeaController_show10_invoker = createInvoker(
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

  // @LINE:24
  private[this] lazy val controllers_TeaController_edit11_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("teas/"), DynamicPart("id", """[^/]+""",true), StaticPart("/edit")))
  )
  private[this] lazy val controllers_TeaController_edit11_invoker = createInvoker(
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

  // @LINE:25
  private[this] lazy val controllers_TeaController_update12_route = Route("PUT",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("teas/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val controllers_TeaController_update12_invoker = createInvoker(
    TeaController_0.update(fakeValue[Long]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.TeaController",
      "update",
      Seq(classOf[Long]),
      "PUT",
      this.prefix + """teas/""" + "$" + """id<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:26
  private[this] lazy val controllers_TeaController_delete13_route = Route("DELETE",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("teas/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val controllers_TeaController_delete13_invoker = createInvoker(
    TeaController_0.delete(fakeValue[Long]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.TeaController",
      "delete",
      Seq(classOf[Long]),
      "DELETE",
      this.prefix + """teas/""" + "$" + """id<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:29
  private[this] lazy val controllers_TeaController_profile14_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("profile")))
  )
  private[this] lazy val controllers_TeaController_profile14_invoker = createInvoker(
    TeaController_0.profile(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.TeaController",
      "profile",
      Nil,
      "GET",
      this.prefix + """profile""",
      """ Profile routes""",
      Seq()
    )
  )

  // @LINE:30
  private[this] lazy val controllers_TeaController_updateProfile15_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("profile")))
  )
  private[this] lazy val controllers_TeaController_updateProfile15_invoker = createInvoker(
    TeaController_0.updateProfile(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.TeaController",
      "updateProfile",
      Nil,
      "POST",
      this.prefix + """profile""",
      """""",
      Seq()
    )
  )

  // @LINE:31
  private[this] lazy val controllers_TeaController_updatePassword16_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("profile/password")))
  )
  private[this] lazy val controllers_TeaController_updatePassword16_invoker = createInvoker(
    TeaController_0.updatePassword(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.TeaController",
      "updatePassword",
      Nil,
      "POST",
      this.prefix + """profile/password""",
      """""",
      Seq()
    )
  )

  // @LINE:34
  private[this] lazy val controllers_ErrorController_forbidden17_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("error/forbidden")))
  )
  private[this] lazy val controllers_ErrorController_forbidden17_invoker = createInvoker(
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
    case controllers_Assets_versioned0_route(params@_) =>
      call(Param[String]("path", Right("/public")), params.fromPath[Asset]("file", None)) { (path, file) =>
        controllers_Assets_versioned0_invoker.call(Assets_3.versioned(path, file))
      }
  
    // @LINE:10
    case controllers_HomeController_index1_route(params@_) =>
      call { 
        controllers_HomeController_index1_invoker.call(HomeController_4.index())
      }
  
    // @LINE:13
    case controllers_AuthController_login2_route(params@_) =>
      call { 
        controllers_AuthController_login2_invoker.call(AuthController_2.login())
      }
  
    // @LINE:14
    case controllers_AuthController_authenticate3_route(params@_) =>
      call { 
        controllers_AuthController_authenticate3_invoker.call(AuthController_2.authenticate())
      }
  
    // @LINE:15
    case controllers_AuthController_register4_route(params@_) =>
      call { 
        controllers_AuthController_register4_invoker.call(AuthController_2.register())
      }
  
    // @LINE:16
    case controllers_AuthController_create5_route(params@_) =>
      call { 
        controllers_AuthController_create5_invoker.call(AuthController_2.create())
      }
  
    // @LINE:17
    case controllers_AuthController_logout6_route(params@_) =>
      call { 
        controllers_AuthController_logout6_invoker.call(AuthController_2.logout())
      }
  
    // @LINE:20
    case controllers_TeaController_index7_route(params@_) =>
      call { 
        controllers_TeaController_index7_invoker.call(TeaController_0.index())
      }
  
    // @LINE:21
    case controllers_TeaController_create8_route(params@_) =>
      call { 
        controllers_TeaController_create8_invoker.call(TeaController_0.create())
      }
  
    // @LINE:22
    case controllers_TeaController_save9_route(params@_) =>
      call { 
        controllers_TeaController_save9_invoker.call(TeaController_0.save())
      }
  
    // @LINE:23
    case controllers_TeaController_show10_route(params@_) =>
      call(params.fromPath[Long]("id", None)) { (id) =>
        controllers_TeaController_show10_invoker.call(TeaController_0.show(id))
      }
  
    // @LINE:24
    case controllers_TeaController_edit11_route(params@_) =>
      call(params.fromPath[Long]("id", None)) { (id) =>
        controllers_TeaController_edit11_invoker.call(TeaController_0.edit(id))
      }
  
    // @LINE:25
    case controllers_TeaController_update12_route(params@_) =>
      call(params.fromPath[Long]("id", None)) { (id) =>
        controllers_TeaController_update12_invoker.call(TeaController_0.update(id))
      }
  
    // @LINE:26
    case controllers_TeaController_delete13_route(params@_) =>
      call(params.fromPath[Long]("id", None)) { (id) =>
        controllers_TeaController_delete13_invoker.call(TeaController_0.delete(id))
      }
  
    // @LINE:29
    case controllers_TeaController_profile14_route(params@_) =>
      call { 
        controllers_TeaController_profile14_invoker.call(TeaController_0.profile())
      }
  
    // @LINE:30
    case controllers_TeaController_updateProfile15_route(params@_) =>
      call { 
        controllers_TeaController_updateProfile15_invoker.call(TeaController_0.updateProfile())
      }
  
    // @LINE:31
    case controllers_TeaController_updatePassword16_route(params@_) =>
      call { 
        controllers_TeaController_updatePassword16_invoker.call(TeaController_0.updatePassword())
      }
  
    // @LINE:34
    case controllers_ErrorController_forbidden17_route(params@_) =>
      call { 
        controllers_ErrorController_forbidden17_invoker.call(ErrorController_1.forbidden())
      }
  }
}
