package RF.Notely.app.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Note", propOrder = {
		"title",
		"body"
})
public class Note {

	@XmlAttribute(required = true)
	protected Integer id;
	
	@XmlElement(required = true)
	protected String title;
	
	@XmlElement(required = true)
	protected String body;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "Note [id=" + id + ", title=" + title + ", body=" + body + "]";
	}
	
	

}
