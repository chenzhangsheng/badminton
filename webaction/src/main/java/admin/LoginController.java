/**
 *
 * @author geloin
 * @date 2012-5-5 涓婂崍9:31:52
 */
package admin;

import base.BaseController;
import bean.Admin;
import bean.ResultBean;
import exception.InvalidRequestRuntimeException;
import mongo.AdminService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author chenzhangsheng
 * @date 2016-12-7 18:31:52
 */

@Controller
@RequestMapping(value = "/web")
public class LoginController extends BaseController {
    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/loginUser" , method = { RequestMethod.POST })
    @ResponseBody
    public Object login(HttpServletRequest request,HttpServletResponse response) throws Exception {
        try {
            Admin query = (Admin) JSONObject.toBean(getPostJSONObject(request), Admin.class);
            if (query.getAccount() == null) {
                return new ResultBean("", ResultBean.NOT_FOUND, "userAccount is null");
            }
            if (query.getPassword() == null) {
                return new ResultBean("", ResultBean.NOT_FOUND, "Password is null");
            }
            query= adminService.Login(query);
            return new ResultBean(query,ResultBean.OK,"adminLogin success");
        } catch (InvalidRequestRuntimeException e) {
            log.error("adminLogin error:" + e.getMessage() + "_" + ExceptionUtils.getStackTrace(e));
            return new ResultBean("", e.getErr(), e.getMessage());
        } catch (Exception e) {
            return new ResultBean("", ResultBean.SYS_ERROR, "adminLogin error:" + e.getMessage() + "_" + ExceptionUtils.getStackTrace(e));
        }
    }
}