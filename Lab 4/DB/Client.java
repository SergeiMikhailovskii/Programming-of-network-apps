package com.mikhailovskii.lab4;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Client {
    private Socket socket;
    private InputStream is;
    private OutputStream os;

    public static void main(String[] args) {
        Client client = new Client();
        client.connectToServer();

        int choice;

        while (true) {
            choice = ClientMenu.showClientMenu();
            String res;
            switch (choice) {
                case 1:
                    res = client.enterRecord();
                    client.sendDataToServer(res);
                    client.getDataFromServer();
                    break;
                case 2:
                    res = "Select";
                    client.sendDataToServer(res);
                    System.out.println();
                    System.out.println("Database:");
                    client.getDataFromServer();
                    System.out.println();
                    break;
                case 0:
                    break;
                default:
                    break;
            }
        }

    }

    private void connectToServer() {
        try {
            socket = new Socket("127.0.0.1", 1024);
            System.out.println("Connected!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String enterRecord() {
        String res = "";
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Enter name: ");
            String name = bufferedReader.readLine();
            System.out.println("Enter manufacturer: ");
            String manufacturer = bufferedReader.readLine();
            System.out.println("Enter price: ");
            int price = Integer.parseInt(bufferedReader.readLine());
            System.out.println("Enter weight: ");
            int weight = Integer.parseInt(bufferedReader.readLine());
            res = name + " " + manufacturer + " " + price + " " + weight;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    private void sendDataToServer(String res) {
        try {
            os = socket.getOutputStream();
            os.write(res.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getDataFromServer() {
        byte[] bytes = new byte[1024];
        try {
            is = socket.getInputStream();
            is.read(bytes);
            String str = new String(bytes, StandardCharsets.UTF_8);
            str = str.trim();
            System.out.println(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
