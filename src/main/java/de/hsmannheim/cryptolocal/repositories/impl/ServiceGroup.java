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
		repositoryusergroup.save(usergroup);

		Set<UserGroup> _users = group.getUsers();
		_users.add(usergroup);
		group.setUsers(_users);
		repositorygroup.save(group); 
		
		//repositoryusergroup.save(usergroup);
		
		_users = user.getUsergroup();
		user.setUsergroup(_users);
		repositoryuser.save(user);
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}


	public Map<String, String> groupId( Long groupId ){
		Group group = repositorygroup.getOne(groupId);

		Map<String, String> result = new HashMap<String, String>();
		result.put("groupname", group.getName() );
		result.put("id", String.valueOf(group.getId()) );
		result.put("documents", String.valueOf(group.getDocuments().size()) );
		result.put("users",   String.valueOf(group.getUsers().size()) );
		return result;
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

	public boolean addUserToGroup( Long gvid , Long groupid, String newuseremail, String passphrase  ){
		boolean ret = false;

//		User user = repositoryuser.getOne(gvid);
//		User newuser = repositoryuser.findOneByEmail(newuseremail);
//
//		if(  user != null && newuser != null && (repositorygroup.exists(groupid) == true) ){
//			Group group = repositorygroup.findOne(groupid);
//
//			AESCrypto aes = (AESCrypto) CryptFactor.getInstance( CryptFactor.CRYPT_SYM_AES );
//			RSAPBECrypto rsa = (RSAPBECrypto) CryptFactor.getInstance(CryptFactor.CRYPT_ASYM_RSA_PBE);
//			String newuser_pubkey = newuser.getKeypair().getPubkey();
//			String gv_prikey = user.getKeypair().getPrikey();
//
//			Set<UserGroup> usergroup = user.getUsergroup();
//
//			KeySym enc_keysym =  new KeySym();
//
//			for( Iterator<UserGroup> it = usergroup.iterator(); it.hasNext(); ){
//				UserGroup obj = it.next();
//				if( (obj.getGroupId() == groupid) && (obj.isGroupLead() == true ) ){
//					enc_keysym = obj.getKeysym();
//					ret  = true;
//					break;
//				}
//			}
//
//			if( ret == false ) return ret; else{}
//
//			String dec_keysym = rsa.decrypt(gv_prikey, passphrase, user.getKeypair().getSalt(), enc_keysym.getSymkey());
//
//			KeySym newuser_symkey = new KeySym();
//			newuser_symkey.setSymkey( rsa.encrypt(newuser_pubkey, dec_keysym) );
//			repositorykeysym.save(newuser_symkey);
//
//
//			UserGroup usergp = new UserGroup();
//			usergp.setUsers(newuser);
//			usergp.setGroups(group);
//			usergp.setUseringroupId(newuser.getId());
//			usergp.setGroupId(group.getId());
//			usergp.setGroupLead(false);
//
//			usergp.setKeysym(newuser_symkey);
//			repositoryusergroup.save(usergp);
//			newuser.getUsergroup().add(usergp);
//			repositoryuser.save(newuser);
			
		//	ret = true;

//		}
		
		return true;
	}



}
