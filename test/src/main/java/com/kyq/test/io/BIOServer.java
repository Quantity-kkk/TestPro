package com.kyq.test.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Description:
 * Copyright: ? 2017

 *
 * @version 1.0
 * @author: kyq1024
 * @timestamp: 2018-08-02 15:34
 */
public class BIOServer extends Thread{
    private ServerSocket serverSocket;

    public int getPort(){
        return serverSocket.getLocalPort();
    }

    @Override
    public void run(){
        try {
            serverSocket = new ServerSocket(0);
            ExecutorService executor = Executors.newFixedThreadPool(8);
            while (true){
                Socket socket = serverSocket.accept();
                RequestHandler requestHandler = new RequestHandler(socket);
//                requestHandler.start();
                executor.execute(requestHandler);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(serverSocket!=null){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String args[]) throws IOException {
        BIOServer server = new BIOServer();
        server.start();
        try(Socket client = new Socket(InetAddress.getLocalHost(),server.getPort())) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            bufferedReader.lines().forEach(s->System.out.println(s));
        }
    }

    class RequestHandler extends Thread{
        private Socket socket;
        RequestHandler(Socket socket){
            this.socket = socket;
        }

        public void run(){
            try(PrintWriter out = new PrintWriter(socket.getOutputStream());) {
                out.println("hello!");
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
