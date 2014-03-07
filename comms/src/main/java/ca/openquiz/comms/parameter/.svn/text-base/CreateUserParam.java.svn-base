package ca.openquiz.comms.parameter;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonRootName;

import ca.openquiz.comms.enums.Language;

@JsonRootName("newUser")
public class CreateUserParam {

	@JsonProperty
	private Date birthDate;

	private String email;

	private String firstName;

	private String lastName;

	private Language language;

	private String password;
	

	public CreateUserParam() {

	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@JsonIgnore
	public boolean isValid() {

		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
		Matcher m = p.matcher(this.email);

		if (this.birthDate != null 
				&& this.email != null && !this.email.isEmpty() && m.matches()
				&& !this.firstName.isEmpty() && this.firstName != null
				&& !this.lastName.isEmpty() && this.lastName != null
				&& this.language != null 
				&& this.password != null && this.password.length() >= 6) {
			return true;
		}
		return false;
	}
}