package ca.openquiz.comms.model;

import ca.openquiz.comms.enums.CategoryType;

public class Category extends BaseModel {
	private static final long serialVersionUID = 7163886476308157946L;

	private CategoryType type = null;
	
	private String name;

	public CategoryType getType() {
		return type;
	}

	public void setType(CategoryType type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}
