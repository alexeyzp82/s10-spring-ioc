package com.softserve.itacademy.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class ToDo {

    private String title;

    private LocalDateTime createdAt;

    private User owner;

    private List<Task> tasks;

    public ToDo(String title, LocalDateTime createdAt, User owner, List<Task> tasks) {
        this.title = title;
        this.createdAt = createdAt;
        this.owner = owner;
        this.tasks = tasks;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ToDo toDo = (ToDo) o;
        return Objects.equals(title, toDo.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}
