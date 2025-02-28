package RF.Notely.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Note", propOrder = {
		"id",
		"title",
		"body"
})
public class Note {

	@XmlTransient
	@JsonProperty("@attributes")
	private Attributes attributes;
	
	@XmlAttribute(required = true, name = "id")
	protected Integer id;
	
	@JsonProperty("title")
	@XmlElement(required = true)
	protected String title;
	
	@JsonProperty("body")
	@XmlElement(required = true)
	protected String body;

	public Integer getId() {
		return id != null ? id : attributes.id;
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
