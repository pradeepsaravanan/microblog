$(function(){
    
    $("#friends-link").click(e => {
        e.preventDefault();
        $("#feed").hide();
        $("#HOME-THIRD-SECTION").hide();
        $("#FRIENDS-THIRD-SECTION").show();
        $("#followersList").show();
        $("#followingList").hide(); 
        $("#friends-page").show();
        $("#profile-page").hide();
        FollowerArray();
        frdRequest();
        suggest();       
    });

    
    $("#Followers").click(e =>{
        e.preventDefault();
        $("#followersList").show();
        $("#followingList").hide();
        return FollowerArray();
    });

    
    $("#Following").click(e =>{
        e.preventDefault();
        $("#followingList").show();
        $("#followersList").hide();
        return FollowingArray();
    });

    
    const FollowerArray = ()=>{
        let body ={"followeeEmail":sessionStorage.getItem("email")};
        $.ajax({
            url:"http://localhost:8080/follow/followers",
            data:body,
            method:"GET",
            success:(result)=>{
                $("#followersList").empty();
                $.each(result,(index,data)=>{
                    $("#followersList").append("<p class='bg-outline-primary border border-primary text-white text-center my-3 py-3'>"+data+"</p>");
                });
               
            },
            error:(result)=>{
                console.log("OUTPUT VARALA PA")
            }
        });
    };

    const FollowingArray = ()=>{
        let body ={"followerEmail":sessionStorage.getItem("email")};
        $.ajax({
            url:"http://localhost:8080/follow/following",
            data:body,
            method:"GET",
            success:(result)=>{
                $("#followingList").empty();
                $.each(result,(index,data)=>{
                    $("#followingList").append("<p class='bg-outline-primary border border-primary text-white text-center my-3 py-3'>"+data+"</p>");
                });
               
            },
            error:(result)=>{
                console.log("OUTPUT VARALA PA FROM FOLLOWING")
            }
        });
    };



$("#search-button").click(e => {
        e.preventDefault();
        let name = $("#search-value").val();
        let email= sessionStorage.getItem("email");
        $("#FIRST_HOME-PAGE").hide();
       
        searchUser();
});

$("#search-value").change(e=>{
    e.preventDefault()
    if($("#search-value").val() === ""){
        $("#SearchList").hide()
        $("#FIRST_HOME-PAGE").show();
    }
});

let searchUser = () => {
    let name = $("#search-value").val();
    let email= sessionStorage.getItem("email");
    $.get("http://localhost:8080/user/search",{ 
        "name": name,       	
        "email": email
    },
            (data, error) => {
                $("#SearchList").empty();
                
                for(var i=0; i<data.length; i++)
                {
                    $("#SearchList").append(
                        "<div class='col-6'>"+
                        "<div id="+data[i].id+" class='card my-2'>"+
                        "<div class='card-body'>"+
                        "<p class='text-dark'>"+
                        data[i].email+
                        "</p>"+
                        "<div class='d-flex rounded-0 flex-reverse'>"+
                        "<button class='sendRequestBtn btn btn-primary mr-2 rounded-0' "+"recieverEmail="+data[i].email+">Follow</button>"+
                        "</div>"+  
                        "</div>"+
                        "</div>"+
                        "</div>"                      			
                     );                        		 
                     console.log(data[i].id);
                }
        });
}

    
let frdRequest = () => {
        let email = sessionStorage.getItem("email");
        $.get("http://localhost:8080/request/find",{        	
            "recieverEmail": email
        },
                (data, error) => {
                    $("#RequestList").empty();
                    $("#RequestList").append("<div class=' text-center h5 lead mb-3'>Your Requests:</div>");
                    for(var i=0; i<data.length; i++)
                    {
                         $("#RequestList").append(
                                 "<div id="+data[i].id+" class='card my-2'>"+
                                 "<div class='card-body'>"+
                                 "<p class='text-dark'>"+
                                 data[i].senderEmail+
                                 "</p>"+
                                 "<div class='d-flex rounded-0 flex-reverse'>"+
                                 "<button class='acceptbtn btn btn-primary mr-2 rounded-0' senderEmail="+data[i].senderEmail+" recieverEmail="+data[i].recieverEmail+" >Accept</button>"+
                                 "<button class='cancelbtn btn btn-outline-dark rounded-0 ' senderEmail="+data[i].senderEmail+" recieverEmail="+data[i].recieverEmail+" >Cancel</button>"+
                                 "</div>"+  
                                 "</div>"+
                                 "</div>"                 			
                         );  
                         
                         console.log(data[i].id);
                    }
                    onRequestsRendered();
            });
            
        }
   
    let suggest = () => {
        let email = sessionStorage.getItem("email");
        $.get("http://localhost:8080/user/suggest",{        	
            "email": email
        },
                (data, error) => {
                    $("#SuggestionList").empty();
                    $("#SuggestionList").append("<div class='text-center h5 lead mb-3'>Suggested:</div>");
                    for(var i=0; i<data.length; i++)
                    {
                         $("#SuggestionList").append(
                                 "<div id="+data[i].id+" class='card my-2'>"+
                                 "<div class='card-body'>"+
                                 "<p class='text-dark'>"+
                                 data[i].email+
                                 "</p>"+
                                 "<div class='d-flex rounded-0 flex-reverse'>"+
                                 "<button class='sendRequestBtn btn btn-primary mr-2 rounded-0' "+"recieverEmail="+data[i].email+">Follow</button>"+
                                 "</div>"+  
                                 "</div>"+
                                 "</div>"                 			
                         );                        		 
                         console.log(data[i].id);
                    }
                  onSuggestRendered();  
            });
    }



let onRequestsRendered=() => {
        
        $('.cancelbtn').click(event => {
            console.log("hi");
             let senderEmail = $(event.target).attr("senderEmail");
             let recieverEmail = $(event.target).attr("recieverEmail");
             
             $.get("http://localhost:8080/request/manage",{
                "senderEmail": senderEmail,
                "recieverEmail": recieverEmail,
                "action": "cancel"
             },(data, error) => {
                 if(error == "success")            
                 frdRequest();
             });
        });
       
        $('.acceptbtn').click(event => {
            let senderEmail = $(event.target).attr("senderEmail");
             let recieverEmail = $(event.target).attr("recieverEmail");
             
             
             $.get("http://localhost:8080/request/manage",{
                "senderEmail": senderEmail,
                "recieverEmail": recieverEmail,
                "action": "accept"
             },(data, error) => {
                 if(error == "success")   
                 frdRequest();
                 FollowerArray();
             });
        });  
    }   
    


let onSuggestRendered=() => {
        
        $('.sendRequestBtn').click(event => {
            console.log("hi");
             let senderEmail = sessionStorage.getItem("email");
             let recieverEmail = $(event.target).attr("recieverEmail");
             
             $.post("http://localhost:8080/request/add",{
                "senderEmail": senderEmail,
                "recieverEmail": recieverEmail,
                "status": "sent"
             },(data, error) => {
                 if(error == "success")            
                 suggest();
             });
        }); 
    } 
    
}); 
