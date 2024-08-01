package com.kyq.test.elasticsearch.connection;

import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Description:
\\\". All rights reserved.

 *
 * @version 1.0
 * @author: kyq1024
 * @timestamp: 2018-04-10 15:14
 */
public class Test {

    public void finalize(){

    }
    public static void main(String args[]){

        Map storage = new HashMap(){{
            put(new Integer(0),"你好");

        }};
        System.out.println(storage.get(0));
//        createIndex();
//        getIndex();
//        mutilSearchByKeyWords();
        //ik
//        searchByKeyWords("");
//        createIndexOnly("knowledge");
//        CreateIndexResponse know = ConnectionFactory.getClient().admin().indices().prepareCreate("know").get();
//        System.out.println(know.isAcknowledged());
//        know.isAcknowledged();
//        searchByKeyWords("我 国");
//        System.out.println(ConnectionFactory.isConnected());
//        setFieldProperties();
//        getIndex();
//        prepareUpdate();
    }

    public static void createIndex(){
        //prepare data
        Map map = new HashMap();
        map.put("user","JackGu");
        map.put("postDate","1992-01-30");
        map.put("message","try out elasticsearch");
        map.put("content","我是一个中国人");
//        JSONObject jsonObject = new JSONObject(map);

        IndexResponse response = ConnectionFactory.getClient().prepareIndex("twitter", "tweet")
                .setSource(map)
                .get();
        String _index = response.getIndex();
        String _type = response.getType();
        String _id = response.getId();
        long _version = response.getVersion();
        RestStatus status = response.status();
    }

    public static void getIndex(){
        GetResponse response = ConnectionFactory.getClient().prepareGet("twitter", "tweet", "1").get();
        Map<String, Object> source = response.getSource();
        System.out.println(source);
    }

    public static void deleteIndex(){
        DeleteResponse response = ConnectionFactory.getClient().prepareDelete("twitter", "tweet", "1").get();
        System.out.println(response.getResult());
    }

    /*example:
     SearchRequestBuilder srb1 = client
     .prepareSearch().setQuery(QueryBuilders.queryStringQuery("elasticsearch")).setSize(1);
     SearchRequestBuilder srb2 = client
     .prepareSearch().setQuery(QueryBuilders.matchQuery("name", "kimchy")).setSize(1);

     MultiSearchResponse sr = client.prepareMultiSearch()
     .add(srb1)
     .add(srb2)
     .get();

     // You will get all individual responses from MultiSearchResponse#getResponses()
     long nbHits = 0;
     for (MultiSearchResponse.Item item : sr.getResponses()) {
     SearchResponse response = item.getResponse();
     nbHits += response.getHits().getTotalHits();
     }
     * */
    public static void mutilSearchByKeyWords(){
        String keyWords = "kimmy";// kimmy elasticsearch

        SearchRequestBuilder srb1 = ConnectionFactory.getClient()
                .prepareSearch().setQuery(QueryBuilders.queryStringQuery(keyWords)).setFrom(0).setSize(10);
        MultiSearchResponse sr = ConnectionFactory.getClient().prepareMultiSearch()
                .add(srb1)
                .get();
        // You will get all individual responses from MultiSearchResponse#getResponses()
        long nbHits = 0;
        for (MultiSearchResponse.Item item : sr.getResponses()) {
            SearchResponse response = item.getResponse();
            Iterator<SearchHit> iterator = response.getHits().iterator();
            while (iterator.hasNext()){
                SearchHit searchHit = iterator.next();
                System.out.println(searchHit.getSourceAsMap());
//                searchHit.getSourceAsMap();
            }
            nbHits += response.getHits().getTotalHits();
        }
        System.out.println(nbHits);
    }

