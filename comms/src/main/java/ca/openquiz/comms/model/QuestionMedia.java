package ca.openquiz.comms.model;

public class QuestionMedia extends Question {
	private static final long serialVersionUID = -8894131134162956804L;

	private String mediaId;

	private String mediaUrl;
	
	private String answer;
	
	private String statement;
	
	public QuestionMedia() {
	}

	public QuestionMedia(String statement, String mediaId, String answer) {
		this.statement = statement;
		this.mediaId = mediaId;
		this.answer = answer;
	}
	
	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}
	
	public String getMediaUrl(){
		return mediaUrl;
	}
	
	public void setMediaUrl(String mediaUrl){
		this.mediaUrl = mediaUrl;
	}
	
	@Override
	public boolean isValid(){
		return super.isValid() 
				&& this.statement != null && !this.statement.isEmpty()
				&& this.answer != null && !this.answer.isEmpty();
	}
}