package ca.openquiz.webservice.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.codehaus.jackson.map.ObjectMapper;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import ca.openquiz.webservice.manager.DBManager;
import ca.openquiz.webservice.manager.security.RoleManager;
import ca.openquiz.webservice.parameter.SearchGameStatParam;
import ca.openquiz.webservice.response.GameStatListResponse;
import ca.openquiz.webservice.tools.ExceptionLogger;

@Path("/stats")
public class StatResource {
	
	@GET
	@Produces( MediaType.APPLICATION_JSON )
	public String getGameStatsList(@Context SecurityContext sc,
			@QueryParam("for") String statsfor, 
			@QueryParam("context") String context,
			@QueryParam("page") int page,
			@QueryParam("max") int results){
		
		if(statsfor != null){
			Key statsForKey = KeyFactory.stringToKey(statsfor);
			Key groupKey = null;
			
			if(statsForKey.getKind().equals("User")){
				groupKey = DBManager.getUser(statsForKey).getKey();
			}
			else if(statsForKey.getKind().equals("Team")){
				groupKey = DBManager.getUser(statsForKey).getKey();
			}
			
			if(RoleManager.getUserGroups(sc).contains(groupKey)){
				
				SearchGameStatParam param = new SearchGameStatParam();
				param.setStatsFor(statsForKey);
				
				if(context != null){
					param.setContext(KeyFactory.stringToKey(context));
				}
				
				param.setPageNumber(page);
				param.setResultsByPage(results);
				
				ObjectMapper mapper = new ObjectMapper();
				
				try {
					return mapper.writeValueAsString(new GameStatListResponse(DBManager.searchStat(param)));
				} catch (Exception e) {
					ExceptionLogger.getLogger().warning(e.toString());
					throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
				}
			}
		}
		else{
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}
		return "";
	}
}
