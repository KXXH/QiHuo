var $$ = mdui.JQ;
var user_info;
var edit_dialog=new mdui.Dialog("#edit_dialog");
var search_dialog=new mdui.Dialog("#search_dialog");
var is_email_ok=false;
var is_username_ok=false;
var raw_username="";
var raw_email="";

edit_dialog.close();
$('input[type=radio][name=sorted_by]').change(function(){
    fetchUserInfo();
});
$('input[type=radio][name=sorted_by2]').change(function(){
    fetchUserInfo();
});
//document.getElementById("output").href="/dataToCSVAction?token="+getCookie("tocken");

function addUser(){

    console.log("add method is on");
    document.forms['user_edit_form']['user_id'].value="";
    document.forms['user_edit_form']['username'].value="";
    document.forms['user_edit_form']['email'].value="";
    document.forms['user_edit_form']['phone'].value="";
    document.forms['user_edit_form']['wechat_id'].value="";
    document.forms['user_edit_form']['role_id_radio'].value="normal";
    document.getElementById("password_box").style.display="block";
    var url=getQueryPath("editUserPermissionAction");
    $.post(url,function(json){
        console.log(JSON.stringify(json));
        init_role_id_radio();
        for(var i=0;i<json.choice.length;i++){
            console.log(json.choice[i]);
            var option=document.getElementById("role_id_radio_"+json.choice[i]);
            option.removeAttribute("disabled");
            option.parentNode.removeAttribute("mdui-tooltip");
        }});
    is_email_ok=false;
    is_username_ok=false;
    document.getElementById("btn_submit").onclick=function(){addUserInfo();};
    document.getElementById("btn_submit").innerText='提交';
    document.getElementById("username_textfield").className="mdui-textfield";
    document.getElementById("username_edit").onblur=function(){checkUsername(false);};
    document.getElementById("email_edit").onblur=function(){checkEmail(false);};
    edit_dialog.open();
}
function submitEdit(){
    if(document.forms['user_edit_form']['user_id'].value.length==0){
        checkEmail(false);
        checkUsername(false);
    }else{
        checkEmail(true);
        checkUsername(true);
    }
    if(!(is_email_ok&&is_username_ok)){
        return;
    }
    console.log(document.forms['user_edit_form']['user_id'].value.length);
    if(document.forms['user_edit_form']['user_id'].value.length==0){addUserInfo();}
    else{updateUserInfo();}
}
function addUserInfo(){
    checkEmail(false);
    checkUsername(false);
    if(!(is_email_ok&&is_username_ok)){
        return;
    }
    var username=document.forms['user_edit_form']['username'].value;
    var email=document.forms['user_edit_form']['email'].value;
    var phone=document.forms['user_edit_form']['phone'].value;
    var wechat_id=document.forms['user_edit_form']['wechat_id'].value;
    var role_id=document.forms['user_edit_form']['role_id_radio'].value;
    var password=document.forms['user_edit_form']['password'].value;
    var token=getCookie("tocken");
    var j={'username':username,'email':email,'phone':phone,'wechat_id':wechat_id,'role_id':role_id,'token':token,'password':password};
    var url=getQueryPath('userAddAction');
    $.post(url,j,function(json){
        if(json.status=="ok"){
            mdui.snackbar({'message':'添加成功!'});
            edit_dialog.close();
        }
        else{
            mdui.snackbar({'message':'添加失败!'});
        }
        edit_dialog.close();
    })
}
function  drawChart111(json){
    console.log('开始绘图');
    am4core.useTheme(am4themes_animated);
    var chart = am4core.create("chartdiv1", am4charts.XYChart);
    chart.data=json.data;
    chart.dateFormatter.inputDateFormat = "yyyy-MM-dd";

// Create axes
    var dateAxis = chart.xAxes.push(new am4charts.DateAxis());
    var valueAxis = chart.yAxes.push(new am4charts.ValueAxis());

// Create series
    var series = chart.series.push(new am4charts.LineSeries());
    series.dataFields.valueY = "value";
    series.dataFields.dateX = "date";
    series.tooltipText = "{value}"
    series.strokeWidth = 2;
    series.minBulletDistance = 15;

// Drop-shaped tooltips
    series.tooltip.background.cornerRadius = 20;
    series.tooltip.background.strokeOpacity = 0;
    series.tooltip.pointerOrientation = "vertical";
    series.tooltip.label.minWidth = 40;
    series.tooltip.label.minHeight = 40;
    series.tooltip.label.textAlign = "middle";
    series.tooltip.label.textValign = "middle";

// Make bullets grow on hover
    var bullet = series.bullets.push(new am4charts.CircleBullet());
    bullet.circle.strokeWidth = 2;
    bullet.circle.radius = 4;
    bullet.circle.fill = am4core.color("#fff");

    var bullethover = bullet.states.create("hover");
    bullethover.properties.scale = 1.3;

// Make a panning cursor
    chart.cursor = new am4charts.XYCursor();
    chart.cursor.behavior = "panXY";
    chart.cursor.xAxis = dateAxis;
    chart.cursor.snapToSeries = series;

// Create vertical scrollbar and place it before the value axis
    chart.scrollbarY = new am4core.Scrollbar();
    chart.scrollbarY.parent = chart.leftAxesContainer;
    chart.scrollbarY.toBack();

// Create a horizontal scrollbar with previe and place it underneath the date axis
    chart.scrollbarX = new am4charts.XYChartScrollbar();
    chart.scrollbarX.series.push(series);
    chart.scrollbarX.parent = chart.bottomAxesContainer;

    chart.events.on("ready", function () {
        dateAxis.zoom({start:0.79, end:1});
    });


}

