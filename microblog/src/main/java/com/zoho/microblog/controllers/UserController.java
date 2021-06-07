package com.zoho.microblog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zoho.microblog.models.RESPONSE;
import com.zoho.microblog.models.USER;
import com.zoho.microblog.repositories.UserRepository;

@Controller
@RequestMapping(path="/user")
public class UserController {
  @Autowired
 
  private UserRepository userRepository;

  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public @ResponseBody RESPONSE addNewUser (@RequestParam String name
      , @RequestParam String email, @RequestParam String password) {

    USER n = new USER();
    n.setName(name);
    n.setEmail(email);
    n.setPassword(password);
    userRepository.save(n);
    
    RESPONSE r = new RESPONSE();
    r.setResponse("success");
    return r;
  }

  @RequestMapping(value = "/find", method = RequestMethod.GET)
  public @ResponseBody USER getUser(@RequestParam String email) {
    return userRepository.findByEmail(email);
  }
  
  @RequestMapping(value = "/login", method = RequestMethod.GET)
  public @ResponseBody RESPONSE authenticate(@RequestParam String email,
		  @RequestParam String password) {
	  USER u = userRepository.findByEmail(email);
	  if(u.getPassword().equals(password)) {
		  RESPONSE r = new RESPONSE();
		    r.setResponse("success");
		    return r;
	  }
	  RESPONSE r = new RESPONSE();
	    r.setResponse("failed");
	    return r;
  }
  
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	  public @ResponseBody Iterable<USER> getAllUsers() {
	     
	    return userRepository.findAll();
	  }
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	  public @ResponseBody Iterable<USER> searchUsers(@RequestParam String name,
			  @RequestParam String email) {
	     
	    return userRepository.findUsers(name,email);
	  }
	@RequestMapping(value = "/suggest", method = RequestMethod.GET)
	  public @ResponseBody Iterable<USER> suggestUsers(@RequestParam String email) 
		{
	     
	    return userRepository.suggestUsers(email);
	  }
}
