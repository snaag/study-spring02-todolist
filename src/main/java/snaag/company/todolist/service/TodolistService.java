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
    public TodoItem create(TodoItem todoItem) {
        return todolistRepository.save(todoItem);
    }

//    retrieve (all)
    public List<TodoItem> getAll() {
        return todolistRepository.findAll();
    }

//    retrieve (byId)
    public Optional<TodoItem> getById(Long id) {
        return todolistRepository.findById(id);
    }

//    update
    public TodoItem update(TodoItem todoItem) {
        return todolistRepository.update(todoItem);
    }

//    clear
    public void deleteById(Long id) {
        todolistRepository.delete(id);
    }

//    delete
    public void clear() {
        todolistRepository.clear();
    }

}
