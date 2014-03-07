package ca.openquiz.comms.model;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonRootName;

@JsonRootName("categories")
public class CategoryWrapper
{
	@JsonProperty("category")
	List<Category> CategoryList;

	public List<Category> getCategories()
	{
		return CategoryList;
	}
}
