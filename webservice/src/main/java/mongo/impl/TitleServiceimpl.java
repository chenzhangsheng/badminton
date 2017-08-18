package mongo.impl;

import bean.Title;
import com.mongodb.BasicDBObject;
import mongo.MongoBaseDao;
import mongo.TitleService;
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

    private Criteria createCriteria(Long titleid) {
        Map<String, Object> eqMap = new HashMap<String, Object>();
        eqMap.put("titleid", titleid);
        return createCriteria(null,null,eqMap,null,null,null,null,null);
    }

    public void insert(Title title) {
        this.mongoTemplate.insert(title);
    }

    public Title get(Long id) {
        Query query = new Query();
        Criteria criteria = createCriteria(id);
        query.addCriteria(criteria);
        return this.mongoTemplate.findOne(query, Title.class);
    }
}
