package de.hsmannheim.cryptolocal;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.parser.Part;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import de.hsmannheim.cryptolocal.models.Document;
import de.hsmannheim.cryptolocal.models.forms.Form_Document;
import de.hsmannheim.cryptolocal.repositories.impl.ServiceDocument;

@RestController
@RequestMapping(value = "/api/documents"  )
public class ControllerDocument {

	@Autowired
	ServiceDocument servicedoc;

	/*
	 * Gibt Dokumenteliste von benutzergroup 
	 */

	@RequestMapping( method= RequestMethod.GET  )
	public Set<Document> getAllDocuments( @PathVariable(value="groupId") Long groupid ){
		return servicedoc.listAlldocumentFromGroup(groupid);
	}



	@RequestMapping( value="/addDocument", method= RequestMethod.POST )
	public @ResponseBody
	void uploadDocumentToGroup( HttpServletRequest request, HttpServletResponse response ) throws IOException, ServletException
	{
		System.out.println("commm here");
		 final String path = request.getParameter("destination");
		  final  javax.servlet.http.Part filePart = request.getPart("file");
		  final String fileName = request.getParameter("filename");
		System.out.println(fileName);

		  OutputStream out = null;
		  InputStream fileContent = null;
		  final PrintWriter writer = response.getWriter();

		  try {
//		    out = new FileOutputStream(new File(path + File.separator
//		            + fileName));

		    out = new FileOutputStream(new File(fileName));
		    fileContent = filePart.getInputStream();

		    int read = 0;
		    final byte[] bytes = new byte[1024];

		    while ((read = fileContent.read(bytes)) != -1) {
		      out.write(bytes, 0, read);
		    }

		    writer.println("New file " + fileName + " created at " + path);

		  } catch (FileNotFoundException fne) {
		    writer.println("You either did not specify a file to upload or are "
		            + "trying to upload a file to a protected or nonexistent "
		            + "location.");
		    writer.println("<br/> ERROR: " + fne.getMessage());

		  } finally {
		    if (out != null) {
		      out.close();
		    }
		    if (fileContent != null) {
		      fileContent.close();
		    }
		    if (writer != null) {
		      writer.close();
		    }
		  }

	}


	/*
	 * Schiebe Datei von GruppeA -> GruppeB
	 */
	public void changeFileOwner(){
		
	}


}
