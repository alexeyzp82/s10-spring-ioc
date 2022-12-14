package com.softserve.itacademy;

import org.junit.jupiter.api.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.UserService;

import java.lang.reflect.Method;
import static org.junit.Assert.*;


//@RunWith(JUnitPlatform.class)
public class UserServiceTest {
    private static UserService userService;

    @BeforeAll
    public static void setupBeforeClass() throws Exception {
        AnnotationConfigApplicationContext annotationConfigContext = new AnnotationConfigApplicationContext(Config.class);
        userService = annotationConfigContext.getBean(UserService.class);
        annotationConfigContext.close();
    }

    @BeforeEach
    public void setUp(){
        userService.getAll().clear();
    }

    @DisplayName("Check that User class implements all necessary methods.")
	@Test
	void hasTypeDeclaredMethod() {
		try {
			Class<?> clazz = Class.forName("com.softserve.itacademy.service.impl.UserServiceImpl");
			Method[] methods = clazz.getMethods();
			int methodsCount = 0;
			for (Method method : methods) {
				if ("addUser".equals(method.getName()) ||
                    ("updateUser".equals(method.getName())) ||
                    ("deleteUser".equals(method.getName()) )||
                    ("getAll".equals(method.getName()))) {
					methodsCount++;
				}
			}
			assertTrue(methodsCount == 4);
		} catch (ClassNotFoundException e) {
			fail("There is no UserServiceImpl class");
		}
	}

    @DisplayName("Checks if valid user adds to the list of users.")
    @Test
    public void checkAddUser() {
        User user = new User("Ben","Barnes","ben@mail.com","pass");
        User expected = new User("Ben","Barnes","ben@mail.com","pass");
        User actual = userService.addUser(user);
        Assertions.assertEquals(expected, actual, "Check message");
    }

    @DisplayName("Adding user with already registered email.")
    @Test
    public void checkAddExistingUserWithException(){
        User user = new User("Ivan","Berd","ivan@mail.com","pass");
        User user2 = new User("IvanDUB","BerdDUB","ivan@mail.com","pass");
        userService.addUser(user);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.addUser(user2);
        });
    }

//    @DisplayName("Adding user with already registered email.")
//    @Test
//    public void checkAddUserWithDublicateEmail() {
//        User user = new User("Andrew","Collins","acollins@mail.com","pass",null);
//        User user2 = new User("AndrewDUB","CollinsDUB","acollins@mail.com","pass",null);
//        userService.addUser(user);
//        User actual = userService.addUser(user2);
//        // cannot add user with the same email therefor null is returned.
//        assertNull(actual);
//    }


    @DisplayName("Check update of existent user.")
    @Test
    public void checkUpdateUser() {
        User user1 = new User("Andrew","Collins","acollins@mail.com","pass");
        User user2 = new User("Anna","Holland","anna@mail.com","pass");
        User userUpd = new User("","Connar","acollins@mail.com","newPassword");
        User expected = new User("Andrew","Connar","acollins@mail.com","newPassword");
        userService.addUser(user1);
        userService.addUser(user2);
        User actual = userService.updateUser(userUpd);
        Assertions.assertEquals(expected, actual, "Check message");
    }

    @DisplayName("Check update of non-existent user.")
    @Test
    public  void checkUpdateNonExistentUser() {
        User user1 = new User("Olha","Clooney","olga23@mail.com","pass");
        User user2 = new User("Iryna","Valley","iryna4646@mail.com","pass");
        User userUpd = new User("Tonny","Connar","unknown@mail.com","newPassword");
        userService.addUser(user1);
        userService.addUser(user2);
        User actual = userService.updateUser(userUpd);
        Assertions.assertNull(actual);
    }


    @DisplayName("Delete  existing user.")
    @Test
    public  void checkDeleteValidUser() {
        User user1 = new User("Taras","Pool","taras@mail.com","pass");
        User user2 = new User("Mike","Musk","mike5674@mail.com","pass");
        userService.addUser(user1);
        userService.addUser(user2);

        userService.deleteUser(user1);
        boolean contains = userService.getAll().contains(user1);
        Assertions.assertFalse(contains);
    }

    @DisplayName("Delete non-existing user.")
    @Test
    public  void checkDeleteNonExistentUser() {
        User user1 = new User("Andrew","Collins","acollins@mail.com","pass");
        User user2 = new User("Anna","Holland","anna@mail.com","pass");
        userService.addUser(user1);
        userService.addUser(user2);
        int sizeBeforeDelete  = userService.getAll().size();
        User randomUser = new User("Harry","Johnson","john@mail.com","password");
        userService.deleteUser(randomUser);
        int sizeAfterDelete = userService.getAll().size();
        assertEquals(sizeBeforeDelete,sizeAfterDelete);
    }

    @DisplayName("Check get user by email.")
    @Test
    public  void checkGetUserByEmail(){
        String emailToExtract = "ola@email.com";
        User expected = new User("Ola","Burger","ola@email.com","pass");
        userService.addUser(expected);
        User actual = userService.getUserByEmail(emailToExtract);
        Assertions.assertEquals(expected,actual);
    }

    @DisplayName("Check get user by non-registered email.")
    @Test
    public  void checkGetUserByWrongEmail(){
        String emailToExtract = "random@email.com";
        User expected = new User("Mike","Vale","mike68754@email.com","pass");
        userService.addUser(expected);
        User actual = userService.getUserByEmail(emailToExtract);
        Assertions.assertNull(actual);
    }
}
