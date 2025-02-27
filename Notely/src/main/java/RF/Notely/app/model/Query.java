package RF.Notely.app.model;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

import org.json.JSONObject;

public class Query {
	
	private String route;
	private Map<String, Object> parameters;
	private RequestMethod requestMethod;
	
	public Query() {
		this.requestMethod = RequestMethod.GET;
		this.parameters = new HashMap<String, Object>();
	}
	
	public Query(RequestMethod requestMethod) {
		this.requestMethod = requestMethod;
		this.parameters = new HashMap<String, Object>();
	}
	
	public Query setRoute(String route) {
		this.route = route;
		return this;
	}
	
	public Query setRequestMethod(RequestMethod method) {
        this.requestMethod = method;
		return this;
    }
	
	public Query addResponseMethod(ResponseMethod RSP_MTHD) {
		if(!this.parameters.containsKey("mod")) {
			this.parameters.put("mod", (RSP_MTHD.getMode().equals(ResponseMethod.Mode.XML_REQUEST_METHOD) ? "xml" : "json"));
		}
		return this;
	}
	
	public Query addToken(Authenticator AUTH) {
		if(!this.parameters.containsKey("token")) {
			this.parameters.put("token", AUTH.getToken());
		}
		return this;
	}

	public Query addParameter(String key, Object value) {
		if(!this.parameters.containsKey("mod")) {
			this.parameters.put(key, String.valueOf(value));
		}
		return this;
	}
	
	public String build() {
		if (parameters.isEmpty()) {
	        return "";
	    }

	    if (requestMethod == RequestMethod.GET || requestMethod == RequestMethod.POST) {
	        StringJoiner joiner = new StringJoiner("&");
	        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
	            joiner.add(entry.getKey() + "=" + entry.getValue());
	        }
	        return (requestMethod == RequestMethod.GET) ? "?" + route + "&" + joiner : route + "&" + joiner.toString();
	    } else if (requestMethod == RequestMethod.PUT || requestMethod == RequestMethod.DELETE) {
	        JSONObject json = new JSONObject();
	        json.put("route", route);
	        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
	            json.put(entry.getKey(), entry.getValue());
	        }
	        return json.toString();
	    } 
	    
	    return "";
	}


}
