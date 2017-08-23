package mongo;

import bean.Admin;
import bean.Article;

/**
 * Created by ChenZhangsheng on 2017/8/23.
 */
public interface AdminService {
    public void insert(Admin admin);
    public Admin Login(Admin admin);
}
