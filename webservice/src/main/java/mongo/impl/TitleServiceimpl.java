package mongo.impl;

import bean.Article;
import bean.Title;
import com.mongodb.BasicDBObject;
import mongo.MongoBaseDao;
import mongo.TitleService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
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
        eqMap.put("classifyId", id);
        return createCriteria(null,null,eqMap,null,null,null,null,null);
    }

    public void insert(Article title) {

        this.mongoTemplate.save(title);
    }

    public Article get(Long id) {
        Query query = new Query();
        Criteria criteria = createCriteria(id);
        query.addCriteria(criteria);
        return this.mongoTemplate.findOne(query, Article.class);
    }
}
