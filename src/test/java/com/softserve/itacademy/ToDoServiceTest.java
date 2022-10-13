package com.softserve.itacademy;

import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.service.ToDoService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.UserService;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

public class ToDoServiceTest {
    private static UserService userService;
    private static ToDoService toDoService;

    @BeforeAll
    public static void setupBeforeClass() {
        AnnotationConfigApplicationContext annotationConfigContext = new AnnotationConfigApplicationContext(Config.class);
        userService = annotationConfigContext.getBean(UserService.class);
        toDoService = annotationConfigContext.getBean(ToDoService.class);
        annotationConfigContext.close();
        userService.addUser(new User("f1", "l1", "e1", "p1"));
        userService.addUser(new User("f2", "l2", "e2", "p2"));
    }

    @DisplayName("Check to-do adding")
    @Test
    public void checkAddToDo() {
        ToDo todo = new ToDo("todo1", null);
        User user = userService.getAll().get(0);
        assumeTrue(toDoService.addTodo(todo, user).equals(todo));
        assumeTrue(toDoService.getAll().contains(todo));
        assumeTrue(toDoService.addTodo(todo, user) == null);
        assumeTrue(toDoService.addTodo(todo, null) == null);
    }

    @DisplayName("Check to-do updating")
    @Test
    public void checkUpdateToDo() {
        ToDo oldTodo = new ToDo("todo1", null);
        ToDo newTodo = new ToDo("todo1", null);
        newTodo.setCreatedAt(LocalDateTime.now().minusDays(1));
        User user = userService.getAll().get(0);
        toDoService.addTodo(oldTodo, user);
        assumeTrue(toDoService.updateTodo(newTodo).equals(newTodo));
        assumeTrue(toDoService.getAll().get(0).getCreatedAt() == newTodo.getCreatedAt());
    }

    @DisplayName("Check to-do deleting")
    @Test
    public void checkDeleteToDo() {
        ToDo oldTodo = new ToDo("todo1", null);
        User user = userService.getAll().get(0);
        toDoService.addTodo(oldTodo, user);
        assumeTrue(toDoService.getAll().size() == 1);
        toDoService.deleteTodo(oldTodo);
        assumeTrue(toDoService.getAll().size() == 0);
    }

    @DisplayName("Check getting all to-do")
    @Test
    public void checkGetAllToDo() {
        ToDo todo1 = new ToDo("todo1", null);
        ToDo todo2 = new ToDo("todo2", null);
        toDoService.addTodo(todo1, userService.getAll().get(0));
        toDoService.addTodo(todo2, userService.getAll().get(1));
        assertEquals(2, toDoService.getAll().size());
    }

    @DisplayName("Check getting all user's todo")
    @Test
    public void checkGetByUserToDo() {
        ToDo todo = new ToDo("todo1", null);
        User user = userService.getAll().get(0);
        toDoService.addTodo(todo, user);
        assertTrue(toDoService.getByUser(user).contains(todo));
    }


    @DisplayName("Check getting to-do by user and title")
    @Test
    public void checkGetByUserTitleToDo() {
        ToDo todo = new ToDo("todo1", null);
        User user1 = userService.getAll().get(0);
        User user2 = userService.getAll().get(1);
        toDoService.addTodo(todo, user1);
        assertEquals(todo, toDoService.getByUserTitle(user1, todo.getTitle()));
        assertNull(toDoService.getByUserTitle(user2, todo.getTitle()));
        assertNull(toDoService.getByUserTitle(user1, ""));
    }
}
