package ch.ocn.OCNEval;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ch.ocn.OCNEval.controler.Pdf;

@Path("pdf")
public class PdfResource {
	
	public PdfResource() {
		
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response generatePdf(String infos) throws SQLException, IOException {
		Pdf.generate(infos);
		return Response.status(200).build();
	}
}
