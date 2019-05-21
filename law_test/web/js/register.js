function add(){
    window.location="register.html";
}
function modify(){
    window.location="change_list.html";
}
function query(){
    window.location="query_list.html";
}



function addstock(){

	var url = "/registerAction";
    var stockid = document.getElementById("StockId").value;
    var stockname = document.getElementById("StockName").value;
    var quantity = document.getElementById("Quantity").value;
    var bunitprice = document.getElementById("BUnitPrice").value;

    var coo = getCookie("tocken");
    var data1 = '{"StockId":"'+stockid+'","StockName":"'+stockname+'","Quantity":"'+quantity+'","BUnitPrice":"'+bunitprice+'","Cookie":"'+coo+'"}';
    var obj = JSON.parse(data1);

    $.post(url,obj,function(json){
        console.log(JSON.stringify(json));
        //initMenu(json);
    })
    if(stockid=="" || stockname=="" || quantity==""|| bunitprice=="")
    {
        alert("输入不正确,请重新输入!");
    }
    else
    {
        alert("买入成功!");
    }



}

