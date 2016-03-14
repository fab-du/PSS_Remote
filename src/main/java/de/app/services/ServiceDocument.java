package de.app.services;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
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
import de.app.model.form.Form_Document;

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

public
ResponseEntity<?> create( MultipartFile file ) throws IOException{
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
		return new ResponseEntity<>(HttpStatus.OK);
}



public boolean save( Form_Document doc, HttpServletRequest request, MultipartFile file ){

	boolean noError = false;
	ServletInputStream sis = null;

		try {
			byte[] b = null;
			sis.read(b);

		} catch (IOException e1) {
			e1.printStackTrace();
		}

		if( !file.isEmpty() ){
			try{
				byte[] bytes = file.getBytes();
				BufferedOutputStream stream = 
				new BufferedOutputStream( new FileOutputStream(new File( "/home/batie/workspace/CrytoOne-Local/uploads/" +  doc.getDocumentName())));
				stream.write(bytes);
				stream.close();
				noError =  true;
			} catch( Exception e ){
				System.out.println("something goes wrong with file upload");
				return false;
			}
		}

	return noError;
}


public Set<Document> listAlldocumentFromGroup( Long groupid ){

	if( repositorygroup.exists(groupid)){
		Group group = repositorygroup.getOne(groupid);
		return group.getDocuments();
	}

	return null;
}

}
