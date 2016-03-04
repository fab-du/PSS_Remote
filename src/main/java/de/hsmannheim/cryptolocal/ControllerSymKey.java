package de.hsmannheim.cryptolocal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.hsmannheim.cryptolocal.models.Group;
import de.hsmannheim.cryptolocal.models.KeySym;
import de.hsmannheim.cryptolocal.repositories.RepositoryGroup;

@RestController
public class ControllerSymKey {

	@Autowired
	RepositoryGroup repositoryGroup;
	
	@RequestMapping(value="/api/{groupId}/keysym", method= RequestMethod.GET )
	public ResponseEntity<KeySym>  getGroupSymKey( @PathVariable(value="groupId") Long groupId ){
		Group group = repositoryGroup.findOne(groupId);
		System.out.println("=============================");
		System.out.println( group.getGroupkey().toString() );
		return new ResponseEntity<KeySym>( group.getGroupkey(), HttpStatus.OK  );
	}
	
}
