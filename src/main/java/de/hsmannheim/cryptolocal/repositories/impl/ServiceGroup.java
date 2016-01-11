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


	public boolean save( Group group, Long userid, boolean isLead , String enc_symkey ){

		System.out.print(group.getName());
		Group existingGroup = repositorygroup.findOneByName(group.getName());

		if( existingGroup != null ) return false; else{}
					
		if ( !repositoryuser.exists(userid) ) return false; else{}

		User user = repositoryuser.findOne(userid);
		KeySym enc_keysym = new KeySym();
		enc_keysym.setSymkey(enc_symkey);

		repositorykeysym.save(enc_keysym);
		repositorygroup.save(group);

		group = repositorygroup.findOneByName(group.getName());

		UserGroup usergroup = new UserGroup();
		usergroup.setUsers(user);
		usergroup.setGroups(group);
		usergroup.setUseringroupId(user.getId());
		usergroup.setGroupId(group.getId());
		usergroup.setGroupLead(isLead);

		usergroup.setKeysym(enc_keysym);
		repositoryusergroup.save(usergroup);
		user.getUsergroup().add(usergroup);
		repositoryuser.save(user);
		return true;
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

	public Set<User> findMitglieder( Long groupid ){
		Group group = repositorygroup.getOne(groupid);
		String groupname = group.getName();
		Set<User> mitglieder =  this.findMitglieder(groupname);
		return mitglieder;
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

	public Set<Group> findAll( ){
		Set<Group> ret = new HashSet<Group>( repositorygroup.findAll() );
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
