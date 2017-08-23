package mongo;

import base.PageInfo;
import bean.Article;
import bean.Title;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * Created by zhangshengchen on 2017/8/17.
 */
public interface TitleService {

    public void insert(Article title);
    public Article get(Long id);
    public PageInfo getPage(int start, int size);
}
