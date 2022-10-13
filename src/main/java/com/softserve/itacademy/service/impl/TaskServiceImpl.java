package com.softserve.itacademy.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.TaskService;
import com.softserve.itacademy.service.ToDoService;

@Service
public class TaskServiceImpl implements TaskService {

    private ToDoService toDoService;

    @Autowired
    public TaskServiceImpl(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    public Task addTask(Task task, ToDo todo) {
        if (task == null) {
            throw new RuntimeException("Task is null!");
        }

        if (task.getName() == null || task.getPriority() == null) {
            throw new RuntimeException("Task parameter is null!");
        }

        if (todo == null || toDoService.getByUserTitle(todo.getOwner(), todo.getTitle()) == null) {
            throw new RuntimeException("ToDo not found!");
        }

        boolean isContains = todo.getTasks().stream().anyMatch(t -> t.equals(task));

        if (isContains) {
            throw new RuntimeException(String.format("Task %s already exists!", task.getName()));
        }

        todo.getTasks().add(task);
        return task;
    }

    public Task updateTask(Task task) {
        if (task == null) {
            throw new RuntimeException("Task is null!");
        }

        if (task.getName() == null || task.getPriority() == null) {
            throw new RuntimeException("Task parameter is null!");
        }

        for (ToDo toDo : toDoService.getAll()) {
            if (toDo.getTasks().contains(task)) {
                toDo.getTasks().get(toDo.getTasks().indexOf(task)).setPriority(task.getPriority());
                toDoService.updateTodo(toDo);
                return task;
            }
        }
        throw new RuntimeException(String.format("Task %s not found!", task.getName()));
    }

    public void deleteTask(Task task) {
        if (task == null) {
            throw new RuntimeException("Task is null!");
        }
        boolean find = false;
        for (ToDo toDo : toDoService.getAll()) {
            if (toDo.getTasks().contains(task)) {
                toDo.getTasks().remove(task);
                toDoService.updateTodo(toDo);
                return;
            }
        }

        throw new RuntimeException(String.format("Task %s not found!", task.getName()));
    }

    public List<Task> getAll() {
        List<Task> tasks = new ArrayList<>();
        for (ToDo toDo : toDoService.getAll()) {
            tasks.addAll(toDo.getTasks());
        }
        return tasks;
    }

    public List<Task> getByToDo(ToDo todo) {
        return toDoService.getByUserTitle(todo.getOwner(), todo.getTitle()) != null ?  todo.getTasks() : null;
    }

    public Task getByToDoName(ToDo todo, String name) {
        if (getByToDo(todo) != null) {
            return todo.getTasks()
                    .stream()
                    .filter(t -> t.getName().equals(name))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    public Task getByUserName(User user, String name) {
        if (toDoService.getByUser(user) != null) {
            return toDoService.getByUser(user)
                    .stream()
                    .flatMap(t -> t.getTasks().stream())
                    .filter(t -> t.getName().equals(name))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

}
