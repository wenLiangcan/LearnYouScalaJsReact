package wheely.scalajs.learnyoureact

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import org.scalajs.dom

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExport

/**
  * Created by wheely on 16-3-23.
  */
@JSExport("TodoBox")
object TodoBox extends js.JSApp {

    val TodoBox = ReactComponentB[Unit]("Todo Box")
        .render(_ =>
            <.div(^.className := "todoBox",
                "Hello, world!"
            ))
        .buildU

    @JSExport
    override def main(): Unit = {
        ReactDOM.render(TodoBox(), dom.document.getElementById("root"))
    }
}
