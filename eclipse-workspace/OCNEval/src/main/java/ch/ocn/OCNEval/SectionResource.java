package ch.ocn.OCNEval;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import ch.ocn.OCNEval.data.Profile;
import ch.ocn.OCNEval.data.Section;
import ch.ocn.OCNEval.sql.SqlRequest;

@Path("sections")
public class SectionResource {
	
	public SectionResource() {
		
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSectionsJson() throws SQLException {
		String request = "SELECT * FROM section WHERE valid='1';";
		
		return Response.status(Response.Status.OK).entity(SqlRequest.requestSections(request)).build(); 
	}
	
	@GET
	@Path("{sectionId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSectionJson(@PathParam("sectionId") String sectionId) throws SQLException {
		String request = "SELECT * FROM section WHERE id='" + sectionId + "' AND valid = '1';";
		
		return Response.status(Response.Status.OK).entity(SqlRequest.requestSection(request)).build();
	}
	
	@PUT
	@Path("{sectionId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response modifySection(@PathParam("sectionId") String sectionId, String sectionJson) throws SQLException {
		Gson gson = new Gson();
		Section section = gson.fromJson(sectionJson, Section.class);
		if (section.getValidityDate().equals("")) {
			section.setValidityDate("NULL");
		} else {
			section.setValidityDate("'" + section.getValidityDate() + "'");
		}
		String request = "UPDATE section SET name = '" + section.getName() + "', description = '" + section.getDescription() + "', path = '" + section.getPath() + "', validity_date = " + section.getValidityDate() + " WHERE id = '" + sectionId + "';";
		SqlRequest.modifySection(request);
		
		return Response.status(200).build();
	}
	
	@DELETE
	@Path("{sectionId}")
	public Response deleteSection(@PathParam("sectionId") String sectionId) throws SQLException {
		String request = "UPDATE section SET valid = '0' WHERE id ='" + sectionId + "';";
		SqlRequest.deleteSection(request);
		
		return Response.status(200).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addSection(String sectionJson) throws SQLException {
		Gson gson = new Gson();
		Section section = gson.fromJson(sectionJson, Section.class);
		if (section.getValidityDate().equals("")) {
			section.setValidityDate("NULL");
		} else {
			section.setValidityDate("'" + section.getValidityDate() + "'");
		}
		String request = "INSERT INTO section VALUES (NULL, '" + section.getName() + "', '" + section.getDescription() + "', '" + section.getPath() + "', "+ section.getValidityDate() + ", 1);";
		SqlRequest.addSection(request);
		
		return Response.status(200).build();
	}
	
	@POST
	@Path("{profileId}")
	public Response addSectionToProfile(@PathParam("profileId") String profileId, String sectionNameJson) throws SQLException {
		Gson gson = new Gson();
		Section section = gson.fromJson(sectionNameJson, Section.class);
		String requestSectionId = "SELECT * FROM section WHERE name='" + section.getName() + "';";
		Section section2 = SqlRequest.requestSection(requestSectionId);
		String requestBind = "INSERT INTO profile_section_junction VALUES(" + profileId + ", " + section2.getId() + ");"; 
		SqlRequest.bindSectionToProfile(requestBind);
		
		return Response.status(200).build();
	}
}
