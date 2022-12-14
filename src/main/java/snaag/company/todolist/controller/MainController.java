package snaag.company.todolist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import snaag.company.todolist.domain.TodoItem;
import snaag.company.todolist.service.TodolistService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class MainController {

    private final TodolistService todolistService;

    @Autowired
    public MainController(TodolistService todolistService) {
        this.todolistService = todolistService;
    }

    @GetMapping("/")
    String main(Model model) {
        List<TodoItem> result = todolistService.getAll();

        model.addAttribute("data", now());
        model.addAttribute("todolist", result);

        return "todolist/main";
    }


    private String now() {
        // time format 참고
        // https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
}
