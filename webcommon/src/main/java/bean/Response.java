package bean;

import base.BaseDO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by zhangshengchen on 2017/8/22.
 */

public class Response {
    private String responseUserName;
    private String userName;
    private String userPhone;
    private String content;

    public String getResponseUserName() {
        return responseUserName;
    }

    public void setResponseUserName(String responseUserName) {
        this.responseUserName = responseUserName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
