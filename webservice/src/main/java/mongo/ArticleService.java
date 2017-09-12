package mongo;

import base.PageInfo;
import bean.Article;
import query.ArticleQuery;

/**
 * Created by zhangshengchen on 2017/8/23.
 */
public interface ArticleService {
    public Long insert(ArticleQuery title);
    public Article get(Long id);
    public PageInfo getPage(ArticleQuery article, int start, int size);
    public void update(Article title);
}
