package bean;

import base.BaseDO;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

/**
 * Created by zhangshengchen on 2017/8/22.
 */

@Document(collection = "Comment")
public class Comment extends BaseDO {

    private Integer acticleId;
    private String commentUserName;
    private String commentUserPhone;
    private String commentContent;
    private ArrayList<Response> responseList;
    public Integer getActicleId() {
        return acticleId;
    }
    public void setActicleId(Integer acticleId) {
        this.acticleId = acticleId;
    }
    public String getCommentUserName() {
        return commentUserName;
    }
    public void setCommentUserName(String commentUserName) {
        this.commentUserName = commentUserName;
    }
    public String getCommentUserPhone() {
        return commentUserPhone;
    }
    public void setCommentUserPhone(String commentUserPhone) {
        this.commentUserPhone = commentUserPhone;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public ArrayList<Response> getResponseList() {
        return responseList;
    }

    public void setResponseList(ArrayList<Response> responseList) {
        this.responseList = responseList;
    }
}
