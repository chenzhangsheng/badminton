package bean;

import base.BaseDO;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by zhangshengchen on 2017/8/22.
 */
@Document(collection = "Article")
public class Article extends BaseDO{

    private String title;
    private String author;
    private Integer isTop; // 1置顶，0不置顶
    private Integer isRecommend; //1推荐，0不推荐
    private String abstracts;
    private Long classifyId;
    private Integer numberRead;
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getIsTop() {
        return isTop;
    }

    public void setIsTop(Integer isTop) {
        this.isTop = isTop;
    }

    public Integer getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(Integer isRecommend) {
        this.isRecommend = isRecommend;
    }

    public String getAbstracts() {
        return abstracts;
    }

    public void setAbstracts(String abstracts) {
        this.abstracts = abstracts;
    }

    public Long getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(Long classifyId) {
        this.classifyId = classifyId;
    }

    public Integer getNumberRead() {
        return numberRead;
    }

    public void setNumberRead(Integer numberRead) {
        this.numberRead = numberRead;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
