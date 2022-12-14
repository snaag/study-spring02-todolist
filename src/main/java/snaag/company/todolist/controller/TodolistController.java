package snaag.company.todolist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import snaag.company.todolist.domain.TodoItem;
import snaag.company.todolist.service.TodolistService;


@Controller
public class TodolistController {

    final TodolistService todolistService;

    @Autowired
    public TodolistController(TodolistService todolistService) {
        this.todolistService = todolistService;
    }

    @PostMapping("create")
    public String createTodoItem(TodoItemForm todoItemForm) {
        TodoItem todoItem = new TodoItem();
        todoItem.setText(todoItemForm.getText());

        todolistService.create(todoItem);

        return "redirect:/";
    }

    @PostMapping("update")
    public String updateTodoItem(TodoItemForm todoItemForm) {
        TodoItem todoItem = new TodoItem();

        todoItem.setId(Long.parseLong(todoItemForm.getId()));
        todoItem.setText(todoItemForm.getText());
        todoItem.setDone(Boolean.parseBoolean(todoItemForm.getDone()));

        todolistService.update(todoItem);
        return "redirect:/";
    }
}
