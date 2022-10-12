package com.softserve.itacademy.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.UserService;


@Service
public class UserServiceImpl implements UserService {

    private List<User> users;

    public UserServiceImpl() {
        //TODO : check if we should move this from constructor?
        users = new ArrayList<>();
    }

    @Override
    /**
     * Adds user based on email as identifier.
     * @return
     * NULL -  in case our user cannot be added because User with same email already exists.
     * User -  object that was successfully added.
     * */
    public User addUser(User user) {
        for(User u : this.users){
            if(u.getEmail().equals(user.getEmail()))
                return null;
        }
        //If no user with same email was found
        users.add(user);
        return user;
    }


    @Override
    /**
     * @param User user might have null or empty string passed as values on the fields that it does not want to change.
     *             Null values  and ""  as one of the User element arguments will be ignored and old value will remain.
     * @return
     * User - newly updated user that was updated returned from list of all users.
     * Null - no such user is registered with this email, cannot update user info.
     * */
    public User updateUser(User user) {
        for (User x : getAll()) {
             if (x.getEmail().equals(user.getEmail())) {
                 x.setFirstName(user.getFirstName());
                 x.setLastName(user.getLastName());
                 x.setPassword(user.getPassword());
                 x.setMyTodos(user.getMyTodos());
                 return x;
            }
        }

      //Update using stream code
//        users.stream().filter(x -> x.getEmail().equals(user.getEmail()))
//                .findFirst()
//                .ifPresent( x-> {
//                    x.setFirstName(user.getFirstName());
//                    x.setLastName(user.getLastName());
//                    x.setPassword(user.getPassword());
//                    x.setMyTodos(user.getMyTodos());
//
//                });
        return null;
    }

    @Override
    /**
     * Removes user from the List based on email.
     * */
    public void deleteUser(User user) {
        this.users.removeIf(u -> u.getEmail().equals(user.getEmail()));
    }


    @Override
    /**
     * @return list of all users field.
     * */
    public List<User> getAll() {
        return this.users;
    }

}




/**
 * TODO:
 * Artem Lysiak — 10/09/2022
 *Яка логіка має бути в методі updateUser(User user)?
 Цей юзер в параметрі це є вже апдейтнутий юзер? Якщо так то по якому значенню я маю шукати юзера в списку
 якого я маю оновити цим юзером?
 Olha_Shutylieva — 10/09/2022
 Для юзера скоріше унікальне буде емейл, по ньому перевірили і якщо такий є апдейтнули поля

 Artem Lysiak — 10/09/2022
 А якшо емейл також хочуть апдейтнути?)
 І ше таке питання, в мейні нам треба створити юзера,
 провести з ним всі ті можливі операції, які ми реалізуєм і в кінці видати?
 Чи просто створити його там і все
 Якщо чесно, я завдання не зрозумів взагалі
 Ihor Kucher — 10/09/2022
 це мабуть у тестах треба робити
 Artem Lysiak — 10/09/2022
 Ну це більш сходе на правду

 Ihor Kucher — 10/09/2022
 ми ж маєм тести на всі операції написати по завданню
 Artem Lysiak — 10/09/2022
 Тобто просто реалізувати методи, без їх використання?
 Ihor Kucher — 10/09/2022
 ну да
 VladimirZinchenko — 10/09/2022
 як я зрозумів треба ревлізувати методи в класах і покрити їх тестами
 Olha_Shutylieva — 10/09/2022
 все вірно, вам треба реалізувати service layer який працює з сутностями
 і реалізувати крад операції. все те покрити тестами

 Vasyl_Bazhan — 10/09/2022
 створюєте інтерфейс
 public interface UserService {
 і там необхідні метод по типу
 List<User> getAll();

 і створюєте
 @Service
 public class UserServiceImpl implements UserService {

 private List<User> users;
 тут вже реалізовуєш методи і сюди в ліст записуєш юзерів

 Інші по анології

 Є питання стосовно методу UpdateUser у класі UserServiceImp.
 А саме яке поле в нас незмінне, тобто якщо ми перейдемо вже нового юзера,
 як саме ми маємо знайти юзера у листі, якого ми маємо обновити
 Julia Seti — Yesterday at 2:43 PM
 мабуть залежно від того, як ти записав у іквелс
 Vyacheslav Tinkovan — Yesterday at 2:44 PM
 Так ось у цьому і питання, чи є вимоги до поля/Ів, чи то ми самі вирішуємо?
 Julia Seti — Yesterday at 2:45 PM
 десь вчора Оля радила порівнювати за емейлом
 Vyacheslav Tinkovan — Yesterday at 2:45 PM
 Дякую! Так і зробимо
 Olha_Shutylieva — Yesterday at 2:46 PM
 Самі вирішуєте, але найоптимальніше брати емейл
 */
