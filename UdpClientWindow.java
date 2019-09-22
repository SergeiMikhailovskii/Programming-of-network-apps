package com.mikhailovskii.lab2.ui;

import javax.swing.*;
import java.io.IOException;
import java.net.*;

public class UdpClientWindow extends JFrame {

    private JLabel aLabel = new JLabel("A");
    private JLabel bLabel = new JLabel("B");
    private JLabel cLabel = new JLabel("C");
    private JLabel resultLabel = new JLabel("Result:");

    private JTextField aTf = new JTextField();
    private JTextField bTf = new JTextField();
    private JTextField cTf = new JTextField();
    private JTextField resultTf = new JTextField();

    private JButton calculateBtn = new JButton("Calculate");

    public static void main(String[] args) {
        new UdpClientWindow().setVisible(true);
    }

    private UdpClientWindow() {
        super("UDP client window");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        aLabel.setLocation(25, 15);
        aLabel.setSize(10, 20);
        panel.add(aLabel);

        aTf.setLocation(20, 40);
        aTf.setSize(20, 20);
        aTf.setText("2");
        panel.add(aTf);

        bLabel.setLocation(55, 15);
        bLabel.setSize(10, 20);
        panel.add(bLabel);

        bTf.setLocation(50, 40);
        bTf.setSize(20, 20);
        bTf.setText("8");
        panel.add(bTf);

        cLabel.setLocation(85, 15);
        cLabel.setSize(10, 20);
        panel.add(cLabel);

        cTf.setLocation(80, 40);
        cTf.setSize(20, 20);
        cTf.setText("11");
        panel.add(cTf);

        calculateBtn.setLocation(110, 40);
        calculateBtn.setSize(100, 20);
        calculateBtn.addActionListener(e -> onCalculateClick());
        panel.add(calculateBtn);

        resultLabel.setLocation(20, 70);
        resultLabel.setSize(40, 20);
        panel.add(resultLabel);

        resultTf.setLocation(70, 70);
        resultTf.setSize(120, 20);
        panel.add(resultTf);

        setContentPane(panel);
        setSize(300, 250);

    }

    private void onCalculateClick() {
        try {
            DatagramSocket socket = new DatagramSocket(12346);
            DatagramPacket datagramPacket;
            InetAddress local = InetAddress.getByName("localhost");
            byte[] bytes;

            bytes = aTf.getText().getBytes();
            datagramPacket = new DatagramPacket(bytes, bytes.length, local, 12345);
            socket.send(datagramPacket);

            bytes = bTf.getText().getBytes();
            datagramPacket = new DatagramPacket(bytes, bytes.length, local, 12345);
            socket.send(datagramPacket);

            bytes = cTf.getText().getBytes();
            datagramPacket = new DatagramPacket(bytes, bytes.length, local, 12345);
            socket.send(datagramPacket);

            bytes = new byte[100];

            datagramPacket = new DatagramPacket(bytes, 100);
            socket.receive(datagramPacket);
            String str = new String(datagramPacket.getData());
            resultTf.setText(str);
            socket.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
