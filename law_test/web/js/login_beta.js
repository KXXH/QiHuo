/**
 * Created by zjm97 on 2019/3/29.
 */

var process_dialog=new mdui.Dialog('#process');

document.getElementById("process").addEventListener('closed.mdui.dialog', function () {

    document.getElementById("body").style.width="100%";
    console.log('closed');
});

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
    //var height=document.body.scrollHeight;
    $.backstretch("images/p2.jpg");
    //document.getElementById("container").style.height=height+"px";
    if(tocken!=""){
        loginWithTocken(tocken);
    }
}

function loginWithTocken(tocken){
    var url=getQueryPath('loginAction');
    var username = document.getElementById("UserName").value;
    var password = document.getElementById("PassWd").value;
    var data = '{"UserName":"'+username+'","PassWd":"'+password+'","rememberPassword":"'+document.getElementById("rememberPassword").checked+'","tocken":"'+tocken+'"}';
    var obj = JSON.parse(data);
    obj={'UserName':username,'PassWd':password,'rememberPassword':document.getElementById("rememberPassword").checked,'tocken':tocken,'jcaptcha':''};
    $.post(url,obj,function(json){
        checkResult(json);
    })

    mdui.mutation();
}

function loginTask(){
    mdui.mutation();
    //process_dialog.open();
    var url = getQueryPath('loginAction');
    var username = document.getElementById("UserName").value;
    var password = document.getElementById("PassWd").value;
    //var data = '{"UserName":"'+username+'","PassWd":"'+password+'","rememberPassword":"'+document.getElementById("rememberPassword").checked+'","tocken":""}';
    var data={'UserName':username,'PassWd':password,'rememberPassword':document.getElementById("rememberPassword").checked,'token':'','jcaptcha':document.getElementById("jcaptcha").value};
    //var obj = JSON.parse(data);

    $.post(url,data,function(json){
        checkResult(json);
    })
    refreshCaptcha();
    mdui.mutation();

}

function checkResult(json){
    var result=json.status;
    if(result=="success"){
        /*
        if(document.getElementById("rememberPassword").checked){
            setCookieDays("tocken",json.tocken,30);
        }
        else{
            setCookieMins("tocken",json.tocken,5);
        }*/
        window.location.href = "index";
    }
    else{
        var type = json.error;
        console.log(type);
        if(type==1){
            alert("用户名或密码错误!");
        }else if(type==10){
            alert("请填写验证码后继续!");
            document.getElementById("captcha_div").style.display="block";
            refreshCaptcha();
            //window.location.href="login";
        }else if(type==11){
            alert("验证码错误!");
            document.getElementById("captcha_div").style.display="block";
            refreshCaptcha();
        }
        else{
            delCookie("tocken");
        }
        document.getElementById("body").style.width="100%";
    }
}

function refreshCaptcha(){
    document.getElementById("captcha_img").src='jcaptcha.jpg?'+Math.random();
}