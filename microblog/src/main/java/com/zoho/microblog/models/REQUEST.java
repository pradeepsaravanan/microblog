package com.zoho.microblog.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class REQUEST {
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Integer id;

  private String senderEmail;

  private String recieverEmail;
  
  private String status;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getSenderEmail() {
	    return senderEmail;
	  }

  public void setSenderEmail(String SenderEmail) {
	    this.senderEmail = SenderEmail;
	  }

  public String getRecieverEmail() {
	    return recieverEmail;
	  }

  public void setRecieverEmail(String recieverEmail) {
	    this.recieverEmail = recieverEmail;
	  }
  
  public String getStatus() {
	    return status;
	  }

  public void setStatus(String status) {
	    this.status = status;
	  }
}
