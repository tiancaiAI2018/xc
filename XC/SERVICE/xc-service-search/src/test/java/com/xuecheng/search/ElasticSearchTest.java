package com.xuecheng.search;

import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.get.GetResult;
import org.elasticsearch.index.query.*;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ElasticSearchTest {
    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Autowired
    private RestClient restClient;

    /**
     * 创建索引
     *
     * @throws IOException
     */
    @Test
    public void CreateIndexTest() throws IOException {
        //构建创建索引的请求对象
        CreateIndexRequest indexRequest = new CreateIndexRequest("xc_course1");
        //设置索引分片number_of_shards和副本number_of_replicas
        indexRequest.settings(Settings.builder().
                put("number_of_shards", 2).
                put("number_of_replicas", 1));
        //设置映射
        indexRequest.mapping("doc", "{ \"properties\": { \"name\": { \"type\": \"text\", \"analyzer\":\"ik_max_word\", \"search_analyzer\":\"ik_smart\" },\"description\": { \"type\": \"text\", \"analyzer\":\"ik_max_word\", \"search_analyzer\":\"ik_smart\" },\"studymodel\": { \"type\": \"keyword\" },\"price\": { \"type\": \"float\" },\"timestamp\": { \"type\": \"date\", \"format\": \"yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis\" } } }", XContentType.JSON);
        //创建索引操作客户端
        IndicesClient indices = restHighLevelClient.indices();
        //创建索引，拿到结果
        CreateIndexResponse response = indices.create(indexRequest);
        //是否操作成功
        boolean result = response.isShardsAcknowledged();
        System.out.println(result);
    }

    /**
     * 添加文档
     */
    @Test
    public void addDoc() throws IOException {
        //创建文档索引对象
        IndexRequest indexRequest = new IndexRequest("xc_course1", "doc");
        //文档数据
        Map<String, Object> map = new HashMap<>();
        map.put("name", "java实战开发");
        map.put("description", "java实战开发： 1.微服务架构入门 2.spring cloud java实战开发 3.实战Spring java实战开发 4.注册中心eureka。");
        map.put("studymodel", "201001");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        map.put("timestamp", dateFormat.format(new Date()));
        map.put("price", 11.8f);
        indexRequest.source(map);
        //添加文档，返回结果
        IndexResponse response = restHighLevelClient.index(indexRequest);
        int status = response.status().getStatus();
        DocWriteResponse.Result result = response.getResult();
        System.out.println(result);
        System.out.println(status);
    }
    @Test
    public void selectDoc() throws IOException {
        GetRequest getRequest = new GetRequest("xc_course1", "doc", "9WwbH20BFgCapv-y38wD");
        GetResponse response = restHighLevelClient.get(getRequest);
        Map<String, Object> sourceAsMap = response.getSourceAsMap();
        System.out.println(sourceAsMap.toString());
    }
    @Test
    public void updateDoc() throws IOException {
        UpdateRequest updateRequest = new UpdateRequest("xc_course1", "doc", "9mwvH20BFgCapv-y-cwz");
        HashMap<String, Object> map = new HashMap<>();
        map.put("name","bootstrap开发");
        updateRequest.doc(map);
        UpdateResponse response = restHighLevelClient.update(updateRequest);
        GetResult getResult = response.getGetResult();
        System.out.println(getResult);
    }
    @Test
    public void delDoc() throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest("xc_course1", "doc", "9mwvH20BFgCapv-y-cwz");
        DeleteResponse response = restHighLevelClient.delete(deleteRequest);
        RestStatus status = response.status();
        System.out.println(status);
    }

    /**
     * 简单搜索
     */
    @Test
    public void searchDoc() throws IOException {
        //设置搜索的索引库
        SearchRequest request = new SearchRequest("xc_course1");
        //设置搜索的type
        request.types("doc");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //搜索全部的文档
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        //设置过滤字段（第一个参数设置包含什么字段，第二个设置不包含什么字段）
        searchSourceBuilder.fetchSource(new String[]{"name","price","timestamp"},new String[]{});
        request.source(searchSourceBuilder);
        SearchResponse search = restHighLevelClient.search(request);
        //获得命中的对象
        SearchHits hits = search.getHits();
        //获得命中条数
        long totalHits = hits.getTotalHits();
        System.out.println(totalHits);
        //获得命中靠前的n条数据
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            String index = hit.getIndex();
            String source = hit.getSourceAsString();
            System.out.println(source);
        }
    }

    /**
     * 分页查找
     */
    @Test
    public void pageSearchDoc() throws IOException {
        //设置搜索的索引库
        SearchRequest request = new SearchRequest("xc_course1");
        //设置搜索的type
        request.types("doc");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //搜索全部的文档
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        //设置过滤字段（第一个参数设置包含什么字段，第二个设置不包含什么字段）
        searchSourceBuilder.fetchSource(new String[]{"name","price","timestamp"},new String[]{});
        //从哪里开始（默认从0开始）
        searchSourceBuilder.from(0);
        //显示多少条
        searchSourceBuilder.size(1);
        request.source(searchSourceBuilder);
        SearchResponse search = restHighLevelClient.search(request);
        //获得命中的对象
        SearchHits hits = search.getHits();
        //获得命中条数
        long totalHits = hits.getTotalHits();
        System.out.println(totalHits);
        //获得命中靠前的n条数据
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            String index = hit.getIndex();
            String source = hit.getSourceAsString();
            System.out.println(source);
        }
    }

    /**
     * 精确搜索（搜索不会分词）
     */
    @Test
    public void termSearchDoc() throws IOException {
        SearchRequest searchRequest = new SearchRequest("xc_course1");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //设置精确搜索(termQuery 没有s)
        searchSourceBuilder.query(QueryBuilders.termQuery("description","java"));
        searchSourceBuilder.fetchSource(new String[]{"name","price","timestamp",""},new String[]{});
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = restHighLevelClient.search(searchRequest);
        SearchHits responseHits = response.getHits();
        long totalHits = responseHits.getTotalHits();
        System.out.println(totalHits);
        SearchHit[] hits = responseHits.getHits();
        for (SearchHit hit : hits) {
            String source = hit.getSourceAsString();
            System.out.println(source);
        }
    }
    @Test
    public void idsSearchDoc() throws IOException {
        //设置搜索的索引库
        SearchRequest request = new SearchRequest("xc_course1");
        //设置搜索的type
        request.types("doc");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //编写要查找的id
        List<String> ids = Arrays.asList("9WwbH20BFgCapv-y38wD","92yeH20BFgCapv-yYsyE");
        //根据id查找（termsQuery 存在s）可以命中多个（即可以使第一个id 也可以是第二个id）
        searchSourceBuilder.query(QueryBuilders.termsQuery("_id",ids));
        //设置过滤字段（第一个参数设置包含什么字段，第二个设置不包含什么字段）
        searchSourceBuilder.fetchSource(new String[]{"name","price","timestamp"},new String[]{});
        request.source(searchSourceBuilder);
        SearchResponse search = restHighLevelClient.search(request);
        //获得命中的对象
        SearchHits hits = search.getHits();
        //获得命中条数
        long totalHits = hits.getTotalHits();
        System.out.println(totalHits);
        //获得命中靠前的n条数据
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            String index = hit.getIndex();
            String source = hit.getSourceAsString();
            System.out.println(source);
        }
    }

    /**
     * 分词匹配（match会将搜索词进行分词）
     */
    @Test
    public void matchSearchDoc() throws IOException {
        //设置搜索的索引库
        SearchRequest request = new SearchRequest("xc_course1");
        //设置搜索的type
        request.types("doc");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //搜索词分词匹配(Operator.OR 至存在一个分词匹配即可，Operator.AND 所有分词要全部匹配上)
        //searchSourceBuilder.query(QueryBuilders.matchQuery("name","实战开发").operator(Operator.OR));
        //搜索词分词匹配(minimumShoudMatch 表示按分词的百分比取值，如 3个分词 80% 表示 满足 3*0.8=2.4 向下取整 2 表示有两个分配匹配上即可)
        searchSourceBuilder.query(QueryBuilders.matchQuery("name","实战开发").minimumShouldMatch("80%"));
        //设置过滤字段（第一个参数设置包含什么字段，第二个设置不包含什么字段）
        searchSourceBuilder.fetchSource(new String[]{"name","price","timestamp"},new String[]{});
        request.source(searchSourceBuilder);
        SearchResponse search = restHighLevelClient.search(request);
        //获得命中的对象
        SearchHits hits = search.getHits();
        //获得命中条数
        long totalHits = hits.getTotalHits();
        System.out.println(totalHits);
        //获得命中靠前的n条数据
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            String index = hit.getIndex();
            String source = hit.getSourceAsString();
            System.out.println(source);
        }
    }

    /**
     * 多条件匹配（多个属性可以同时匹配搜索值）
     */
    @Test
    public void multiSearchDoc() throws IOException {
        //设置搜索的索引库
        SearchRequest request = new SearchRequest("xc_course1");
        //设置搜索的type
        request.types("doc");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //多属性匹配(boost设置权值（name的权值提升10倍）权值的提升可以有效地提高评分)
        searchSourceBuilder.query(QueryBuilders.
                multiMatchQuery("spring","name","description").
                minimumShouldMatch("80%").field("name",10f)
        );
        //设置过滤字段（第一个参数设置包含什么字段，第二个设置不包含什么字段）
        searchSourceBuilder.fetchSource(new String[]{"name","price","timestamp"},new String[]{});
        request.source(searchSourceBuilder);
        SearchResponse search = restHighLevelClient.search(request);
        //获得命中的对象
        SearchHits hits = search.getHits();
        //获得命中条数
        long totalHits = hits.getTotalHits();
        System.out.println(totalHits);
        //获得命中靠前的n条数据
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            String index = hit.getIndex();
            String source = hit.getSourceAsString();
            System.out.println(source);
        }
    }
    @Test
    public void boolSearchTest() throws IOException {
        //设置搜索的索引库
        SearchRequest request = new SearchRequest("xc_course1");
        //设置搜索的type
        request.types("doc");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //创建查询的条件
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery("spring", "name", "description").minimumShouldMatch("80%");
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", "spring");
        //创建bool查询（must代表and should代表or mustNot代表not）
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.must(multiMatchQueryBuilder);
        boolQueryBuilder.must(termQueryBuilder);
        //设置bool为查询对象
        searchSourceBuilder.query(boolQueryBuilder);
        //设置过滤字段（第一个参数设置包含什么字段，第二个设置不包含什么字段）
        searchSourceBuilder.fetchSource(new String[]{"name","price","timestamp"},new String[]{});
        request.source(searchSourceBuilder);
        SearchResponse search = restHighLevelClient.search(request);
        //获得命中的对象
        SearchHits hits = search.getHits();
        //获得命中条数
        long totalHits = hits.getTotalHits();
        System.out.println(totalHits);
        //获得命中靠前的n条数据
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            String index = hit.getIndex();
            String source = hit.getSourceAsString();
            System.out.println(source);
        }
    }

    /**
     * 过滤查询
     */
    @Test
    public void filterSearchDoc() throws IOException {
        //设置搜索的索引库
        SearchRequest request = new SearchRequest("xc_course1");
        //设置搜索的type
        request.types("doc");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //创建查询的条件
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery("spring", "name", "description").minimumShouldMatch("80%");
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", "spring");
        //创建bool查询（must代表and should代表or mustNot代表not）
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.must(multiMatchQueryBuilder);
        boolQueryBuilder.must(termQueryBuilder);
        //过滤
        boolQueryBuilder.filter(QueryBuilders.termQuery("studymodel","201001"));
        boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").gt(100f));
        //设置bool为查询对象
        searchSourceBuilder.query(boolQueryBuilder);
        //设置过滤字段（第一个参数设置包含什么字段，第二个设置不包含什么字段）
        searchSourceBuilder.fetchSource(new String[]{"name","price","timestamp"},new String[]{});
        request.source(searchSourceBuilder);
        SearchResponse search = restHighLevelClient.search(request);
        //获得命中的对象
        SearchHits hits = search.getHits();
        //获得命中条数
        long totalHits = hits.getTotalHits();
        System.out.println(totalHits);
        //获得命中靠前的n条数据
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            String index = hit.getIndex();
            String source = hit.getSourceAsString();
            System.out.println(source);
        }
    }

    /**
     * 排序
     * @throws IOException
     */
    @Test
    public void sortSearchDoc() throws IOException {
        //设置搜索的索引库
        SearchRequest request = new SearchRequest("xc_course1");
        //设置搜索的type
        request.types("doc");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //创建查询的条件
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery("spring", "name", "description").minimumShouldMatch("80%");
        //创建bool查询（must代表and should代表or mustNot代表not）
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.must(multiMatchQueryBuilder);
        boolQueryBuilder.filter(QueryBuilders.termQuery("studymodel","201001"));
        //设置bool为查询对象
        searchSourceBuilder.query(boolQueryBuilder);
        //排序
        searchSourceBuilder.sort(new FieldSortBuilder("price").order(SortOrder.DESC));
        //无法对汉字排序（如有需要安装pinyin分词器插件）
        //searchSourceBuilder.sort(new FieldSortBuilder("name").order(SortOrder.ASC));
        //设置过滤字段（第一个参数设置包含什么字段，第二个设置不包含什么字段）
        searchSourceBuilder.fetchSource(new String[]{"name","price","timestamp"},new String[]{});
        request.source(searchSourceBuilder);
        SearchResponse search = restHighLevelClient.search(request);
        //获得命中的对象
        SearchHits hits = search.getHits();
        //获得命中条数
        long totalHits = hits.getTotalHits();
        System.out.println(totalHits);
        //获得命中靠前的n条数据
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            String index = hit.getIndex();
            String source = hit.getSourceAsString();
            System.out.println(source);
        }
    }
    @Test
    public void hightlightSearchDoc() throws IOException {
        //设置搜索的索引库
        SearchRequest request = new SearchRequest("xc_course1");
        //设置搜索的type
        request.types("doc");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //创建查询的条件
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery("spring", "name", "description").minimumShouldMatch("80%");
        //创建bool查询（must代表and should代表or mustNot代表not）
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.must(multiMatchQueryBuilder);
        boolQueryBuilder.filter(QueryBuilders.termQuery("studymodel","201001"));
        //设置bool为查询对象
        searchSourceBuilder.query(boolQueryBuilder);
        //排序
        searchSourceBuilder.sort(new FieldSortBuilder("price").order(SortOrder.DESC));
        //无法对汉字排序（如有需要安装pinyin分词器插件）
        //searchSourceBuilder.sort(new FieldSortBuilder("name").order(SortOrder.ASC));
        //设置过滤字段（第一个参数设置包含什么字段，第二个设置不包含什么字段）
        searchSourceBuilder.fetchSource(new String[]{"name","price","timestamp"},new String[]{});
        //高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<em>");
        highlightBuilder.postTags("</em>");
        highlightBuilder.fields().add(new HighlightBuilder.Field("name"));
        highlightBuilder.fields().add(new HighlightBuilder.Field("description"));
        searchSourceBuilder.highlighter(highlightBuilder);
        request.source(searchSourceBuilder);
        SearchResponse response = restHighLevelClient.search(request);
        SearchHits searchHits = response.getHits();
        SearchHit[] hits = searchHits.getHits();
        for (SearchHit hit : hits) {
            String source = hit.getSourceAsString();
            System.out.println(source);
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            highlightFields.forEach((k,v)->{
                Text[] fragments = v.getFragments();
                StringBuffer stringBuffer = new StringBuffer();
                for (Text fragment : fragments) {
                    stringBuffer.append(fragment);
                }
                System.out.println(stringBuffer.toString());
            });
        }
    }
    @Test
    public void test(){
        String a = new String("abc");
        String b = new String("abc");
//        System.out.println(a==b);
//        System.out.println(a.intern()==b.intern());
//        System.out.println(a.intern());
//        System.out.println("abc"==a.intern());
        System.out.println(a.equals(b));
    }
}
