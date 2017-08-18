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
@Document(collection = "Title")
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

}
