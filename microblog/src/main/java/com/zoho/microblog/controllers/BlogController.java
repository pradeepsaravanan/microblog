package com.zoho.microblog.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zoho.microblog.models.BLOG;
import com.zoho.microblog.models.RESPONSE;
import com.zoho.microblog.repositories.BlogRepository;


@Controller
@RequestMapping(path="/blog")
public class BlogController {
	@Autowired
	private BlogRepository blogRepository;
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public @ResponseBody RESPONSE addNewBlog (@RequestParam String content,
		   @RequestParam String author,@RequestParam String access) {
		 BLOG n = new BLOG();
		    n.setContent(content);
		    n.setAuthor(author);
		    n.setAccess(access);
		    blogRepository.save(n);
		    
		    RESPONSE r = new RESPONSE();
		    r.setResponse("success");
		    return r;
	}
	
	@RequestMapping(value = "/public", method = RequestMethod.GET)
	  public @ResponseBody Iterable<BLOG> getPublicBlogs() {
	     
	    return blogRepository.findPublic();
	  }
	@RequestMapping(value = "/private", method = RequestMethod.GET)
	  public @ResponseBody Iterable<BLOG> getPrivateBlogs(
			  @RequestParam String author) {
	    
	    return blogRepository.findPrivate(author);
	  }
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	  public @ResponseBody Integer updateBlog(
			  @RequestParam String content,
			  @RequestParam String access,
			  @RequestParam Integer id) {
	    
	    
	    return blogRepository.updateBlog(content,access,id);
	  }
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	  public @ResponseBody RESPONSE deleteBlog(
			  @RequestParam Integer id) {
	    
	    blogRepository.deleteById(id);
	    RESPONSE r = new RESPONSE();
	    r.setResponse("success");
	    return r;
	  }
	
	@RequestMapping(value = "/myBlogs", method = RequestMethod.GET)
	  public @ResponseBody Iterable<BLOG> findMyBlogs(
			  @RequestParam String author) {
	    	    
	    return blogRepository.findMyBlogs(author);
	  }
	@RequestMapping(value = "/findBlog", method = RequestMethod.GET)
	  public @ResponseBody Optional<BLOG> findBlog(
			  @RequestParam Integer id) {
	    	    
	    return blogRepository.findById(id);
	  }
	@RequestMapping(value = "/feed", method = RequestMethod.GET)
	  public @ResponseBody Iterable<BLOG> feed(
			  @RequestParam String email) {
				List<String>following = blogRepository.following(email);
	    	    following.add(email);
	    return blogRepository.feed(following);
	  }
}
