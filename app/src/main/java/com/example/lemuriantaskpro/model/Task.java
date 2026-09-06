package com.example.lemuriantaskpro.model;

import java.time.LocalDateTime;

public class Task {
    private String name;
    private String description;
    private LocalDateTime create_at;
    private LocalDateTime complete_at;
    private Boolean completed;

    public Task() {
    }

    public Task(String name, String description, LocalDateTime create_at, LocalDateTime complete_at, Boolean completed) {
        this.name = name;
        this.description = description;
        this.create_at = create_at;
        this.complete_at = complete_at;
        this.completed = completed;
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

    public LocalDateTime getCreate_at() {
        return create_at;
    }

    public void setCreate_at(LocalDateTime create_at) {
        this.create_at = create_at;
    }

    public LocalDateTime getComplete_at() {
        return complete_at;
    }

    public void setComplete_at(LocalDateTime complete_at) {
        this.complete_at = complete_at;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}
