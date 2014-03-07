package ca.openquiz.webservice.response;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import ca.openquiz.webservice.model.Tournament;

@XmlRootElement
public class TournamentListResponse extends BaseResponse  {

	@XmlElement
	private List<Tournament> tournaments = new ArrayList<Tournament>();

	@XmlTransient
	public List<Tournament> getTournaments() {
		return tournaments;
	}

	public void setTournaments(List<Tournament> tournaments) {
		this.tournaments = tournaments;
	}
}
