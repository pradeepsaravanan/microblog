package com.zoho.microblog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zoho.microblog.models.FOLLOW;
import com.zoho.microblog.models.RESPONSE;
import com.zoho.microblog.repositories.FollowRepository;

@Controller
@RequestMapping(path="/follow")
public class FollowController {
	
	@Autowired
	private FollowRepository followrepository;
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public @ResponseBody RESPONSE addNewFollow (@RequestParam String followeeEmail,
		   @RequestParam String followerEmail) {
		 FOLLOW n = new FOLLOW();
		    n.setFollweeEmail(followeeEmail);
		    n.setFollowerEmail(followerEmail);
		    followrepository.save(n);
		    RESPONSE r = new RESPONSE();
		    r.setResponse("success");
		    return r;
	}
	
	@RequestMapping(value = "/followers", method = RequestMethod.GET)
	  public @ResponseBody Iterable<String> getMyFollowers(
			  @RequestParam String followeeEmail) {
	    
	    return followrepository.myFollowers(followeeEmail);
	  }

	@RequestMapping(value = "/following", method = RequestMethod.GET)
	  public @ResponseBody Iterable<String> getMyFollowing(
			  @RequestParam String followerEmail) {
	    
	    return followrepository.myFollowing(followerEmail);
	  }
	
}
