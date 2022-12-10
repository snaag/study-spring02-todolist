package snaag.company.todolist.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import snaag.company.todolist.domain.TodoItem;

import java.util.List;
import java.util.Optional;

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

    @Test
    void findAll() {
        List<TodoItem> store = todolistRepository.findAll();
        for (TodoItem item: store) {
            System.out.println(">>>>> item.getText() = " + item.getText());
            System.out.println(">>>>> item.getId() = " + item.getId());
        }
    }

    @Test
    void findById() {
        Long idPresent = 3L;
        Optional<TodoItem> resultPresent = todolistRepository.findById(idPresent);
        assertThat(resultPresent.isPresent()).isEqualTo(true);

        Long idEmpty = 100L;
        Optional<TodoItem> resultEmpty = todolistRepository.findById(idEmpty);
        assertThat(resultEmpty.isEmpty()).isEqualTo(true);
    }

    @Test
    void update() {
        TodoItem updateItem = new TodoItem();
        updateItem.setId(1L);
        updateItem.setText("UPDATE");
        updateItem.setDone(true);

        todolistRepository.update(updateItem);

        assertThat(todolistRepository.findById(1L).get().getText()).isEqualTo("UPDATE");
    }

    @Test
    void delete() {
        Long deleteId = 1L;

        todolistRepository.delete(deleteId);

        assertThat(todolistRepository.findById(deleteId).isEmpty()).isEqualTo(true);
    }

    @Test
    void clear() {
        todolistRepository.clear();

        TodoItem item1 = new TodoItem();
        item1.setText("cat cute");
        item1.setDone(true);

        TodoItem item2 = new TodoItem();
        item2.setText("dog cute");
        item2.setDone(true);

        TodoItem item3 = new TodoItem();
        item3.setText("coffee");
        item3.setDone(false);

        todolistRepository.save(item1);
        todolistRepository.save(item2);
        todolistRepository.save(item3);

        assertThat(todolistRepository.findAll().size()).isEqualTo(3);
    }
}