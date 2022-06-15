package ru.netology.javacore;

public class TaskStorage {
    Type type;
    String task;

    public enum Type {
        ADD, REMOVE
    }

    public TaskStorage(Type type, String task) {
        this.type = type;
        this.task = task;
    }
}