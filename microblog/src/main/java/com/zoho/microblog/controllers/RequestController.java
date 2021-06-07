package com.zoho.microblog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zoho.microblog.models.FOLLOW;
import com.zoho.microblog.models.REQUEST;
import com.zoho.microblog.models.RESPONSE;
import com.zoho.microblog.repositories.FollowRepository;
import com.zoho.microblog.repositories.RequestRepository;


@Controller
@RequestMapping(path="/request")
public class RequestController {
	@Autowired
	private RequestRepository requestRepository;
	
	@Autowired
	private FollowRepository followRepository;
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public @ResponseBody RESPONSE addNewRequest (@RequestParam String senderEmail,
		   @RequestParam String recieverEmail,@RequestParam String status) {
		
		if(requestRepository.allowRequest(senderEmail) == 0) {
		 REQUEST n = new REQUEST();
		    n.setSenderEmail(senderEmail);
		    n.setRecieverEmail(recieverEmail);
		    n.setStatus(status);
		    requestRepository.save(n);

		    RESPONSE r = new RESPONSE();
		    r.setResponse("success");
		    return r;}
		
		RESPONSE r = new RESPONSE();
	    r.setResponse("failed");
	    return r;
	}

	@RequestMapping(value = "/manage", method = RequestMethod.GET)
	  public @ResponseBody RESPONSE manageRequest(
			 @RequestParam String senderEmail,
			 @RequestParam String recieverEmail,
			 @RequestParam String action) {
	    if(action.equals("accept")) {
	    	FOLLOW n = new FOLLOW();
		    n.setFollweeEmail(recieverEmail);
		    n.setFollowerEmail(senderEmail);
		    followRepository.save(n);
	    	requestRepository.deleteRequest(senderEmail, recieverEmail);
	    }
	    else if(action.equals("cancel")) {
	    	requestRepository.deleteRequest(senderEmail, recieverEmail);
	    }
	    else if(action.equals("block"))
		requestRepository.blockRequest( senderEmail, recieverEmail);
	    
	    RESPONSE r = new RESPONSE();
	    r.setResponse("success");
	    return r;
	  }

	@RequestMapping(value = "/find", method = RequestMethod.GET)
	  public @ResponseBody Iterable<REQUEST> getMyRequests(
			  @RequestParam String recieverEmail) {
	    
	    return requestRepository.findMyRequests(recieverEmail);
	  }
	
	@RequestMapping(value = "/sent", method = RequestMethod.GET)
	  public @ResponseBody Iterable<REQUEST> sentRequests(
			  @RequestParam String senderEmail) {
	    
	    return requestRepository.sentRequests(senderEmail);
	  }
}
