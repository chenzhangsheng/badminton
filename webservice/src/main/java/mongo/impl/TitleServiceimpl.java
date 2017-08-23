package mongo.impl;

import base.PageInfo;
import bean.Article;
import bean.Comment;
import bean.SeqInfo;
import bean.Title;
import com.mongodb.BasicDBObject;
import mongo.MongoBaseDao;
import mongo.TitleService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangshengchen on 2017/8/17.
 */
@Service
public class TitleServiceimpl extends MongoBaseDao implements TitleService {

    public void createCollection(Long id) {

        // TODO Auto-generated method stub
//        if (!this.mongoTemplate.collectionExists(Title.class)) {
//            this.mongoTemplate.createCollection(Title.class);
//        }
    }

    private Criteria createCriteria(Long id) {
        Map<String, Object> eqMap = new HashMap<String, Object>();
        eqMap.put("_id", id);
        return createCriteria(null,null,eqMap,null,null,null,null,null);
    }

    public void insert(Article title) {
        Long id=getNextId(title.getClass().getSimpleName());
        title.setId(id);
        this.mongoTemplate.save(title);
    }
    public void update(Article acticle){
    }

    public Article get(Long id) {
        Query query = new Query();
        Criteria criteria = createCriteria(id);
        query.addCriteria(criteria);
        return this.mongoTemplate.findOne(query, Article.class);
    }
    public PageInfo getPage(int start, int size){
        Query query = new Query();
        Map<String, Object> eqMap = new HashMap<String, Object>();
        eqMap.put("classifyId", 1);
        Criteria criteria = createCriteria(null,null,eqMap,null,null,null,null,null);
        query.addCriteria(criteria);
        query.skip(start);
        query.limit(size);
        PageInfo<Article> pageInfo = new PageInfo<Article>();
        pageInfo.setTotal(this.mongoTemplate.count(query,  Article.class));
        pageInfo.setPageNum(start);
        pageInfo.setPageSize(size);
        List<Article> lists = this.mongoTemplate.find(query, Article.class);
        pageInfo.setList(lists);
        return pageInfo;
    }

}
