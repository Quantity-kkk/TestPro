package com.kyq.test.io;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.util.concurrent.*;

/**
 * Description:
 * Copyright: © 2018

 *
 * @author kyq1024
 * @version 1.0
 * @timestamp 2018/11/22
 */
public class AIOServer implements Runnable{
    private int port = 8888;
    private int threadSize = 10;

    protected AsynchronousChannelGroup asynchronousChannelGroup;
    protected AsynchronousServerSocketChannel serverChannel;

    public AIOServer(int port, int threadSize){
        this.port = port;
        this.threadSize = threadSize;
        init();
    }

    private void init() {
        try {
            ThreadFactory serverThreadFactory = new ThreadFactoryBuilder().build();
            ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, 5000,
                    60L, TimeUnit.SECONDS,
                    new SynchronousQueue<>(), serverThreadFactory, new ThreadPoolExecutor.AbortPolicy());
            asynchronousChannelGroup = AsynchronousChannelGroup.withCachedThreadPool(threadPoolExecutor,10);
            serverChannel = AsynchronousServerSocketChannel.open(asynchronousChannelGroup);
            serverChannel.bind(new InetSocketAddress(port));
            System.out.println("listening on port: "+port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        if(serverChannel!=null){
            try {
                ///future方式：serverChannel.accept();
                serverChannel.accept(this, new CompletionHandler<AsynchronousSocketChannel, AIOServer>() {
                    final ByteBuffer echoBuffer = ByteBuffer.allocate(1024);
                    @Override
                    public void completed(AsynchronousSocketChannel result, AIOServer attachment) {
                        //监听新请求，递归调用
                        attachment.serverChannel.accept(attachment,this);

                        System.out.println("=======================");
                        System.out.println("server process begin...");

                        try {
                            System.out.println("client host: "+result.getRemoteAddress());
                            echoBuffer.clear();
                            result.read(echoBuffer).get();
                            echoBuffer.flip();
                            System.out.println("received : "+ Charset.defaultCharset().decode(echoBuffer));

                            int random = ThreadLocalRandom.current().nextInt(5);
                            printProcess(random);
                            System.out.println("Server deal request execute: "+ random +"s");

                            String msg = "server test msg-"+Math.random();
                            System.out.println("server send data:"+msg);
                            result.write(ByteBuffer.wrap(msg.getBytes()));
                            System.out.println("server process end...");
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failed(Throwable exc, AIOServer attachment) {
                        System.out.println("received failed..");
                        exc.printStackTrace();
                        attachment.serverChannel.accept(attachment,this);
                    }
                });
                ///主要是阻塞作用，因为AIO是异步的，所以此处不阻塞的话，主线程很快执行完毕，并会关闭通道
                System.in.read();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    private void printProcess(int random) throws InterruptedException {
        String dot = "";
        for(int i = 0; i < random; i++){
            Thread.sleep(1000);
            dot +=".";
            System.out.println(dot);
        }
    }

    public static void main(String[] args){
        new Thread(new AIOServer(8888,19)).start();
    }

}