function createDAUChart(data){
    am4core.useTheme(am4themes_animated);
    var chart = am4core.create("dauChartDiv", am4charts.XYChart);
    chart.data=data;
    chart.dateFormatter.inputDateFormat = "yyyy-MM-dd";

// Create axes
    var dateAxis = chart.xAxes.push(new am4charts.DateAxis());
    var valueAxis = chart.yAxes.push(new am4charts.ValueAxis());

// Create series
    var series = chart.series.push(new am4charts.LineSeries());
    series.dataFields.valueY = "value";
    series.dataFields.dateX = "date";
    series.tooltipText = "{value}"
    series.strokeWidth = 2;
    series.minBulletDistance = 15;

// Drop-shaped tooltips
    series.tooltip.background.cornerRadius = 20;
    series.tooltip.background.strokeOpacity = 0;
    series.tooltip.pointerOrientation = "vertical";
    series.tooltip.label.minWidth = 40;
    series.tooltip.label.minHeight = 40;
    series.tooltip.label.textAlign = "middle";
    series.tooltip.label.textValign = "middle";

// Make bullets grow on hover
    var bullet = series.bullets.push(new am4charts.CircleBullet());
    bullet.circle.strokeWidth = 2;
    bullet.circle.radius = 4;
    bullet.circle.fill = am4core.color("#fff");

    var bullethover = bullet.states.create("hover");
    bullethover.properties.scale = 1.3;

// Make a panning cursor
    chart.cursor = new am4charts.XYCursor();
    chart.cursor.behavior = "panXY";
    chart.cursor.xAxis = dateAxis;
    chart.cursor.snapToSeries = series;

// Create vertical scrollbar and place it before the value axis
    chart.scrollbarY = new am4core.Scrollbar();
    chart.scrollbarY.parent = chart.leftAxesContainer;
    chart.scrollbarY.toBack();

// Create a horizontal scrollbar with previe and place it underneath the date axis
    chart.scrollbarX = new am4charts.XYChartScrollbar();
    chart.scrollbarX.series.push(series);
    chart.scrollbarX.parent = chart.bottomAxesContainer;

    chart.events.on("ready", function () {
        dateAxis.zoom({start:0.79, end:1});
    });

}

function createChart(){
    am4core.useTheme(am4themes_material);
    var token=getCookie("tocken");
    var j={'token':token};
    var url=getQueryPath('userStatisticAction');
    $.post(url,j,function(json){
        console.log(JSON.stringify(json));
        drawChart111(json);
        createDAUChart(json.dauData);
    });

}

