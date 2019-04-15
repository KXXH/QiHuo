/**
 * Created by zjm97 on 2019/3/29.
 */

function setCookieDays(cname,cvalue,exdays)
{
    var d = new Date();
    d.setTime(d.getTime()+(exdays*24*60*60*1000));
    var expires = "expires="+d.toGMTString();
    document.cookie = cname + "=" + cvalue + "; " + expires;
}

function setCookieMins(cname,cvalue,exmins){
    var d = new Date();
    d.setTime(d.getTime()+(exmins*60*1000));
    var expires = "expires="+d.toGMTString();
    document.cookie = cname + "=" + cvalue + "; " + expires;
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
    delCookie("tocken");
}

function initPage(){
    var tocken = getCookie("tocken");
    if(tocken!=""){
        loginWithTocken(tocken);
    }
}

function loginWithTocken(tocken){
    var url = "/loginAction";
    var username = document.getElementById("UserName").value;
    var password = document.getElementById("PassWd").value;
    var data = '{"UserName":"'+username+'","PassWd":"'+password+'","rememberPassword":"'+document.getElementById("rememberPassword").checked+'","tocken":"'+tocken+'"}';
    var obj = JSON.parse(data);

    $.post(url,obj,function(json){
        checkResult(json);
    })

    mdui.mutation();
}

function loginTask(){
    var url = "/loginAction";
    var username = document.getElementById("UserName").value;
    var password = document.getElementById("PassWd").value;
    var data = '{"UserName":"'+username+'","PassWd":"'+password+'","rememberPassword":"'+document.getElementById("rememberPassword").checked+'","tocken":""}';
    var obj = JSON.parse(data);

    $.post(url,obj,function(json){
        checkResult(json);
    })

    mdui.mutation();
}

function checkResult(json){
    var result=json.status;
    if(result=="success"){

        if(document.getElementById("rememberPassword").checked){
            setCookieDays("tocken",json.tocken,10);
        }
        else{
            setCookieMins("tocken",json.tocken,5);
        }
        window.location.href = "index_beta.html";
    }
    else{
        var type = json.error;
        if(type==1){
            alert("用户名或密码错误!");
        }
        else{
            delCookie("tocken");
        }
    }
}