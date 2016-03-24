package wheely.scalajs.learnyoureact

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import org.scalajs.dom

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExport
import scalacss.Defaults._
import scalacss.ScalaCssReact._

/**
  * Created by wheely on 16-3-23.
  */
@JSExport("TodoBox")
object TodoBox extends js.JSApp {

    object TodoTableStyle extends StyleSheet.Inline {

        import dsl._

        val tableContent = style(
            borderWidth(1 px),
            borderStyle.solid,
            borderColor.black
        )

        val tableBorder = style(
            borderWidth(2 px),
            borderStyle.solid,
            borderColor.black
        )

        val checkedTodo = style(textDecorationLine.lineThrough)
        val notCheckedTodo = style(textDecorationLine.none)
    }

    final case class TodoData(title: String, detail: String)

    final case class TodoState(checked: Boolean)

    type TodoProps = (TodoData, TodoListBackend)

    final class TodoBackend($: BackendScope[TodoProps, TodoState]) {
        private def handleChange(title: String)(e: ReactEventI) = {
            val newState: Boolean = e.target.checked
            $.modState(_.copy(checked = newState)) >>
                Callback.log(s"$title: $newState")
        }

        def render(state: TodoState, props: TodoProps) = props match {
            case (data, listBackend) => <.tr(
                state.checked ?= TodoTableStyle.checkedTodo,
                !state.checked ?= TodoTableStyle.notCheckedTodo,
                <.td(TodoTableStyle.tableContent,
                    <.button(^.onClick --> listBackend.onDelete(data.title), "X")
                ),
                <.td(TodoTableStyle.tableContent,
                    <.input(
                        ^.`type` := "checkbox",
                        ^.checked := state.checked,
                        ^.onChange ==> handleChange(data.title))
                ),
                <.td(TodoTableStyle.tableContent,
                    data.title),
                <.td(TodoTableStyle.tableContent,
                    data.detail)
            )
        }
    }

    val Todo = ReactComponentB[TodoProps]("Todo")
        .initialState(TodoState(false))
        .renderBackend[TodoBackend]
        .build

    type TodoListData = List[TodoData]

    def todos: TodoListData =
        List(TodoData("Shopping", "Milk"), TodoData("Hair cut", "13:00"))

    final case class TodoListState(data: TodoListData,
                                   titleValue: Option[String],
                                   detailValue: Option[String]) {

        def removeTodo(title: String): TodoListState = {
            this.copy(data = data.filter { case TodoData(t, _) => t != title })
        }
    }

    final class TodoListBackend($: BackendScope[TodoListData, TodoListState]) {

        private def changeTitle(e: ReactEventI) =
            $.modState(_.copy(titleValue = Some(e.target.value)))

        private def changeDetail(e: ReactEventI) =
            $.modState(_.copy(detailValue = Some(e.target.value)))

        private def addTodo() = $.modState {
            case TodoListState(data, Some(title), Some(detail)) =>
                TodoListState(data :+ TodoData(title, detail), None, None)
            case state => state
        }

        def onDelete(title: String): Callback =
            $.modState(_.removeTodo(title))

        def render(state: TodoListState) =
            <.div(^.className := "todoList",
                <.div(
                    "Title:",
                    <.input(
                        ^.`type` := "text",
                        ^.value := state.titleValue,
                        ^.onChange ==> changeTitle),
                    "Detail:",
                    <.input(
                        ^.`type` := "text",
                        ^.value := state.detailValue,
                        ^.onChange ==> changeDetail),
                    <.button(^.onClick --> addTodo, "Add")
                ),
                <.table(TodoTableStyle.tableBorder,
                    <.tbody(
                        state.data.map(d => Todo.withKey(d.title)((d, this)))
                    )
                )
            )
    }

    val TodoList = ReactComponentB[TodoListData]("TodoList")
        .initialState(TodoListState(Nil, None, None))
        .renderBackend[TodoListBackend]
        .componentDidMount($ => $.modState(_.copy(data = $.props)))
        .build

    val TodoForm = ReactComponentB[Unit]("TodoForm")
        .render(_ =>
            <.div(^.className := "todoForm",
                "I am a TodoForm."
            )
        )
        .buildU

    val TodoBox = ReactComponentB[TodoListData]("Todo Box")
        .render_P(todos =>
            <.div(^.className := "todoBox",
                <.h1("Todos"),
                TodoList(todos),
                TodoForm()
            ))
        .build

    @JSExport
    override def main(): Unit = {
        TodoTableStyle.addToDocument()
        ReactDOM.render(TodoBox(todos), dom.document.getElementById("root"))
    }
}
