<%@ page import="java.io.File" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Removedata</title>
</head>
<body>
<%
request.setCharacterEncoding("UTF-8");
String filename = (String)request.getParameter("filename") + ".txt";
String filepath = request.getRealPath(filename);
File file = new File(filepath);
file.delete();
%>
</body>
</html>