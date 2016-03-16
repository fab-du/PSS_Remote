package de.app.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import de.app.repositories.RepositoryDocuments;
import de.app.repositories.RepositoryGroup;
import de.app.model.Document;
import de.app.model.Group;

@Service
@Transactional
public class ServiceDocument {

@Autowired
RepositoryDocuments repositorydocument;
@Autowired
ServiceGroup servicegroup;
@Autowired
RepositoryGroup repositorygroup;

public final static String UPLOAD_PATH = "uploads"; 

public ResponseEntity<?> create( MultipartFile file ) throws IOException{
	
	if ( file.isEmpty() )
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

	InputStream is = file.getInputStream();
	FileOutputStream fos = new FileOutputStream(new File(file.getOriginalFilename()));
	int read = 0;
	final byte[] bytes = new byte[1024];
	
	while((read=is.read(bytes)) != -1){
		fos.write(bytes, 0, read);
	}
		
	is.close();
	fos.close();
	return new ResponseEntity<>(HttpStatus.CREATED);
}


public Set<Document> listAlldocumentFromGroup( Long groupid ){

	if( repositorygroup.exists(groupid)){
		Group group = repositorygroup.getOne(groupid);
		return group.getDocuments();
	}

	return null;
}

}
