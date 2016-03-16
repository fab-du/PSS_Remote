package de.app.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.lang.Exception;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import de.app.repositories.RepositoryDocuments;
import de.app.repositories.RepositoryGroup;
import de.app.repositories.RepositoryUserGroup;
import de.app.repositories.RepositoryUsers;
import de.app.model.Document;
import de.app.model.Group;
import de.app.model.KeySym;
import de.app.model.User;
import de.app.model.UserGroup;

@Service
@Transactional
public class ServiceGroup {

	@Autowired
	private RepositoryGroup repositorygroup;
	
	@Autowired
	private RepositoryDocuments repositorydocument;

	@Autowired
	private RepositoryUsers repositoryuser;
	
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
		usergroup.setKeysym(group.getGroupkey());
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

	public ResponseEntity<List<Group>> findWhereUserIsGV( long userid ){
		List<Group> result =  repositorygroup.findByGvid(userid);
		return new ResponseEntity<List<Group>>(result, HttpStatus.OK);
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

	public Set<User> findMitgliederByGroupId( Long groupId ) throws Exception{
		Group group = repositorygroup.getOne(groupId);
        if ( group == null )
			throw new Exception("No groups with id:" +  groupId );
        return this.findMitglieder(group.getName());
    }

	public Set<User> findMitglieder( String groupname ) throws Exception{
		Group group = repositorygroup.findOneByName(groupname);

        if ( group == null )
			throw new Exception("No groups with name:" + groupname );

		Set<UserGroup> usergroup = group.getUsers();
		Set<User> ret = new HashSet<User>();

		for( Iterator<UserGroup> it = usergroup.iterator(); it.hasNext(); ){
			UserGroup obj = it.next();
			ret.add( repositoryuser.getOne( obj.getUseringroupId() )); 
		}
		return ret;
	}

	
	public ResponseEntity<?> addUser( Long userId, Long groupId, KeySym symkey ){
		Group group = repositorygroup.findOne( groupId ); 
		User _user = repositoryuser.findOne( userId );
		
		if( group == null || _user == null)
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);

		UserGroup usergroup = new UserGroup();
		usergroup.setUsers(_user);
		usergroup.setGroups(group);
		usergroup.setUseringroupId(_user.getId());
		usergroup.setGroupId(group.getId());
		usergroup.setGroupLead(false);
        usergroup.setKeysym( symkey );
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

	public ResponseEntity<Document> addDocument(Long groupId, MultipartFile file) throws IOException {
		Group group = repositorygroup.findOne( groupId );
		if( group == null || file.isEmpty() )
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);

		String groupname = group.getName(); 
		this.makeUploadGroupDir(groupname);
		
		InputStream is = file.getInputStream();

		File _file = new File( "uploads/" + groupname + "/" + file.getOriginalFilename() );
		FileOutputStream fos = new FileOutputStream( _file );
		int read = 0;
		final byte[] bytes = new byte[1024];
	
		while((read=is.read(bytes)) != -1){
			fos.write(bytes, 0, read);
		}
		is.close();
		fos.close();

		Document document = this.saveDocument(group, file.getOriginalFilename());
		if( document != null )
			return new ResponseEntity<Document>(document, HttpStatus.CREATED);
			return new ResponseEntity<Document>(HttpStatus.OK);
	}
	
	private Document saveDocument( Group group, String filename ){
		Document document = new Document();
		document.setGroups(group);
		document.setName(filename);
		document.setPath(group.getName());
		document = repositorydocument.save(document);
		
		Set<Document> documents = group.getDocuments();
		documents.add( document );
		group.setDocuments(documents);
		repositorygroup.save(group);
		return document;
	}
	
	public  ResponseEntity<Document> groupId_documents_documentId( Long groupId, Long documentId ){
		Group group;
		
		if( groupId == null || 
			documentId == null || 
			( group= repositorygroup.findOne(groupId)) == null)
		        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

		Set<Document> documents = group.getDocuments();
		
		Iterator<Document> it = documents.iterator();
		
		Document document = null;
		boolean ret = false;
		while( it.hasNext() ){
			document = it.next();
			
			if ( document.getId().equals(documentId)){
				ret = true;
				break;
			}
		}
			
		if ( ret && document != null )
			return new ResponseEntity<Document>(document, HttpStatus.OK);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	private void makeUploadGroupDir( String groupname ){
		File file = new File("uploads/" + groupname);
		
		if( !file.exists()){
			if( file.mkdir()){
				System.out.println("Make upload Group dir");
			}
			else{
				System.out.println("Upload group dir already exists");
			}
		}
	}

}
