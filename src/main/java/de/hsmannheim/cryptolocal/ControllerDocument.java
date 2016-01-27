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

	
	@RequestMapping( method=RequestMethod.GET  )
	public ResponseEntity<Document[]> find(){
		return null;
	}
	
	@RequestMapping(value="/{documentId}", method=RequestMethod.GET  )
	public ResponseEntity<Document> findOne( @PathVariable(value="documentId") Long documentId ){
		return null;
	}
	
	@RequestMapping( method=RequestMethod.POST )
	public ResponseEntity<?> create(@RequestParam("file") MultipartFile file) throws IOException{
		System.out.println( file.getOriginalFilename());
		return servicedoc.create(file);
	}

	@RequestMapping(value="/{documentId}/changeOwner", method=RequestMethod.POST  )
	public ResponseEntity<?> documentId_changeOwner( @PathVariable(value="documentId") Long documentId ){
		return null;
	}

	@RequestMapping(value="/{documentId}/shareDocument", method=RequestMethod.POST  )
	public ResponseEntity<?> documentId_shareDocument( @PathVariable(value="documentId") Long documentId ){
		return null;
	}

}
