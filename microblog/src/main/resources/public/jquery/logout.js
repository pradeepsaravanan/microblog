$(function(){
    $("#logout").click((e) => {
        e.preventDefault();
        
        sessionStorage.clear();
        let url = "http://localhost:8080/index.html";
        $(location).attr('href', url);
        return;
    });

});

