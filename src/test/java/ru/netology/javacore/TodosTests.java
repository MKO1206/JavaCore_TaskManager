package ru.netology.javacore;

import org.junit.jupiter.api.*;

class TodosTests {

    private static Todos todos;
    private static Thread thread;

    @BeforeAll
    static void start() {
        thread = new Thread(TodosTests::setUp);
        thread.start();
    }

    @BeforeEach
    private void startEach() {
        todos = new Todos();
    }

    @AfterEach
    private void finishEach() {
        todos = null;
    }

    @AfterAll
    static void finish() {
        thread.interrupt();
    }

    private static void setUp() {
        TodoServer server = new TodoServer(8989, todos);
        server.start();
    }

    @Test
    void addTask() {
        todos.addTask("Купить кабель");
        String actual = todos.getListTasks().toString();
        String expected = "[Купить кабель]";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getAllTasks() {
        todos.addTask("Купить кабель");
        todos.addTask("Помыть авто");
        todos.addTask("Выкинуть мусор");
        String actual = todos.getAllTasks();
        String expected = "Выкинуть мусор Купить кабель Помыть авто";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void removeTask() {
        todos.addTask("Купить кабель");
        todos.addTask("Помыть авто");
        todos.removeTask("Помыть авто");
        String actual = todos.getListTasks().toString();
        String expected = "[Купить кабель]";
        Assertions.assertEquals(expected, actual);
    }
}