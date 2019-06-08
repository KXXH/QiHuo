var tokenList;
var login_username="";
var login_count=0;
var token_username="";
var token_count=0;
var dayCount=7;
var fab=new mdui.Fab('#loginFAB');
var token_username='';
var token_sort_dlg=new mdui.Dialog("#tokenSortDlg");
var login_record_edit_dlg=new mdui.Dialog('#loginEditDlg');
function drawPieChart(data){
    am4core.useTheme(am4themes_animated);

    var chart = am4core.create("piediv", am4charts.PieChart3D);
    chart.hiddenState.properties.opacity = 0; // this creates initial fade-in

    chart.legend = new am4charts.Legend();

    chart.data = [
        {
            type: "失效token",
            count: data.useless_count
        },
        {
            type: "有效token",
            count:data.useful_count
        },
        {
            type: "没有token记录的用户",
            count:data.other_count
        }
    ];

    var series = chart.series.push(new am4charts.PieSeries3D());
    series.dataFields.value = "count";
    series.dataFields.category = "type";

}

function getPieChart(){
    var url="tokenStatisticAction";
    var stTimestamp=0;
    var endTimestamp=new Date().getTime()+30*24*3600;
    var j={'stTimestamp':stTimestamp,'endTimestamp':endTimestamp};
    $.post(url,j,function(json){
        drawPieChart(json);
    });
}
function fetchTokenInfo(){
    var url=getQueryPath('getTokenAction');
    var sort1=$('input[type=radio][name=token_sorted_by]:checked').val();
    var sort2=$('input[type=radio][name=token_sorted_by2]:checked').val();
    var j={'sort_by_1':sort1,'sort_by_2':sort2,'username':token_username};
    $.post(url,j,function(json){
        console.log(JSON.stringify(json));
        if(json.status=="ok"){
            tokenList=json.data;
            initTokenTable();

        }
    });
}
function submitSort(){

    fetchTokenInfo();

}
function submitLoginSort(){
    login_count=0;
    clearLoginTable();
    loadMoreLoginRecord();
}

function initFAB(){
    //fab.hide();
    $('#loginFAB').attr("mdui-dialog","{target: '#tokenPieDlg'}");
    document.getElementById('tokenTab').addEventListener('show.mdui.tab', function () {
        console.log('token页面');
        //fab.hide();
        $('#loginFAB').attr("mdui-dialog","{target: '#tokenPieDlg'}");
    });
    document.getElementById('loginTab').addEventListener('show.mdui.tab', function () {
        console.log('login页面');
        //fab.show();
        $('#loginFAB').attr("mdui-dialog","{target: '#mapDlg'}");
    });
}

