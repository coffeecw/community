function post() {
    var questionId = $("#question_id").val();
    var content = $("#text-comment").val();
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType:"application/json",
        data: JSON.stringify(
            {
            "parentId":questionId,
            "content":content,
            "type":1
            }),
        success: function (response) {
            if(response.code==200){
                $("#comment_section").hide();
            }else{
                if(response.code==1002){
                    var isAccepted = confirm(response.message);
                    if(isAccepted){
                        window.open("https://github.com/login/oauth/authorize?client_id=fa9efdea9271da2c50a6&redirect_uri=http://localhost:8888/callback&scope=user&state=1");
                        //web存储
                        window.localStorage.setItem("closeable",true);
                    }else{
                        alert(response.message);
                    }
                }
            }
           console.log(response);
        },
        dataType: "json"
    });
    console.log(questionId);
}