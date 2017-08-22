package mongo;

import bean.Article;
import bean.Title;
import net.sf.json.JSONObject;

/**
 * Created by zhangshengchen on 2017/8/17.
 */
public interface TitleService {

    public void insert(Article title);
    public Article get(Long id);
}
