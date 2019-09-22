package com.mikhailovskii.lab2.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class TcpServer {

    private static int clientsCounter = 0;

    public static void main(String[] args) throws IOException {
        ServerSocket socket = null;
        InputStream is = null;
        OutputStream os = null;

        System.out.println("TCP server started");

        try {
            socket = new ServerSocket(1024);
            //noinspection InfiniteLoopStatement,LoopStatementThatDoesntLoop
            while (true){
                Socket client = socket.accept();
                clientsCounter++;
                System.out.println("Client"+clientsCounter+" connected");
                is = client.getInputStream();
                os = client.getOutputStream();
                //noinspection InfiniteLoopStatement
                while (true){
                    byte[] bytes = new byte[1024];
                    //noinspection ResultOfMethodCallIgnored
                    is.read(bytes);
                    String str = new String(bytes, StandardCharsets.UTF_8);
                    System.out.println(str);
                    char[] arr = str.toCharArray();
                    Arrays.sort(arr);
                    str = new String(arr);
                    bytes = str.getBytes();
                    os.write(bytes);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                is.close();
            }
            if (os != null) {
                os.close();
            }
            if (socket != null) {
                socket.close();
            }
        }
    }

}