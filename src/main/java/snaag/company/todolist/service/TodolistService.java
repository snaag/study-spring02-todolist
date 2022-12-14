package snaag.company.todolist.service;

import snaag.company.todolist.domain.TodoItem;
import snaag.company.todolist.repository.TodolistRepository;

import java.util.List;
import java.util.Optional;

public class TodolistService {

    private final TodolistRepository todolistRepository;

    public TodolistService(TodolistRepository todolistRepository) {
        this.todolistRepository = todolistRepository;
    }

//    create
    private TodoItem create(TodoItem todoItem) {
        return todolistRepository.save(todoItem);
    }

//    retrieve (all)
    private List<TodoItem> getAll() {
        return todolistRepository.findAll();
    }

//    retrieve (byId)
    private Optional<TodoItem> getById(Long id) {
        return todolistRepository.findById(id);
    }

//    update
    private TodoItem update(TodoItem todoItem) {
        return todolistRepository.update(todoItem);
    }

//    clear
    private void deleteById(Long id) {
        todolistRepository.delete(id);
    }

//    delete
    private void clear() {
        todolistRepository.clear();
    }

}
