package snaag.company.todolist.repository;

import snaag.company.todolist.domain.TodoItem;

import java.util.*;

public class MemoryTodolistRepository implements TodolistRepository {
    private Map<Long, TodoItem> store = new HashMap<>();
    private Long sequence = 0L;

    @Override
    public TodoItem save(TodoItem todoItem) {
        todoItem.setId(sequence);
        store.put(sequence, todoItem);
        sequence++;
        return todoItem;
    }

    @Override
    public List<TodoItem> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Optional<TodoItem> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public TodoItem update(TodoItem todoItem) {
        store.put(todoItem.getId(), todoItem);
        return todoItem;
    }

    @Override
    public void delete(Long todoId) {
        store.remove(todoId);
    }

    @Override
    public void clear() {
        store.clear();
        sequence = 0L;
    }

}
