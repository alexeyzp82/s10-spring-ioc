package com.softserve.itacademy.service.impl;

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
        if (task == null) throw new RuntimeException("Task is null!");
        if (task.getName() == null || task.getPriority() == null) throw new RuntimeException("Task parameter is null!");
        if (todo == null || toDoService.getByUserTitle(todo.getOwner(), todo.getTitle()) == null) throw new RuntimeException("ToDo not found!");
        boolean status = todo.getTasks().stream().anyMatch(t -> t.getName().equals(task.getName()));
        if (!status) {
            todo.getTasks().add(task);
            return task;
        } else {
            throw  new RuntimeException(String.format("Task %s already exists!", task.getName()));
        }
    }

    public Task updateTask(Task task) {
        // TODO
        return null;
    }

    public void deleteTask(Task task) {
        // TODO
    }

    public List<Task> getAll() {
        // TODO
        return null;
    }

    public List<Task> getByToDo(ToDo todo) {
        // TODO
        return null;
    }

    public Task getByToDoName(ToDo todo, String name) {
        // TODO
        return null;
    }

    public Task getByUserName(User user, String name) {
        // TODO
        return null;
    }

}
