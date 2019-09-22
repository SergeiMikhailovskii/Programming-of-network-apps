package com.mikhailovskii.lab2.ui;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TcpClientWindow extends JFrame {

    private JLabel portLb = new JLabel("Port:");
    private JLabel enterStringLB = new JLabel("Enter string:");
    private JLabel serverResponseLB = new JLabel("Server response:");

    private JTextField portTF = new JTextField(10);
    private JTextField inputStringTF = new JTextField(128);
    private JTextField responseTF = new JTextField(128);

    private JButton connectBtn = new JButton("Connect");
    private JButton sendTcpBtn = new JButton("Send TCP");

    private Socket socket = null;
    private InputStream is = null;
    private OutputStream os = null;

    public static void main(String[] args) {
        new TcpClientWindow().setVisible(true);
    }

    private TcpClientWindow() {
        super("TCP client window");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        portLb.setLocation(20, 15);
        portLb.setSize(30, 20);
        panel.add(portLb);

        portTF.setLocation(60, 15);
        portTF.setSize(40, 20);
        portTF.setText("1234");
        panel.add(portTF);

        connectBtn.setLocation(110, 15);
        connectBtn.setSize(90, 20);
        connectBtn.addActionListener(e -> {
            try {
                socket = new Socket("127.0.0.1", 1024);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        panel.add(connectBtn);

        enterStringLB.setLocation(20, 40);
        enterStringLB.setSize(80, 20);
        panel.add(enterStringLB);

        inputStringTF.setLocation(110, 40);
        inputStringTF.setSize(200, 20);
        panel.add(inputStringTF);

        sendTcpBtn.setLocation(320, 40);
        sendTcpBtn.setSize(100, 20);
        sendTcpBtn.addActionListener(e -> {
            if (socket == null) {
                return;
            }
            try {
                is = socket.getInputStream();
                os = socket.getOutputStream();
                System.out.println(inputStringTF.getText());
                os.write(inputStringTF.getText().getBytes());
                byte[] bytes = new byte[1024];
                //noinspection ResultOfMethodCallIgnored
                is.read(bytes);
                String str = new String(bytes, StandardCharsets.UTF_8);
                responseTF.setText(str);

            } catch (IOException e1) {
                e1.printStackTrace();
            }

        });
        panel.add(sendTcpBtn);


        serverResponseLB.setLocation(20, 70);
        serverResponseLB.setSize(100, 20);
        panel.add(serverResponseLB);

        responseTF.setLocation(130, 70);
        responseTF.setSize(200, 20);
        panel.add(responseTF);

        setContentPane(panel);
        setSize(500, 350);
    }

}
