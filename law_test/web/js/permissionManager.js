var permission_classname="";
var permission_count=0;
var permission_list=[];
var check='<i class="mdui-icon material-icons">check</i>';
var close='<i class="mdui-icon material-icons">close</i>';
var edit_dlg=new mdui.Dialog("#permission_edit_dialog");
function showEditDlg(row){
    clearEditDlg();
    document.forms['permission_edit']['id'].value=row.cells[0].innerText;
    document.forms['permission_edit']['classname'].value=row.cells[1].innerText;
    document.getElementById('confirm_btn').onclick=function(){submitEdit();};
    document.getElementById('delete_btn').removeAttribute("style");
    document.getElementById('permission_id_textfield').removeAttribute("style");
    document.getElementById('confirm_btn').innerText='修改';
    if(row.cells[2].innerHTML==check){
        document.getElementById("permission_checkbox_super_admin").checked=true;
    }
    if(row.cells[3].innerHTML==check){
        document.getElementById("permission_checkbox_admin").checked=true;
    }
    if(row.cells[4].innerHTML==check){
        document.getElementById("permission_checkbox_normal").checked=true;
    }
    if(row.cells[5].innerHTML==check){
        document.getElementById("permission_checkbox_unchecked").checked=true;
    }
    if(row.cells[6].innerHTML==check){
        document.getElementById("permission_checkbox_others").checked=true;
    }
    edit_dlg.open();
}

function showAddDlg(){
    clearEditDlg();
    document.getElementById('confirm_btn').onclick=function(){addPermission();};
    document.getElementById('delete_btn').style.display="none";
    document.getElementById('permission_id_textfield').style.display="none";
    document.getElementById('confirm_btn').innerText='添加';
    edit_dlg.open();
}

function clearEditDlg(){
    document.forms['permission_edit']['id'].value="";
    document.forms['permission_edit']['classname'].value="";
    document.getElementById("permission_checkbox_super_admin").checked=false;
    document.getElementById("permission_checkbox_admin").checked=false;
    document.getElementById("permission_checkbox_normal").checked=false;
    document.getElementById("permission_checkbox_unchecked").checked=false;
    document.getElementById("permission_checkbox_others").checked=false;
}

function clearPermissionTable(){
    var table=document.getElementById("permission_table");
    var rowCount=table.rows.length;
    for(var i=rowCount-1;i>0;i--){
        table.deleteRow(i);
    }
    mdui.updateTables("#permission_table");
}

function updatePermissionTable(data){
    for(var i=0;i<data.length;i++){
        var row=document.getElementById("permission_table").insertRow();
        row.onclick=function(){showEditDlg(this);};
        row.insertCell(0).innerHTML=data[i].id;
        row.insertCell(1).innerHTML=data[i].classname;
        if((data[i].permission_code&16)!=0){
            row.insertCell(2).innerHTML=check;
        }else{
            row.insertCell(2).innerHTML=close;
        }
        if((data[i].permission_code&8)!=0){
            row.insertCell(3).innerHTML=check;
        }else{
            row.insertCell(3).innerHTML=close;
        }
        if((data[i].permission_code&4)!=0){
            row.insertCell(4).innerHTML=check;
        }else{
            row.insertCell(4).innerHTML=close;
        }
        if((data[i].permission_code&2)!=0){
            row.insertCell(5).innerHTML=check;
        }else{
            row.insertCell(5).innerHTML=close;
        }
        if((data[i].permission_code&1!=0)){
            row.insertCell(6).innerHTML=check;
        }else{
            row.insertCell(6).innerHTML=close;
        }

    }
    mdui.updateTables("#login_table");
}

function loadMorePermission(){
    var j={'classname':permission_classname,'count':permission_count};
    var url=getQueryPath('getPermissionAction');
    $.post(url,j,function(json){
        if(json.status=="ok"){
            updatePermissionTable(json.data);
            permission_count=json.count;
        }else if(json.status=="error"){
            mdui.alert(json.error,"错误");
        }else if(json.status=="end"){
            mdui.snackbar("已经到达最后了!");
            updatePermissionTable(json.data);
        }
    })
}

function queryPermission(){
    mdui.prompt('请输入查询的类名:','查找',function(value){
        permission_classname=value;
        permission_count=0;
        clearPermissionTable();
        loadMorePermission();
    });
}

function submitEdit(){
    var classname=document.forms['permission_edit']['classname'].value;
    var id=document.forms['permission_edit']['id'].value;
    var code=0;
    if(document.getElementById('permission_checkbox_super_admin').checked){
        code=code|16;
    }
    if(document.getElementById('permission_checkbox_admin').checked){
        code=code|8;
    }
    if(document.getElementById('permission_checkbox_normal').checked){
        code=code|4;
    }
    if(document.getElementById('permission_checkbox_unchecked').checked){
        code=code|2;
    }
    if(document.getElementById('permission_checkbox_others').checked){
        code=code|1;
    }
    var url=getQueryPath('editPermissionAction');
    var j={'permission_code':code,'id':id,'classname':classname};
    $.post(url,j,function(json){
        edit_dlg.close();
        if(json.status=='ok'){
            mdui.snackbar("修改成功!");
            permission_count=0;
            clearPermissionTable();
            loadMorePermission();

        }else{
            mdui.alert(json.error,"错误");
        }
    });

}

function addPermission(){
    var classname=document.forms['permission_edit']['classname'].value;
    var code=0;
    if(document.getElementById('permission_checkbox_super_admin').checked){
        code=code|16;
    }
    if(document.getElementById('permission_checkbox_admin').checked){
        code=code|8;
    }
    if(document.getElementById('permission_checkbox_normal').checked){
        code=code|4;
    }
    if(document.getElementById('permission_checkbox_unchecked').checked){
        code=code|2;
    }
    if(document.getElementById('permission_checkbox_others').checked){
        code=code|1;
    }
    var url=getQueryPath('addPermissionAction');
    var j={'permission_code':code,'classname':classname};
    $.post(url,j,function(json){
        edit_dlg.close();
        if(json.status=='ok'){
            mdui.snackbar("添加成功!");
            permission_count=0;
            clearPermissionTable();
            loadMorePermission();

        }else{

            mdui.alert(json.error,"错误");
        }
    });
}

function submitDelete(){
    var id=document.forms['permission_edit']['id'].value;
    console.log(id);
    var url=getQueryPath('delPermissionAction');
    var js={'id':id};
    console.log(JSON.stringify(js));
    $.post(url,js,function(json){
        edit_dlg.close();
        if(json.status=='ok'){
            mdui.snackbar("删除成功!");
            permission_count=0;
            clearPermissionTable();
            loadMorePermission();
        }else{
            mdui.alert(json.error,"错误");
        }
    });
}
/**
 * Created by zjm97 on 2019/5/25.
 */
