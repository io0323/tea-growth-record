// @GENERATOR:play-routes-compiler
// @SOURCE:conf/routes

package router

import play.core.routing._
import play.core.routing.HandlerInvokerFactory._

import play.api.mvc._

import _root_.controllers.Assets.Asset

class Routes(
  override val errorHandler: play.api.http.HttpErrorHandler, 
  // @LINE:6
  HomeController_4: controllers.HomeController,
  // @LINE:9
  AuthController_1: controllers.AuthController,
  // @LINE:16
  UserController_2: controllers.UserController,
  // @LINE:21
  TeaController_0: controllers.TeaController,
  // @LINE:29
  Assets_3: controllers.Assets,
  val prefix: String
) extends GeneratedRouter {

   @javax.inject.Inject()
   def this(errorHandler: play.api.http.HttpErrorHandler,
    // @LINE:6
    HomeController_4: controllers.HomeController,
    // @LINE:9
    AuthController_1: controllers.AuthController,
    // @LINE:16
    UserController_2: controllers.UserController,
    // @LINE:21
    TeaController_0: controllers.TeaController,
    // @LINE:29
    Assets_3: controllers.Assets
  ) = this(errorHandler, HomeController_4, AuthController_1, UserController_2, TeaController_0, Assets_3, "/")

  def withPrefix(addPrefix: String): Routes = {
    val prefix = play.api.routing.Router.concatPrefix(addPrefix, this.prefix)
    router.RoutesPrefix.setPrefix(prefix)
    new Routes(errorHandler, HomeController_4, AuthController_1, UserController_2, TeaController_0, Assets_3, prefix)
  }

  private[this] val defaultPrefix: String = {
    if (this.prefix.endsWith("/")) "" else "/"
  }

  def documentation = List(
    ("""GET""", this.prefix, """controllers.HomeController.index()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """login""", """controllers.AuthController.login()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """login""", """controllers.AuthController.loginSubmit()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """logout""", """controllers.AuthController.logout()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """signup""", """controllers.AuthController.signup()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """signup""", """controllers.AuthController.signupSubmit()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """profile""", """controllers.UserController.profile()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """profile/update""", """controllers.UserController.updateProfile()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """profile/change-password""", """controllers.UserController.changePassword()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """api/teas""", """controllers.TeaController.index"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """api/teas/""" + "$" + """id<[^/]+>""", """controllers.TeaController.show(id:Long)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """api/teas""", """controllers.TeaController.create"""),
    ("""PUT""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """api/teas/""" + "$" + """id<[^/]+>""", """controllers.TeaController.update(id:Long)"""),
    ("""DELETE""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """api/teas/""" + "$" + """id<[^/]+>""", """controllers.TeaController.delete(id:Long)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """api/teas/""" + "$" + """id<[^/]+>/image""", """controllers.TeaController.uploadImage(id:Long)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """assets/""" + "$" + """file<.+>""", """controllers.Assets.versioned(path:String = "/public", file:Asset)"""),
    Nil
  ).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
    case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
    case l => s ++ l.asInstanceOf[List[(String,String,String)]]
  }}


  // @LINE:6
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

  // @LINE:9
  private[this] lazy val controllers_AuthController_login1_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("login")))
  )
  private[this] lazy val controllers_AuthController_login1_invoker = createInvoker(
    AuthController_1.login(),
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

  // @LINE:10
  private[this] lazy val controllers_AuthController_loginSubmit2_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("login")))
  )
  private[this] lazy val controllers_AuthController_loginSubmit2_invoker = createInvoker(
    AuthController_1.loginSubmit(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.AuthController",
      "loginSubmit",
      Nil,
      "POST",
      this.prefix + """login""",
      """""",
      Seq()
    )
  )

  // @LINE:11
  private[this] lazy val controllers_AuthController_logout3_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("logout")))
  )
  private[this] lazy val controllers_AuthController_logout3_invoker = createInvoker(
    AuthController_1.logout(),
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

  // @LINE:12
  private[this] lazy val controllers_AuthController_signup4_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("signup")))
  )
  private[this] lazy val controllers_AuthController_signup4_invoker = createInvoker(
    AuthController_1.signup(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.AuthController",
      "signup",
      Nil,
      "GET",
      this.prefix + """signup""",
      """""",
      Seq()
    )
  )

  // @LINE:13
  private[this] lazy val controllers_AuthController_signupSubmit5_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("signup")))
  )
  private[this] lazy val controllers_AuthController_signupSubmit5_invoker = createInvoker(
    AuthController_1.signupSubmit(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.AuthController",
      "signupSubmit",
      Nil,
      "POST",
      this.prefix + """signup""",
      """""",
      Seq()
    )
  )

  // @LINE:16
  private[this] lazy val controllers_UserController_profile6_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("profile")))
  )
  private[this] lazy val controllers_UserController_profile6_invoker = createInvoker(
    UserController_2.profile(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.UserController",
      "profile",
      Nil,
      "GET",
      this.prefix + """profile""",
      """ User profile""",
      Seq()
    )
  )

  // @LINE:17
  private[this] lazy val controllers_UserController_updateProfile7_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("profile/update")))
  )
  private[this] lazy val controllers_UserController_updateProfile7_invoker = createInvoker(
    UserController_2.updateProfile(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.UserController",
      "updateProfile",
      Nil,
      "POST",
      this.prefix + """profile/update""",
      """""",
      Seq()
    )
  )

  // @LINE:18
  private[this] lazy val controllers_UserController_changePassword8_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("profile/change-password")))
  )
  private[this] lazy val controllers_UserController_changePassword8_invoker = createInvoker(
    UserController_2.changePassword(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.UserController",
      "changePassword",
      Nil,
      "POST",
      this.prefix + """profile/change-password""",
      """""",
      Seq()
    )
  )

  // @LINE:21
  private[this] lazy val controllers_TeaController_index9_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("api/teas")))
  )
  private[this] lazy val controllers_TeaController_index9_invoker = createInvoker(
    TeaController_0.index,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.TeaController",
      "index",
      Nil,
      "GET",
      this.prefix + """api/teas""",
      """ Tea management""",
      Seq()
    )
  )

  // @LINE:22
  private[this] lazy val controllers_TeaController_show10_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("api/teas/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val controllers_TeaController_show10_invoker = createInvoker(
    TeaController_0.show(fakeValue[Long]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.TeaController",
      "show",
      Seq(classOf[Long]),
      "GET",
      this.prefix + """api/teas/""" + "$" + """id<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:23
  private[this] lazy val controllers_TeaController_create11_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("api/teas")))
  )
  private[this] lazy val controllers_TeaController_create11_invoker = createInvoker(
    TeaController_0.create,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.TeaController",
      "create",
      Nil,
      "POST",
      this.prefix + """api/teas""",
      """""",
      Seq()
    )
  )

  // @LINE:24
  private[this] lazy val controllers_TeaController_update12_route = Route("PUT",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("api/teas/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val controllers_TeaController_update12_invoker = createInvoker(
    TeaController_0.update(fakeValue[Long]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.TeaController",
      "update",
      Seq(classOf[Long]),
      "PUT",
      this.prefix + """api/teas/""" + "$" + """id<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:25
  private[this] lazy val controllers_TeaController_delete13_route = Route("DELETE",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("api/teas/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val controllers_TeaController_delete13_invoker = createInvoker(
    TeaController_0.delete(fakeValue[Long]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.TeaController",
      "delete",
      Seq(classOf[Long]),
      "DELETE",
      this.prefix + """api/teas/""" + "$" + """id<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:26
  private[this] lazy val controllers_TeaController_uploadImage14_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("api/teas/"), DynamicPart("id", """[^/]+""",true), StaticPart("/image")))
  )
  private[this] lazy val controllers_TeaController_uploadImage14_invoker = createInvoker(
    TeaController_0.uploadImage(fakeValue[Long]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.TeaController",
      "uploadImage",
      Seq(classOf[Long]),
      "POST",
      this.prefix + """api/teas/""" + "$" + """id<[^/]+>/image""",
      """""",
      Seq()
    )
  )

  // @LINE:29
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


  def routes: PartialFunction[RequestHeader, Handler] = {
  
    // @LINE:6
    case controllers_HomeController_index0_route(params@_) =>
      call { 
        controllers_HomeController_index0_invoker.call(HomeController_4.index())
      }
  
    // @LINE:9
    case controllers_AuthController_login1_route(params@_) =>
      call { 
        controllers_AuthController_login1_invoker.call(AuthController_1.login())
      }
  
    // @LINE:10
    case controllers_AuthController_loginSubmit2_route(params@_) =>
      call { 
        controllers_AuthController_loginSubmit2_invoker.call(AuthController_1.loginSubmit())
      }
  
    // @LINE:11
    case controllers_AuthController_logout3_route(params@_) =>
      call { 
        controllers_AuthController_logout3_invoker.call(AuthController_1.logout())
      }
  
    // @LINE:12
    case controllers_AuthController_signup4_route(params@_) =>
      call { 
        controllers_AuthController_signup4_invoker.call(AuthController_1.signup())
      }
  
    // @LINE:13
    case controllers_AuthController_signupSubmit5_route(params@_) =>
      call { 
        controllers_AuthController_signupSubmit5_invoker.call(AuthController_1.signupSubmit())
      }
  
    // @LINE:16
    case controllers_UserController_profile6_route(params@_) =>
      call { 
        controllers_UserController_profile6_invoker.call(UserController_2.profile())
      }
  
    // @LINE:17
    case controllers_UserController_updateProfile7_route(params@_) =>
      call { 
        controllers_UserController_updateProfile7_invoker.call(UserController_2.updateProfile())
      }
  
    // @LINE:18
    case controllers_UserController_changePassword8_route(params@_) =>
      call { 
        controllers_UserController_changePassword8_invoker.call(UserController_2.changePassword())
      }
  
    // @LINE:21
    case controllers_TeaController_index9_route(params@_) =>
      call { 
        controllers_TeaController_index9_invoker.call(TeaController_0.index)
      }
  
    // @LINE:22
    case controllers_TeaController_show10_route(params@_) =>
      call(params.fromPath[Long]("id", None)) { (id) =>
        controllers_TeaController_show10_invoker.call(TeaController_0.show(id))
      }
  
    // @LINE:23
    case controllers_TeaController_create11_route(params@_) =>
      call { 
        controllers_TeaController_create11_invoker.call(TeaController_0.create)
      }
  
    // @LINE:24
    case controllers_TeaController_update12_route(params@_) =>
      call(params.fromPath[Long]("id", None)) { (id) =>
        controllers_TeaController_update12_invoker.call(TeaController_0.update(id))
      }
  
    // @LINE:25
    case controllers_TeaController_delete13_route(params@_) =>
      call(params.fromPath[Long]("id", None)) { (id) =>
        controllers_TeaController_delete13_invoker.call(TeaController_0.delete(id))
      }
  
    // @LINE:26
    case controllers_TeaController_uploadImage14_route(params@_) =>
      call(params.fromPath[Long]("id", None)) { (id) =>
        controllers_TeaController_uploadImage14_invoker.call(TeaController_0.uploadImage(id))
      }
  
    // @LINE:29
    case controllers_Assets_versioned15_route(params@_) =>
      call(Param[String]("path", Right("/public")), params.fromPath[Asset]("file", None)) { (path, file) =>
        controllers_Assets_versioned15_invoker.call(Assets_3.versioned(path, file))
      }
  }
}
