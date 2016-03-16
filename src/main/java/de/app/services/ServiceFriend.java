package de.app.services;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import de.app.repositories.RepositoryFriend;
import de.app.repositories.RepositoryGroup;
import de.app.repositories.RepositoryUsers;
import de.app.model.Friendship;
import de.app.model.User;

@Service
@Transactional
public class ServiceFriend {

	@Autowired
	RepositoryFriend repositoryFriend;
	@Autowired
	RepositoryGroup repositoryGroup;
	@Autowired
	RepositoryUsers repositoryUser;
	@Autowired
	ServiceGroup serviceGroup;
	
	public
	ResponseEntity<Iterable<User>> 
	find( Long id ){
	
		de.app.model.User user = repositoryUser.findOne( id ); 
		if( user == null)
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
		Set<Friendship> friends = user.getFriends();
		Iterator<Friendship> it = friends.iterator();
		Set<User> result = new HashSet<User>();
		while( it.hasNext() ){
			User friend = repositoryUser.findOne( it.next().getFriendId() );
			result.add(friend);
		}
		
		if( !result.isEmpty() )
			return new ResponseEntity<Iterable<User>>( result, HttpStatus.OK);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	public
	ResponseEntity<User> 
	findOne( Long id, Long friendId ){
	
		User user = repositoryUser.findOne( id ); 
		
		if( user == null)
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
		Set<Friendship> friends = user.getFriends();
		Iterator<Friendship> it =friends.iterator();
		
		boolean ret = false;
		while(it.hasNext()){
			if( it.next().getFriendId().equals(friendId)){
				ret = true;
				break;
			}
		}
		
		if( ret ){
			User friend = repositoryUser.findOne(friendId);
			return new ResponseEntity<User>( friend, HttpStatus.OK);	
		}
		else{
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);	
		}
		
	}
	
	public
	ResponseEntity<User> 
	create( Long id, Friendship friendship){
		User _user = repositoryUser.findOne(id);
		friendship.setUsers(  _user );
		repositoryFriend.save(friendship); 
		Set<Friendship> _friends = _user.getFriends();
		_friends.add(friendship);
		_user.setFriends( _friends );
		repositoryUser.save( _user );
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	public
	ResponseEntity<?> 
	revoke( Long userId, Long friendId ){
		User user = repositoryUser.findOne( userId ); 	
		
		if( user == null )
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	
		Set<Friendship> friends = user.getFriends();
		Iterator<Friendship> it =friends.iterator();
		
		boolean ret = false;
		Friendship _friendship = null; 
		
		while(it.hasNext()){
			_friendship = it.next();
			if( _friendship.getFriendId().equals(friendId)){
				ret = true;
				break;
			}
		}	
		
		if( ret  && _friendship != null){
			repositoryFriend.delete( _friendship.getId());
			
			friends.remove( _friendship );
			user.setFriends(friends);
			repositoryUser.save(user);
			return new ResponseEntity<>( HttpStatus.ACCEPTED);	
		}
		else{
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);	
		}
	}
	
	
//    //@Deprecated
//    //TODO
//	public ResponseEntity<?> addFriendToGroup( Long friendId, Long gvId, Long groupId ){
//		boolean gvExist = repositoryUser.exists(gvId);
//		Group group = repositoryGroup.findOne(groupId);
//		
//		boolean isGv  = group.getGvid().equals(gvId);
//		
//		if( !gvExist || !isGv ) 
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
//		
//		User friend = repositoryUser.findOne( friendId );
//        return null;
//	 //   return serviceGroup.addUser( friend, groupId, );
//	}
}
