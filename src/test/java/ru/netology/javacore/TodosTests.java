package ru.netology.javacore;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;

public class TodosTests {

    private Todos todos;

    @BeforeAll
    public static void startTesting() {
        System.out.println("Запуск тестов");
    }

    @BeforeEach
    public void setUp() {
        System.out.println("Действия перед каждым тестом");
        String task1 = "Отдых";
        String task2 = "Пробежка";
        String task3 = "Работа";
        Todos.tasks.add(task1);
        Todos.tasks.add(task2);
        Todos.tasks.add(task3);
        todos = new Todos();
    }

    @AfterEach
    public void refresh() {
       Todos.tasks.clear();
    }

    @Test
    public void whenRemoveTask(){
        todos.removeTask("Работа");
        ArrayList<String> taskList = todos.getListTask();
        Assertions.assertEquals(taskList.size(), 2);
    }

    @Test
    public void whenAddNewTask(){
        Assertions.assertEquals(todos.getListTask().size(), 3);
        todos.addTask("Учеба");
        Assertions.assertEquals(todos.getListTask().size(), 4);
    }

    @Test
    public void whenGetAllTasks(){
        String testTaskLIst = "[Отдых, Пробежка, Работа]";
        assertTrue(todos.getAllTasks().equals(testTaskLIst));
    }
}
