package com.kyq.test.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.Executors;

/**
 * Description:
 * Copyright: © 2018

 *
 * @author kyq1024
 * @version 1.0
 * @timestamp 2018/11/22
 */
public class AIOClient implements Runnable{
    private AsynchronousChannelGroup group;
    private String host;
    private int port;

    public AIOClient(String host, int port){
        this.host = host;
        this.port = port;
        initGroup();
    }

    private void initGroup() {
        if(group==null){
            try {
                //使用固定线程池实例化组
                group = AsynchronousChannelGroup.withCachedThreadPool(Executors.newFixedThreadPool(5),5);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void send(){
        try{
            final AsynchronousSocketChannel client = AsynchronousSocketChannel.open(group);

            client.connect(new InetSocketAddress(host, port), null, new CompletionHandler<Void, Object>() {
                @Override
                public void completed(Void result, Object attachment) {
                    String msg = "client test msg = "+Math.random();
                    client.write(ByteBuffer.wrap(msg.getBytes()));
                    System.out.println(Thread.currentThread().getName()+" client send data:" + msg);

                    final ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    client.read(byteBuffer, this, new CompletionHandler<Integer, CompletionHandler<Void, Object>>() {
                        @Override
                        public void completed(Integer result, CompletionHandler<Void, Object> attachment) {
                            System.out.println(Thread.currentThread().getName()+" client read data : "+new String(byteBuffer.array()));

                            try {
                                byteBuffer.clear();
                                if (client!=null){
                                    client.close();;
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void failed(Throwable exc, CompletionHandler<Void, Object> attachment) {
                            System.out.println("read failed..");
                        }
                    });

                }

                @Override
                public void failed(Throwable exc, Object attachment) {
                    System.out.println("client send failed..");
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        for (int i = 0; i < 2; i++){
            send();
            try{
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String args[]){
        try {
            new Thread(new AIOClient("localhost",8888)).start();
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
