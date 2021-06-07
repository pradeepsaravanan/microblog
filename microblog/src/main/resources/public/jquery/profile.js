


$(function(){    

    $("#profile-link").click(e => {
        e.preventDefault();
        $("#feed").hide();
        $("#friends-page").hide();
        $("#profile-page").show();
        $("#HOME-THIRD-SECTION").hide();
        $("#FRIENDS-THIRD-SECTION").show();
        FollowerArray();
        FollowingArray();
        profileHeading();
        blog2();
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


    const profileHeading =()=>{
        let body = {
            email: sessionStorage.getItem("email")
        }
        $.get("http://localhost:8080/user/find",{
        "email" : sessionStorage.getItem("email")
    },(data, error) => {
            console.log(data.name);
           $("#Proile_Heading").text(data.name);      
           $("#Profile_Email").text(data.email);      
    });
    }

let blog2 = () => {
    let body = {
        author: sessionStorage.getItem("email")                   
    };   
    $.ajax({
        url:"http://localhost:8080/blog/myBlogs",
        data:body,
        success:(data, error) => {    
            $("#PROFILE_FEED").empty();             
            for(var i=0; i<data.length; i++)
            {   
                
                 $("#PROFILE_FEED").append("<div class='card bg-dark m-3'>"+
                                    "<div class='card-header d-flex flex-reverse justify-content-between'>"+
                                    "<div class=' h6'>"+data[i].author+"</div>"+
                                    "<div class='btn btn-outline-danger deleteBtn' blogId="+data[i].id+">Delete</div>"+
                                    "</div>"+
                                    "<div class='card-body'>"+
                                    "<p class='card-text '>"+data[i].content+"</p>"+
                                    "</div>"+
                                    "</div>");
                 console.log(data[i].content);
            }  
            onprofileRendered();                 
    },
    error:(result)=>{console.log("fasdsadf");} 
});
return;
}


let onprofileRendered=() => {
        	
    $('.deleteBtn').click(event => {
        console.log("hi");
         let blogID = $(event.target).attr("blogID");
         console.log("Blogid"+blogID);
         $.get("http://localhost:8080/blog/delete",{
            "id": blogID
         },(data, error) => {
             if(error == "success")   
             blog2();
         });
    });       	
}

});