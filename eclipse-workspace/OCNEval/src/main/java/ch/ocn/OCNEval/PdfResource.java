package ch.ocn.OCNEval;

import java.io.IOException;
import java.sql.SQLException;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ch.ocn.OCNEval.pdf.Pdf;

//Si le chemin de l'URL est webapi/pdf, cette classe sera utilisée
@Path("pdf")
public class PdfResource {
	
	//Constructeur par défaut
	public PdfResource() {
		
	}
	
	//Méthode permettant de lancer la génération d'un PDF avec les infos venant du client (prénom de la personne et nom du profil)
	//Cette méthode sera effectuée en cas de requête POST et prend des données sous format JSON
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response generatePdf(String infos) throws SQLException, IOException {
		Pdf.generate(infos); //Lance la méthode de génération du PDF avec les informations contenues dans la requête
		return Response.status(200).build(); //Renvoie un code HTTP dans la réponse
	}
}
