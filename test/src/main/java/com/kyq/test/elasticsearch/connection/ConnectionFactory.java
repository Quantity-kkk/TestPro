package com.kyq.test.elasticsearch.connection;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;

/**
 * Description:
\\\". All rights reserved.

 *
 * @version 1.0
 * @author: kyq1024
 * @timestamp: 2018-04-10 14:15
 */
public class ConnectionFactory {
    private static TransportClient client;
    static {
        try {
            //可以一次连接多个Cluster
            client = new PreBuiltTransportClient(Settings.EMPTY)
//                    .addTransportAddress(new TransportAddress(InetAddress.getByName("host1"), 9300))
                    .addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.177.130"), 9300))
            ;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static TransportClient getClient(){
        return client;
    }
    // on shutdown
    public static void shutdown(){
        client.close();
    }

    public static boolean isConnected(){
        return client.connectedNodes().size()>0;
    }

}
