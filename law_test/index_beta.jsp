<!DOCTYPE html>
<html lang="en">

<head>
<title>Home</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta charset="utf-8">
<meta name="keywords" content="" />
<link rel="stylesheet" href="//cdnjs.loli.net/ajax/libs/mdui/0.4.2/css/mdui.min.css">
<script src="//cdnjs.loli.net/ajax/libs/mdui/0.4.2/js/mdui.min.js"></script>
<script src="js/jquery-2.1.4.min.js"></script>
</head>

<body onload="initPage();" class="mdui-drawer-body-left mdui-appbar-with-toolbar">
	<div class="mdui-appbar mdui-appbar-fixed">
		<div class="mdui-toolbar mdui-color-theme">
		  <a href="javascript:;" class="mdui-btn mdui-btn-icon"><i class="mdui-icon material-icons">menu</i></a>
		  <a href="javascript:;" class="mdui-typo-headline">期货交易系统</a>
		  <a href="javascript:;" class="mdui-typo-title">首页</a>
		  <div class="mdui-toolbar-spacer"></div>
		  <a href="javascript:;" class="mdui-btn mdui-btn-icon"><i class="mdui-icon material-icons">search</i></a>
		  <a href="javascript:;" class="mdui-btn mdui-btn-icon"><i class="mdui-icon material-icons">refresh</i></a>
		  <a href="javascript:;" class="mdui-btn mdui-btn-icon"><i class="mdui-icon material-icons">more_vert</i></a>
		</div>
	  </div>
	<div class="mdui-drawer">

	</div>
</body>
<script>
function initPage(){
		getMenu();
	}
	function initMenu(json){
		var list=json.aaData;
		var html="";
		for(var i=0;i<list.length;i++){
			html+="<li><a href="+list[i][1]+" class=\"scroll\">"+list[i][2]+" </a> </li>";
		}
		$("#menu_div").html(html);
	}
	function getMenu(){
		url="query_ok.jsp";
		var data = '{"UserName":"'+'<%=request.getParameter("UserName")%>'+'"}';
		var obj=JSON.parse(data);
		$.post(url,obj, function(json){
			initMenu(json);
		})
	}
</script>
</html>