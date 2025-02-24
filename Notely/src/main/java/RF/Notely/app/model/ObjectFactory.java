package RF.Notely.app.model;

import jakarta.xml.bind.annotation.XmlRegistry;


@XmlRegistry
public class ObjectFactory {

    public ObjectFactory() {
    }

    public AuthenticationResult createVoti() {
        return new AuthenticationResult();
    }

}