function initTokenTable(){
    var table=document.getElementById("token_table_body");
    var rowCount=table.rows.length;
    for(var i=rowCount-1;i>=0;i--){
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
function queryToken(){
    token_username=mdui.prompt('请输入要查找的用户名','搜索',
        function (value) {
            token_username=value;
            fetchTokenInfo();
        },
        function (value) {
            ;
        }
    );

}
function clearLoginTable(){
    var table=document.getElementById("login_table_body");
    var rowCount=table.rows.length;
    for(var i=rowCount-1;i>=0;i--){
        table.deleteRow(i);
    }
    mdui.updateTables("#login_table");
}

function updateLoginTable(data){
    for(var i=0;i<data.length;i++){
        var row=document.getElementById("login_table_body").insertRow();

        row.onclick=function(){
            showEditDlg(this);
        };
        row.insertCell(0).innerHTML=data[i].id;
        row.insertCell(1).innerHTML=data[i].username;
        row.insertCell(2).innerHTML=data[i].time;
        row.insertCell(3).innerHTML=data[i].action;
        row.insertCell(4).innerHTML=data[i].ip;
        row.insertCell(5).innerHTML=data[i].location;
    }
    mdui.updateTables("#login_table");
}

function showEditDlg(row){
    $('#login_record_edit_id').val(row.cells[1].innerText);
    $('#login_record_edit_username').val(row.cells[2].innerText);
    $('#login_record_edit_time').val(row.cells[3].innerText);
    $('#login_record_edit_action').val(row.cells[4].innerText);
    $('#login_record_edit_ip').val(row.cells[5].innerText);
    $('#login_record_edit_position').val(row.cells[6].innerText);
    login_record_edit_dlg.open();
}

function editLoginRecord(){
    var id=$('#login_record_edit_id').val();
    var username=$('#login_record_edit_username').val();
    var time=$('#login_record_edit_time').val();
    var action=$('#login_record_edit_action').val();
    var ip=$('#login_record_edit_ip').val();
    var position=$('#login_record_edit_position').val();
    var j={'id':id,'username':username,'time':time,'action':action,'ip':ip,'location':position};
    var url="editLoginRecordAction";
    $.post(url,j,function(json){
        if(json.status=="ok"){
            login_record_edit_dlg.close();
            login_count=0;
            clearLoginTable();
            loadMoreLoginRecord();
        }else{
            mdui.alert(json.error,"错误");
        }
    });
}

function loadMoreLoginRecord(){
    var sort1=$('input[type=radio][name=login_sorted_by]:checked').val();
    var sort2=$('input[type=radio][name=login_sorted_by2]:checked').val();
    var asc_1=$('#asc_1_checkbox').attr('checked')=="checked"?-1:1;
    var asc_2=$('#asc_2_checkbox').attr('checked')=="checked"?-1:1;

    var j={'username':login_username,'count':login_count,'sort_by_1':sort1,'sort_by_2':sort2,'asc_1':asc_1,'asc_2':asc_2};
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
    var selects=$("#token_table .mdui-table-row-selected");
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

function delLoginRecord(){
    var selects=$("#login_table .mdui-table-row-selected");
    var ids=new Array();
    for(var i=0;i<selects.length;i++){
        console.log(selects[i].cells[1].innerHTML);
        ids.push(selects[i].cells[1].innerHTML);
    }
    var j={'selects':ids};
    var url=getQueryPath('delLoginRecordAction');
    $.post(url,j, function (json) {
        if(json.status=="ok"){
            mdui.snackbar("删除成功!");
            for(var i=0;i<selects.length;i++){
                console.log("aaa="+typeof(selects[i]));
                selects[i].style.display="none";
            }

        }else{
            mdui.alert(json.error,"出现错误");
        }

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


function tokenOutputSubmit(){
    var date=new Date();
    var type=$('#tokenOutputFromSelect').children('option:selected').val();
    var stDate=getDate($('#tokenOutputStDate').val());
    var endDate=getDate($('#tokenOutputEndDate').val());
    if(type!='-1'){
        stDate=new Date(date.getTime()-(type*24*3600*1000));
        endDate=date;
    }
    console.log('stDate='+stDate);
    console.log('endDate='+endDate);
    window.open('getTokenOutputAction?startTime='+stDate.getTime()+'&endTime='+endDate.getTime());
}
function tokenOutputSetEndTimeLimit(){
    var stDate=$('#loginOutputStDate').val();
    $('#tokenOutputEndDate').attr('min',stDate);
}
function tokenOutputSetStTimeLimit(){
    var endDate=$('#loginOutputEndDate').val();
    $('#loginOutputStDate').attr('max',endDate);
}
function loginOutputSetEndTimeLimit(){
    var stDate=$('#loginOutputStDate').val();
    $('#loginOutputEndDate').attr('min',stDate);
}
function loginOutputSetStTimeLimit(){
    var endDate=$('#loginOutputEndDate').val();
    $('#loginOutputStDate').attr('max',endDate);
}

function getDate(strDate){
    var date = eval('new Date(' + strDate.replace(/\d+(?=-[^-]+$)/,
            function (a) { return parseInt(a, 10) - 1; }).match(/\d+/g) + ')');
    return date;
}

function loginOutputSubmit(){
    var date=new Date();
    var type=$('#loginOutputFromSelect').children('option:selected').val();
    var stDate=getDate($('#loginOutputStDate').val());
    var endDate=getDate($('#loginOutputEndDate').val());
    if(type!='-1'){
        stDate=new Date(date.getTime()-(type*24*3600*1000));
        endDate=date;
    }
    console.log('stDate='+stDate);
    console.log('endDate='+endDate);
    window.open('getLoginOutputAction?startTime='+stDate.getTime()+'&endTime='+endDate.getTime());
}

function submitLoginPrint(){
    var count=$('#loginPrintCount').val();
    if(parseInt(count)!=NaN){
        window.open("login_print.html?page_size="+count);
    }else{
        mdui.alert("请输入正确的数字!");
    }
}

$('#tokenOutputFromSelect').on('close.mdui.select',function(){
    if($('#tokenOutputFromSelect').children('option:selected').text()=="自定义"){
        $('#tokenOutputDatePicker').removeClass('mdui-invisible');
        var date=new Date();
        $('#tokenOutputStDate').val((date.getYear()+1900)+'-'+(date.getMonth()+1<10?'0'+(date.getMonth()+1):date.getMonth()+1)+'-'+(date.getDate()<10?'0'+date.getDate():date.getDate()));
        $('#tokenOutputStDate').attr('max',((date.getYear()+1900)+'-'+(date.getMonth()+1<10?'0'+(date.getMonth()+1):date.getMonth()+1)+'-'+(date.getDate()<10?'0'+date.getDate():date.getDate())));
        $('#tokenOutputEndDate').val((date.getYear()+1900)+'-'+(date.getMonth()+1<10?'0'+(date.getMonth()+1):date.getMonth()+1)+'-'+(date.getDate()<10?'0'+date.getDate():date.getDate()));
    }else{
        $('#tokenOutputDatePicker').addClass('mdui-invisible');
    }
});

$('#loginOutputFromSelect').on('close.mdui.select',function(){
    if($('#loginOutputFromSelect').children('option:selected').text()=="自定义"){
        $('#loginOutputDatePicker').removeClass('mdui-invisible');
        var date=new Date();
        $('#loginOutputStDate').val((date.getYear()+1900)+'-'+(date.getMonth()+1<10?'0'+(date.getMonth()+1):date.getMonth()+1)+'-'+(date.getDate()<10?'0'+date.getDate():date.getDate()));
        $('#loginOutputStDate').attr('max',((date.getYear()+1900)+'-'+(date.getMonth()+1<10?'0'+(date.getMonth()+1):date.getMonth()+1)+'-'+(date.getDate()<10?'0'+date.getDate():date.getDate())));
        $('#loginOutputEndDate').val((date.getYear()+1900)+'-'+(date.getMonth()+1<10?'0'+(date.getMonth()+1):date.getMonth()+1)+'-'+(date.getDate()<10?'0'+date.getDate():date.getDate()));
    }else{
        $('#loginOutputDatePicker').addClass('mdui-invisible');
    }
});
$('#asc_1_checkbox').change(function(){
    if(this.hasAttribute("checked")) this.removeAttribute("checked");
    else this.setAttribute("checked","true");
    if($('#asc_1_label').text()=="升序") $('#asc_1_label').text("降序");
    else $('#asc_1_label').text("升序");
});
$('#asc_2_checkbox').change(function(){
    if(this.hasAttribute("checked")) this.removeAttribute("checked");
    else this.setAttribute("checked","true");
    if($('#asc_1_label').text()=="升序") $('#asc_1_label').text("降序");
    else $('#asc_1_label').text("升序");
});
/**
 * Created by zjm97 on 2019/5/25.
 */
