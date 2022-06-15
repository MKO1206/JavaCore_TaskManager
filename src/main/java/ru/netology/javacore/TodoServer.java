package ru.netology.javacore;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TodoServer {

    Todos todos;
    private final int port;

    public TodoServer(int port, Todos todos) {
        this.port = port;
        this.todos = todos;
    }

    public void start() {
        System.out.println("Starting server at " + port + "...");
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                try (
                        Socket clientSocket = serverSocket.accept();
                        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                ) {
                    System.out.printf("New connection accepted. Port: %d%n", clientSocket.getPort());
                    while (true) {
                        String line = in.readLine();
                        GsonBuilder builder = new GsonBuilder();
                        Gson gson = builder.create();
                        TaskStorage task = gson.fromJson(line, TaskStorage.class);
                        if (task.type == TaskStorage.Type.ADD) {
                            todos.addTask(task.task);
                            out.println(todos.getAllTasks());
                        } else if (task.type == TaskStorage.Type.REMOVE) {
                            todos.removeTask(task.task);
                            out.println(todos.getAllTasks());
                        }
                    }
                } catch (Exception exception) {
                    System.out.println(exception);
                }
            }
        } catch (Exception exception) {
            System.out.println("Соединение не установлено");
            exception.printStackTrace();
        }
    }

    private String requestProcessing(String str, Todos todos) {
        JsonObject obj = new Gson().fromJson(str, JsonObject.class);
        String type = obj.get("type").getAsString();
        String task = obj.get("task").getAsString();
        if (type.equals("ADD")) {
            todos.addTask(task);
            System.out.println("Task added");
        }
        if (type.equals("REMOVE")) {
            todos.removeTask(task);
            System.out.println("Task deleted");
        }
        todos.getAllTasks();
        return todos.getAllTasks();
    }
}