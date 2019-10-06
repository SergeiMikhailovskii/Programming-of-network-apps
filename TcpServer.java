package com.sitairis.tcp_lab4;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class TcpServer {

    private static int clientsCounter = 0;

    public static void main(String[] args) {
        ServerSocket socket;

        try {
            socket = new ServerSocket(1024);
            //noinspection InfiniteLoopStatement
            while (true) {
                Socket client = socket.accept();
                clientsCounter++;

                System.out.println("Client" + clientsCounter + " connected");

                new Thread(new ClientHandler(clientsCounter++, client)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler implements Runnable {

        private int clientId;
        private InputStream is;
        private OutputStream os;
        private String clientString;
        private Socket client;

        ClientHandler(int clientId, Socket client) {
            this.clientId = clientId;
            this.client = client;
        }

        @Override
        public void run() {
            try {
                os = client.getOutputStream();
                is = client.getInputStream();

                boolean flag = true;
                while (flag) {
                    int k;
                    byte[] clientMessage = new byte[100];
                    k = is.read(clientMessage);
                    clientString = new String(clientMessage, 0, k);
                    clientString = clientString.trim();
                    if (clientString.equalsIgnoreCase("disconnect")) {
                        System.out.println("Message \"disconnect\" received from client");
                        flag = false;
                    } else {
                        char[] arr = clientString.toCharArray();
                        Arrays.sort(arr);
                        clientString = new String(arr);
                        System.out.println("Result: " + clientString);
                        os.write(clientString.getBytes());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                    os.close();
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Client" + clientId + " disconnected");
            }

        }

    }

}
