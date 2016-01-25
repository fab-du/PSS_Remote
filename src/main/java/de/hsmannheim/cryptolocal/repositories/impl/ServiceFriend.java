package de.hsmannheim.cryptolocal.repositories.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import de.hsmannheim.cryptolocal.models.Friendship;
import de.hsmannheim.cryptolocal.models.User;
import de.hsmannheim.cryptolocal.repositories.RepositoryFriend;
import de.hsmannheim.cryptolocal.repositories.RepositoryUsers;

@Service
@Transactional
public class ServiceFriend {

	@Autowired
	RepositoryFriend repositoryFriend;
	
	@Autowired
	RepositoryUsers repositoryUser;
	
	public
	ResponseEntity<Iterable<User>> 
	find( Long id ){
	
		User user = repositoryUser.findOne( id ); 
		
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
	create( Long id, User user){
		Friendship friendship = new Friendship();
		friendship.setFriendId( user.getId() );
		User _user = repositoryUser.findOne(id);
		friendship.setUsers(  _user );
		repositoryFriend.save(friendship); 
		Set<Friendship> _friends = _user.getFriends();
		_friends.add(friendship);
		_user.setFriends( _friends );
		repositoryUser.save( _user );
		return new ResponseEntity<>(HttpStatus.OK);
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
		int pos = -1; 
		
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
			return new ResponseEntity<>( HttpStatus.OK);	
		}
		else{
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);	
		}
	}
	
	
}
