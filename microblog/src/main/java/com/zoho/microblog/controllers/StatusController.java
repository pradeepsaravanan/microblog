package com.zoho.microblog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zoho.microblog.models.RESPONSE;
import com.zoho.microblog.models.STATUS;
import com.zoho.microblog.repositories.StatusRepository;


@Controller
@RequestMapping(path="/status")
public class StatusController {
	@Autowired
	private StatusRepository statusRepository;
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public @ResponseBody RESPONSE addNewStatus (@RequestParam String content,
		   @RequestParam String author) {
		 STATUS n = new STATUS();
		    n.setContent(content);
		    n.setAuthor(author);
		    statusRepository.save(n);
		    
		    RESPONSE r = new RESPONSE();
		    r.setResponse("success");
		    return r;
	}
	
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	  public @ResponseBody RESPONSE deleteStatus(
			  @RequestParam Integer id) {
	    
	    statusRepository.deleteById(id);
	    
	    RESPONSE r = new RESPONSE();
	    r.setResponse("success");
	    return r;
	  }
	
	@RequestMapping(value = "/myStatus", method = RequestMethod.GET)
	  public @ResponseBody Iterable<STATUS> findMyStatus(
			  @RequestParam String author) {
	    	    
	    return statusRepository.findMyStatus(author);
	  }

	@RequestMapping(value = "/feed", method = RequestMethod.GET)
	  public @ResponseBody Iterable<STATUS> feed(
			  @RequestParam String email) {
		
				statusRepository.clearStatus();
				List<String>following = statusRepository.following(email);
				following.add(email);
	    return statusRepository.feed(following);
	  }
}
