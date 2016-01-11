package de.hsmannheim.cryptolocal;



import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.hsmannheim.cryptolocal.models.Group;
import de.hsmannheim.cryptolocal.models.User;
import de.hsmannheim.cryptolocal.repositories.RepositoryKeyPair;
import de.hsmannheim.cryptolocal.repositories.RepositoryUsers;

@RestController
@RequestMapping(value = "/api/keypair", produces = MediaType.APPLICATION_JSON_VALUE )
public class ControllerKeyPair {
	
	@Autowired
	RepositoryUsers repositoryuser;


	@RequestMapping( value="/{userId}", method= RequestMethod.GET )
	public ResponseEntity<String>  userpublickey( @PathVariable(value="userId") Long userid ){
		User user =  repositoryuser.findOne( userid );
		String ret = user.getKeypair().getPubkey();

		if(ret != null  ) 
			return new ResponseEntity<String>( ret, HttpStatus.OK); 
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT); 
	}
	

}
