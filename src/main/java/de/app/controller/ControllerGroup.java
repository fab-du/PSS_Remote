package de.app.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.Exception;
import java.net.URLConnection;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import de.app.repositories.RepositoryDocuments;
import de.app.services.ServiceGroup;
import de.app.model.Document;
import de.app.model.Group;
import de.app.model.User;
import de.app.model.KeySym;

@RestController
@RequestMapping(value = "/api/groups", produces = MediaType.APPLICATION_JSON_VALUE )
public class ControllerGroup {

	@Autowired
	private ServiceGroup servicegroup;
	
	@Autowired
	private RepositoryDocuments repositorydoc;

	@RequestMapping( method= RequestMethod.GET )
	public ResponseEntity<List<Group>>  find( ){
		return servicegroup.find();
	}
	
	
	@RequestMapping(value="/{groupId}", method= RequestMethod.GET )
	public ResponseEntity<Group>  findOne( @PathVariable(value="groupId") Long groupId){
		return servicegroup.findOne(groupId);
	}

	@RequestMapping( method= RequestMethod.POST )
	public ResponseEntity<?>  create( @Validated @RequestBody Group group){
		return servicegroup.create(group, true);
	}

	@RequestMapping(value="/{groupId}/users", method= RequestMethod.GET )
    @ResponseBody 
	public Set<User>  users( @PathVariable(value="groupId") Long groupId ) throws Exception{
		return servicegroup.findMitgliederByGroupId(groupId);
	}
	
	@RequestMapping( value="/{groupId}/users/{userId}", method = RequestMethod.POST )
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> addUser( @PathVariable(value="groupId") Long groupId, @PathVariable( value="userId") Long userId, @Validated @RequestBody KeySym symkey ){
		return servicegroup.addUser(userId, groupId, symkey);
	}
	
	@RequestMapping( value="/users/{userId}", method= RequestMethod.GET )
	public ResponseEntity<List<Group>>  find_where_user_group(@PathVariable(value="userId") Long userId ){
		return servicegroup.findWhereUserIsGV(userId);
	}
	
	@RequestMapping( value="/{groupId}/documents", method = RequestMethod.GET)
	public ResponseEntity<Set<Document>> documents( @PathVariable(value="groupId") Long groupId){
		return servicegroup.documents(groupId);
	}
	
	@RequestMapping( value="/{groupId}/documents", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> addDocument( @PathVariable(value="groupId") Long groupId, @Validated @RequestParam("file") MultipartFile file ) throws IOException{
		return servicegroup.addDocument(groupId, file);
	}
	
	@RequestMapping( value="/{groupId}/documents/{documentId}", method = RequestMethod.GET )
	public ResponseEntity<Document> groupId_documents_documentId( @PathVariable(value="groupId") Long groupId,
			@PathVariable(value="documentId") Long documentId){
		System.out.println("comme hr");
		return servicegroup.groupId_documents_documentId(groupId, documentId);
	}
	
	@RequestMapping( value="/{groupId}/documents/{documentId}/download/{document}", method = RequestMethod.GET )
	public ResponseEntity<InputStreamResource> groupId_documents_documentId_download( @PathVariable(value="groupId") Long groupId,
			@PathVariable(value="documentId") Long documentId ) throws IOException, NullPointerException{
		
		Document doc = repositorydoc.findOne(documentId);
		File file = new File( "uploads/" + doc.getPath() + "/" + doc.getName() );
		
		String mime = URLConnection.guessContentTypeFromName(file.getName());
		HttpHeaders headers = new HttpHeaders();
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");
		headers.setContentType( MediaType.parseMediaType(mime));
		headers.setContentDispositionFormData("attachment", file.getName());
		InputStreamResource	isr = new InputStreamResource(new FileInputStream(file));
		return ResponseEntity
				.ok()
				.headers(headers)
				.contentLength(file.length())
				.body( isr);
	}
	
	//TODO
	@RequestMapping( value="/{groupId}/documents/{documentId}/shareDocument", method = RequestMethod.POST )
	public ResponseEntity<?> groupId_documents_documentId_shareDocument( @PathVariable(value="groupId") Long groupId,
			@PathVariable(value="documentId") Long documentId){
		return null;
	}	
	
}
