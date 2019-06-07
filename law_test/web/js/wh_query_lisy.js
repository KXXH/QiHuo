var wh_info;
var list;
var len=0;
var Order=0;
var id="";
var userid="";
var username="";
var stockid="";
var stockname="";
var create="";
function add(){
    window.location.href="wh_register.html";
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

function modify_record(i){
    var inst = new mdui.Dialog("#modify");
    inst.open();
    document.getElementById("user_id").value = list[i].userid;
    document.getElementById("user_name").value = list[i].username;
    document.getElementById("stock_id").value = list[i].stockid;
    document.getElementById("stock_name").value = list[i].stockname;
    document.getElementById("quantity").value = list[i].quantity;
    document.getElementById("bunitprice").value = list[i].quotation;
    document.getElementById("createat").value = list[i].createat;

}

function modify(){
    url = "changeAction";
    url = getQueryPath(url)
    //orderid=document.getElementById("order_id").value;
    userid=document.getElementById("user_id").value;
    username=document.getElementById("user_name").value;
    stockid=document.getElementById("stock_id").value;
    stockname=document.getElementById("stock_name").value;
    quantity=document.getElementById("quantity").value;
    quotation=document.getElementById("bunitprice").value;
    createat=document.getElementById("createat").value;
    var data = '{"UserId":"'+userid+'","UserName":"'+username+'","StockId":"'+stockid+'","StockName":"'+stockname+'","Quantity":"'+quantity+'","Quotation":"'+quotation+'","CreateAt":"'+createat+'"}';
    var obj = JSON.parse(data);
    $.post(url,obj,function(json){
        if(json.status=="1"){
            mdui.snackbar({'message':'发布成功！'});
            show();
        }

        else
            mdui.snackbar({'message':'发布失败！'});
        show();
    })
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
    console.log("11111111111111111");
    var data1 = '{"StockId":"'+stockid+'","StockName":"'+stockname+'"}';
    console.log("================");
    var obj = JSON.parse(data1);
    console.log(data1);
    $.post(url,obj,function(json){
        console.log(JSON.stringify(json));
        updateTable(json);
    })
}

function updateTable(json){
    list=json.list;
    var table = document.getElementById('wh_list');
    table.innerHTML="";
    for(var i = 0; i < list.length; i++){
        var newNode = document.createElement("tr");
        var id = list[i].stockid;
        newNode.innerHTML = "<td>"+list[i].stockid+"</td>"
        newNode.innerHTML += "<td>"+list[i].stockname +"</td><td>"+list[i].quantity +"</td><td>"+list[i].quotation +"</td><td>" +list[i].createat + "</td>";
        newNode.innerHTML += "<td><button class=\"mdui-btn mdui-btn-raised\" onclick='delet("+id+")'>删除</button><button class=\"mdui-btn mdui-btn-raised\" onclick='modify_record("+i+")'>卖出</button></td>"
        table.appendChild(newNode);
    }
}


function show(){

    var url = "querylistAction";
    url=getQueryPath(url);
    sel1 = $("input[type=radio][name=group1]:checked").val();
    sel2 = $("input[type=radio][name=group2]:checked").val();
    var data1 = '{"Sel1":"'+sel1+'","Sel2":"'+sel2+'","StockId":"'+stockid+'","StockName":"'+stockname+'"}';

    var j = JSON.parse(data1);
    $.post(url,j,function(json){
        //console.log(JSON.stringify(json));
        bus_info=json.list;
        if(json.status == "error"){
            mdui.alert("错误!");
        }
        else
        {
            updateTable(json);
        }

    })
}

function hide(){
    var table = document.getElementById('wh_list');
    table.innerHTML="";
}