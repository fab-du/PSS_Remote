package de.hsmannheim.cryptolocal;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import de.hsmannheim.cryptolocal.models.Document;
import de.hsmannheim.cryptolocal.models.Group;
import de.hsmannheim.cryptolocal.models.User;
import de.hsmannheim.cryptolocal.repositories.impl.ServiceGroup;

@RestController
@RequestMapping(value = "/api/groups", produces = MediaType.APPLICATION_JSON_VALUE )
public class ControllerGroup {

	@Autowired
	private ServiceGroup servicegroup;

	@RequestMapping( method= RequestMethod.GET )
	public ResponseEntity<List<Group>>  find( ){
		return servicegroup.find();
	}
	
	@RequestMapping(value="/{groupId}", method= RequestMethod.GET )
	public ResponseEntity<Group>  findOne( @PathVariable(value="groupId") Long groupId){
		return servicegroup.findOne(groupId);
	}

	@RequestMapping( method= RequestMethod.POST )
	public ResponseEntity<?>  create( @RequestBody Group group){
		System.out.println( group.toString());
		return servicegroup.create(group, true);
	}

	@RequestMapping(value="/{groupId}/users", method= RequestMethod.GET )
	public ResponseEntity<Set<User>>  users( @PathVariable(value="groupId") Long groupid ){
		return servicegroup.users(groupid);
	}
	
	@RequestMapping( value="/{groupId}/users", method = RequestMethod.POST )
	public ResponseEntity<?> addUser( @PathVariable(value="groupId") Long groupId,@RequestBody User user ){
		return servicegroup.addUser(user, groupId);
	}
	
	@RequestMapping( value="/{groupId}/documents", method = RequestMethod.GET)
	public ResponseEntity<Set<Document>> documents( @PathVariable(value="groupId") Long groupId){
		return servicegroup.documents(groupId);
	}
	
	@RequestMapping( value="/{groupId}/documents", method = RequestMethod.POST)
	public ResponseEntity<?> addDocument( @PathVariable(value="groupId") Long groupId,@RequestParam("file") MultipartFile file ) throws IOException{
		return servicegroup.addDocument(groupId, file);
	}
	
	@RequestMapping( value="/{groupId}/documents/{documentId}", method = RequestMethod.GET )
	public ResponseEntity<Document> groupId_documents_documentId( @PathVariable(value="groupId") Long groupId,
			@PathVariable(value="documentId") Long documentId){
		System.out.println("comme hr");
		return servicegroup.groupId_documents_documentId(groupId, documentId);
	}
	
	//TODO
	@RequestMapping( value="/{groupId}/documents/{documentId}/shareDocument", method = RequestMethod.POST )
	public ResponseEntity<?> groupId_documents_documentId_shareDocument( @PathVariable(value="groupId") Long groupId,
			@PathVariable(value="documentId") Long documentId){
		return null;
	}	
	
}
