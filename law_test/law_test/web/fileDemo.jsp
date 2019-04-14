<%@ page import="java.util.List" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>文件上传</title>
</head>
<body>
<form action="fileUpload" method="post" enctype="multipart/form-data">
    <input type="file" name="file">
    <input type="submit" value="上传文件">
</form>
<div>
    <h2>文件下载</h2>
    <forEach items = "${files }" var="name">
        <a href="/fileUpload/${name }">${name }</a><br/>
    </forEach>
    <%
        List<String> list = (List<String>)session.getAttribute("files");
        if(list!=null){
            for(int i=0;i<list.size();i++){
                %>
    <a href="/fileUpload/<%=list.get(i)%>"><%=list.get(i)%></a><br/>
    <%
            }
        }
    %>
</div>
</body>
</html>