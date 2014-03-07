package ca.openquiz.webservice.model;

import javax.jdo.annotations.PersistenceCapable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@PersistenceCapable
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Choice extends BaseModel {

	public Choice()
	{}
	
	public Choice(String choice, String answer)
	{
		this.choice = choice;
		this.answer = answer;
	}
	
	private String choice;
	
	private String answer;
	
	public String getChoice() {
		return choice;
	}

	public void setChoice(String choice) {
		this.choice = choice;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}
	
	public boolean isValid() {
		return this.choice != null && !this.choice.isEmpty()
				&& this.answer != null && !this.answer.isEmpty();
	}
	
}
