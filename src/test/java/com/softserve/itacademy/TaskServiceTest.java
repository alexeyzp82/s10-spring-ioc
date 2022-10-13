package com.softserve.itacademy;

import com.softserve.itacademy.model.Priority;
import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.service.TaskService;
import com.softserve.itacademy.service.ToDoService;
import org.junit.jupiter.api.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.UserService;

import java.lang.reflect.Method;
import java.util.*;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;


//@RunWith(JUnitPlatform.class)
public class TaskServiceTest {
    private static UserService userService;
    private static TaskService taskService;
    private static ToDoService toDoService;
    private static User user;
    private static ToDo todo;

    private static User createUser() {
        return new User("Firstname", "Lastname", "jhhjhjh@gmail.com", "asd123455");
    }

    private static ToDo createTodo(User user) {
        return new ToDo("some todo", user);
    }


    @BeforeEach
    public void setupBeforeClass() throws Exception {
        AnnotationConfigApplicationContext annotationConfigContext = new AnnotationConfigApplicationContext(Config.class);
        userService = annotationConfigContext.getBean(UserService.class);
        toDoService = annotationConfigContext.getBean(ToDoService.class);
        taskService = annotationConfigContext.getBean(TaskService.class);

        user = createUser();
        todo = createTodo(user);
        toDoService.addTodo(todo, user);
        annotationConfigContext.close();
    }

    @DisplayName("Check that User class implements all necessary methods.")
    @Test
    void hasTypeDeclaredMethod() {
        try {
            Class<?> clazz = Class.forName("com.softserve.itacademy.service.impl.TaskServiceImpl");
            Method[] methods = clazz.getMethods();
            int methodsCount = 0;
            for (Method method : methods) {
                if ("addTask".equals(method.getName()) ||
                        ("updateTask".equals(method.getName())) ||
                        ("deleteTask".equals(method.getName())) ||
                        ("getAll".equals(method.getName())) ||
                        ("getByToDo".equals(method.getName())) ||
                        ("getByToDoName".equals(method.getName())) ||
                        ("getByUserName".equals(method.getName()))
                ) {
                    methodsCount++;
                }
            }
            assertTrue(methodsCount == 7);
        } catch (ClassNotFoundException e) {
            fail("There is no UserServiceImpl class");
        }
    }

    @Test
    public void checkAddTask() {
        Task task = new Task("Task #1", Priority.HIGH);
        Task expected = new Task("Task #1", Priority.HIGH);
        Task actual = taskService.addTask(task, todo);
        System.out.println(taskService);
        assertEquals(expected, actual, "Task not added");
    }

    @Test
    public void checkAddNullTask() {
        Throwable exception = assertThrows(
                RuntimeException.class,
                () -> {
                    taskService.addTask(null, todo);
                }
        );
        assertEquals("Task is null!", exception.getMessage());
    }

    @Test
    public void checkUpdateTask() {
        userService.addUser(user);
        toDoService.addTodo(todo, user);
        taskService.addTask(new Task("Task#1", Priority.HIGH), todo);
        taskService.addTask(new Task("Task#2", Priority.LOW), todo);
        taskService.addTask(new Task("Task#3", Priority.MEDIUM), todo);
        toDoService.updateTodo(todo);
        Task actual = taskService.updateTask(new Task("Task#1", Priority.LOW));
        Task expected = new Task("Task#1", Priority.LOW);
        toDoService.updateTodo(todo);
        assertAll("checkUpdateUser",
                () -> assertEquals(expected.getName(), actual.getName(), "Task name is not updated"),
                () -> assertEquals(expected.getPriority(), actual.getPriority(), "Task priority is not updated"),
                () -> assertEquals(expected, todo.getTasks()
                        .stream()
                        .filter(t -> t.equals(expected))
                        .findFirst()
                        .orElse(null), "Task is not updated in the ToDo")
        );
    }

    @Test
    public void checkDeleteTask() {
        userService.addUser(user);
        toDoService.addTodo(todo, user);
        Task task1 = taskService.addTask( new Task("Task#1", Priority.HIGH), todo);
        Task task2 = taskService.addTask( new Task("Task#2", Priority.LOW), todo);
        Task task3 = taskService.addTask( new Task("Task#3", Priority.MEDIUM), todo);
        toDoService.updateTodo(todo);
        taskService.deleteTask(task2);
        toDoService.updateTodo(todo);

        Iterable<Task> actual = todo.getTasks();
        Iterable<Task> expected = new ArrayList<>(Arrays.asList(task1, task3));

        assertIterableEquals(expected, actual, "The task has not been deleted");
    }
    @Test
    public void checkGetAllTasks() {
        userService.addUser(user);
        toDoService.addTodo(todo, user);
        Task task1 = taskService.addTask(new Task("Task#1", Priority.HIGH), todo);
        Task task2 = taskService.addTask(new Task("Task#2", Priority.LOW), todo);
        Task task3 = taskService.addTask(new Task("Task#3", Priority.MEDIUM), todo);
        toDoService.updateTodo(todo);

        Iterable<Task> actual = taskService.getAll();
        Iterable<Task> expected = new ArrayList<>(Arrays.asList(task1, task2, task3));

        assertEquals(expected, actual, "Not a complete task list");
    }


}
