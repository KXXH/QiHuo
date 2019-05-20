function add(){
    window.location="register.html";
}
function modify(){
    window.location="change_list.html";
}
function query(){
    window.location="query_list.html";
}

function getParam(){
    C1=window.location.href.split("?")[1]; //得到id=楼主
    C2=C1.split("=")[1]; //得到楼主
    //alert(C1); alert(C2);
    return C2;
    }
function showSuccess(json){

    window.location="query_list.html";
}

function changestock(){
    var stockid = getParam();
	var url = "/changeAction";
    console.log("22222222222222");
    var quantity = document.getElementById("Quantity").value;
    var bunitprice = document.getElementById("BUnitPrice").value;
    var coo = getCookie("tocken");
    var data1 = '{"StockId":"'+stockid+'","Quantity":"'+quantity+'","BUnitPrice":"'+bunitprice+'","Cookie":"'+coo+'"}';

    var obj = JSON.parse(data1);
    $.post(url,obj,function(json){
        console.log(JSON.stringify(json));
        console.log("333333333");

        showSuccess(json);
    })
    alert("修改成功！");


}

