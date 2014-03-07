package ca.openquiz.webservice.model;

import java.util.List;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import ca.openquiz.webservice.enums.CategoryType;
import ca.openquiz.webservice.manager.DBManager;
import ca.openquiz.webservice.parameter.SearchCategoryParam;
import ca.openquiz.webservice.parameter.SearchQuestionParam;

@XmlRootElement(name = "category")
@PersistenceCapable(detachable="true")
public class Category extends BaseModel {

	@Persistent
	@XmlElement
	private CategoryType type = null;

	@Persistent
	@XmlElement
	private String name;

	@XmlTransient
	public CategoryType getType() {
		return type;
	}

	public void setType(CategoryType type) {
		this.type = type;
	}

	@XmlTransient
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void delete() {
		SearchQuestionParam param = new SearchQuestionParam();
		param.setCategory(this.key);

		List<Question> questions = DBManager.searchQuestion(param);

		if(questions != null && !questions.isEmpty()) {
			
			SearchCategoryParam catParam = new SearchCategoryParam();
			catParam.setName("Deleted");
			catParam.setCategoryType(CategoryType.Unknown);
			List<Category> categories = DBManager.searchCategory(catParam);
			
			Category cat = null;
			
			if(categories != null && !categories.isEmpty()){
				cat = categories.get(0);
			}
			else {
				cat = new Category();
				cat.setName("Deleted");
				cat.setType(CategoryType.Unknown);
				DBManager.save(cat);
			}
			
			for(Question q : DBManager.searchQuestion(param)){
				q.setCategory(cat.getKey());
				DBManager.save(q);
			}
		}
		
		DBManager.delete(this.key);
	}

	@Override
	public boolean isValid() {
		return name != null && !name.isEmpty() && type != null;
	}	
}
