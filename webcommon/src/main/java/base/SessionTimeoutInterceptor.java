package base;

import bean.Admin;
import bean.ResultBean;
import exception.InvalidRequestRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
/**
 * Created by zhangshengchen on 2017/8/24.
 */
public class SessionTimeoutInterceptor  implements HandlerInterceptor{
    protected org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(this.getClass());
    public String[] allowUrls;//还没发现可以直接配置不拦截的资源，所以在代码里面来排�?

    public void setAllowUrls(String[] allowUrls) {
        this.allowUrls = allowUrls;
    }

    public void afterCompletion(HttpServletRequest arg0,
                                HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
    }

    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
                           Object arg2, ModelAndView arg3) throws Exception {


    }

    @SuppressWarnings("unchecked")
    public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1,Object arg2) throws Exception {
        String requestUrl = arg0.getRequestURI().replace(arg0.getContextPath(), "");

        //angularjs跨域请求限制

        System.out.println("requestUrl="+requestUrl);
        System.out.println("request getMethod="+arg0);
        System.out.println("request getRemoteUser="+arg0.getRemoteUser());
        System.out.println("request getHeaderNames ="+arg0.getHeaderNames());
//        arg1.setHeader("Access-Control-Allow-Origin", "*");
//        arg1.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
//        arg1.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With");
        System.out.println("requestUrlend="+requestUrl);
        if(null != allowUrls && allowUrls.length>=1){
            for(String url : allowUrls) {
                if(requestUrl.startsWith(url)) {
                    return true;
                }
            }
        }
        if(requestUrl.contains("phone")||requestUrl.contains("/weixin")){
            return true;
        }
        Admin user = (Admin) arg0.getSession().getAttribute("SESSION_USER");
        if(user != null){
            Set<String> set = (Set<String>) arg0.getSession().getAttribute("SESSION_EVENT");
            if(requestUrl.indexOf(".")==-1){
                boolean flag = false;
                for(String url : set) {
                    if(requestUrl.startsWith(url)) {
                        flag = true;
                        break;
                    }
                }
                if(!flag && arg0.getHeader("x-requested-with") != null && arg0.getHeader("x-requested-with").equals("XMLHttpRequest")){
                    arg1.setStatus(999);
                    return flag;
                }
                return flag;
            }else{
                return true;
            }
        }else{
            //没有session的验�?
            if(arg0.getHeader("x-requested-with") != null && arg0.getHeader("x-requested-with").equals("XMLHttpRequest")){
                arg1.setStatus(975);
                return false;
            }else{
//                throw new InvalidRequestRuntimeException("当前登陆超时,请重新登", ResultBean.TIMEOUT,
//                        HttpStatus.GATEWAY_TIMEOUT);
                return true;
            }
        }
    }


}