function post() {
    var parent_id = $("#com_question").val();
    var commentContent = $("#comment_content").val();
    var comment_parent = $("#comment_parent").val();
    if (!commentContent) {
        alert("回复内容不能为空");
        return;
    }

    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: 'application/json',
        data: JSON.stringify({
            "parent:": parent_id,
            "content": commentContent,
            "type": 1,
            "id": comment_parent
        }),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();
            } else {
                // 未登陆情况
                if (response.code == 2003) {
                    // 确认登陆
                    var is_accepted = confirm(response.message);
                    if (is_accepted) {
                        window.open("https://github.com/login/oauth/authorize?client_id=f2cd43e0f2411e6b3ff8&redirect_uri=http://117.50.19.236/callback&scope=user&state=1");
                        window.localStorage.setItem("closable", "true");
                    } else {
                        alert(response.message);
                    }
                }
            }
            console.log(response);
        },
        dataType: "json"
    });
}

function comment(e) {
    var commentId = e.getAttribute("data-id");
    console.log(commentId);
    var realId = e.getAttribute("data-id");
    var content = $("#input-" + commentId).val();
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: 'application/json',
        data: JSON.stringify({
            "parent:": commentId,
            "content": content,
            "type": 2,
            "id": realId
        }),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();
            } else {
                // 未登陆情况
                if (response.code == 2003) {
                    // 确认登陆
                    var is_accepted = confirm(response.message);
                    if (is_accepted) {
                        window.open("https://github.com/login/oauth/authorize?client_id=f2cd43e0f2411e6b3ff8&redirect_uri=http://117.50.19.236/callback&scope=user&state=1");
                        window.localStorage.setItem("closable", "true");
                    } else {
                        alert(response.message);
                    }
                }
            }
            console.log(response);
        },
        dataType: "json"
    });
}


/*
展开二级评论
 */
function collapseContent(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);


    // 折叠评论
    if (comments.hasClass("in")) {

        comments.removeClass("in");
        e.classList.remove("active")
    }
    // 展开评论
    else {
        $.getJSON("/comment/" + id, function (data) {
                var subCommentContainer = $("#comment-" + id);
                // 子元素包含回复框和回复，不等于1表示有回复内容
                if (subCommentContainer.children().length != 1) {

                    comments.addClass("in");
                    e.classList.add("active");
                } else {
                    $.each(data.data.reverse(), function (index, comment) {

                        var contentElement = $("<div/>", {
                            "class": "add-margin"
                        }).append($("<div/>", {
                            "text": comment.content
                        }));
                        var gmtCreateElement = $("<div/>", {
                            "class": "menu"
                        }).append($("<span/>", {
                            "class": "pull-right icon",
                            "html": moment(comment.gmtCreate).format('YYYY-MM-DD')
                        }));

                        var mediaLeftElement = $("<div/>", {
                            "class": "media-left"
                        }).append($("<img/>", {
                            "class": "media-object img-rounded",
                            "src": comment.user.avatarUrl
                        }));

                        var mediaBodyElement = $("<div/>", {
                            "class": "media-body customize-media-body add-margin"
                        }).append($("<h4/>", {
                            "class": "media-heading"
                        }).append($("<span/>", {
                            "text": comment.user.name
                        }).append(mediaLeftElement)));

                        var mediaElement = $("<div/>", {
                            "class": "media"
                        }).append(mediaLeftElement)
                            .append(mediaBodyElement)
                            .append(contentElement)
                            .append(gmtCreateElement);


                        var commentElement = $("<div/>", {
                            "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                        });
                        commentElement.append(mediaElement);
                        subCommentContainer.prepend(commentElement);
                    });
                    comments.addClass("in");
                    e.classList.add("active");
                }
            }
        );

    }
}

function selectTag(e) {
    var value = e.getAttribute("data-tag");
    var previous = $("#tag").val();
    // 判断是否已经包含
    if (previous.indexOf(value) == -1) {
        if (previous) {
            $("#tag").val(previous + ',' + value)
        } else {
            $("#tag").val(value);
        }
    } else {
    }
}

function showClickTag() {
    $("#select-tag").show();

}