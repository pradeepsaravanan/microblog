package com.zoho.microblog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.zoho.microblog.models.USER;


public interface UserRepository extends CrudRepository<USER, Integer> {

  USER findByEmail(String email);
  
	@Query(
			  value = "SELECT * FROM USER u WHERE u.name LIKE %?1% AND u.email != ?2", 
			  nativeQuery = true)
	List<USER> findUsers(String name, String email);
	
	@Query(
			  value = "SELECT * FROM USER u WHERE (u.email NOT IN (SELECT followee_email from follow f where f.follower_email = ?1)) AND (u.email != ?1) AND (u.email NOT IN (SELECT reciever_email from request r where r.sender_email=?1))",
					  
			  nativeQuery = true)
	List<USER> suggestUsers(String email);
}
