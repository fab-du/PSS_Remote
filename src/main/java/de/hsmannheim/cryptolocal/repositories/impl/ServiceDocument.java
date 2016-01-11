package de.hsmannheim.cryptolocal.repositories.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import de.hsmannheim.cryptolocal.models.Document;
import de.hsmannheim.cryptolocal.models.Group;
import de.hsmannheim.cryptolocal.models.forms.Form_Document;
import de.hsmannheim.cryptolocal.repositories.RepositoryDocuments;
import de.hsmannheim.cryptolocal.repositories.RepositoryGroup;

@Service
@Transactional
public class ServiceDocument {

@Autowired
RepositoryDocuments repositorydocument;

@Autowired
ServiceGroup servicegroup;

@Autowired
RepositoryGroup repositorygroup;

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

		if( noError ){
			
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
