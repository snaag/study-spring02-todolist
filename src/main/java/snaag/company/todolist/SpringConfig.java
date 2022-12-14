package snaag.company.todolist;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import snaag.company.todolist.repository.JdbcTemplateTodolistRepository;
import snaag.company.todolist.repository.JdbcTodolistRepository;
import snaag.company.todolist.repository.TodolistRepository;
import snaag.company.todolist.service.TodolistService;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private DataSource dataSource;

    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // 동적으로 Bean 을 만들때에는 annotation(@) 대신 SpringConfig 에 작성하는것이 좋다
    @Bean
    public TodolistRepository todolistRepository() {
//        return new MemoryTodolistRepository();
//        return new JdbcTodolistRepository(dataSource);
        return new JdbcTemplateTodolistRepository(dataSource);
    }

    @Bean
    public TodolistService todolistService() {
        return new TodolistService(todolistRepository());
    }

}
