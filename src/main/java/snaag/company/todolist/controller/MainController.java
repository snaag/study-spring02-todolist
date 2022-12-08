package snaag.company.todolist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class MainController {

    @GetMapping("/")
    String main(Model model) {
        model.addAttribute("data", now());
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
