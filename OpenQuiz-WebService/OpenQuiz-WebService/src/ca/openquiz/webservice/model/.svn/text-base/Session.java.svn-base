package ca.openquiz.webservice.model;

import java.util.Date;
import java.util.UUID;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Unique;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.google.appengine.api.datastore.Key;

import ca.openquiz.webservice.converter.DateConverter;
import ca.openquiz.webservice.converter.KeyConverter;
import ca.openquiz.webservice.manager.DBManager;

@XmlRootElement
@PersistenceCapable()
public class Session extends BaseModel {
	
	@XmlTransient
	@Unique
	private String sessionKey = UUID.randomUUID().toString();
	
	@XmlTransient
	private Key userId = null;
	
	@XmlTransient
	private boolean active;
	
	@XmlTransient
	private Date createTime;
	
	@XmlTransient
	private Date endTime;
	
	public Session() {
		init();
	}
	
	public Session(Key userId) {
		this.userId = userId;
		init();
	}
	
	private void init() {
		Date now = new Date();
		Date endTime = new Date(now.getTime() + 60 * 60 * 1000);
		
		this.createTime = now;
		this.active = true;
		this.endTime = endTime;
	}
	
	@XmlElement
	public String getSessionKey() {
		return sessionKey;
	}
	
	@XmlElement
	@XmlJavaTypeAdapter(KeyConverter.class)
	public Key getUserId() {
		return userId;
	}
	
	public void setUserId(Key userId) {
		this.userId = userId;
	}

	@XmlTransient
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@XmlElement
	@XmlJavaTypeAdapter(DateConverter.class)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@XmlElement
	@XmlJavaTypeAdapter(DateConverter.class)
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@XmlElement
	public boolean isExpired() {
		Date now = new Date();
		return this.endTime.before(now);
	}

	@Override
	public void delete() {
		DBManager.delete(this.getKey());
		key = null;
	}

	@Override
	public boolean isValid() {
		return sessionKey != null && !sessionKey.isEmpty();
	}
}
