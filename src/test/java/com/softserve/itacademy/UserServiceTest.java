package com.softserve.itacademy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.UserService;

@RunWith(JUnitPlatform.class)
public class UserServiceTest {
    private static UserService userService;

    @BeforeAll
    public static void setupBeforeClass() throws Exception {
        AnnotationConfigApplicationContext annotationConfigContext = new AnnotationConfigApplicationContext(Config.class);
        userService = annotationConfigContext.getBean(UserService.class);
        annotationConfigContext.close();
    }

    @Test
    public void checkAddUser() {
        User user = new User("Andrew","Collins","acollins@mail.com","pass",null);       // TODO, update code
        User expected = new User("Andrew","Collins","acollins@mail.com","pass",null);   // TODO, update code
        User actual = userService.addUser(user);
        Assertions.assertEquals(expected, actual, "Check message");
    }

    @Test
    public void checkAddUserWithDublicateEmail() {
        User user = new User("Andrew","Collins","acollins@mail.com","pass",null);       // TODO, update code
        User user2 = new User("AndrewDUB","CollinsDUB","acollins@mail.com","pass",null);       // TODO, update code
        User expected =null;  // cannot add user with the same email
        userService.addUser(user);
        User actual = userService.addUser(user2);
        Assertions.assertEquals(expected, actual, "Check message");
    }

    @Test
    public void checkUpdateUser() {
        User user1 = new User("Andrew","Collins","acollins@mail.com","pass",null);
        User user2 = new User("Anna","Holland","anna@mail.com","pass",null);
        userService.addUser(user1);
        userService.addUser(user2);
        User userUpd = new User("","Connar","acollins@mail.com","newPassword",null);
        User expected = new User("Andrew","Connar","acollins@mail.com","newPassword",null);   // TODO, update code
        User actual = userService.updateUser(userUpd);
        Assertions.assertEquals(expected, actual, "Check message");
    }
}
