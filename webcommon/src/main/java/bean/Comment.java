package bean;

import base.AutoIncKey;
import base.BaseDO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangshengchen on 2017/8/22.
 */

public class Comment  {

    @Id
    private Long id = 0L;
    private Integer acticleId;
    private String commentUserName;
    private String commentUserPhone;
    private String commentContent;
    private ArrayList<Response> responseList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
