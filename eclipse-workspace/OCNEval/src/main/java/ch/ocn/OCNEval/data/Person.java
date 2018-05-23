package ch.ocn.OCNEval.data;

public class Person {
	private int id;
	private String firstname;
	private String name;
	private String sector;
	private String responsable;
	private String function;
	private String dateFunction;
	private String language;
	private String validityDate;
	
	public Person() {
		
	}

	public Person(int id, String firstname, String name, String sector, String responsable, String function,
			String dateFunction, String language, String validityDate) {
		this.id = id;
		this.firstname = firstname;
		this.name = name;
		this.sector = sector;
		this.responsable = responsable;
		this.function = function;
		this.dateFunction = dateFunction;
		this.language = language;
		this.validityDate = validityDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public String getDateFunction() {
		return dateFunction;
	}

	public void setDateFunction(String dateFunction) {
		this.dateFunction = dateFunction;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getValidityDate() {
		if (validityDate == null) {
			validityDate = "NULL";
		}
		else {
			validityDate = "'" + validityDate + "'";
		}
		
		return validityDate;
	}

	public void setValidityDate(String validityDate) {
		this.validityDate = validityDate;
	}
}
