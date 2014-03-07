package ca.openquiz.comms.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import ca.openquiz.comms.enums.Degree;
import ca.openquiz.comms.enums.Language;
import ca.openquiz.comms.enums.ReportReason;

public abstract class Question extends BaseModel {
	private static final long serialVersionUID = 1560562217756202325L;

	private String authorKey;

	private Boolean isReported = false;

	protected int correctAnswer = 0;

	protected int attemptedAnswer = 0;
	
	@JsonProperty
	protected Degree degree;
	
	@JsonProperty
	protected Date availableDate = new Date();
	
	@JsonProperty
	protected Language language;
	
	@JsonProperty
	protected Category category;
	
	protected String reportComment;
	
	@JsonProperty
	protected ReportReason reportReason = ReportReason.None;
	
	@JsonProperty
	protected List<String> groupKeys = new ArrayList<String>();

	public Question() {
	}

	@JsonProperty("author")
	public String getAuthorKey() {
		return authorKey;
	}

	public void setAuthorKey(String author) {
		this.authorKey = author;
	}

	public int getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(int correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public int getAttemptedAnswer() {
		return attemptedAnswer;
	}

	public void setAttemptedAnswer(int attemptedAnswer) {
		this.attemptedAnswer = attemptedAnswer;
	}

	public Boolean getIsReported() {
		return isReported;
	}

	public void setIsReported(Boolean isReported) {
		this.isReported = isReported;
	}

	public Degree getDegree() {
		return degree;
	}
	
	public void setDegree(Degree degree) {
		this.degree = degree;
	}

	public Date getAvailableDate() {
		return this.availableDate;
	}

	public void setAvailableDate(Date availableDate) {
		this.availableDate = availableDate;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}
	
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	public ReportReason getReportReason() {
		return reportReason;
	}

	public void setReportReason(ReportReason reportReason) {
		this.reportReason = reportReason;
	}
	
	public String getReportComment() {
		return reportComment;
	}

	public void setreportComment(String reportComment) {
		this.reportComment = reportComment;
	}

	@JsonProperty("groups")
	public List<String> getGroupKeys() {
		return groupKeys;
	}

	@JsonIgnore
	public boolean isValid(){
		return this.availableDate != null && this.category != null && this.degree != null && this.language != null;
	}
	
}