package com.softserve.itacademy.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.ToDoService;
import com.softserve.itacademy.service.UserService;

@Service
public class ToDoServiceImpl implements ToDoService {

    private UserService userService;

    @Autowired
    public ToDoServiceImpl(UserService userService) {
        this.userService = userService;
    }

    public ToDo addTodo(ToDo todo, User user) {
        if (userService.getAll()
            .stream()
            .flatMap(x -> x.getMyTodos().stream())
            .anyMatch(todo::equals))
            return null;

        User oldUser = userService.getAll()
            .stream()
            .filter(user::equals)
            .findFirst()
            .orElse(null);

        if (oldUser == null)
            return null;

        oldUser.getMyTodos().add(todo);
        return todo;
    }

    public ToDo updateTodo(ToDo todo) {
        ToDo oldToDo = userService.getAll()
            .stream()
            .flatMap(x -> x.getMyTodos().stream())
            .filter(todo::equals)
            .findFirst()
            .orElse(null);

        if (oldToDo == null)
            return null;

        oldToDo.setCreatedAt(todo.getCreatedAt());
        oldToDo.setOwner(todo.getOwner());
        oldToDo.setTasks(todo.getTasks());
        return oldToDo;
    }

    public void deleteTodo(ToDo todo) {
        for (User user : userService.getAll())
            if (user.getMyTodos().removeIf(x -> x.equals(todo)))
                break;
    }

    public List<ToDo> getAll() {
        return userService.getAll()
            .stream()
            .flatMap(x -> x.getMyTodos().stream())
            .collect(Collectors.toList());
    }

    public List<ToDo> getByUser(User user) {
        User oldUser = userService.getAll()
            .stream()
            .filter(user::equals)
            .findFirst()
            .orElse(null);

        return oldUser == null ? null : oldUser.getMyTodos();
    }

    public ToDo getByUserTitle(User user, String title) {
        List<ToDo> toDoList = getByUser(user);
        return toDoList == null ? null : toDoList.stream()
            .filter(x -> x.getTitle().equals(title))
            .findFirst()
            .orElse(null);
    }

}
