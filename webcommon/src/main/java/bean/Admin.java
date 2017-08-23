package bean;

import base.BaseDO;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by ChenZhangsheng on 2017/8/23.
 */
@Document(collection = "Admin")
public class Admin extends BaseDO {

    private String account;
    private String password;
    private String phone;
    private String userName;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
