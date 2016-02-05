package de.hsmannheim.cryptolocal;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.hsmannheim.cryptolocal.models.KeyPair;
import de.hsmannheim.cryptolocal.models.User;
import de.hsmannheim.cryptolocal.repositories.RepositoryUsers;

@RestController
@RequestMapping(value = "/api/{userId}/keypair", produces = MediaType.APPLICATION_JSON_VALUE )
public class ControllerKeyPair {
	
	@Autowired
	RepositoryUsers repositoryuser;


	@RequestMapping( method= RequestMethod.GET )
	public ResponseEntity<KeyPair>  userpublickey( @PathVariable(value="userId") Long userid ){
		User user =  repositoryuser.findOne( userid );
		KeyPair ret = user.getKeypair();

		if(ret != null  ) 
			return new ResponseEntity<KeyPair>( ret, HttpStatus.OK); 
			return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
	}
	

}
