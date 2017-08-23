package mongo.impl;

import base.PageInfo;
import bean.Article;
import exception.InvalidRequestRuntimeException;
import mongo.ArticleService;
import mongo.MongoBaseDao;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import query.ArticleQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangshengchen on 2017/8/23.
 */
@Service
public class Articleimpl extends MongoBaseDao implements ArticleService {

    private Criteria createCriteria(Long id) {
        Map<String, Object> eqMap = new HashMap<String, Object>();
        eqMap.put("_id", id);
        return createCriteria(null,null,eqMap,null,null,null,null,null);
    }

    public void insert(Article article) {
        Long id=getNextId(article.getClass().getSimpleName());
        article.setId(id);
        this.mongoTemplate.save(article);
    }
    public void update(Article article){
        Query query = new Query();
        if(article.getId() == null){
            throw new InvalidRequestRuntimeException("input error : need acticle_id");
        }
        Criteria criteria = createCriteria(article.getId());
        query.addCriteria(criteria);
        this.mongoTemplate.save(article);
    }

    public Article get(Long id) {
        Query query = new Query();
        Criteria criteria = createCriteria(id);
        query.addCriteria(criteria);
        return this.mongoTemplate.findOne(query, Article.class);
    }
    public PageInfo getPage(ArticleQuery article, int start, int size){
        Map<String, Object> eqMap = new HashMap<String, Object>();
        Map<String, String> regexMap = new HashMap<String, String>();
        if(article.getClassifyId()!= null){
            eqMap.put("classifyId", article.getClassifyId());

        }
        if(article.getTitle()!= null){
            regexMap.put("title",article.getTitle());
        }
        Criteria criteria = createCriteria(null,null,eqMap,null,null,regexMap,null,null);
        Query query = new Query();
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
