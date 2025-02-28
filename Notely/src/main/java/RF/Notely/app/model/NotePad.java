package RF.Notely.app.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "id", "title", "description", "notes"
})
@XmlRootElement(name = "NotePad")
public class NotePad {
	
	@XmlTransient
	@JsonProperty("@attributes")
	private Attributes attributes;

	@JsonProperty("title")
	@XmlElement(name = "title")
	protected String title;
	
	@JsonProperty("description")
	@XmlElement(name = "description")
	protected String description;
	
	@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	@JsonProperty("Note")
    @XmlElement(name = "Note")
    protected List<Note> notes;
	
    @XmlAttribute(name= "id")
    protected Integer id;

    
    public List<Note> getNotes() {
        if (notes == null) {
        	notes = new ArrayList<Note>();
        }
        return this.notes;
    }
    
    public int getID() {
    	return this.id != null ? this.id : attributes.id;
    }

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return "NotePad [notes=" + notes + "]";
	}
    

}
