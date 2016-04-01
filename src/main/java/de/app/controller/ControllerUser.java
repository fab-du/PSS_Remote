package de.app.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

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
import de.app.services.ServiceFriend;
import de.app.services.ServiceGroup;
import de.app.services.ServiceUser;
import de.app.model.Group;

@RestController
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE  )
public class ControllerUser {

	@Autowired
	private ServiceUser userservice;
	
	@Autowired
	private ServiceFriend friendservice;
	
	@Autowired
	private ServiceGroup servicegroup;

	@RequestMapping( method= RequestMethod.GET )
	public ResponseEntity<Iterable<User>> find(){
		return userservice.find();
	}
	
	@RequestMapping( value="/{userId}", method= RequestMethod.GET)
	public ResponseEntity<User> findOne( @PathVariable(value="userId") Long id){
		return userservice.findOne(id);
	}
	

	@RequestMapping( value="/{currentUserId}/groups", method= RequestMethod.GET, consumes= MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<Iterable<User>> getGroups(  @PathVariable( value="currentUserId") Long currentUserId ){
		return friendservice.find(currentUserId);
	}
	
	@RequestMapping( value="/{currentUserId}/friends", method= RequestMethod.GET, consumes= MediaType.APPLICATION_JSON_VALUE )
	public  ResponseEntity<List<Group>> getFriends(  @PathVariable( value="currentUserId") Long currentUserId ){
		return servicegroup.findWhereUserIsGV(currentUserId);
	}
}
