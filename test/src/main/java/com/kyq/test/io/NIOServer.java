package com.kyq.test.io;


import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * Description:
 * Copyright: ? 2017

 *
 * @version 1.0
 * @author: kyq1024
 * @timestamp: 2018-08-02 15:52
 */
public class NIOServer extends Thread{
    @Override
    public void run(){
        try(Selector selector = Selector.open();
            ServerSocketChannel serverSocket = ServerSocketChannel.open()){
            serverSocket.bind(new InetSocketAddress(InetAddress.getLocalHost(),8888));
            serverSocket.configureBlocking(false);
            //ע�ᵽSelector����˵����ע��
            serverSocket.register(selector, SelectionKey.OP_ACCEPT);
            while (true){
                //�����ȴ�������Channel�����ǹؼ���֮һ
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectionKeys.iterator();
                while (it.hasNext()){
                    SelectionKey key = it.next();
                    // ����ϵͳ��һ��������о���״̬���
                    sayHelloWorld((ServerSocketChannel)key.channel());
                    it.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void sayHelloWorld(ServerSocketChannel server) throws IOException {
        try(SocketChannel client = server.accept()){
            client.write(Charset.defaultCharset().encode("Hello World!"));
        }
    }

    public static void main(String args[]) throws IOException {
        NIOServer server = new NIOServer();
        server.start();
//        try(SocketChannel client = SocketChannel.open()) {
//            client.configureBlocking(false);
//            client.connect(new InetSocketAddress(InetAddress.getLocalHost(),8888));
//
//            while (!client.finishConnect()) {
////                System.out.println("�ȴ����������ӽ���....");
//                try {
//                    Thread.sleep(10);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//
//
//            ByteBuffer buf = ByteBuffer.allocateDirect(1024);
//            buf.clear();
//            int numBytesRead;
//            while ((numBytesRead=client.read(buf))!=-1){
//                if (numBytesRead == 0) {
//                    // ���û�����ݣ�����΢�ȴ�һ��
//                    try {
//                        Thread.sleep(1);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    continue;
//                }
//
//                buf.flip();
//                while (buf.remaining() > 0) {
//                    System.out.print((char) buf.get());
//                }
//            }
//            // ��λ�����
//            buf.clear();
//        }
    }
}
