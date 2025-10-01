// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/io/Projects/GitHub/tea-growth-record/conf/routes
// @DATE:Wed Oct 01 20:12:34 JST 2025

import play.api.mvc.Call


import _root_.controllers.Assets.Asset
import _root_.models.TeaType
import _root_.models.TeaStatus

// @LINE:7
package controllers {

  // @LINE:7
  class ReverseAssets(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:7
    def versioned(file:Asset): Call = {
      implicit lazy val _rrc = new play.core.routing.ReverseRouteContext(Map(("path", "/public"))); _rrc
      Call("GET", _prefix + { _defaultPrefix } + "assets/" + implicitly[play.api.mvc.PathBindable[Asset]].unbind("file", file))
    }
  
  }

  // @LINE:34
  class ReverseErrorController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:34
    def forbidden(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "error/forbidden")
    }
  
  }

  // @LINE:20
  class ReverseTeaController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:26
    def delete(id:Long): Call = {
      
      Call("DELETE", _prefix + { _defaultPrefix } + "teas/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Long]].unbind("id", id)))
    }
  
    // @LINE:23
    def show(id:Long): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "teas/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Long]].unbind("id", id)))
    }
  
    // @LINE:21
    def create(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "teas/new")
    }
  
    // @LINE:24
    def edit(id:Long): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "teas/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Long]].unbind("id", id)) + "/edit")
    }
  
    // @LINE:29
    def profile(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "profile")
    }
  
    // @LINE:25
    def update(id:Long): Call = {
      
      Call("PUT", _prefix + { _defaultPrefix } + "teas/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Long]].unbind("id", id)))
    }
  
    // @LINE:22
    def save(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "teas")
    }
  
    // @LINE:31
    def updatePassword(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "profile/password")
    }
  
    // @LINE:30
    def updateProfile(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "profile")
    }
  
    // @LINE:20
    def index(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "teas")
    }
  
  }

  // @LINE:10
  class ReverseHomeController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:10
    def index(): Call = {
      
      Call("GET", _prefix)
    }
  
  }

  // @LINE:13
  class ReverseAuthController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:16
    def create(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "register")
    }
  
    // @LINE:17
    def logout(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "logout")
    }
  
    // @LINE:15
    def register(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "register")
    }
  
    // @LINE:14
    def authenticate(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "login")
    }
  
    // @LINE:13
    def login(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "login")
    }
  
  }


}
