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
    "NotePad"
})
@XmlRootElement(name = "NotePad")
public class NotePad {

    @XmlElement(name = "Note")
    protected List<Note> notes;

    
    public List<Note> getBooks() {
        if (notes == null) {
        	notes = new ArrayList<Note>();
        }
        return this.notes;
    }


	@Override
	public String toString() {
		return "Books [books=" + notes + "]";
	}
    
    

}