function searchUser(){
    var sort=$('input[type=radio][name=sorted_by]:checked').val();
    var sort2=$('input[type=radio][name=sorted_by2]:checked').val();
    var username=document.forms['user_search_form']['username_search'].value;
    var token=getCookie('tocken');
    var url=getQueryPath('userQueryAction');
    var j={'token':token,'username':username,'sorted_by':sort,'sorted_by2':sort2};
    $.post(url,j,function(json){
        user_info=json.list;
        updateTable(json);
        search_dialog.close();
    })
}

function updateUserInfo(){
    checkEmail(true);
    checkUsername(true);
    if(!(is_email_ok&&is_username_ok)){
        return;
    }
    var user_id=document.forms['user_edit_form']['user_id'].value;
    var username=document.forms['user_edit_form']['username'].value;
    var email=document.forms['user_edit_form']['email'].value;
    var phone=document.forms['user_edit_form']['phone'].value;
    var wechat_id=document.forms['user_edit_form']['wechat_id'].value;
    var role_id=document.forms['user_edit_form']['role_id_radio'].value;
    var token=getCookie("tocken");
    var j={'user_id':user_id,'username':username,'email':email,'phone':phone,'wechat_id':wechat_id,'role_id':role_id,'token':token};
    var url=getQueryPath('userEditAction');
    $.post(url,j,function(json){
        if(json.status=="ok"){
            mdui.snackbar({'message':'修改成功!'});
            edit_dialog.close();
        }
        else{
            mdui.snackbar({'message':'修改失败!'});
        }

    })
}

function delUserInfo(i){
    mdui.confirm("您确认要删除这个用户吗?<br>注意：删除操作是不可逆的，请仔细考虑!","删除确认",function(){
        var user_id=user_info[i].user_id;
        console.log(user_id);
        var j={'user_id':user_id,'token':getCookie('tocken')};
        console.log(JSON.stringify(j));
        var url=getQueryPath('userDeleteAction');
        $.post(url,j,function(json){
            console.log(JSON.stringify(json));
            if(json.status=="ok"){
                mdui.snackbar({'message':'删除成功!'});
                fetchUserInfo();
            }
            else if(json.status=="error"&&json.code==123){
                mdui.alert("您没有删除这一级别用户的权限，建议您联系高级管理员处理!","权限错误");
            }
            else{
                mdui.snackbar({'message':'删除失败!'});
            }
        })
    },function(){
        ;
    });
}

function editUser(i){


    document.forms['user_edit_form']['user_id'].value=user_info[i].user_id;
    document.forms['user_edit_form']['username'].value=user_info[i].username;
    raw_username=user_info[i].username;

    document.forms['user_edit_form']['email'].value=user_info[i].email;
    raw_email=user_info[i].email;
    document.forms['user_edit_form']['phone'].value=user_info[i].phone;
    document.forms['user_edit_form']['wechat_id'].value=user_info[i].wechat_id;
    document.forms['user_edit_form']['role_id_radio'].value=user_info[i].role_id;
    document.getElementById("password_box").style.display="none";
    var j={'user_id':user_info[i].user_id};
    var url=getQueryPath("editUserPermissionAction");
    $.post(url,j,function(json){
        console.log(JSON.stringify(json));
        if(json.status=="ok"){
            init_role_id_radio();
            for(var i=0;i<json.choice.length;i++){
                console.log(json.choice[i]);
                var option=document.getElementById("role_id_radio_"+json.choice[i]);
                option.removeAttribute("disabled");
                option.parentNode.removeAttribute("mdui-tooltip");
                is_email_ok=false;
                is_username_ok=false;
                document.getElementById("btn_submit").onclick=function(){updateUserInfo();};
                document.getElementById("username_textfield").className="mdui-textfield";
                document.getElementById("username_edit").onblur=function(){checkUsername(true);};
                document.getElementById("email_edit").onblur=function(){checkEmail(true);};
                edit_dialog.open();
            }
        }else if(json.status=="error"&&json.code==123){
            mdui.alert("您不具有修改这一级别用户的权限，建议联系高级管理员处理!","权限错误");
        }


    });

}


