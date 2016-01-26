package de.hsmannheim.cryptolocal;

import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
	public ResponseEntity<?> addDocument( @PathVariable(value="groupId") Long groupId, @RequestBody Document document){
		return servicegroup.addDocument(groupId, document);
	}

//	@RequestMapping(value="/{gv}",  method= RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_VALUE )
//	public ResponseEntity<Iterable<Group>>  createGroup( @RequestBody @Valid Group newgroup,
//		@PathVariable( value="gv") Long gv){
//
//
//		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
//		Set<ConstraintViolation<Group>> constraintViolations = validator.validate( newgroup);
//
//		if( constraintViolations.size() > 0 ) return
//				new ResponseEntity<Iterable<Group>>(HttpStatus.EXPECTATION_FAILED); 
//
//		boolean ret = servicegroup.save(newgroup, gv , true);
//		if(ret) return new ResponseEntity<Iterable<Group>>(HttpStatus.CREATED); 
//				return new ResponseEntity<Iterable<Group>>(HttpStatus.CONFLICT); 
//	}

}
