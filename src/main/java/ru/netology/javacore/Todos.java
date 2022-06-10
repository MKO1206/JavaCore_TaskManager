package ru.netology.javacore;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Todos {

    List<String> listTasks = new ArrayList<>();

    public void addTask(String task) {
        listTasks.add(task);
    }

    public void removeTask(String task) {
        listTasks.remove(task);
    }

    public String getAllTasks() {
        Stream<String> stream = listTasks.stream();
        List<String> resultList = stream
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
        StringJoiner sj = new StringJoiner(" ");
        for (String str : resultList) {
            sj.add(str);
        }
        return sj.toString();
    }

    public List<String> getListTasks() {
        return listTasks;
    }
}