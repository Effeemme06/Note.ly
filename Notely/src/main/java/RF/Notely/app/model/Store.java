package RF.Notely.app.model;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "notePads"
})
@XmlRootElement(name = "Store")
public class Store {

    @XmlElement(name = "NotePad")
    protected List<NotePad> notePads;

    
    public List<NotePad> getNotePads() {
        if (notePads == null) {
        	notePads = new ArrayList<NotePad>();
        }
        return this.notePads;
    }


	@Override
	public String toString() {
		return "Store [notepads=" + notePads + "]";
	}
    
    

}
