package ca.openquiz.webservice.model;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import ca.openquiz.webservice.converter.KeyConverter;
import ca.openquiz.webservice.manager.DBManager;
import ca.openquiz.webservice.parameter.SearchGroupRoleParam;
import ca.openquiz.webservice.parameter.SearchTeamParam;
import ca.openquiz.webservice.parameter.SearchTournamentParam;

import com.google.appengine.api.datastore.Key;

import java.util.ArrayList;
import java.util.List;


@XmlRootElement
@PersistenceCapable(detachable="true")
public class Group extends BaseModel {

	@Persistent
	private String name;

	@Persistent
	@XmlJavaTypeAdapter(KeyConverter.class)
	private List<Key> questions = new ArrayList<Key>();

	@Persistent
	@XmlJavaTypeAdapter(KeyConverter.class)
	private List<Key> teams = new ArrayList<Key>();

	@Persistent
	@XmlJavaTypeAdapter(KeyConverter.class)
	private List<Key> tournaments = new ArrayList<Key>();

	public Group() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Key> getQuestions() {
		return questions;
	}

	public List<Key> getTeams() {
		return teams;
	}

	public List<Key> getTournaments() {
		return tournaments;
	}

	public void addGroupRoles(GroupRole groupRole)
	{
		if (groupRole.getKey() == null)
			DBManager.save(groupRole);

		if (this.key == null)
			DBManager.save(this);

		//		this.groupRoles.add(groupRole.getKey());
		groupRole.setGroup(this.getKey());

		DBManager.save(groupRole);
		DBManager.save(this);
	}

	public void removeGroupRoles(GroupRole groupRole)
	{
		SearchGroupRoleParam param = new SearchGroupRoleParam();
		param.setGroup(key);
		for(GroupRole gr: DBManager.searchGroupRole(param))
		{
			if(gr.getKey().equals(groupRole.getKey()))
			{
				groupRole.delete();
				DBManager.save(this);
			}
		}

		//		if(DBManager.searchGroupRole(param).contains(groupRole.getKey()))
		{
			//			this.groupRoles.remove(groupRole.getKey());
			//			groupRole.delete();
			//			
			//			DBManager.save(this);
		}
	}

	public void addTeam(Team team)
	{
		if(team.getGroup() == null){
			team.setGroup(this);
		}
		
		if (team.getKey() == null)
			DBManager.save(team);
		
		if(!this.teams.contains(team.getKey())){
			this.teams.add(team.getKey());
			DBManager.save(this);
		}
	}

	public void removeTeam(Team team)
	{
		if (this.teams.contains(team.getKey()))
		{
			this.teams.remove(team.getKey());
			team.setActive(false);

			DBManager.save(team);
			DBManager.save(this);
		}
	}

	public void addTournament(Tournament tournament)
	{
		if (tournament.getKey() == null)
			DBManager.save(tournament);

		if (this.key == null)
			DBManager.save(this);

		this.tournaments.add(tournament.getKey());
		tournament.setGroup(this.getKey());

		DBManager.save(tournament);
		DBManager.save(this);
	}

	public void removeTournament(Tournament tournament)
	{
		if (this.tournaments.contains(tournament.getKey()))
		{
			this.tournaments.remove(tournament.getKey());
			tournament.setGroup(null);

			DBManager.save(tournament);
			DBManager.save(this);
		}
	}

	public void addQuestion(Question question) {
		if (question.getKey() == null) {
			DBManager.save(question);
		}

		if (this.key == null) {
			DBManager.save(this);
		}

		this.questions.add(question.getKey());
		question.getGroups().add(this.getKey());

		DBManager.save(question);
		DBManager.save(this);
	}

	public void removeQuestion(Question question)
	{
		if (this.questions.contains(question.getKey()))
		{
			this.questions.remove(question.getKey());
			question.getGroups().remove(this.getKey());

			DBManager.save(question);
			DBManager.save(this);
		}
	}

	@Override
	public void delete() {

		//TODO delete Question

		SearchTeamParam searchParam = new SearchTeamParam();
		searchParam.setGroup(this.getKey());

		for(Team t : DBManager.searchTeams(searchParam))
		{
			this.getTeams().remove(t.getKey());
			t.setGroup((Group)null);
		}

		SearchGroupRoleParam param = new SearchGroupRoleParam();
		param.setGroup(this.getKey());	
		for(GroupRole gr : DBManager.searchGroupRole(param))
		{
			//			this.getGroupRoles().remove(gr.getKey());
			gr.delete();
		}

		SearchTournamentParam tournamentParam = new SearchTournamentParam();
		tournamentParam.setGroup(this.getKey());

		for(Tournament t : DBManager.searchTournament(tournamentParam))
		{
			this.getTournaments().remove(t.getKey());
			t.removeGroup();
		}

		DBManager.save(this);

		DBManager.delete(this.getKey());
		key = null;
	}

	@Override
	public boolean isValid() {
		return name != null && !name.isEmpty();
	}
}