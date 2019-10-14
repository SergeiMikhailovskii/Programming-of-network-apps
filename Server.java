package com.mikhailovskii.lab4;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;

public class Server {

    private Connection db;
    private Statement statement;
    private ServerSocket socket;

    public static void main(String[] args) {
        Server server = new Server();
        server.initializeServer();
        server.listenConnections();
    }

    private void initializeServer() {
        try {
            db = DriverManager.getConnection("jdbc:mysql://localhost:3306/new_schema" +
                            "?verifyServerCertificate=false" +
                            "&useSSL=false" +
                            "&requireSSL=false" +
                            "&useLegacyDatetimeCode=false" +
                            "&amp" +
                            "&serverTimezone=UTC",
                    "Sergei",
                    "12345");
            statement = db.createStatement();
            socket = new ServerSocket(1024);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private void listenConnections() {
        //noinspection InfiniteLoopStatement
        while (true) {
            try {
                Socket client = socket.accept();
                new Thread(() -> {
                    try {
                        OutputStream outputStream = client.getOutputStream();
                        InputStream inputStream = client.getInputStream();
                        String clientString;

                        boolean flag = true;

                        while (flag) {
                            byte[] clientMessage = new byte[1024];
                            int k = inputStream.read(clientMessage);
                            clientString = new String(clientMessage, 0, k);
                            clientString = clientString.trim();

                            if (clientString.equalsIgnoreCase("end")) {
                                flag = false;
                            }
                            if (clientString.equalsIgnoreCase("Select")) {
                                String res = getDataFromDb(statement);
                                outputStream.write(res.getBytes());
                            } else {
                                insertValueToDb(clientString);
                                outputStream.write("Added".getBytes());
                            }
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertValueToDb(String row) {
        String[] array = row.split(" ");
        String name = array[0];
        String manufacturer = array[1];
        int price = Integer.parseInt(array[2]);
        int weight = Integer.parseInt(array[3]);

        try {
            statement.executeUpdate("INSERT INTO Product (Name, Manufacturer, Price, Weight) VALUES ('" + name + "', '" + manufacturer + "'," + price + "," + weight + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getDataFromDb(Statement statement) {
        ResultSet resultSet = null;
        StringBuilder res = new StringBuilder();
        try {
            resultSet = statement.executeQuery("SELECT * FROM Product");
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("Name");
                String manufacturer = resultSet.getString("Manufacturer");
                int price = resultSet.getInt("Price");
                int weight = resultSet.getInt("Weight");

                res.append(id).append(" ").append(name).append(" ").append(manufacturer).append(" ").append(price).append(" ").append(weight).append('\n');
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println(res.toString());
        return res.toString();
    }

}
