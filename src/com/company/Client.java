package com.company;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {

    public static void main(String[] args) {
        try {
            Socket mySocket = new Socket("127.0.0.1", 5005);
            final DataInputStream dis = new DataInputStream(mySocket.getInputStream());
            final PrintStream ps = new PrintStream(mySocket.getOutputStream());
            final Scanner scanner = new Scanner(System.in);

            new Thread() {
                public void run() {
                    while (true) {
                        String input = scanner.next();
                        ps.println(input);

                        if (input.trim().toLowerCase().equals("exit")) {
                            System.exit(0);
                        }
                    }
                }
            }.start();

            new Thread() {
                public void run() {
                    while (true) {
                        try {
                            String msg = dis.readLine();
                            System.out.println(msg);
                        } catch (IOException e) {
                            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, e);
                        }
                    }
                }
            }.start();

        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}