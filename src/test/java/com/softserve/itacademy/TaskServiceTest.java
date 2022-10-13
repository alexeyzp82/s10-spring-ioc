package com.softserve.itacademy;

import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.service.TaskService;
import com.softserve.itacademy.service.ToDoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.UserService;

import java.util.ArrayList;

@RunWith(JUnitPlatform.class)
public class TaskServiceTest {
    private static UserService userService;
    private static TaskService taskService;
    private static ToDoService toDoService;
    private static User user;
    private static ToDo todo;



    @BeforeAll
    public static void setupBeforeClass() throws Exception {
//        AnnotationConfigApplicationContext annotationConfigContext = new AnnotationConfigApplicationContext(Config.class);
//
//        userService = annotationConfigContext.getBean(UserService.class);
//        toDoService = annotationConfigContext.getBean(ToDoService.class);
//        taskService = annotationConfigContext.getBean(TaskService.class);
//
//        user = new User("Firstname","Lastname","asasas@gmail.com","asd123455", new ArrayList<>());
//        todo = new ToDo("todo", user);
//        toDoService.addTodo(todo, user);
//        annotationConfigContext.close();


    }

    @Test
    public void checkAddTask() {
        // TODO, code
    }

    // TODO, other tests
}
