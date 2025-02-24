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
	@XmlElement(name = "message")
	public String message;
	@XmlElement(name = "error")
	public String error;
	
	public AuthenticationResult() {
		success = false;
		userID = -1;
		message = "";
		error = "";
	}
	
	public AuthenticationResult(Boolean success, Integer userID, String message, String error) {
		this.success = success;
		this.userID = userID;
		this.message = message;
		this.error = error;
	}
	
}