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
    final case class TodoData(title: String, detail: String)

    val Todo = ReactComponentB[TodoData]("Todo")
        .render_P(data =>
            <.tr(
                <.td(^.border := "1px solid black;",
                    data.title),
                <.td(^.border := "1px solid black;",
                    data.detail)
            )
        )
        .build

    val TodoList = ReactComponentB[Unit]("TodoList")
        .render(_ =>
            <.div(^.className := "todoList",
                <.table(^.border := "2px solid black",
                    <.tbody(
                        Todo(TodoData("Shopping", "Milk")),
                        Todo(TodoData("Hair cut", "13:00"))
                    )
                )
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
