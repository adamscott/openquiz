package ca.openquiz.webservice.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import ca.openquiz.webservice.manager.DBManager;
import ca.openquiz.webservice.model.Tournament;
import ca.openquiz.webservice.parameter.SearchTournamentParam;
import ca.openquiz.webservice.response.BaseResponse;
import ca.openquiz.webservice.response.KeyResponse;
import ca.openquiz.webservice.response.TournamentListResponse;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@Path("/tournaments")
public class TournamentResource {

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public TournamentListResponse getTournamentList(
				@QueryParam("game") String game,
				@QueryParam("team") String team,
				@QueryParam("name") String name)
		{
		TournamentListResponse returnValue = new TournamentListResponse();
		SearchTournamentParam p = new SearchTournamentParam();
		
		if(game != null)
			p.setGame(KeyFactory.stringToKey(game));
		
		if(team != null)
			p.setTeam(KeyFactory.stringToKey(team));
		
		if(name != null)
			p.setName(name);
		
		returnValue.setTournaments(DBManager.searchTournament(p));

		return returnValue;
	}

	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Tournament getTournament(@PathParam("id") String id) {

		Key key = KeyFactory.stringToKey(id);
		Tournament t = DBManager.getTournament(key);

		return t;

	}
	
	@POST
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public KeyResponse addTournament(Tournament tournament)
	{
		KeyResponse resp = new KeyResponse();
				
		try {
			DBManager.save(tournament);
			resp.setKey(KeyFactory.keyToString(tournament.getKey()));
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
	public BaseResponse editGame(@PathParam("id") String id, Tournament tournament) {
		
		BaseResponse resp = new BaseResponse();
		Key key = KeyFactory.stringToKey(id);
		try {
			Tournament tmpTournament = DBManager.getTournament(key);
			
			tmpTournament.setAddress(tournament.getAddress());
			tmpTournament.setCity(tournament.getCity());
			tmpTournament.setCountry(tournament.getCountry());
			tmpTournament.setEndDate(tournament.getEndDate());
			tmpTournament.setName(tournament.getName());
			tmpTournament.setPostalCode(tournament.getPostalCode());
			tmpTournament.setStartDate(tournament.getStartDate());
			
			DBManager.save(tmpTournament);
			
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
	public BaseResponse deleteTournament(@PathParam("id") String id) {
		BaseResponse response = new BaseResponse();
		
		try{
			Key key = KeyFactory.stringToKey(id);
			Tournament t = DBManager.getTournament(key);
			t.delete();
		}
		catch(Exception e)
		{
			response.setError("Error while deleting a tournament : " + e.getMessage());
		}
		
		return response;
	}
}
