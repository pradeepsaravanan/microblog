$(function(){

    function Home(){
        $("#friends-page").hide();
        $("#profile-page").hide();
        $("#feed").html('');
        $("#FRIENDS-THIRD-SECTION").hide();
        $("#feed").show();
        status();

        if(sessionStorage.getItem("email") == undefined){
    		let url = "http://localhost:8080/index.html";                       
            $(location).attr('href',url); 
    	}
    }
    
    Home();

    $("#home-link").click(e =>{
        e.preventDefault();
        $("#FRIENDS-THIRD-SECTION").hide();
        $("#HOME-THIRD-SECTION").show();
        return Home();
    });

    
    
$("#post-blog").click(e => {
    e.preventDefault();
    let blog = $("#create").val();
    console.log(blog);
    console.log(sessionStorage.getItem("email"));
    
    if($("#followers").is(':checked'))
        var access = "followers";
    else
        var access = "public"; 
    console.log(access);
    $.post("http://localhost:8080/blog/add",{
        "content" : blog,
        "author" : sessionStorage.getItem("email"),
        "access" : access
    },(data, error) => {
        if(data.response == "success"){
            $('#create').val('');
            $("#feed").empty();
            status();}
        console.log(error,data);
    });
    
});

$("#post-status").click(e => {
    e.preventDefault();
    let content = $("#status").val();
    console.log(content);
    console.log(sessionStorage.getItem("email"));

    $.post("http://localhost:8080/status/add",{
        "content" : content,
        "author" : sessionStorage.getItem("email")
    },(data, error) => {
        if(data.response == "success"){
            $('#status').val('');
            $("#feed").empty();
            status();
        }
        console.log(error,data);
    });

    
});
return;
});

//GET ALL BLOGS
let blog1 = () => {
    let body = {
        email: sessionStorage.getItem("email")                   
    };   
    $.ajax({
        url:"http://localhost:8080/blog/feed",
        data:body,
        success:(data, error) => {                 
            for(var i=0; i<data.length; i++)
            {   
                 $("#feed").append("<div class='card bg-dark m-3'>"+
                                    "<div class='card-header'>"+
                                    data[i].author+
                                    "</div>"+
                                    "<div class='card-body'>"+
                                    "<p class='card-text '>"+data[i].content+"</p>"+
                                    "</div>"+
                                    "</div>");
                 console.log(data[i].content);
            }                   
    },
    error:(result)=>{console.log("fasdsadf");} 
});
return;
}

let status = () => {
    let body = {
        email: sessionStorage.getItem("email")                   
    }; 
    $.ajax({
        url:"http://localhost:8080/status/feed",
        data:body,
        success:(data, error) => {                 
            for(var i=0; i<data.length; i++)
            {   
                 $("#feed").append("<div class='card bg-dark m-3 border border-primary'>"+
                                    "<div class='card-header'>"+
                                    data[i].author+
                                    "</div>"+
                                    "<div class='card-body'>"+
                                    "<p class='card-text '>"+data[i].content+"</p>"+
                                    "</div>"+
                                    "</div>");
                 console.log(data[i].content);
            } 
            blog1();                  
    },
    error:(result)=>{console.log("fasdsadf");}
    });
    return;    
}