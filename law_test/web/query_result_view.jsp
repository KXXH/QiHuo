<%@page contentType="text/html; charset=UTF-8"
	import="java.sql.*,java.io.*"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Home</title>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta charset="utf-8">
		<meta name="keywords" content="" />
		<link rel="stylesheet" href="//cdnjs.loli.net/ajax/libs/mdui/0.4.2/css/mdui.min.css">
		<script src="//cdnjs.loli.net/ajax/libs/mdui/0.4.2/js/mdui.min.js"></script>
		<script src="js/jquery-2.1.4.min.js"></script>
		<script src="js/register.js"></script>
	</head>
	<body onload="initPage();" class="mdui-drawer-body-left mdui-appbar-with-toolbar mdui-theme-primary-pink ">
		<div class="mdui-appbar mdui-appbar-fixed">
		<div class="mdui-toolbar mdui-color-theme">
			<a class="mdui-btn mdui-btn-icon" mdui-drawer="{target: '#left-drawer'}"><i class="mdui-icon material-icons">menu</i></a>
			<a href="javascript:;" class="mdui-typo-headline">期货交易系统</a>
			<a href="javascript:;" class="mdui-typo-title">首页</a>
			<div class="mdui-toolbar-spacer"></div>
			<a href="javascript:;" class="mdui-btn mdui-btn-icon"><i class="mdui-icon material-icons">search</i></a>
			<a href="javascript:;" class="mdui-btn mdui-btn-icon"><i class="mdui-icon material-icons">refresh</i></a>
			<a onclick="logOut();" class="mdui-btn mdui-btn-icon"><i class="mdui-icon material-icons">exit_to_app</i></a>
		</div>
	</div>

	<div class="mdui-drawer mdui-shadow-5" id="left-drawer">
		<div class="mdui-spinner mdui-spinner-colorful" id="loader" style="display: block;margin-left:auto;margin-right:auto;margin-top: 50%"></div>
		<div class="mdui-list mdui-collapse" mdui-collapse="{accordion:true}" id="drawer"></div>

	</div>
	<div class="mdui-container">

		<button class="mdui-btn" onclick="mdui.mutation();mdui.Collapse('#drawer').closeAll();">mutation</button>
    </div>


		<%
			String id = request.getParameter("id");
			String deviceId = request.getParameter("device_id");
			String deviceName = request.getParameter("device_name");
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException classnotfoundexception) {
				classnotfoundexception.printStackTrace();
			}
			try {
				Connection conn = DriverManager
						.getConnection("jdbc:mysql://localhost:3306/ylxdb?user=root&password=123456&useUnicode=true&characterEncoding=UTF-8");
				Statement statement = conn.createStatement();
				out.println("Connect Database Ok！！！");
				ResultSet rs = statement
						.executeQuery("select * from video_file where id=" + id);
				while (rs.next()) {
		%>
		<form name="changeForm" action="change_ok.jsp" method="POST">
			<input type="hidden" name="id" value="<%=id%>">
			设备ID
			<input type="text" name="device_id"
				value="<%=rs.getString("device_id")%>">
			<br>
			设备名称
			<input type="text" name="device_name"
				value="<%=rs.getString("device_name")%>">
			<input type="submit" name="subbtn" value="提交">
		</form>
		<%
			}
				out.println("<br>");
				statement.close();
				conn.close();
				out.println("Database Closed！！！");
			} catch (SQLException sqlexception) {
				sqlexception.printStackTrace();
			}
		%>
		<input type="button" value="添加" onclick="add()"/>
		<input type="button" value="修改" onclick="modify()"/>
		<input type="button" value="查询" onclick="query()"/>
	</body>
</html>
<script>
function add(){
	window.location="register.html";
}
function modify(){
	window.location="change_list.html";
}
function query(){
	window.location="query_list.jsp";
}
</script>