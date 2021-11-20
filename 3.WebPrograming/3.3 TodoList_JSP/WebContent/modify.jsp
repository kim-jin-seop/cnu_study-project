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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Modify</title>
</head>
<body>
<%
request.setCharacterEncoding("UTF-8");
String filePath = (String)request.getParameter("filename");
BufferedReader reader = new BufferedReader(new FileReader(filePath));
String day = reader.readLine();
String title = (String)request.getParameter("Title");
String content = (String)request.getParameter("content");
reader.close();
File f = new File(filePath);
f.delete();

String temp;
StringTokenizer  token  =  new StringTokenizer(title," ");
String maketitle = token.nextToken();
while(token.hasMoreTokens()){
	maketitle += "_";
	maketitle += token.nextToken();
}
temp = title;
title = maketitle;

String dst = (String)request.getParameter("dst")+title +request.getParameter("num")+".txt";
File file = new File(dst);
file.createNewFile();
FileWriter fw = new FileWriter(file);
fw.write( day + "\r\n" + temp + "\r\n"+content);
fw.close();
%>
</body>
</html>