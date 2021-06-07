package com.zoho.microblog.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class FOLLOW {
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Integer id;

  private String followeeEmail;

  private String followerEmail;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getFolloweeEmail() {
	    return followeeEmail;
	  }

  public void setFollweeEmail(String followeeEmail) {
	    this.followeeEmail = followeeEmail;
	  }

  public String getFollowerEmail() {
	    return followerEmail;
	  }

  public void setFollowerEmail(String followerEmail) {
	    this.followerEmail = followerEmail;
	  }
}
