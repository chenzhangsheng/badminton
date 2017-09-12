package base;

import bean.ResultBean;
import exception.InvalidRequestRuntimeException;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhangshengchen on 2017/9/1.
 */
public class Freemark {
    public static Template getActicleTemplate(HttpServletRequest request){
        try{
            Configuration configuration = new Configuration();
            configuration.setServletContextForTemplateLoading(request.getSession().getServletContext(), "freemark");
            configuration.setObjectWrapper(new DefaultObjectWrapper());
            configuration.setDefaultEncoding("UTF-8");  //这个一定要设置，不然在生成的页面中 会乱码
            return configuration.getTemplate("article.html");
        }catch (Exception e){
            throw new InvalidRequestRuntimeException("freemark generate err", ResultBean.SYS_ERROR, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

}
