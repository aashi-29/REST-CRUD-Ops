package com.mobile.app.myProject.ui.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mobile.app.myProject.ui.model.UpdateUserDetailsRequestModel;
import com.mobile.app.myProject.ui.model.UserDetailsRequestModel;
import com.mobile.app.myProject.ui.model.UserRest;

@RestController
@RequestMapping("/users")  //http://localhost:8080/users (since we are using tomcat server)
public class UserController {

	Map<String, UserRest> users;
	
	@RequestMapping   //for optional request parameters
	public String getUsers(@RequestParam(value="page", defaultValue="1") int page, @RequestParam(value="limit", defaultValue="20") int limit)
	{
		return "update user was called with page" +page + limit;
	}
	
	
	@RequestMapping(value="/{userId}", method = RequestMethod.GET, produces= {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	//to return the set values in xml and json
	public ResponseEntity<UserRest> getUser(@PathVariable String userId)
	{
		if(users.containsKey(userId))
		{
			return new  ResponseEntity<>(users.get(userId), HttpStatus.OK);
		}
		else
		{
			return new  ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	
	@RequestMapping( method = RequestMethod.POST, consumes= {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},  produces= {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	
	public ResponseEntity<UserRest> createUser(@RequestBody UserDetailsRequestModel userDetails)
	{
		UserRest returnValue= new UserRest();
		returnValue.setEmail(userDetails.getEmail());
		returnValue.setFirstName(userDetails.getFirstName());
		returnValue.setLastName(userDetails.getLastName());
		
		String userId= UUID.randomUUID().toString();
		returnValue.setUserId(userId);
		
		if(users == null) users = new HashMap<>();
		users.put(userId, returnValue);
		return new ResponseEntity<UserRest>(returnValue, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{userId}", method = RequestMethod.PUT, consumes= {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},  produces= {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public UserRest updateUser(@PathVariable String userId, @RequestBody UpdateUserDetailsRequestModel userDetails )
	{
		UserRest storedUserDetails= users.get(userId);
		storedUserDetails.setFirstName(userDetails.getFirstName());
		storedUserDetails.setLastName(userDetails.getLastName());
		users.put(userId, storedUserDetails);
		return storedUserDetails;
	}
	
	@RequestMapping( value="/{id}",method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteUser(@PathVariable String id)
	{
		users.remove(id);
		
		return ResponseEntity.noContent().build();
	}
	
	
}
