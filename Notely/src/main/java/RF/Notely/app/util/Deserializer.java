package RF.Notely.app.util;

import RF.Notely.app.model.ResponseMethod;

public class Deserializer {

	public static <T> T deserialize(Class<T> _class, String content, ResponseMethod context) throws Exception {
//		System.out.println("Deserializing: " + content);
		if(context.getMode() == ResponseMethod.Mode.XML_REQUEST_METHOD) {
			return (T)XmlUtils.unmarshal(_class, content);
		}else {
			return (T)JSONUtils.unmarshal(_class, content);
			
		}
	}

}
