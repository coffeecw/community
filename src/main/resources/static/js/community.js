/**
 * 提交回复
 */
function post() {
    var questionId = $("#question_id").val();
    var content = $("#text-comment").val();
   comment2Target(questionId,1,content);
}

function comment2Target(questionId,type,content) {
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
                "type":type
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

function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-"+commentId).val();
    comment2Target(commentId,2,content);
}

/**
 * 展开二级评论
 */
function collapseComments(e) {
    //获取评论的id
    var id = e.getAttribute("data-id");
    var comments = $("#comment-"+id);
    //获取二级评论的展开状态
    var collapse = e.getAttribute("collapse-data");
    if(collapse){
        //折叠二级评论
        comments.removeClass("in");
        e.removeAttribute("collapse-data");
        e.classList.remove("active");
    }else{
        var subCommentContainer = $("#comment-" + id);
        if (subCommentContainer.children().length != 1) {
            //展开二级评论
            comments.addClass("in");
            // 标记二级评论展开状态
            e.setAttribute("collapse-data", "in");
            e.classList.add("active");
        } else {//将请求的数据通过js渲染到页面
            $.getJSON("/comment/" + id, function (data) {
                $.each(data.data.reverse(), function (index, comment) {
                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left"
                    }).append($("<img/>", {
                        "class": "media-object img-rounded",
                        "src": comment.user.avatarUrl
                    }));

                    var mediaBodyElement = $("<div/>", {
                        "class": "media-body"
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html": comment.user.name
                    })).append($("<div/>", {
                        "html": comment.content
                    })).append($("<div/>", {
                        "class": "menu"
                    }).append($("<span/>", {
                        "class": "pull-right",
                        "html": moment(comment.gmtCreate).format('YYYY-MM-DD')
                    })));

                    var hrElement = $("<hr/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12",
                        "style":"margin-top:0px"
                    });

                    var mediaElement = $("<div/>", {
                        "class": "media"
                    }).append(mediaLeftElement).append(mediaBodyElement).append(hrElement);

                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                    }).append(mediaElement);


                    subCommentContainer.prepend(commentElement);
                });
                //展开二级评论
                comments.addClass("in");
                // 标记二级评论展开状态
                e.setAttribute("collapse-data", "in");
                e.classList.add("active");
            });
        }

    }
    console.log(id);
}