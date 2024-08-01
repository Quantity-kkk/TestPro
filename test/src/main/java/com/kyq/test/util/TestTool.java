package com.kyq.test.util;

import com.kyq.tools.CollectionUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestTool {
    public static void main(String[] args) {
        List<Map<String, Object>> list = new ArrayList<>();

        list.add(new HashMap(){{
            put("id","001");
            put("name", "李鑫");
        }});

        list.add(new HashMap(){{
            put("id","002");
            put("name", "刘翔");
        }});

        Map list2Map = CollectionUtil.toMap(list, key -> key.get("id"));

        System.out.println(list2Map);
    }
}
