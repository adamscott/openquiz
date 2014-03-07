package ca.openquiz.webservice.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import ca.openquiz.webservice.enums.CategoryType;
import ca.openquiz.webservice.manager.DBManager;
import ca.openquiz.webservice.manager.security.RoleManager;
import ca.openquiz.webservice.model.Category;
import ca.openquiz.webservice.model.Question;
import ca.openquiz.webservice.model.TemplateSection;
import ca.openquiz.webservice.parameter.SearchCategoryParam;
import ca.openquiz.webservice.parameter.SearchQuestionParam;
import ca.openquiz.webservice.parameter.SearchTemplateSectionParam;
import ca.openquiz.webservice.response.BaseResponse;
import ca.openquiz.webservice.response.KeyResponse;

@Path("/categories")
public class CategoryResource {

	/**
	 * GET rest/categories
	 * Search a category
	 * @param name Name of the categories to get
	 * @param group Group id of the categories to get
	 * @param type CategoryType of the categories to get
	 * @param page Number of the page to show
	 * @param max Number of elements to be returned for each page
	 * @return List of categories
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Category> getCategoryList(
			@QueryParam("name") String name,
			@QueryParam("type") String type,
			@QueryParam("page") int pageNumber,
			@QueryParam("max") int resultsByPage) {
		
		SearchCategoryParam p = new SearchCategoryParam();

		if(name != null && !name.isEmpty())
			p.setName(name);
		if(type != null && !type.isEmpty())
			p.setCategoryType(CategoryType.valueOf(type));

		return DBManager.searchCategory(p);
	}

	/**
	 * GET rest/categories/{id}
	 * Get a category by ID
	 * @param id ID of the category to get
	 * @return Category
	 */
	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Category getCategory(@Context SecurityContext sc, @PathParam("id") String id) {

		Key key = KeyFactory.stringToKey(id);
		Category cat = DBManager.getCategory(key);

		return cat;
	}

	/**
	 * POST rest/categories
	 * Add a new Category
	 * @param sc Authentication of an admin, a Manager or a QuestionManger
	 * @param cat Category to add
	 * @return Completed or error
	 */
	@POST
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public KeyResponse addCategory(@Context SecurityContext sc, Category cat)
	{
		KeyResponse resp = new KeyResponse();

		if(RoleManager.isUserAdmin(sc)) {

			try {
				DBManager.save(cat);
				resp.setKey(KeyFactory.keyToString(cat.getKey()));
			} 
			catch(Exception e) {
				resp.setError(e.getMessage());
			}
		}
		else{
			throw new WebApplicationException(Response.Status.FORBIDDEN);
		}

		return resp;
	}

	/**
	 * PUT rest/categories/{id}
	 * Modify an existing category
	 * @param sc Authentication of an admin, a Manager or a QuestionManger
	 * @param id ID of the category to modify
	 * @param newCat New Category
	 * @return Completed or error 
	 */
	@PUT
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public BaseResponse editCategory(@Context SecurityContext sc, @PathParam("id") String id, Category newCat)
	{
		BaseResponse resp = new BaseResponse();

		Key key = KeyFactory.stringToKey(id);

		SearchCategoryParam catParam = new SearchCategoryParam();
		SearchQuestionParam questionParam = new SearchQuestionParam();
		SearchTemplateSectionParam templateSectionParam = new SearchTemplateSectionParam();
		List<Category> catList = new ArrayList<Category>();
		List<Question> questionList = new ArrayList<Question>();
		List<TemplateSection> templateSectionList = new ArrayList<TemplateSection>();
		try {

			Category cat = DBManager.getCategory(key);

			if(RoleManager.isUserAdmin(sc)) {

				catParam.setCategoryType(newCat.getType());
				catParam.setName(newCat.getName());
				catList = DBManager.searchCategory(catParam);

				if(catList.size() == 0)
				{
					cat.setName(newCat.getName());
					cat.setType(newCat.getType());
					DBManager.save(cat);
				}
				else
				{
					questionParam.setCategory(key);
					questionList = DBManager.searchQuestion(questionParam);
					for(Question q : questionList)
					{
						q.setCategory(catList.get(0).getKey());
						DBManager.save(q);
					}

					templateSectionParam.setCategory(key);
					templateSectionList = DBManager.searchTemplateSection(templateSectionParam);
					for(TemplateSection qst : templateSectionList)
					{
						qst.setCategory(catList.get(0));
						DBManager.save(qst);
					}

					DBManager.delete(cat.getKey());
				}
			}
			else{
				throw new WebApplicationException(Response.Status.FORBIDDEN);
			}
		}
		catch(Exception e) {
			resp.setError(e.getMessage());
		}
		return resp;
	}
}
