package com.company;
import javax.imageio.IIOException;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class server {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(5005);
            Socket clientSocket = serverSocket.accept();  
            final DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());
            final PrintStream outputStream = new PrintStream(clientSocket.getOutputStream());

            new Thread() {
                public void run() {
                    boolean running = true;
                    while (running) {
                        try {
                            String message = inputStream.readLine();
                            if (message.trim().toLowerCase().equals("exit"))
                                running = false;
                            else {
                                System.out.println(message);
                                outputStream.println("Received");
                            }
                        } catch (IOException ex) {
                            Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }.start();

        } catch (IOException e) {
            Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}