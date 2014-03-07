package ca.openquiz.webservice.parameter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import ca.openquiz.webservice.converter.KeyConverter;
import com.google.appengine.api.datastore.Key;


@XmlRootElement(name="createQuestionSet")
public class CreateQuestionSetParam {

	@XmlElement(name="availableDate")
	private Date availableDate;

	@XmlElement(name="name")
	private String name;
	
	@XmlElementWrapper(name="createSections")
	@XmlElement(name="createSection")
	private List<CreateQuestionSetSectionParam> sectionList = new ArrayList<CreateQuestionSetSectionParam>();
	
	@XmlElementWrapper(name="groups")
	@XmlElement(name="key")
	@XmlJavaTypeAdapter(KeyConverter.class)
	private List<Key> groups = new ArrayList<Key>();
	
	public CreateQuestionSetParam() {
		
	}

	@XmlTransient
	public Date getAvailableDate() {
		return availableDate;
	}

	public void setAvailableDate(Date availableDate) {
		this.availableDate = availableDate;
	}

	@XmlTransient
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlTransient
	public List<CreateQuestionSetSectionParam> getSectionList() {
		return sectionList;
	}

	public void setSectionList(List<CreateQuestionSetSectionParam> sectionList) {
		this.sectionList = sectionList;
	}

	@XmlTransient
	public List<Key> getGroups() {
		return groups;
	}

	public void setGroups(List<Key> groups) {
		this.groups = groups;
	}

	@XmlTransient
	public boolean isValid(){
		return this.name != null && !this.name.isEmpty() && this.sectionList.size() >= 1;
	}
	
}
