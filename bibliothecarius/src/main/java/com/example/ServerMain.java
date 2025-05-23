package com.example;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerMain {
    private static final int PORT = 12345;
    private static final ExecutorService clientPool = Executors.newFixedThreadPool(10);
    public static final List<ClientHandler> connectedClients = Collections.synchronizedList(new ArrayList<>());  // not just Arraylist because it must be safe for multiple threads to access
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT))
        {
            System.out.println("Server started on port " + PORT);
            while (true) 
            {
                Socket clientSocket = serverSocket.accept();
                ClientHandler handler = new ClientHandler(clientSocket);
                connectedClients.add(handler);
                clientPool.execute(handler);
            }
        } catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
}
