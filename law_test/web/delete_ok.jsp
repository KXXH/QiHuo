<%@page contentType="text/html; charset=UTF-8"
	import="java.sql.*,java.io.*"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	</head>
	<body>
		<%
			String id = request.getParameter("id");
			out.println("前端页面传过来的ID是："+id+"<br>");
			request.setCharacterEncoding("UTF-8");
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException classnotfoundexception) {
				classnotfoundexception.printStackTrace();
			}
			try {
				Connection conn = DriverManager
						.getConnection("jdbc:mysql://localhost:3306/ylxdb?user=root&password=123456&useUnicode=true&characterEncoding=UTF-8");
				Statement statement = conn.createStatement();
				out.println("Connect Database Ok！！！<br>");
				String sql = "delete from video_file where id=" + id;
				statement.executeUpdate(sql);
				out.println(sql);
				statement.close();
				conn.close();
				out.println("Database Closed！！！<br>");
		%>
删除成功！请返回。
<input type="button" name="listBtn" value="返回列表" onclick="window.location='change_list.jsp'">
		<%
			} catch (SQLException sqlexception) {
				sqlexception.printStackTrace();
		%>
出现错误，请看Console提示！请返回。
<input type="button" name="listBtn" value="返回列表" onclick="window.location='change_list.jsp'">
		<%
			}
		%>
	</body>
</html>
