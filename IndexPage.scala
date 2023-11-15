package views.jumpstart.day2

import com.greenfossil.data.mapping.Mapping
import com.greenfossil.jumpstart.day2.Task
import com.greenfossil.htmltags.{*, given}
import com.greenfossil.thorium.{*, given}
import controllers.jumpstart.day2.HomeController

/**
 * This is to simulate an HTML Page
 */
case class IndexPage(tasks: List[Task], addTaskMapping: Mapping[String]):
  def render: Tag = html(
    head(
      Tags2.title("To Do"),
      link(rel := "shortcut icon", href := "data:;base64,iVBORw0KGgo="),
      link(rel := "stylesheet", href := "https://cdn.jsdelivr.net/npm/fomantic-ui@2.9.3/dist/semantic.min.css"),
      Tags2.style(
        // For advanced developer, you can add extra CSS style below
        raw(
          """/*
            | * Enter your CSS code here (optional)
            | */
            | .h1 {
            |   color: yellow;
            | }
            |""".stripMargin)
      )
    ),
    body(marginTop := 2.rem)(
      div(cls := "ui container")(
        h1(cls := "ui dividing header", "To Do List"),

        form(cls := "ui form", method := "POST", action := HomeController.createTask.endpoint.url)(
          div(cls := "field", ifTrue(addTaskMapping.hasErrors, cls := "error"))(
            div(cls := "ui fluid action input")(
              input(tpe := "text", name := "task", placeholder := "Enter task"),
              button(cls := "ui primary button", "Add")
            )
          )
        ),

        h3(cls := "ui header", "Tasks: ", tasks.size),


        table(cls:="ui very basic table",
          tbody(
            tasks.map{task =>
              tr(
                td(cls := "collapsing", h1(task.id)),
                td(h3(task.description)),
                td(cls := "collapsing"),
                td(
                  form(cls := "ui form", method := "POST", action := HomeController.deleteTask(task.id).endpoint.url)(
                    button(cls := "ui primary button", "Delete")
                  ),
                  ifFalse(task.isCompleted,
                    form(cls := "ui form", method := "POST", action := HomeController.completeTask(task.id).endpoint.url)(
                      div(cls := "field")(
                        button(cls := "ui primary button", "Complete")
                      )
                    )
                  ),
                  ifTrue(task.isCompleted,
                    div(cls := "field")(
                      button(cls := "ui primary button", "Completed", backgroundColor := "green")
                    ),
                  ),
                )
              )
            }
          )
        )
        /*
         * Implement a table that lists down in a table, with the below structure:
         * table[class="ui very basic table"]:
         *    tbody:
         *        tr:
         *            td[class="collapsing] > Task index, starting at 1
         *            td > Task description [Strikethrough if the task is completed]
         *            td[class="collapsing"]
         *                button > Complete [Marks task as completed]
         *                button > Delete [Deletes task]
         */
      ),

      script(tpe := "text/javascript", src := "https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.min.js"),
      script(tpe := "text/javascript", src := "https://cdn.jsdelivr.net/npm/fomantic-ui@2.9.3/dist/semantic.min.js"),
      script(tpe := "text/javascript",
        raw(
          """ /*
            |  *  Enter JavaScript code here
            |  */
            |""".stripMargin)
      )
    )
  )
