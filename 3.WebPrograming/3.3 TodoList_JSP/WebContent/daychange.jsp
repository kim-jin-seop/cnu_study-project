<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import ="java.io.FileReader"%>
<%@ page import="java.io.File" %>
<%@ page import ="java.io.BufferedReader" %> 
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="java.io.FileWriter" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>daychange</title>
</head>
<body>
<%
request.setCharacterEncoding("UTF-8");
String data = (String)request.getParameter("data");
String filePath = request.getRealPath(data + ".txt");
BufferedReader reader = new BufferedReader(new FileReader(filePath));
String day = (String)request.getParameter("day");
reader.readLine();
String title = reader.readLine();
String content = reader.readLine();
reader.close();
File f = new File(filePath);
f.delete();

File file = new File(filePath);
file.createNewFile();
FileWriter fw = new FileWriter(file);
fw.write( day + "\r\n" + title + "\r\n"+content);
fw.close();%>>
</body>
</html>