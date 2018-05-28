package ch.ocn.OCNEval.data;

import java.util.List;

//Modèle Profile
public class Profile {
	private int id;
	private String name;
	private String description;
	private String validityDate;
	private List<Section> sections;
	
	public Profile() {
		
	}
	
	public Profile(int id, String name, String description, String validityDate, List<Section> sections) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.validityDate = validityDate;
		this.sections = sections;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getValidityDate() {
		return validityDate;
	}
	
	public void setValidityDate(String validityDate) {
		this.validityDate = validityDate;
	}
	
	public List<Section> getSections() {
		return sections;
	}
	
	public void setSections(List<Section> sections) {
		this.sections = sections;
	}
}
