package com.company;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by INDIGO-ПС on 10.12.2015.
 */
public class HelloClient {

    public static void main(String args[]) throws InterruptedException {
        DatagramSocket sock = null;
        int port = Integer.parseInt(args[1]);
        try {
            sock = new DatagramSocket();
            InetAddress host = InetAddress.getByName(args[0]);
            System.out.println("Send message : ");
            String s ;
                int threads = Integer.parseInt(args[3]);// потоки
                for (int i = 1; i <= threads; i ++) {
                    final DatagramSocket finalSock = sock;

                    Thread t = new Thread() {
                        public void run()
                        {
                            Thread ct = Thread.currentThread();
                            for (int i = 1; i <= Integer.parseInt(args[4])/* номер запроса в потоке*/; i++)
                            {
                                //отправляем
                                String s = "<"+args[2]+">_"+"<"+ct.getName()+">"+"_<"+i+">";
                                byte[] b = s.getBytes();
                                DatagramPacket dp = new DatagramPacket(b, b.length, host, port);
                                try {
                                    finalSock.send(dp);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                //дожидаемся ответа
                                    byte[] buffer = new byte[65536];
                                    DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
                                    try {
                                        System.out.println("1");
                                        finalSock.setSoTimeout(3000);
                                        finalSock.receive(reply);

                                        System.out.println("2");
                                    } catch (IOException  e) {
//                                        e.printStackTrace();
                                        continue;
                                    }
                                    byte[] data = reply.getData();
                                    s = new String(data, 0, reply.getLength());
                                    //выводим ответ
                                    System.out.println(reply.getAddress().getHostAddress() + " : " + reply.getPort() + " - " + s);
                                }
                        }
                    };
                    t.start();
                    Thread.yield();
                }
        } catch (IOException e) {
            System.err.println("IOException " + e);
        }

    }
}
