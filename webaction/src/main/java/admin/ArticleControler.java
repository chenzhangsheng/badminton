package admin;

import base.BaseController;
import base.PageInfo;
import bean.Admin;
import bean.Article;
import bean.ResultBean;
import exception.InvalidRequestRuntimeException;
import mongo.AdminService;
import mongo.ArticleService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import query.ArticleQuery;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhangshengchen on 2017/8/23.
 */
@Controller
@RequestMapping(value = "/web")
public class ArticleControler extends BaseController {

    @Autowired
    private ArticleService articleService;
    /**
     * 后台查询文章
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/selectArticle", method = { RequestMethod.POST })
    @ResponseBody
    public Object selectArticleParams(HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        ArticleQuery query = (ArticleQuery) JSONObject.toBean(getPostJSONObject(request), ArticleQuery.class);
            int oneRecord = 10;// 一页几行
			int pageNo = 0;// 第几行
        try {
            if(query.getPageSize()== 0){
                throw new InvalidRequestRuntimeException("input error : PageSize need >0",1111111,
                        HttpStatus.UNPROCESSABLE_ENTITY);
            }
            oneRecord=query.getPageSize();
            pageNo=query.getPageNum();
            PageInfo<Article> articlePageInfo=articleService.getPage(query,pageNo,oneRecord);
            return new ResultBean(articlePageInfo,ResultBean.OK,"getArticleList success");
        } catch (InvalidRequestRuntimeException e) {
            log.error("selectArticle error:" + e.getMessage() + "_" + ExceptionUtils.getStackTrace(e));
            return new ResultBean("",e.getErr(),e.getMessage());
        }catch (Exception e){
            return new ResultBean("",ResultBean.SYS_ERROR,"selectArticle error:" +e.getMessage()+ "_" + ExceptionUtils.getStackTrace(e));
        }

    }

}
