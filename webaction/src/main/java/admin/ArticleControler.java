package admin;

import base.BaseController;
import base.Freemark;
import base.PageInfo;
import bean.Article;
import bean.ResultBean;
import exception.InvalidRequestRuntimeException;
import mongo.ArticleService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import query.ArticleQuery;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import redis.RedisTest;
import utils.Constant;
import utils.DateUtil;

/**
 * Created by zhangshengchen on 2017/8/23.
 */
@Controller
@RequestMapping(value = "/web")
public class ArticleControler extends BaseController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private RedisTest redisTest;

    /**
     * 后台查询文章
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/selectArticle")
    @ResponseBody
    public Object selectArticleParams(HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
//        ArticleQuery query = (ArticleQuery) JSONObject.toBean(getPostJSONObject(request), ArticleQuery.class);
        ArticleQuery query = new ArticleQuery();
        int count = 0;
        query.setPageSize(10);
        query.setPageNum(0);
        int oneRecord = 10;// 一页几行
        int pageNo = 0;// 第几行
        try {
            if (query.getPageSize() == 0) {
                throw new InvalidRequestRuntimeException("input error : PageSize need >0", ResultBean.INVALID_REQUST,
                        HttpStatus.UNPROCESSABLE_ENTITY);
            }
            oneRecord = query.getPageSize();
            pageNo = query.getPageNum();
            count++;
            System.out.println("第"+count+"次进入测试");
            System.out.println(redisTest.getKey("key"));
            //asdsadsadsadsadsadsadsad 合并的结果
            PageInfo<Article> articlePageInfo = articleService.getPage(query, pageNo, oneRecord);
            return new ResultBean(articlePageInfo, ResultBean.OK, "getArticleList success");
        } catch (InvalidRequestRuntimeException e) {
            log.error("selectArticle error:" + e.getMessage() + "_" + ExceptionUtils.getStackTrace(e));
            return new ResultBean("", e.getErr(), e.getMessage());
        } catch (Exception e) {
            return new ResultBean("", ResultBean.SYS_ERROR, "selectArticle error:" + e.getMessage() + "_" + ExceptionUtils.getStackTrace(e));
        }

    }

    /**
     * 添加文章
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/addArticle")
    @ResponseBody
    public Object addArticle(HttpServletRequest request,
                             HttpServletResponse response) throws Exception {
        //创建一个合适的Configration对象
        try {
            ArticleQuery query = (ArticleQuery) JSONObject.toBean(getPostJSONObject(request), ArticleQuery.class);
            Template template = Freemark.getActicleTemplate(request);
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("title", query.getTitle());
            paramMap.put("content", query.getContent());
            String data= DateUtil.getToday(new Date());
            Writer writer = new OutputStreamWriter(new FileOutputStream(Constant.FREEMARKURL+data+"/index.html"), "UTF-8");
            template.process(paramMap, writer);
            query.setContentUrl(data);
            articleService.insert(query);
            //asdadsadsadsadsadasdsadsad 合并的结果
            return new ResultBean("", ResultBean.OK,"addArticle Success" );
        } catch (InvalidRequestRuntimeException e) {
            log.error("addArticle error:" + e.getMessage() + "_" + ExceptionUtils.getStackTrace(e));
            return new ResultBean("", e.getErr(), e.getMessage());
        } catch (Exception e) {
            return new ResultBean("", ResultBean.SYS_ERROR, "addArticle error:" + e.getMessage() + "_" + ExceptionUtils.getStackTrace(e));
        }

     }

}
