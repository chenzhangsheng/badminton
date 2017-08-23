package mongo.impl;

import bean.Admin;
import bean.Article;
import bean.ResultBean;
import exception.InvalidRequestRuntimeException;
import mongo.AdminService;
import mongo.ArticleService;
import mongo.MongoBaseDao;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ChenZhangsheng on 2017/8/23.
 */
@Service
public class AdminServiceimpl extends MongoBaseDao implements AdminService {

    private Criteria createCriteria(Admin admin) {
        Map<String, Object> eqMap = new HashMap<String, Object>();
        eqMap.put("account", admin.getAccount());
        eqMap.put("password", admin.getPassword());
        return createCriteria(null,null,eqMap,null,null,null,null,null);
    }
    public void insert(Admin admin) {
        Long id=getNextId(admin.getClass().getSimpleName());
        admin.setId(id);
        this.mongoTemplate.save(admin);
    }

    public Admin Login(Admin admin) {
        Query query = new Query();
        Criteria criteria = createCriteria(admin);
        query.addCriteria(criteria);
        admin =this.mongoTemplate.findOne(query, Admin.class);
        if(admin!=null){
            return admin;
        }else{
            throw new InvalidRequestRuntimeException("account or password error", ResultBean.INVALID_REQUST, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
