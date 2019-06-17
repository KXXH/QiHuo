/**
 * Created by zjm97 on 2019/5/16.
 */
/**
 * Created by zjm97 on 2019/3/29.
 */

var process_dialog=new mdui.Dialog('#process');

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
    $.backstretch("images/p2.jpg");
    if(tocken!=""){
        loginWithTocken(tocken);
    }
}

function loginWithTocken(tocken){
    var url = getQueryPath("loginAction");
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
    mdui.mutation();
    //process_dialog.open();
    var url = getQueryPath("loginAction");
    var username = document.getElementById("UserName").value;
    var password = document.getElementById("PassWd").value;
    var captcha=document.getElementById("jcaptcha").value;
    //var data = '{"UserName":"'+username+'","PassWd":"'+password+'","rememberPassword":"'+document.getElementById("rememberPassword").checked+'","tocken":""}';
    var data={'UserName':username,'PassWd':password,'rememberPassword':document.getElementById("rememberPassword").checked,'token':'','jcaptcha':captcha};
    //var obj = JSON.parse(data);

    $.post(url,data,function(json){
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
        window.location.href = "index";
    }
    else{
        var type = json.error;
        console.log(type);
        if(type==1){
            alert("用户名或密码错误!");
        }
        else{
            delCookie("tocken");
        }
    }
}