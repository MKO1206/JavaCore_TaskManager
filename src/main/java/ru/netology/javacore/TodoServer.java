package ru.netology.javacore;

import com.google.gson.Gson;
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
                Socket clientSocket = serverSocket.accept();
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                System.out.printf("New connection accepted. Port: %d%n", clientSocket.getPort());
                // Process
                String incomingString = in.readLine();
                String outputString = requestProcessing(incomingString, todos);
                // Output to client
                out.printf("%s", outputString);
                clientSocket.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
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