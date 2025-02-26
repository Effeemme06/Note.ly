package RF.Notely.app.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "AuthenticationResults")
public class AuthenticationResult {
	
	@XmlElement(name = "success")
	public Boolean success;
	@XmlElement(name = "userID")
	public Integer userID;
	@XmlElement(name = "token")
	public String token;
	@XmlElement(name = "message")
	public String message;
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