    public static void searchByKeyWords(String keyWords){
        //index 不存在的时候会产生IndexNotFoundException.
//        String index = "twitter22";
//        String type = "tweet";
        QueryBuilder queryBuilder;
        if(keyWords==null||keyWords==""){
            queryBuilder = QueryBuilders.matchAllQuery();
            //子文档嵌套查询
//            JoinQueryBuilders.hasChildQuery("blog",JoinQueryBuilders.hasChildQuery("ind",QueryBuilders.matchQuery("",""),ScoreMode.Avg), ScoreMode.Avg);
            //matchQuery会处理分词，转为小写
            //termQuery不会处理分词，输入是什么，就是什么
            //must,必须，should，不计入score.
//            queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("audit_status","AGREED")).must(QueryBuilders.termQuery("uuid","4028318162e0a5700162e212414b001c"));
        }else {
            queryBuilder = QueryBuilders.queryStringQuery(keyWords);
        }

        //关于sort，要么使用field.keyword，要么设置fielddata为true
        SearchResponse sre = ConnectionFactory.getClient().prepareSearch()
//                .highlighter(new HighlightBuilder().field("user").field("content").preTags("<span style='color:red'>").postTags("</span>"))
//                .setTypes()//设置type，prepare的时候可以设置index
                .setQuery(queryBuilder)
//                .suggest(new SuggestBuilder().addSuggestion("content.suggest",new CompletionSuggestionBuilder("title.suggest"))) //搜索补全，例：搜索“大话”，可以补全为大话西游
                .setFrom(0).setSize(10)
//                .addSort("user.keyword", SortOrder.ASC)
//                .addSort(SortBuilders.fieldSort("user").order(SortOrder.ASC))
//                .addSort("postDate.keyword",SortOrder.ASC)
                .get();
        //命中的所有数据
        Long total = sre.getHits().getTotalHits();
        System.out.println("total:"+total);
        Iterator<SearchHit> it = sre.getHits().iterator();
        while (it.hasNext()){
            SearchHit next = it.next();
//            Map<String, HighlightField> highlightFields = next.getHighlightFields();

            System.out.println(next.getSourceAsMap());
//            System.out.println("======高亮=====");
//            System.out.println(highlightFields);
//            HighlightField content = highlightFields.get("content");
//            Text[] fragments = content.getFragments();
//            String nameTmp ="";
//            for(Text text:fragments){
//                nameTmp+=text;
//            }
//            System.out.println(nameTmp);
        }
    }

    public static void prepareUpdate(){
        Map map = new HashMap();
        map.put("user","Kimmy");
        map.put("postDate","2013-01-29");
        map.put("message","try out elasticsearch");
        UpdateResponse updateResponse = ConnectionFactory.getClient().prepareUpdate("twitter", "tweet", "1")
                .setDoc(map)
                .get();
        System.out.println(updateResponse.getResult().getLowercase());
    }

    public static boolean setFieldProperties(){
        Map map = new HashMap();
        //tweet,表示索引type_，
        String _type="tweet";
        String _id="twitter";
        //twitter，表示索引id_
        //创建mapping的时候，需要注意：ik分词字段需要在未插入数据前创建mapping，否则会产生different index_analyzer错误
        //需要排序text字段，需要设置fielddata为true
        map.put(_type,new HashMap(){{
            put("properties",new HashMap(){{
                put("user",new HashMap(){{
                    put("type","text");
                    put("fielddata",true);
                }});
                put("content",new HashMap(){{
                    put("type","text");
                    put("analyzer","ik_max_word");
                    put("search_analyzer", "ik_max_word");
                }});
            }});
        }});

        return ConnectionFactory.getClient().admin().indices().preparePutMapping("know").setType(_type).setSource(map).get().isAcknowledged();
    }

    public static boolean createIndexOnly(String index){
//        CreateIndexResponse know = ConnectionFactory.getClient().admin().indices().prepareCreate("know").get();
        CreateIndexResponse know = null;
        try {
            know = ConnectionFactory.getClient().admin().indices().prepareCreate(index).get();
            System.out.println(know.isAcknowledged());
            return know.isAcknowledged();
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
