function post() {
    var questionId = $("#question_id").val();
    var content = $("#text-comment").val();
    //js前端校验回复内容
    if(!content){//js中content内容为空返回false
        alert("回复的内容不能为空！");
        return;
    }

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
                //刷新当前页面
                window.location.reload();
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