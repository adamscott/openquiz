package ca.openquiz.webservice.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import ca.openquiz.webservice.enums.Language;
import ca.openquiz.webservice.manager.DBManager;
import ca.openquiz.webservice.manager.security.RoleManager;
import ca.openquiz.webservice.model.AvailableQuestions;
import ca.openquiz.webservice.model.Template;
import ca.openquiz.webservice.model.TemplateSection;
import ca.openquiz.webservice.parameter.SearchAvailableQuestionsParam;
import ca.openquiz.webservice.parameter.SearchQuestionSetParam;
import ca.openquiz.webservice.response.BaseResponse;
import ca.openquiz.webservice.response.IntegerResponse;
import ca.openquiz.webservice.response.KeyResponse;

@Path("/templates")
public class TemplateResource {

	/**
	 * GET rest/templates
	 * Search for templates
	 * @param name Search by template name
	 * @param page Number of the page to show
	 * @param max Number of elements to be returned for each page
	 * @return list of templates basic informations (name and key)
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Template> getTemplateList(
				@QueryParam("name") String name,
				@QueryParam("page") int pageNumber, 
				@QueryParam("max") int resultsByPage) 
	{
		List<Template> returnValue = new ArrayList<Template>();
		SearchQuestionSetParam p = new SearchQuestionSetParam();
		
		if(name != null && !name.isEmpty())
			p.setName(name);
		
		p.setPageNumber(pageNumber);
		p.setResultsByPage(resultsByPage);
		
		returnValue = DBManager.searchTemplate(p);

		return returnValue;
	}

	/**
	 * GET rest/templates/{id}
	 * returns the template information for the specified id
	 * @param id Id of the template to get
	 * @return template informations
	 */
	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Template getTemplate(@PathParam("id") String id) {

		Key key = KeyFactory.stringToKey(id);
		Template t = DBManager.getTemplate(key);

		return t;

	}
	
	/**
	 * POST rest/templates
	 * Add a new template to the database
	 * @param t 
	 * @return
	 */
	@POST
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public KeyResponse addTemplate(Template t)
	{
		KeyResponse resp = new KeyResponse();
				
		try {
			DBManager.save(t);
			resp.setKey(KeyFactory.keyToString(t.getKey()));
		} 
		catch(Exception e) {
			resp.setError(e.getMessage());
		}
		
		return resp;
	}
	
	@POST
	@Path("{id}/addSection")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public BaseResponse addSection(@PathParam("id") String id, @QueryParam("section") String section)
	{
		BaseResponse resp = new BaseResponse();
				
		try {
			Template template = DBManager.getTemplate(KeyFactory.stringToKey(id));
			template.getSectionList().add(
					DBManager.getTemplateSection(KeyFactory.stringToKey(section)));
		} 
		catch(Exception e) {
			resp.setError(e.getMessage());
		}
		
		return resp;
	}
	
	@PUT
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public BaseResponse editTemplate(@PathParam("id") String id, Template template) {
		
		BaseResponse resp = new BaseResponse();
		Key key = KeyFactory.stringToKey(id);
		try {
			Template tmpTemplate = DBManager.getTemplate(key);
			
			tmpTemplate.copy(template);
			
			DBManager.save(tmpTemplate);
			
			return resp;
		}
		catch(Exception e) {
			resp.setError(e.getMessage());
			return resp;
		}
	}
	
	@DELETE
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public BaseResponse deleteTemplate(@PathParam("id") String id) {
		BaseResponse response = new BaseResponse();
		
		try{
			Key key = KeyFactory.stringToKey(id);
			Template qst = DBManager.getTemplate(key);
			qst.delete();
		}
		catch(Exception e)
		{
			response.setError("Error while deleting a Template : " + e.getMessage());
		}
		
		return response;
	}
	
	@POST
	@Path("availableQuestions")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public IntegerResponse getAvailableQuestions(@Context SecurityContext sc, TemplateSection templateSection, 
			@DefaultValue("true") @QueryParam("includePublic") boolean includePublic,
			@DefaultValue("fr") @QueryParam("lang") Language lang) {
		SearchAvailableQuestionsParam param = new SearchAvailableQuestionsParam();
		param.setIncludePublic(includePublic);
		param.setCategory(templateSection.getCategory());
		param.setDegree(templateSection.getDifficulty());
		param.setGroups(RoleManager.getUserGroups(sc));
		param.setLanguage(lang);
		param.setType(templateSection.getQuestionType());
		
		int qty = 0;
		for(AvailableQuestions aq : DBManager.searchAvailableQuestions(param)){
			qty += aq.getQty();
		}
		
		return new IntegerResponse(qty);
	}
}
