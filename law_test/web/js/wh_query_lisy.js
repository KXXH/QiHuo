var wh_info;
var id;
function add(){
    window.location.href="wh_register.html";
}
function modify(id){
    window.location.href = "wh_change.html?StockId="+id;

}
function home(){
    window.location="wh_query_list.html";
}
function delet(id){
    mdui.confirm("您确认要删除这个用户吗?<br>注意：删除操作是不可逆的，请仔细考虑!","删除确认",function(){
        var url = "deleteAction?StockId="+id;
        url=getQueryPath(url);
        var data1 = '{"StockId":"'+id+'"}';

        var obj = JSON.parse(data1);

        $.post(url,obj,function(json){
            console.log(JSON.stringify(json));
            //initMenu(json);
            console.log(JSON.stringify(json));
            if(json.status=="error"){
                mdui.snackbar({'message':'删除失败!'});
            }
            else{
                mdui.snackbar({'message':'删除成功!'});
            }
        })
        show();
    },function(){
        ;
    });
}

function exported(){
    var url = "whdataToCSV";
    url=getQueryPath(url);
    var coo = getCookie("tocken");

    var data1 = '{"Cookie":"'+coo+'"}';
    console.log("================");
    var obj = JSON.parse(data1);
    console.log(data1);
    $.post(url,obj,function(json){
        console.log(JSON.stringify(json));
    })
}

function query(){
    var url = "chaxunAction";
    url=getQueryPath(url);
    console.log("22222222222222");
    var stockid = document.getElementById("StockId").value;
    var stockname = document.getElementById("StockName").value;
    var coo = getCookie("tocken");
    console.log("11111111111111111");
    var data1 = '{"StockId":"'+stockid+'","StockName":"'+stockname+'","Cookie":"'+coo+'"}';
    console.log("================");
    var obj = JSON.parse(data1);
    console.log(data1);
    $.post(url,obj,function(json){
        console.log(JSON.stringify(json));
        updateTable(json);
    })
}

function updateTable(json){
    wh_info=json.list;
    var table = document.getElementById('wh_list');
    table.innerHTML="";
    for(var i=0;i<json.list.length;i++){
        var row = document.createElement('tr');
        id=json.list[i].stockid;

        var stockId = document.createElement('td');
        stockId.innerText=json.list[i].stockid;
        row.appendChild(stockId);

        var stockName = document.createElement('td');
        stockName.innerText=json.list[i].stockname;
        row.appendChild(stockName);

        var Quantity = document.createElement('td');
        Quantity.innerText=json.list[i].quantity;
        row.appendChild(Quantity);

        var createAt = document.createElement('td');
        createAt.innerText=json.list[i].createat;
        row.appendChild(createAt);

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
        item.onclick="delet("+id+")";
        item.innerHTML="<a onclick='delet("+id+")'>删除</a>";
        menu.appendChild(item);
        var item = document.createElement('li');
        item.className="mdui-menu-item";
        item.innerHTML="<a onclick='modify("+id+")'>修改</a>";
        menu.appendChild(item);
        buttonBox.appendChild(button);
        buttonBox.appendChild(menu);
        row.appendChild(buttonBox);
        table.appendChild(row);
    }
    mdui.updateTables("#wh_list")
}




function show(){

    var url = "querylistAction";
    url=getQueryPath(url);
    var sel1 = document.getElementById("sel1").value;
    var sel2 = document.getElementById("sel2").value;
    var sel3 = document.getElementById("sel3").value;
    //var sel4 = document.getElementById("sel4").value;

    var coo = getCookie("tocken");

    var data1 = '{"Sel1":"'+sel1+'","Sel2":"'+sel2+'","Sel3":"'+sel3+'","Cookie":"'+coo+'"}';

    var obj = JSON.parse(data1);
    $.post(url,obj,function(json){
        //console.log(JSON.stringify(json));
        wh_info=json.list;
        updateTable(json);
    })
}

function hide(){
    var table = document.getElementById('wh_list');
    table.innerHTML="";
}