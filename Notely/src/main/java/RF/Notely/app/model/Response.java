package RF.Notely.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Response")
public class Response {
    
	@JsonProperty("statusCode")
    @XmlElement(name = "statusCode")
    private int statusCode;
    
	@JsonProperty("result")
    @XmlElement(name = "result")
    private Object result;
    
	@JsonProperty("error")
    @XmlElement(name = "error")
    private Object error;

    public Response() {
        this.statusCode = 500;
        this.result = null;
        this.error = null;
    }

    public Response(int statusCode, Object result) {
        this.statusCode = statusCode;
        this.result = result;
        this.error = null;
    }

    public Response(int statusCode, Object error, boolean isError) {
        this.statusCode = statusCode;
        this.result = null;
        this.error = error;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }
}
