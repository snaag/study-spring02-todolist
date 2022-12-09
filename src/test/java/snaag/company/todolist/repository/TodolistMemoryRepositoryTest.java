package snaag.company.todolist.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import snaag.company.todolist.domain.TodoItem;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

class TodolistMemoryRepositoryTest {

    MemoryTodolistRepository todolistMemoryRepository = new MemoryTodolistRepository();

    @BeforeEach()
    void beforeEach() {
        todolistMemoryRepository.clear();
        TodoItem item1 = new TodoItem();
        item1.setText("coffee");
        item1.setDone(false);

        TodoItem item2 = new TodoItem();
        item2.setText("pay for ticket");
        item2.setDone(true);

        todolistMemoryRepository.save(item1);
        todolistMemoryRepository.save(item2);
    }

    @Test
    void save() {
        TodoItem newItem = new TodoItem();
        newItem.setText("sandwich");
        newItem.setDone(true);

        todolistMemoryRepository.save(newItem);

        assertThat(todolistMemoryRepository.findAll().size()).isEqualTo(3);
    }

    @Test
    void findAll() {
        assertThat(todolistMemoryRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    void findById() {
        Long emptyId = 3L;
        Long presentId = 0L;

        Optional<TodoItem> resultEmpty = todolistMemoryRepository.findById(emptyId);
        assertThat(resultEmpty.isEmpty()).isEqualTo(true);

        Optional<TodoItem> resultPresent = todolistMemoryRepository.findById(presentId);
        assertThat(resultPresent.isPresent()).isEqualTo(true);
        assertThat(resultPresent.get().getText()).isEqualTo("coffee");
    }

    @Test
    void update() {
        TodoItem newItem = new TodoItem();
        newItem.setText("NEW_ITEM");
        newItem.setDone(true);
        newItem.setId(0L);

        todolistMemoryRepository.update(newItem);

        assertThat(todolistMemoryRepository.findById(0L).get().getText()).isEqualTo("NEW_ITEM");
    }

    @Test
    void delete() {
        todolistMemoryRepository.delete(0L);

        assertThat(todolistMemoryRepository.findById(0L).isEmpty()).isEqualTo(true);
        assertThat(todolistMemoryRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    void clear() {
        todolistMemoryRepository.clear();
        assertThat(todolistMemoryRepository.findAll().size()).isEqualTo(0);
    }


}