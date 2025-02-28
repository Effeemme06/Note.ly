package RF.Notely.app.model;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "title", "description", "notes"
})
@XmlRootElement(name = "NotePad")
public class NotePad {

	@XmlElement(name = "title")
	protected String title;
	@XmlElement(name = "description")
	protected String description;
    @XmlElement(name = "Note")
    protected List<Note> notes;
    @XmlAttribute
    protected Integer id;

    
    public List<Note> getNotes() {
        if (notes == null) {
        	notes = new ArrayList<Note>();
        }
        return this.notes;
    }
    
    public int getID() {
    	return this.id;
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
