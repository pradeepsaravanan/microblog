package com.zoho.microblog.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.zoho.microblog.models.REQUEST;

import java.util.List;

import javax.transaction.Transactional;

public interface RequestRepository extends CrudRepository<REQUEST, Integer> {
	@Query(
			  value = "SELECT * FROM REQUEST u WHERE u.status != 'blocked'", 
			  nativeQuery = true)
	List<REQUEST> findRequests();

@Modifying
	@Transactional
	@Query(
			  value = "INSERT INTO FOLLOW (followee_email, follower_email)VALUES (followee_email=?1 ,follower_email=?2);",	  		 
			  nativeQuery = true)
	void acceptRequest(String recieverEmail, String senderEmail);

@Modifying
	@Transactional
	@Query(
			value = "UPDATE REQUEST u set u.status ='blocked' WHERE (u.sender_email=?1 AND u.reciever_email=?2);",	  		 
			nativeQuery = true)
	void blockRequest(String senderEmail, String recieverEmail);
	
@Modifying
	@Transactional
	@Query(
			  value = "delete from REQUEST where sender_email= ?1 AND reciever_email= ?2", 
			  nativeQuery = true)	
	void deleteRequest(String senderEmail,String recieverEmail);

@Query(
		  value = "SELECT * FROM REQUEST u WHERE u.reciever_email = ?1 AND u.status !='blocked' ORDER BY time1 DESC", 
		  nativeQuery = true)	
List<REQUEST> findMyRequests(String recieverEmail);

@Query(
		  value = "SELECT * FROM REQUEST u WHERE u.sender_email = ?1 AND u.status= 'sent'", 
		  nativeQuery = true)	
List<REQUEST> sentRequests(String senderEmail);

@Query(
		  value = "SELECT COUNT(id) FROM REQUEST u WHERE u.sender_email = ?1 AND u.status= 'blocked'", 
		  nativeQuery = true)	
int allowRequest(String senderEmail);
}