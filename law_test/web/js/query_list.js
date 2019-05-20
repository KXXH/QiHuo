function add(){
    window.location.href="register.html";
}
function modify(id){
    window.location.href = "change_list.html?stockid="+id;
}
function home(){
    window.location="query_list.html";
}
function delet(id){
    var url = "/deleteAction?StockId="+id;

    var data1 = '{"StockId":"'+id+'"}';

    var obj = JSON.parse(data1);

    $.post(url,obj,function(json){
        console.log(JSON.stringify(json));
        //initMenu(json);
    })
    alert("删除成功!");

}

function exported(){
    var url = "/dataToCSV";
    
    $.post(url,function(json){
        console.log(JSON.stringify(json));
    })
}

function query(){
    var url = "/chaxunAction";
    
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
        showPage(json);
    })
}
function showPage(json){
    
    var s="";
    console.log(JSON.stringify(json));

    var map=json.aaData;



    for(var i=0;i<map.length;i++)
    {


            //window.location.replace("query_list.html");
            s+=(map[i].stockid +' | '+map[i].stockname+' | '+map[i].quantity+"\n");
            //var showform = document.createElement("div");
            //showform.className = "changeForm";
            //showform.innerHTML = '<textarea id="content" name="content" style="width:300px;height:300px;float:left;">这里是解析的json</textarea>'+'</div>';

            var id=map[i].stockid;
            document.getElementById('changeForm').innerHTML +=
                '<input type="button" id="change" name="change" value="修改" onclick="modify('+id+')"/>';
            document.getElementById('changeForm').innerHTML +=
                '<input type="button" id="delete" name="delete" value="删除" onclick="delet('+id+')"/>'+'<br/>';
            //document.getElementById('content').innerHTML += '<a class="changeform" href="' + map[i][1] + '">' + map[i][2] + '</div>';


    }


    $("#content").html(s);
        
}
    



function show(){

	var url = "/querylistAction";

    var sel1 = document.getElementById("sel1").value;
    var sel2 = document.getElementById("sel2").value;
    var sel3 = document.getElementById("sel3").value;
    //var sel4 = document.getElementById("sel4").value;

    var coo = getCookie("tocken");

    var data1 = '{"Sel1":"'+sel1+'","Sel2":"'+sel2+'","Sel3":"'+sel3+'","Cookie":"'+coo+'"}';

    var obj = JSON.parse(data1);
    $.post(url,obj,function(json){
        //console.log(JSON.stringify(json));
        showPage(json);
    })

}

