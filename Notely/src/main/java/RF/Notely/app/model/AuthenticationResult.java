package RF.Notely.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "AuthenticationResults")
public class AuthenticationResult {
	
	@JsonProperty("success")
	@XmlElement(name = "success")
	public Boolean success;
	@JsonProperty("userID")
	@XmlElement(name = "userID")
	public Integer userID;
	@JsonProperty("token")
	@XmlElement(name = "token")
	public String token;
	@JsonProperty("message")
	@XmlElement(name = "message")
	public String message;
	@JsonProperty("error")
	@XmlElement(name = "error")
	public String error;
	
	public AuthenticationResult() {
		success = false;
		userID = -1;
		message = "";
		error = "";
		token = "";
	}
	
	public AuthenticationResult(Boolean success, Integer userID, String token, String message, String error) {
		this.success = success;
		this.userID = userID;
		this.token = token;
		this.message = message;
		this.error = error;
	}
	
}