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
     * NULL - no such user is registered with this email, cannot update user info.
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