package com.zoho.microblog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.zoho.microblog.models.FOLLOW;

public interface FollowRepository extends CrudRepository<FOLLOW, Integer>{
	
	@Query(
			  value = "SELECT name from user u where u.email IN (select follower_email from follow f where f.followee_email= ?1);", 
			  nativeQuery = true)	
	List<String> myFollowers(String followeeEmail);
	
	@Query(
			  value = "SELECT name from user u where u.email IN (select followee_email from follow f where f.follower_email= ?1);", 
			  nativeQuery = true)	
	List<String> myFollowing(String followerEmail);
}