function init_role_id_radio(){
    var options=document.getElementsByName("role_id_radio");
    for(var i=0;i<options.length;i++){
        options[i].setAttribute("disabled",true);
        options[i].parentNode.setAttribute("mdui-tooltip","{content:'您暂时没有权限选择这个选项'}");
    }
}

function fetchUserInfo(){
    var sort=$('input[type=radio][name=sorted_by]:checked').val();
    var sort2=$('input[type=radio][name=sorted_by2]:checked').val();
    console.log(sort2);
    var t={'token':getCookie("tocken"),'sorted_by':sort,'sorted_by2':sort2};
    var url=getQueryPath("userQueryAction");
    $.post(url,t,function(json){
        console.log("收到JSON");
        console.log(JSON.stringify(json));
        user_info=json.list;
        updateTable(json);

    });
    createChart();
}

function updateTable(json){
    user_info=json.list;
    var table = document.getElementById('user_list');
    table.innerHTML="";
    for(var i=0;i<json.list.length;i++){
        var row = document.createElement('tr');
        var userId = document.createElement('td');
        userId.innerText=json.list[i].user_id;
        row.appendChild(userId);
        var username = document.createElement('td');
        username.innerText=json.list[i].username;
        row.appendChild(username);
        var email = document.createElement('td');
        email.innerText=json.list[i].email;
        row.appendChild(email);
        var phone = document.createElement('td');
        phone.innerText=json.list[i].phone;
        row.appendChild(phone);
        var wechat_id = document.createElement('td');
        wechat_id.innerText=json.list[i].wechat_id;
        row.appendChild(wechat_id);
        var create_at = document.createElement('td');
        create_at.innerText=json.list[i].create_at;
        row.appendChild(create_at);
        var role_id = document.createElement('td');
        role_id.innerText=json.list[i].role_id;
        row.appendChild(role_id);
        var buttonBox = document.createElement('td');
        var button = document.createElement('button');
        button.className="mdui-btn mdui-color-theme-accent"
        button.setAttribute("mdui-menu","{target:'#userMenu"+i+"',position:'bottom',fixed:true}");
        button.innerText="编辑";


        var menu = document.createElement('ul');
        menu.id="userMenu"+i;
        menu.className="mdui-menu";
        var item = document.createElement('li');
        item.className="mdui-menu-item";
        item.onclick="deleteUser("+i+")";
        item.innerHTML="<a onclick='delUserInfo("+i+")'>删除</a>";
        menu.appendChild(item);
        var item = document.createElement('li');
        item.className="mdui-menu-item";
        item.innerHTML="<a onclick='editUser("+i+")'>修改</a>";
        menu.appendChild(item);
        buttonBox.appendChild(button);
        buttonBox.appendChild(menu);
        row.appendChild(buttonBox);
        table.appendChild(row);
    }
    mdui.updateTables("#user_list");
}

function checkUsername(flag){

    var username=document.getElementById("username_edit").value;
    if(flag&&raw_username==username){
        is_username_ok=true;
        document.getElementById("username_textfield").className="mdui-textfield";
        document.getElementById("username_err_msg").innerText="用户名不能为空!";
        return;
    }
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

function checkEmail(flag){
    var email=document.getElementById("email_edit").value;
    if(flag&&raw_email==email){
        is_email_ok=true;
        document.getElementById("email_textfield").className="mdui-textfield";
        document.getElementById("email_err_msg").innerHTML="请填写邮箱!";
        return;
    }

    var j={'email':email};
    var url=getQueryPath("checkEmailAvailable");

    $.post(url,j,function(json){
        if(json.status=="error"){
            is_email_ok=false;
            document.getElementById("email_textfield").className="mdui-textfield mdui-textfield-invalid-html5 mdui-textfield-has-bottom";
            document.getElementById("email_err_msg").innerHTML="此邮箱已经注册";
        }
        else{
            is_email_ok=true;
            document.getElementById("email_textfield").className="mdui-textfield";
            document.getElementById("email_err_msg").innerHTML="请填写邮箱!";
        }
    });
}

