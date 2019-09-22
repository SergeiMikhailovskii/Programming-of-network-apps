package com.mikhailovskii.lab2.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UpdServer {

    private double sum1 = 0;
    private double sum2 = 0;
    private byte[] bytes = new byte[100];

    private DatagramSocket socket;
    private DatagramPacket datagramPacket = new DatagramPacket(bytes, 100);

    public static void main(String[] args) {
        new UpdServer();
    }

    private UpdServer() {
        try {
            socket = new DatagramSocket(12345);
            listen();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    private void listen() {
        int a, b, c;
        try {
            socket.receive(datagramPacket);
            String str = new String(datagramPacket.getData());
            str = str.trim();
            a = Integer.parseInt(str);

            socket.receive(datagramPacket);
            str = new String(datagramPacket.getData());
            str = str.trim();
            b = Integer.parseInt(str);

            socket.receive(datagramPacket);
            str = new String(datagramPacket.getData());
            str = str.trim();
            c = Integer.parseInt(str);

            Thread firstSumThread = new Thread(() -> {
                for (int i = a; i <= b; i++) {
                    sum1 += (i - 1) * (i - 1);
                }
            });

            Thread secondSumThread = new Thread(() -> {
                for (int i = b; i <= c; i++) {
                    sum2 += (double) 2 * i / (7 * i + 1);
                }
            });

            firstSumThread.start();
            secondSumThread.start();
            firstSumThread.join();
            secondSumThread.join();
            sendBack();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void sendBack() {
        String str = String.valueOf(sum1 - sum2);
        System.out.println(str);
        byte[] send = str.getBytes();
        try {
            datagramPacket = new DatagramPacket(send, send.length, InetAddress.getByName("localhost"), 12346);
            socket.send(datagramPacket);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
