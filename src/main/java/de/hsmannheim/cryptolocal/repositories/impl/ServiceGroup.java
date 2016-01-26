package de.hsmannheim.cryptolocal.repositories.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import de.hsmannheim.cryptolocal.repositories.RepositoryGroup;
import de.hsmannheim.cryptolocal.repositories.RepositoryKeysym;
import de.hsmannheim.cryptolocal.repositories.RepositoryUserGroup;
import de.hsmannheim.cryptolocal.repositories.RepositoryUsers;
import de.hsmannheim.cryptolocal.models.*;
//import de.cryptone.crypto.*;

@Service
@Transactional
public class ServiceGroup {

	@Autowired
	private RepositoryGroup repositorygroup;

	@Autowired
	private RepositoryUsers repositoryuser;
	
	@Autowired
	private RepositoryKeysym repositorykeysym;

	@Autowired
	private RepositoryUserGroup repositoryusergroup;
	
	public ResponseEntity<List<Group>> find(){
		List<Group> groups = repositorygroup.findAll();
		
		if( groups == null )
			return new ResponseEntity<List<Group>>(HttpStatus.NO_CONTENT);
			return new ResponseEntity<List<Group>>( groups, HttpStatus.OK);
	}
	
	public ResponseEntity<Group> findOne( Long groupId ) {
		Group group = repositorygroup.findOne( groupId );
		
		if( group == null )
			return new ResponseEntity<Group>(HttpStatus.NO_CONTENT);
			return new ResponseEntity<Group>( group, HttpStatus.OK);
	}


	public ResponseEntity<?> create( Group group, boolean isLead ){

		Group existingGroup = repositorygroup.findOneByName(group.getName());

		if( existingGroup != null ) 
			return new ResponseEntity<>(HttpStatus.CONFLICT);

		Long gvid = group.getGvid();
		
		if ( gvid == null )
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		
		User user = repositoryuser.findOne(gvid);
		
		Group _group = repositorygroup.findOneByName(group.getName());

		if ( _group != null )
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		
		group = repositorygroup.save( group );
		
		UserGroup usergroup = new UserGroup();
		usergroup.setUsers(user);
		usergroup.setGroups(group);
		usergroup.setUseringroupId(user.getId());
		usergroup.setGroupId(group.getId());
		usergroup.setGroupLead(isLead);
		usergroup =repositoryusergroup.save(usergroup);

		Set<UserGroup> _users = group.getUsers();
		_users.add(usergroup);
		group.setUsers(_users);
		repositorygroup.save(group); 
		
		_users = user.getUsergroup();
		user.setUsergroup(_users);
		repositoryuser.save(user);
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	public List<Group> findWhereUserIsGV( Long gvid ){
		List<UserGroup> groups = repositoryusergroup.findByUseringroupId(gvid);
		List<Group> result = new ArrayList<Group>();
		
		for( Iterator<UserGroup> it = groups.iterator(); it.hasNext(); ){
			UserGroup obj = it.next();
			if( obj.isGroupLead() ) result.add( repositorygroup.getOne( obj.getGroupId()));
		}
		
		return  result; 
	}

	public Set<Group> findUserGroup( long userid ){
		List<UserGroup> usergroups = repositoryusergroup.findByUseringroupId(userid);
		Set<Group> result = new HashSet<Group>();

		for( Iterator<UserGroup> it = usergroups.iterator(); it.hasNext(); ){
			UserGroup obj = it.next();
			result.add( repositorygroup.getOne( obj.getGroupId()));
		}

		return result;
	}

	public ResponseEntity<Set<User>> users( Long groupid ){
		Group group = repositorygroup.getOne(groupid);
		String groupname = group.getName();
		Set<User> mitglieder =  this.findMitglieder(groupname);
				
		if( group == null )
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			return new ResponseEntity<Set<User>>( mitglieder, HttpStatus.OK);
	}
	
	public ResponseEntity<Set<Document>> documents( Long groupid ){
		Group group = repositorygroup.getOne(groupid);

		if( group == null )
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);

		Set<Document> documents = group.getDocuments();
		
		if( documents == null )
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			return new ResponseEntity<Set<Document>>( documents, HttpStatus.OK);
	}

	public Set<User> findMitglieder( String groupname ){
		Group group = repositorygroup.findOneByName(groupname);
		Set<UserGroup> usergroup = group.getUsers();
		Set<User> ret = new HashSet<User>();

		for( Iterator<UserGroup> it = usergroup.iterator(); it.hasNext(); ){
			UserGroup obj = it.next();
			ret.add( repositoryuser.getOne( obj.getUseringroupId() )); 
		}

		return ret;
	}

	
	public ResponseEntity<?> addUser( User user, Long groupId ){
		Group group = repositorygroup.findOne( groupId ); 
		
		if( group == null || user == null)
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);

		System.out.println( user.getId());
		User _user = repositoryuser.findOne( user.getId() );
		
		if ( _user == null )
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
		UserGroup usergroup = new UserGroup();
		usergroup.setUsers(_user);
		usergroup.setGroups(group);
		usergroup.setUseringroupId(_user.getId());
		usergroup.setGroupId(group.getId());
		usergroup.setGroupLead(false);
		usergroup=repositoryusergroup.save(usergroup);
		
		Set<UserGroup> _users = group.getUsers();
		_users.add(usergroup);
		group.setUsers(_users);
		repositorygroup.save(group); 
		
		_users = _user.getUsergroup();
		_users.add(usergroup);
		_user.setUsergroup(_users);
		repositoryuser.save(_user);
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	public ResponseEntity<Set<Document>> addDocument(Long groupId, Document document) {
		return null;
	}

}
