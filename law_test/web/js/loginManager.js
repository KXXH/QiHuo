var tokenList;
var login_username="";
var login_count=0;
var token_username="";
var token_count=0;
var dayCount=7;
var fab=new mdui.Fab('#loginFAB');
function fetchTokenInfo(){
    var url=getQueryPath('getTokenAction');

    $.post(url,function(json){
        console.log(JSON.stringify(json));
        if(json.status=="ok"){
            tokenList=json.data;
            initTokenTable();
        }
    });
}

function initFAB(){
    fab.hide();
    document.getElementById('tokenTab').addEventListener('show.mdui.tab', function () {
        console.log('token页面');
        fab.hide();
    });
    document.getElementById('loginTab').addEventListener('show.mdui.tab', function () {
        console.log('login页面');
        fab.show();
    });
}

function initTokenTable(){
    var table=document.getElementById("token_table_body");
    var rowCount=table.rows.length;
    for(var i=rowCount-1;i>0;i--){
        table.deleteRow(i);
    }
    for(var i=0;i<tokenList.length;i++){
        var row=document.getElementById("token_table_body").insertRow();
        row.insertCell(0).innerHTML=tokenList[i].id;
        row.insertCell(1).innerHTML=tokenList[i].tokenValue;
        row.insertCell(2).innerHTML=tokenList[i].username;
        row.insertCell(3).innerHTML=tokenList[i].TTL;
    }
    mdui.updateTables("#token_table");
    mdui.mutation();
}

function clearLoginTable(){
    var table=document.getElementById("login_table");
    var rowCount=table.rows.length;
    for(var i=rowCount-1;i>0;i--){
        table.deleteRow(i);
    }
    mdui.updateTables("#login_table");
}

function updateLoginTable(data){
    for(var i=0;i<data.length;i++){
        var row=document.getElementById("login_table").insertRow();
        row.insertCell(0).innerHTML=data[i].id;
        row.insertCell(1).innerHTML=data[i].username;
        row.insertCell(2).innerHTML=data[i].time;
        row.insertCell(3).innerHTML=data[i].action;
        row.insertCell(4).innerHTML=data[i].ip;
        row.insertCell(5).innerHTML=data[i].location;
    }
    mdui.updateTables("#login_table");
}

function loadMoreLoginRecord(){
    var j={'username':login_username,'count':login_count};
    var url=getQueryPath('getLoginRecordAction');
    $.post(url,j,function(json){
        if(json.status=="ok"){
            updateLoginTable(json.data);
            login_count=json.count;
        }else if(json.status=="error"){
            mdui.alert(json.error,"错误");
        }else if(json.status=="end"){
            mdui.snackbar("已经到达最后了!");
            updateLoginTable(json.data);
        }
    })
}

function queryLoginRecord(){
    mdui.prompt('请输入查询的用户名:','查找',function(value){
        login_username=value;
        login_count=0;
        clearLoginTable();
        loadMoreLoginRecord();
    });
}

function delToken(){
    var selects=document.getElementsByClassName("mdui-table-row-selected");
    var ids=new Array();
    for(var i=0;i<selects.length;i++){
        console.log(selects[i].cells[1].innerHTML);
        ids.push(selects[i].cells[1].innerHTML);
    }
    var j={'selects':ids};
    var url=getQueryPath('delTokenAction');
    $.post(url,j, function (json) {
        if(json.status=="ok"){
            mdui.snackbar("删除成功!");

        }else{
            mdui.alert(json.error,"出现错误");
        }
        fetchTokenInfo();
    })
}

function get_statistic(){
    var url="loginStatsticAction";
    var j={'dayCount':dayCount};
    $.post(url,j,function(json){
        drawChart(json.locationData,json.min,json.max);
    })
}

function drawChart(data,min,max){
    am4core.useTheme(am4themes_material);
    am4core.useTheme(am4themes_animated);
    var chart = am4core.create("chartdiv", am4maps.MapChart);

    chart.geodataSource.url = "images/china.json";
    chart.projection = new am4maps.projections.Miller();
    var polygonSeries = new am4maps.MapPolygonSeries();
    chart.series.push(polygonSeries);
    polygonSeries.useGeodata = true;
    polygonSeries.data=data;
    polygonSeries.heatRules.push({
        property: "fill",
        target: polygonSeries.mapPolygons.template,
        min: chart.colors.getIndex(1).brighten(1),
        max: chart.colors.getIndex(1).brighten(-0.3)
    });
    let heatLegend = chart.createChild(am4maps.HeatLegend);
    heatLegend.series = polygonSeries;
    heatLegend.align = "right";
    heatLegend.valign = "bottom";
    heatLegend.width = am4core.percent(20);
    heatLegend.marginRight = am4core.percent(4);
    heatLegend.minValue = min;
    heatLegend.maxValue = max;

// Set up custom heat map legend labels using axis ranges
    var minRange = heatLegend.valueAxis.axisRanges.create();
    minRange.value = heatLegend.minValue;
    minRange.label.text = min;
    var maxRange = heatLegend.valueAxis.axisRanges.create();
    maxRange.value = heatLegend.maxValue;
    maxRange.label.text = max+"(人次)";

// Blank out internal heat legend value axis labels
    heatLegend.valueAxis.renderer.labels.template.adapter.add("text", function(labelText) {
        return "";
    });

// Configure series tooltip
    var polygonTemplate = polygonSeries.mapPolygons.template;
    polygonTemplate.tooltipText = "{name}: {value}";
    polygonTemplate.nonScalingStroke = true;
    polygonTemplate.strokeWidth = 0.5;

// Create hover state and set alternative fill color
    var hs = polygonTemplate.states.create("hover");
    hs.properties.fill = am4core.color("#3c5bdc");


}
/**
 * Created by zjm97 on 2019/5/25.
 */
