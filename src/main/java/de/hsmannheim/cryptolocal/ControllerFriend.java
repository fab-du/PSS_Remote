package de.hsmannheim.cryptolocal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import de.hsmannheim.cryptolocal.models.Friendship;
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
		return serviceFriend.findOne(userId, friendId);
	}
	
	@RequestMapping( method=RequestMethod.POST )
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> create( @PathVariable(value="userId") Long userId, @RequestBody Friendship friendship ){
		return serviceFriend.create( userId , friendship );
	}
	
	@RequestMapping( value="/{friendId}/revoke", method=RequestMethod.DELETE )
	public ResponseEntity<?> revoke(@PathVariable(value="userId") Long userId, @PathVariable(value="friendId") Long friendId){
		return serviceFriend.revoke(userId, friendId);
	}
	
	@RequestMapping( value="/{friendId}/addToGroup/{groupId}", method=RequestMethod.PUT )
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> friendId_users_userId_addToGroup( @PathVariable(value="userId") Long userId, 
			@PathVariable(value="friendId") Long friendId, @PathVariable(value="groupId") Long groupId  ){
		return serviceFriend.addFriendToGroup(friendId, userId, groupId);
	}

}
