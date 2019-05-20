function statistics(){

    var url = "/statisticAction";
    
    $.post(url,function(json){
        console.log(JSON.stringify(json));
        drawChart(json);
    })


}
function  drawChart(json){
        console.log('开始绘图');
        var chart=am4core.create("chartdiv",am4charts.XYChart);
        chart.dateFormatter.inputDateFormat="yyyy-MM-dd";
        chart.data=json.data;
        var dateAxis=chart.xAxes.push(new am4charts.DateAxis());
        var valueAxix=chart.yAxes.push(new am4charts.ValueAxis());
        var lineSeries=chart.series.push(new am4charts.LineSeries());
        lineSeries.tooltipText = "{value}"
        lineSeries.dataFields.valueY="value";
        lineSeries.dataFields.dateX="date";
        lineSeries.tooltip.background.cornerRadius = 20;
        lineSeries.tooltip.background.strokeOpacity = 0;
        lineSeries.tooltip.pointerOrientation = "vertical";
        lineSeries.tooltip.label.minWidth = 40;
        lineSeries.tooltip.label.minHeight = 40;
        lineSeries.tooltip.label.textAlign = "middle";
        lineSeries.tooltip.label.textValign = "middle";

        var bullet = lineSeries.bullets.push(new am4charts.CircleBullet());
        bullet.circle.strokeWidth = 2;
        bullet.circle.radius = 4;
        bullet.circle.fill = am4core.color("#fff");
        var bullethover = bullet.states.create("hover");
        bullethover.properties.scale = 1.3;
        chart.cursor = new am4charts.XYCursor();
        chart.cursor.behavior = "panXY";
        chart.cursor.xAxis = dateAxis;
        chart.cursor.snapToSeries = lineSeries;
        lineSeries.name="Sales";
    }
