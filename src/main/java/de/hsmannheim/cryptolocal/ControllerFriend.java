package de.hsmannheim.cryptolocal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.hsmannheim.cryptolocal.models.User;
import de.hsmannheim.cryptolocal.repositories.impl.ServiceFriend;

@RestController
@RequestMapping(value = "/api/{userId}/friends"  )
public class ControllerFriend {
	
	@Autowired
	ServiceFriend serviceFriend; 
	
	@RequestMapping( method=RequestMethod.GET  )
	public ResponseEntity<Iterable<User>> find(@PathVariable(value="userId") Long id){
		return serviceFriend.find( id );
	}
	
	@RequestMapping( value="/{friendId}", method=RequestMethod.GET )
	public ResponseEntity<?> findOne(  @PathVariable(value="userId") Long userId, @PathVariable(value="friendId") Long friendId){
		System.out.println( userId );
		System.out.println( friendId );

		return serviceFriend.findOne(userId, friendId);
	}
	
	@RequestMapping( method=RequestMethod.POST )
	public ResponseEntity<?> create( @PathVariable(value="userId") Long userId, @RequestBody User user ){
		System.out.println("comme here");
		return serviceFriend.create( userId ,user );
	}
	
	@RequestMapping( value="/{friendId}/revoke", method=RequestMethod.DELETE )
	public ResponseEntity<?> revoke(@PathVariable(value="userId") Long userId, @PathVariable(value="friendId") Long friendId){
		System.out.println( userId );
		System.out.println( friendId );
		return serviceFriend.revoke(userId, friendId);
	}
	
	//TODO
	@RequestMapping( value="/{friendId}/addToGroup", method=RequestMethod.PUT )
	public ResponseEntity<?> friendId_users_userId_addToGroup( @PathVariable(value="userId") Long userId, @PathVariable(value="friendId") Long friendId ){
		return null;
	}

}