package base;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.data.annotation.Id;

import java.util.Date;


public class BaseDO {
	private Date createTime;
	private Date updateTime;
	private Integer isDeleted;
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}
	
}
