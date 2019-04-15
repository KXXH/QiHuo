<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@page import="java.sql.*" %>
<%@page import="java.util.Date" %>
<%@page import="org.json.JSONArray,java.io.IOException,org.json.JSONObject,java.util.ArrayList" %>
<%@page import="java.util.List" %>
<html>
    <head>
        <title>搜索用户</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
    </head>
    <body>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <%
		String userName = request.getParameter("UserName");
		
        String userType="normal";
        List jsonList = new ArrayList();
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException classNotFoundException){
            classNotFoundException.printStackTrace();
		}


		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=Zjm,,971014&useUnicode=true&characterEncoding=UTF-8");
		String sql="SELECT * FROM tbl_userinfo WHERE UserName=?";
        PreparedStatement ptmt = conn.prepareStatement(sql);
        ptmt.setString(1,userName);
		ResultSet rs=ptmt.executeQuery();
		if(!rs.next()){

        }
        else{
            userType = rs.getString("role_id");
        }
		
        sql="SELECT * FROM tbl_law_menu WHERE role_id=?";
        ptmt = conn.prepareStatement(sql);
        ptmt.setString(1,userType);
        rs=ptmt.executeQuery();
        while(rs.next()){
            List list = new ArrayList();
            list.add(rs.getString("role_id"));
            list.add(rs.getString("href"));
            list.add(rs.getString("caption"));
            jsonList.add(list);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("aaData",jsonList);
        response.setContentType("application/json; charset=UTF-8");
        try{
            response.getWriter().print(jsonObject);
            response.getWriter().flush();
            response.getWriter().close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        %>
    </body>
</html>
    