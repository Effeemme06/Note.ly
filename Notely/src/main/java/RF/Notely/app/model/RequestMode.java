package RF.Notely.app.model;

public class RequestMode {
	
	public Mode mode;
	
	public RequestMode() {
		mode = Mode.XML_REQUEST_METHOD;
	}
	
	public RequestMode(Mode mode) {
		this.mode = mode;
	}
	
	public Mode getMode() {
		return this.mode;
	}
	
	public void swap() {
		if(mode.equals(Mode.XML_REQUEST_METHOD)) {
			mode = Mode.JSON_REQUEST_METHOD;
		}else {
			mode = Mode.XML_REQUEST_METHOD;
		}
	}
	
	public enum Mode {
		
		XML_REQUEST_METHOD(1), JSON_REQUEST_METHOD(2);
		
		public Integer type;
		
		private Mode(Integer type) {
			this.type = type;
		}
		
		@Override
		public String toString() {
			return "&mod=" + ((this == XML_REQUEST_METHOD) ? "xml" : "json");
		}
		
	}

}
