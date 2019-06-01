/**
 * Created by zjm97 on 2019/5/22.
 */
var is_username_ok=false;
var is_password_ok=false;
var is_email_ok=false;
var is_phone_ok=false;
var is_captcha_ready=false;
function checkUsername(){
    var username=document.getElementById("UserName").value;
    var j={'username':username};
    var url=getQueryPath("checkUsernameAvailable");
    $.post(url,j,function(json){
        if(json.status=="ok"){
            is_username_ok=true;
            document.getElementById("username_textfield").className="mdui-textfield";
            document.getElementById("username_err_msg").innerText="用户名不能为空!";
        }else{
            is_username_ok=false;
            document.getElementById("username_err_msg").innerText=json.error;
            document.getElementById("username_textfield").className="mdui-textfield mdui-textfield-invalid-html5 mdui-textfield-has-bottom";
        }
    })
}

function checkPassword(){
    var password=document.getElementById("PassWd").value;
    var rep_password=document.getElementById("rep_PassWd").value;
    if(password!=rep_password){
        is_password_ok=false;
        document.getElementById("rep_password_textfield").className="mdui-textfield mdui-textfield-invalid-html5 mdui-textfield-has-bottom";
        document.getElementById("rep_password_err_msg").innerText="两次输入的密码不一致!";
    }else{
        is_password_ok=true;
        document.getElementById("rep_password_textfield").className="mdui-textfield";
        document.getElementById("rep_password_err_msg").innerText="重复密码不能为空!";
    }
}

function checkEmail(){
    var email=document.getElementById("email").value;
    var j={'email':email};
    var url=getQueryPath("checkEmailAvailable");
    $.post(url,j,function(json){
        if(json.status=="error"){
            is_email_ok=false;
            document.getElementById("email_textfield").className="mdui-textfield mdui-textfield-invalid-html5 mdui-textfield-has-bottom";
            document.getElementById("email_err_msg").innerHTML="此邮箱已经注册,<a href='/login'>点此登录</a>";
        }
        else{
            is_email_ok=true;
            document.getElementById("email_textfield").className="mdui-textfield";
            document.getElementById("email_err_msg").innerHTML="请填写邮箱!";
        }
    });
}

function checkPhone(){
    if(document.getElementById("phone").value.length==11){
        is_phone_ok=true;
        document.getElementById("phone_textfield").className="mdui-textfield";
    }
    else{
        is_phone_ok=false;
        document.getElementById("phone_textfield").className="mdui-textfield-invalid-html5 mdui-textfield-has-bottom";
    }
}

function checkCaptcha(){
    if(document.getElementById("jcaptcha").value.length==0){
        is_captcha_ready=false;
    }else{
        is_captcha_ready=true;
    }
}

function register(){
    if(is_email_ok&&is_password_ok&&is_phone_ok&&is_username_ok&&is_captcha_ready){
        var username=document.getElementById("UserName").value;
        var password=document.getElementById("PassWd").value;
        var email=document.getElementById("email").value;
        var phone=document.getElementById("phone").value;
        var jcaptcha=document.getElementById("jcaptcha").value;
        var j={'username':username,'password':password,'email':email,'phone':phone,'jcaptcha':jcaptcha};
        var url=getQueryPath('userRegisterAction');
        $.post(url,j,function(json){
            if(json.status=='ok'){
                alert("帐户注册成功，请前往邮箱激活帐户!");
            }else{
                alert(json.error);
            }
        })
    }
}

function initPage(){
    $.backstretch("images/p2.jpg");
}