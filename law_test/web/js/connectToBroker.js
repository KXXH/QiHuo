var socket ;
//登录过后初始化socket连接
function initSocket(userId) {
    if(typeof(WebSocket) == "undefined") {
        console.log("您的浏览器不支持WebSocket");
    }else {
        console.log("您的浏览器支持WebSocket/websocket");
    }
    //socket连接地址: 注意是ws协议
    var path = 'ws://' + getLocalHost() + '/XM10/Broker/' + userId;
    console.log(path)
    socket = new WebSocket(path);
    socket.onopen = function() {
        console.log("Socket 已打开");
    };
    //获得消息事件
    socket.onmessage = function(msg) {
        mdui.snackbar({
            message: msg.data
        });
    };
    //关闭事件
    socket.onclose = function() {
        console.log("Socket已关闭");
    };
    //错误事件
    socket.onerror = function() {
        alert("Socket发生了错误");
    }
    $(window).unload(function(){
        socket.close();
    });
}
//连接
function connect() {
    url = "ChatGetUserName";
    $.post(url,function(json){
        if(json.status=="error"){
            alert("无法进入！");
            window.location.href = getQueryPath("login");
        }
        else{
            var userId = json.username;
            console.log(userId);
            initSocket(userId);
        }

    })
}