package bean;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangshengchen on 2017/8/17.
 */
@Document(collection = "Article")
public class Title {
    @Id
    private Long titleid;
    private String name;
    private String commont;
    private List<Title> replay;
    public Long getTitleid() {
        return titleid;
    }
    public void setTitleid(Long titleid) {
        this.titleid = titleid;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getCommont() {
        return commont;
    }

    public void setCommont(String commont) {
        this.commont = commont;
    }

    public List<Title> getReplay() {
        return replay;
    }

    public void setReplay(List<Title> replay) {
        this.replay = replay;
    }
/*
    {
        "_id" : ObjectId("597aa23add840cd4ce0681d1"),
            "comment_blog_id" : "blog id",
            "comment_user_id" : "A",
            "comment_content" : "A的评论",
            "create_time" : "2017-15-12 14:00",
            "comment_responses" : [
        {
            "response_user_id" : "B",
                "response_user_phone" : "B的phone",
                "response_user_nickname" : "B的nickname",
                "response_content" : [
            "这是B给A的评论(带着索引index)",
                    "这是B给A的评论(带着索引index)",
                    "这是B给A的评论(带着索引index)"
            ],
            "create_time" : "2017-15-12 14:00",
                "get_reply" : [
            "这是A给B的某一个评论的回复如果有就对应插入index对应的元素没有就是空串",
                    "A没有回复B这一条就是空串",
                    "A个神经病跳着回复了这一条评论，这数组的第三个元素就是A回复的内容"
            ]
        },
        {
            "response_user_id" : "C",
                "response_user_phone" : "C的phone",
                "response_user_nickname" : "C的nickname",
                "response_content" : [
            "这是C给A的评论(带着索引index)",
                    "这是C给A的评论(带着索引index)",
                    "这是C给A的评论(带着索引index)"
            ],
            "create_time" : "2017-15-12 14:00",
                "get_reply" : [
            "这是A给C的第index个评论的回复",
                    "A没有回复C这一条就是空串",
                    "A个神经病跳着回复了这一条评论，这数组的第三个元素就是A回复的内容"
            ]
        }
    ]
    }
*/
}
