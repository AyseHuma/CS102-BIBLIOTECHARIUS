package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientConnection {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public boolean connect(String host, int port) {
        try 
        {
            socket = new Socket(host, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            return true;
        } catch (IOException e) 
        {
            e.printStackTrace();
            return false;
        }
    }
    public void sendMessage(String message) 
    {
        out.println(message);
    }

    public String receiveMessage() throws IOException 
    {
        return in.readLine();
    }

    public void close() 
    {
        try { socket.close(); } catch (IOException e) {}
    }
}
