package com.example.lemuriantaskpro.model;

import java.util.Date;

public class Task {
    private int id;
    private String name;
    private String description;
    private Date create_at;
    private Date complete_at;
    private Boolean completed;
    private int priority;

    public Task() {
        this.create_at = new Date();
        this.completed = false;
        this.priority = 3;
    }

    public Task(String name, String description, int priority) {
        this.name = name;
        this.description = description;
        this.create_at = new Date();
        this.completed = false;
        this.priority = priority;
    }

    public Task(int id, String name, String description, Date create_at, Date complete_at, Boolean completed, int priority) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.create_at = create_at;
        this.complete_at = complete_at;
        this.completed = completed;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreate_at() {
        return create_at;
    }

    public void setCreate_at(Date create_at) {
        this.create_at = create_at;
    }

    public Date getComplete_at() {
        return complete_at;
    }

    public void setComplete_at(Date complete_at) {
        this.complete_at = complete_at;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
