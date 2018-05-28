package ch.ocn.OCNEval.data;

//Modèle Section
public class Section {
	int id;
	String name;
	String description;
	String path;
	String validityDate;
	
	public Section() {
		
	}

	public Section(int id, String name, String description, String path, String validityDate) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.path = path;
		this.validityDate = validityDate;
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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getValidityDate() {
		return validityDate;
	}

	public void setValidityDate(String validityDate) {
		this.validityDate = validityDate;
	}
}
