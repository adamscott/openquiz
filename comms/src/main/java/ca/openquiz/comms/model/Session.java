package ca.openquiz.comms.model;


import java.util.Date;

public class Session extends BaseModel {
	private static final long serialVersionUID = -8910099995051502220L;

	private String sessionKey;
	
	private String userId;
	
	private boolean expired;
	
	private Date createTime;
	
	private Date endTime;
	
	public Session() {
	}
	
	public String getSessionKey() {
		return sessionKey;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public boolean isExpired() {
		Date now = new Date();
		return this.endTime.before(now);
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

}

