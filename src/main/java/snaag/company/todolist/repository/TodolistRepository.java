package snaag.company.todolist.repository;

import org.springframework.stereotype.Repository;
import snaag.company.todolist.domain.TodoItem;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface TodolistRepository {
    // C
    TodoItem save(TodoItem todoItem);

    // R
    List<TodoItem> findAll();

    Optional<TodoItem> findById(Long id);

    // U
    TodoItem update(TodoItem todoItem);
    // D
    void delete(Long id);

    void clear();
}
