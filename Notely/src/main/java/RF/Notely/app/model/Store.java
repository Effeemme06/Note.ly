package RF.Notely.app.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

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

	@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	@JsonProperty("NotePad")
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
