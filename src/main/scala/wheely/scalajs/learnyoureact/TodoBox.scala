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

    val TodoList = ReactComponentB[Unit]("TodoList")
        .render(_ =>
            <.div(^.className := "todoList",
                "I am a TodoList."
            )
        )
        .buildU

    val TodoForm = ReactComponentB[Unit]("TodoForm")
        .render(_ =>
            <.div(^.className := "todoForm",
                "I am a TodoForm."
            )
        )
        .buildU

    val TodoBox = ReactComponentB[Unit]("Todo Box")
        .render(_ =>
            <.div(^.className := "todoBox",
                <.h1("Todos"),
                TodoList(),
                TodoForm()
            ))
        .buildU

    @JSExport
    override def main(): Unit = {
        ReactDOM.render(TodoBox(), dom.document.getElementById("root"))
    }
}
