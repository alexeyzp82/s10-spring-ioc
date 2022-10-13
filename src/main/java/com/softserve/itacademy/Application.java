package com.softserve.itacademy;

import com.softserve.itacademy.model.Priority;
import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.TaskService;
import com.softserve.itacademy.service.ToDoService;
import com.softserve.itacademy.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

public class Application {

    private static UserService userService;
    private static ToDoService toDoService;
    private static TaskService taskService;

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        userService = context.getBean(UserService.class);
        toDoService = context.getBean(ToDoService.class);
        taskService = context.getBean(TaskService.class);

        checkUserService();
        checkToDoService();
        checkTaskService();
    }

    private static void checkUserService() {
        System.out.println("User service");

        System.out.print("Create ");
        userService.addUser(new User("Frank", "Coles", "frco23@mail.com", "123456"));
        userService.addUser(new User("Michael", "Shaw", "mshaw154@mail.com", "123456"));
        System.out.println(Arrays.toString(userService.getAll().toArray()));

        System.out.print("Remove ");
        userService.deleteUser(userService.getUserByEmail("mshaw154@mail.com"));
        System.out.println(Arrays.toString(userService.getAll().toArray()));

        System.out.print("Update ");
        userService.updateUser(new User("Frank", "Coles", "frco23@mail.com", "new_password"));
        System.out.println(Arrays.toString(userService.getAll().toArray()));
        System.out.println();
    }

    private static void checkToDoService() {
        User user = userService.getAll().get(0);
        System.out.println("To-do service");

        System.out.print("Create ");
        toDoService.addTodo(new ToDo("Monday", user), user);
        toDoService.addTodo(new ToDo("Tuesday", user), user);
        System.out.println(Arrays.toString(toDoService.getAll().toArray()));

        System.out.print("Remove ");
        toDoService.deleteTodo(toDoService.getByUser(user).get(1));
        System.out.println(Arrays.toString(toDoService.getAll().toArray()));

        System.out.print("Update ");
        ToDo toDo = toDoService.getByUserTitle(user, "Monday");
        toDo.setCreatedAt(toDo.getCreatedAt().plusYears(1));
        toDoService.updateTodo(toDo);
        System.out.println(Arrays.toString(toDoService.getAll().toArray()));
        System.out.println();
    }

    private static void checkTaskService() {
        ToDo toDo = toDoService.getAll().get(0);
        System.out.println("Task service");

        System.out.print("Create ");
        taskService.addTask(new Task("Go shopping", Priority.MEDIUM), toDo);
        taskService.addTask(new Task("Call Brian", Priority.HIGH), toDo);
        System.out.println(Arrays.toString(taskService.getAll().toArray()));

        System.out.print("Remove ");
        taskService.deleteTask(taskService.getByToDoName(toDo, "Call Brian"));
        System.out.println(Arrays.toString(taskService.getAll().toArray()));

        System.out.print("Update ");
        Task task = taskService.getByToDo(toDo).get(0);
        task.setPriority(Priority.LOW);
        taskService.updateTask(task);
        System.out.println(Arrays.toString(taskService.getAll().toArray()));
    }
}
