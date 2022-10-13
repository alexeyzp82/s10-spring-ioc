package com.softserve.itacademy.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private List<ToDo> myTodos;

    // Constructor(s)
    public User(String firstName, String lastName, String email, String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email  = email;
        this.password  = password;
        this.myTodos = new ArrayList<>();
    }

    // getters, setters
    public String getFirstName() {
        return firstName;
    }

    /**
     * Will set firstName field to new value
     * if newly passed value is NOT NULL or EMPTY String.
     * */
    public void setFirstName(String firstName) {
        if(firstName!=null && !firstName.isEmpty())
            this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    /**
     * Will set lastName field to new value if newly passed value is not null or empty String.
     * */
    public void setLastName(String lastName) {
        if(lastName!=null && !lastName.isEmpty())
            this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    /**
     * Will set password field to new value if  :
     * newly passed value is not NULL or empty String.
     * */
    public void setPassword(String password) {
        if(password!=null && !password.isEmpty())
        this.password = password;
    }

    public List<ToDo> getMyTodos() {
        return myTodos;
    }

    /**
     * Assigns new toDos list to user in case it is not null.
     * */
    public void setMyTodos(List<ToDo> myTodos) {
        if(myTodos!=null)
            this.myTodos = myTodos;
    }

    // HashCode, equals, etc.
    @Override
    /**
     * Compares if two objects are completely equal comparing all fields.
     * */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
       // return  Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && email.equals(user.email) && Objects.equals(password, user.password) && Objects.equals(myTodos, user.myTodos);
       //Users equal if two email are equal
        return  email.equals(user.email);
    }

    /**
     * Determines hash code based on all elements od User object.
     * */
    @Override
    public int hashCode() {
        return Objects.hash(/*firstName, lastName,*/email/*, password, myTodos*/);
    }

}
