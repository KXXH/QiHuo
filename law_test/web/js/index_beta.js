/**
 * Created by zjm97 on 2019/3/29.
 */
function getQueryVariable(variable)
{
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i=0;i<vars.length;i++) {
        var pair = vars[i].split("=");
        if(pair[0] == variable){return pair[1];}
    }
    return(false);
}

function getCookie(cname)
{
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++)
    {
        var c = ca[i].trim();
        if (c.indexOf(name)==0) return c.substring(name.length,c.length);
    }
    return "";
}

function delCookie(name)
{
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval=getCookie(name);
    if(cval!=null)
        document.cookie= name + "="+cval+";expires="+exp.toGMTString();
}

function logOut(){
    var url = getQueryPath("logoutAction");
    var data = '{"tocken":"'+getCookie("tocken")+'"}';
    var obj = JSON.parse(data);
    $.post(url,obj);
    delCookie("tocken");
    delCookie("token");
    window.location.href = getQueryPath("login");

}

function initPage(){
    document.getElementById("loader").style.display="block";
    mdui.mutation();
    getMenu();
}
function initMenu(json) {
    document.getElementById("loader").style.display = "none";
    var list = json.aaData;
    var html = "";
    var roots = [];
    var collapse = mdui.Collapse("drawer");
    try {
        for (var i = 0; i < list.length; i++) {
            console.log(list[i][3]);
            if (list[i][3] == 0) {
                var rootItem = document.createElement("div");
                rootItem.id = "RootItem" + i;
                rootItem.className = "mdui-collapse-item mdui-collapse-item-open";
                var title = document.createElement("div");
                if (list[i][1]) {
                    title.className = "mdui-list-item mdui-ripple";
                    title.innerHTML = '<i class="mdui-list-item-icon mdui-icon material-icons">home</i>';
                    title.innerHTML += '<a class="mdui-list-item-content" href="' + list[i][1] + '">' + list[i][2] + '</div>';
                    title.href = list[i][1];
                }
                else {
                    title.className = "mdui-list-item mdui-ripple mdui-collapse-item-header";
                    //title.innerHTML='<div class=" mdui-list-item mdui-ripple">';
                    title.innerHTML = '<i class="mdui-list-item-icon mdui-icon material-icons mdui-text-color-blue">near_me</i>';
                    title.innerHTML += '<div class="mdui-list-item-content">' + list[i][2] + '</div>';
                    title.innerHTML += '<i class="mdui-collapse-item-arrow mdui-icon material-icons">keyboard_arrow_down</i>';
                }
                var body = document.createElement("div");
                body.className = "mdui-collapse-item-body mdui-list";
                body.style.height = "auto";

                //title.innerHTML+='</div>';
                rootItem.appendChild(title);
                rootItem.appendChild(body);
                roots.push(rootItem);
                //html+="<div class=\"mdui-collapse-item\"><a href="+list[i][1]+" class=\"mdui-list-item mdui-ripple\">"+list[i][2]+" </a> </div>";
            }
        }

    }catch (e){
        var error = json.code;
        if(error==123) {
            alert("登录已过期，请重新登录");
            delCookie("tocken");
            logOut();
            return;
        }
    }
    for(var i=0;i<list.length;i++){
        if(list[i][3]!=0){
            console.log("正在执行第"+i+"个内容");
            var secondItem = document.createElement("a");
            secondItem.href = list[i][1];
            secondItem.className = "mdui-list-item mdui-ripple";
            secondItem.innerHTML=list[i][2];
            var index = list[i][3]-1;
            roots[index].lastChild.appendChild(secondItem);
        }
    }
    for(var i=0;i<roots.length;i++){
        document.getElementById("drawer").appendChild(roots[i]);
        document.getElementById("RootItem"+i).click();
    }

    mdui.mutation();
    var collapse = mdui.Collapse("drawer");

    collapse.closeAll();
}
function getMenu(){
    url="query";
    var data;
    if(getCookie("tocken")!=""){
        data = '{"tocken":"'+getCookie("tocken")+'"}';
    }
    else{
        data = '{"tocken":"'+getQueryVariable("tocken")+'"}';
    }
    console.log(data);
    var obj=JSON.parse(data);
    $.post(url,obj, function(json){
        initMenu(json);
        console.log(JSON.stringify(json));
    })
}