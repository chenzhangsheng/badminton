package base;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import bean.ReturnMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import utils.JsonUtil;
import utils.ST;

public class BaseController {
    public static final String DEFAULT_CHARSET = "UTF-8";
    protected Logger log = Logger.getLogger(this.getClass());
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;

    @ModelAttribute
    protected void setReqAndRes(HttpServletRequest request,
            HttpServletResponse response) {
        try {
            request.setCharacterEncoding(DEFAULT_CHARSET);
            response.setCharacterEncoding(DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        this.request = request;
        this.response = response;
        this.session = request.getSession();
    }

    protected String getParam(String k) {
    	return getParam(k, null);  
    }

    protected String getParam(String k, String def) {
        return StringUtils.trim(ST.getDefault(request.getParameter(k), def));
    }
    
    public HttpSession getNowSession()
    {
      ServletRequestAttributes attrs = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
      return attrs.getRequest().getSession();
    }


    public void sendJsonMsg(String msg,HttpServletResponse response){
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding(DEFAULT_CHARSET);
        try {
            PrintWriter out = response.getWriter();
            out.write(msg);
            out.flush();
            out.close();
        } catch (Exception e) {
            log.error("send json error", e);
        }
    }

    public <T> T getPostJSONObject(HttpServletRequest request,Class<T> obj) {
        String reqstring = getPostJSON(request);
        if(StringUtils.isEmpty(reqstring))
            try {
                return obj.newInstance();
            } catch (InstantiationException e) {
                log.error(ExceptionUtils.getStackTrace(e));
                return null;
            } catch (IllegalAccessException e) {
                log.error(ExceptionUtils.getStackTrace(e));
                return null;
            }
        T a = JsonUtil.getObjectFromJson(reqstring, obj);
        return a;
    }

    public String getPostJSON(HttpServletRequest request) {
        String result="";
        try {
            result=inputStreamToString(request.getInputStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    public String inputStreamToString(InputStream in) throws IOException {
        StringBuffer out = new StringBuffer();
        byte[] b = new byte[4096];
        for (int n; (n = in.read(b)) != -1;) {
            out.append(new String(b, 0, n,DEFAULT_CHARSET));
        }
        return out.toString();
    }

    public boolean isQA(String isQA){
        if("1".equals(isQA))
            return true;
        return false;
    }
    public JSONObject getPostJSONObject(HttpServletRequest request) {
        String reqstring = getPostJSON(request);

        if(StringUtils.isEmpty(reqstring))
            return null;
        JSONObject json;
        try {
            json = JSONObject.fromObject(URLDecoder.decode(reqstring,DEFAULT_CHARSET));
            return json;
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new JSONObject();
    }


}
