package com.kyq.test.file;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 * Copyright: © 2019

 *
 * @author kyq
 * @version 1.0
 * @Date 2019/12/5 10:15
 */
public class FilewalkTest {

    @Test
    public void testVisit() throws IOException {
        long start = System.currentTimeMillis();
        for (FileStore store: FileSystems.getDefault().getFileStores()) {
            long total = store.getTotalSpace() / 1024;
            long used = (store.getTotalSpace() - store.getUnallocatedSpace()) / 1024;
            long avail = store.getUsableSpace() / 1024;
            System.out.format("%-20s %12d %12d %12d%n", store, total, used, avail);
        }
        long end = System.currentTimeMillis();
        System.out.println("Time used: "+(end-start));

    }

    public void test(){
        ThreadPoolExecutor es = new ThreadPoolExecutor(50,50,0L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setDaemon(true);
                return t;
            }
        });
//        es.submit();
    }
}
