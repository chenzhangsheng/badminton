package mongo;

import bean.Title;

/**
 * Created by zhangshengchen on 2017/8/17.
 */
public interface TitleService {

    public void insert(Title title);
    public Title get(Long id);
}
