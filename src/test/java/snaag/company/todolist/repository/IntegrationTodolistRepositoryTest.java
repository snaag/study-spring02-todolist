package snaag.company.todolist.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import snaag.company.todolist.domain.TodoItem;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class IntegrationTodolistRepositoryTest {

    @Autowired
    TodolistRepository todolistRepository;
    // IntegrationTodolistRepositoryTest 는 Spring 을 띄우고 하는 테스트 이므로, Spring container 에 등록된 Bean 을 가져올 수 있다.
    // @Autowired 로 Spring 이 알아서 넣어주게 한다.

    @Test
    void save() {
        TodoItem item1 = new TodoItem();
        item1.setText("cat cute");
        item1.setDone(false);

        TodoItem result = todolistRepository.save(item1);
        assertThat(result.getText()).isEqualTo("cat cute");
    }
}