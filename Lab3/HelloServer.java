package com.company;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Created by INDIGO-ÏÑ on 10.12.2015.
 */
public class HelloServer {
    public static void main(String args[]) throws InterruptedException {
        DatagramSocket sock = null;
        try
        {
            sock = new DatagramSocket(Integer.parseInt(args[0]));// ןמנע

            byte[] buffer = new byte[65536];
            DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);
            System.out.println("Server socket created. Waiting for incoming data...");
            int threads = Integer.parseInt(args[1]);

                final DatagramSocket finalSock = sock;
                for (int i =0; i < threads; i ++) {
                    Thread t = new Thread(new Runnable() {
                        public void run() {
                            while (true) {
                                Thread ct = Thread.currentThread();
                                try {
                                    finalSock.receive(incoming);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                byte[] data = incoming.getData();
                                String s = new String(data, 0, incoming.getLength());
                                System.out.println(incoming.getAddress().getHostAddress() + " : " + incoming.getPort() + " - " + s);
                                s = "Hello, " + s +ct.getName();
                                DatagramPacket dp = new DatagramPacket(s.getBytes(), s.getBytes().length, incoming.getAddress(), incoming.getPort());
                                try {
                                    finalSock.send(dp);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                    t.start();
                }
        }
        catch(IOException e)
        {
            System.err.println("IOException " + e);
        }
    }
}