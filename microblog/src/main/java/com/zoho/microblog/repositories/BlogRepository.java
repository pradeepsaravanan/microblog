package com.zoho.microblog.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.zoho.microblog.models.BLOG;

import java.util.List;

import javax.transaction.Transactional;

public interface BlogRepository extends CrudRepository<BLOG, Integer> {
	@Query(
			  value = "SELECT * FROM Blog u WHERE u.access= 'public'", 
			  nativeQuery = true)
	List<BLOG> findPublic();
	
	@Query(
			  value = "SELECT * FROM Blog u WHERE u.author = ?1 and u.access= 'private'", 
			  nativeQuery = true)	
	List<BLOG> findPrivate(String author);
	
	@Query(
			  value = "SELECT * FROM Blog u WHERE u.author = ?1", 
			  nativeQuery = true)	
	List<BLOG> findMyBlogs(String author);
	
	@Query(
			  value = "SELECT followee_email FROM FOLLOW f WHERE f.follower_email = ?1", 
			  nativeQuery = true)	
	List<String> following(String email);
	
	@Query(
			  value = "SELECT * FROM Blog u WHERE (u.access= 'followers' OR u.access= 'public') AND u.author IN ?1 ORDER BY time1 DESC", 
			  nativeQuery = true)	
	List<BLOG> feed(List<String> following);
	
@Modifying
	@Transactional
	@Query(
			  value = "UPDATE Blog SET content = ?1, access= ?2 WHERE id = ?3", 
			  nativeQuery = true)	
	Integer updateBlog(String content,String access,Integer id);
}
