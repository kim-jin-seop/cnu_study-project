<%@ page import="java.io.File" %>
<%@ page import="java.io.FileWriter" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("UTF-8"); 
String num = (String)request.getParameter("num");
String day = (String)request.getParameter("Day");
String title = (String)request.getParameter("Title");
String content = (String)request.getParameter("Content");

String temp;
StringTokenizer  token  =  new StringTokenizer(title," ");
String maketitle = token.nextToken();
while(token.hasMoreTokens()){
	maketitle += "_";
	maketitle += token.nextToken();
}
temp = title;
title = maketitle;

String fileName = title+num+".txt";
String path = request.getRealPath(fileName);
File f = new File(path);
f.createNewFile();
FileWriter fw = new FileWriter(f);
fw.write( day + "\r\n" + title + "\r\n"+content);
fw.close();
%>
<div class = 'list' ondragover='allowDrop(event)' ondragstart='drag(event)' draggable = 'true' id ='<%=title%><%=num%>' >
	<input type='checkbox' name = 'dataList' value=<%=title %><%=num%>> 
	<p class = "<%=temp%>"ondrop='drop(event)' onclick = modify(this) id ='showdata'><%=temp%></p>
</div>