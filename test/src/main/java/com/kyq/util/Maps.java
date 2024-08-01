package com.kyq.util;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Description:
 *
 * @author kyq1024
 * @version 1.0
 * @timestamp 2018/12/3
 */
public final class Maps {
    private final static int EVEN = 2;
    public static Map of(Object ... keyValues){
        if(keyValues.length>0&&(keyValues.length%EVEN==0)){
            Map out = new HashMap(16);
            for(int i=0; i<keyValues.length; i+=EVEN){
                out.put(keyValues[i],keyValues[i+1]);
            }
            return out;
        }else {
            return new HashMap(16);
        }
    }

    public static class Builder{
        private Map map = new HashMap(16);

        private Builder put(Object key, Object value){
            map.put(key,value);
            return this;
        }

        public Builder set(Consumer<Map> consumer){
            consumer.accept(map);
            return this;
        }

        public Map build() {
            return map;
        }
    }

    public static int getMapInitialSize(Integer length){
        int i = length;
        i--;
        i |= i>>1;
        i |= i >> 2;
        i |= i >> 4;
        i |= i >> 8;
        i |= i >> 16;
        i++;

        return i>16?i:16;
    }
    static final int MAXIMUM_CAPACITY = 1 << 30;
    public static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

//    public TestMaps testR(){
//        return this::testM;
//    }
//
//    private void testM(String s){
//        System.out.println(this.getClass());
//        System.out.println("Maps.test..");
//    }
}
