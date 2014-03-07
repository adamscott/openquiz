package ca.openquiz.webservice.model;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import ca.openquiz.webservice.manager.DBManager;

@XmlRootElement
@PersistenceCapable(detachable="true")
public class Template extends BaseModel {
	
	@Persistent
	private String name;
	
	@Persistent(defaultFetchGroup="true")
	@XmlTransient
	private List<TemplateSection> sectionList = new ArrayList<TemplateSection>();
		
	public Template() {
		
	}
	
	@XmlElement
	public List<TemplateSection> getSectionList() {
		return sectionList;
	}

	@XmlElement
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void copy(Template template)
	{
		this.name = template.getName();
		
		for(TemplateSection ts : this.sectionList){
			ts.delete();
		}
		
		this.sectionList.clear();
		this.setSections(template.getSectionList());
	}
	
	public void setSections(List<TemplateSection> sections){
		sectionList = sections;
	}

	@Override
	public void delete() {
		for(TemplateSection s : sectionList){
			s.delete();
		}
		
		DBManager.delete(this.getKey());
		key = null;
	}

	@Override
	public boolean isValid() {
		return sectionList != null && !sectionList.isEmpty() && name != null && !name.isEmpty();
	}
}
