package base;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import bean.ResultBean;
import bean.ReturnMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import exception.InvalidRequestRuntimeException;
import exception.PlatformRequestRuntimeException;
import exception.PlatformRuntimeException;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
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

    public ExceptionErrorMessage getExceptionErrorMessage(Exception ex) {
        int errCode = -1;
//		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        HttpStatus httpStatus = HttpStatus.OK;
        String mString = "";
        if (ex instanceof InvalidRequestRuntimeException) {
            InvalidRequestRuntimeException exception = (InvalidRequestRuntimeException)ex;
            errCode = exception.getErr();
//			httpStatus = exception.getHttpStatus();
            mString =  exception.getMessage();
        } else if (ex instanceof PlatformRequestRuntimeException) {
            PlatformRequestRuntimeException exception = (PlatformRequestRuntimeException)ex;
            errCode = exception.getErr();
            mString = exception.getMessage();
        }  else if (ex instanceof PlatformRuntimeException) {
            PlatformRuntimeException exception = (PlatformRuntimeException)ex;
            errCode = ResultBean.SYS_ERROR;
            mString = exception.getMessage();
        } else {
//            errCode = ErrConstatns.API3_SERVER_ERROR;
//            mString = ErrConstatns.getCodeMessage(ErrConstatns.API3_SERVER_ERROR);
        }

        return new ExceptionErrorMessage(errCode, httpStatus, mString);
    }

    /**
     *  异常信息实体
     * @author chaogao
     */
    public class ExceptionErrorMessage {
        private int errCode = -1;
        private HttpStatus httpStatus = HttpStatus.OK;
        private String errMsg = "";

        public ExceptionErrorMessage(int errCode, HttpStatus httpStatus, String errMsg) {
            super();
            this.errCode = errCode;
            this.httpStatus = httpStatus;
            this.errMsg = errMsg;
        }
        public int getErrCode() {
            return errCode;
        }
        public void setErrCode(int errCode) {
            this.errCode = errCode;
        }
        public HttpStatus getHttpStatus() {
            return httpStatus;
        }
        public void setHttpStatus(HttpStatus httpStatus) {
            this.httpStatus = httpStatus;
        }

        public String getErrMsg() {
            return errMsg;
        }

        public void setErrMsg(String errMsg) {
            this.errMsg = errMsg;
        }


    }
}
