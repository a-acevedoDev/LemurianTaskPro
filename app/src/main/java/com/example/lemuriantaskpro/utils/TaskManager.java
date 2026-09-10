package com.example.lemuriantaskpro.utils;

import com.example.lemuriantaskpro.model.Task;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskManager {
    private static TaskManager instance;
    private List<Task> taskList;
    private int nextId = 1;

    private TaskManager() {
        taskList = new ArrayList<>();
        addSampleTasks();
    }

    public static synchronized TaskManager getInstance() {
        if (instance == null) {
            instance = new TaskManager();
        }
        return instance;
    }

    private void addSampleTasks() {
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(taskList);
    }

    public List<Task> getTasksByPriority(int minPriority) {
        List<Task> filtered = new ArrayList<>();
        for (Task task : taskList) {
            if (task.getPriority() >= minPriority) {
                filtered.add(task);
            }
        }
        return filtered;
    }

    public void addTask(Task task) {
        task.setId(nextId++);
        task.setCreate_at(new Date());
        taskList.add(task);
    }

    public void updateTask(Task updatedTask) {
        for (int i = 0; i < taskList.size(); i++) {
            if (taskList.get(i).getId() == updatedTask.getId()) {
                taskList.set(i, updatedTask);
                return;
            }
        }
        addTask(updatedTask);
    }

    public void deleteTask(int id) {
        taskList.removeIf(task -> task.getId() == id);
    }

    public void toggleCompleted(int id) {
        for (Task task : taskList) {
            if (task.getId() == id) {
                task.setCompleted(!task.getCompleted());
                if (task.getCompleted()) {
                    task.setComplete_at(new Date());
                } else {
                    task.setComplete_at(null);
                }
                return;
            }
        }
    }
}