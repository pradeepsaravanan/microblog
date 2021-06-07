package com.zoho.microblog.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.zoho.microblog.models.STATUS;

import java.util.List;

import javax.transaction.Transactional;


public interface StatusRepository extends CrudRepository<STATUS, Integer> {
	
	@Query(
			  value = "SELECT * FROM STATUS u WHERE u.author = ?1", 
			  nativeQuery = true)	
	List<STATUS> findMyStatus(String author);
	
	@Query(
			  value = "SELECT followee_email FROM FOLLOW f WHERE f.follower_email = ?1", 
			  nativeQuery = true)	
	List<String> following(String email);
	
	@Modifying
	@Transactional		
	@Query(
			  value = "delete from STATUS where time1 < (NOW() - INTERVAL 5 MINUTE)", 
			  nativeQuery = true)	
	void clearStatus();
	
	@Query(
			  value = "SELECT * FROM STATUS u WHERE u.author IN ?1 ORDER BY time1 DESC", 
			  nativeQuery = true)	
	List<STATUS> feed(List<String> following);
	
}	
