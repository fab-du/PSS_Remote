package de.app.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import de.app.model.User;
import de.app.services.ServiceUser;
import de.app.model.Group;

@RestController
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE  )
public class ControllerUser {

	@Autowired
	private ServiceUser userservice;

	@RequestMapping( method= RequestMethod.GET )
	public ResponseEntity<Iterable<User>> find(){
		return userservice.find();
	}
	
	@RequestMapping( value="/{userId}", method= RequestMethod.GET)
	public ResponseEntity<User> findOne( @PathVariable(value="userId") Long id){
		System.out.println("comme here OOOONNNNNEE");

		return userservice.findOne(id);
	}

	@RequestMapping( method= RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_VALUE )
	public  @ResponseBody String createUser( ){

//		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
//		Set<ConstraintViolation<RegisterModel>> constraintViolations = validator.validate( newuser);
//
//		if( constraintViolations.size() >= 1 )  return new String("please enter user infos");
//
//		User user = new User( newuser.getFirstname(), newuser.getSecondname(), newuser.getEmail(), newuser.getCompany(), false );
//		System.out.println(user.toString());
//		boolean ret = userservice.save(user, newuser.getPassphrase(), newuser.getPassword(), newuser.getSalt(), newuser.getVerifier()  );
//		System.out.println(ret);
//
//		if( ret ) return  new String("user created");
//		return new String("user already exist");
		return null;
	}

	@RequestMapping( value="/{currentUserId}/groups", method= RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_VALUE )
	public  void createUser( @RequestBody Group newgroup , @PathVariable( value="currentUserId") Long currentUserId ){
		
	}

	@RequestMapping(value="/{currentUserId}/groups/{currentGroupId}/documents/upload", method=RequestMethod.POST)
	public @ResponseBody String handlefileUpload(
		 @PathVariable( value="currentUserId") Long currentUserId,
		 @PathVariable( value="currentGroupId") Long currentGroupId,
		 @RequestParam("name") String name,
		 @RequestParam("file") MultipartFile file,
		 final HttpServletRequest request
	 
	){
		
		ServletInputStream sis = null;

		try {
			sis = request.getInputStream();
			byte[ ] b = null;
			sis.read(b);

		} catch (IOException e1) {
			e1.printStackTrace();
		}

		if( !file.isEmpty() ){
			try{
				byte[] bytes = file.getBytes();
				BufferedOutputStream stream = 
						new BufferedOutputStream( new FileOutputStream(new File( "/home/batie/workspace/CrytoOne-Local/uploads/" +  name)));
				stream.write(bytes);
				stream.close();
				return "successfull upload";

			} catch( Exception e ){
				return "upload error" + e.getMessage();
			}
		}
		
		return null;
	}

	@RequestMapping(value="/{currentUserId}/groups/{currentGroupId}/documents/upload", method=RequestMethod.GET)
	public @ResponseBody String getfileUpload(){
		return "upload";
	}


}
