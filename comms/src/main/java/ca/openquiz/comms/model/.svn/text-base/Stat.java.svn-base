package ca.openquiz.comms.model;

public class Stat extends BaseModel{
	private static final long serialVersionUID = -4487869340635017372L;

	private int attempts;
	
	private int correctAnswers;
	
	private int penalties;
	
	private int points;
	
	public Stat(){
	}
	
	public Stat(int attempts, int correctAnswers, int penalties, int points){
		this.attempts = attempts;
		this.correctAnswers = correctAnswers;
		this.penalties = penalties;
		this.points = points;
	}

	public int getAttempts() {
		return attempts;
	}

	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}

	public int getCorrectAnswers() {
		return correctAnswers;
	}

	public void setCorrectAnswers(int correctAnswers) {
		this.correctAnswers = correctAnswers;
	}

	public int getPenalties() {
		return penalties;
	}

	public void setPenalties(int penalties) {
		this.penalties = penalties;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public boolean isValid() {
		return this.attempts > 0
			&& this.correctAnswers > 0
			&& this.penalties > 0
			&& this.points > 0;
	}
}
