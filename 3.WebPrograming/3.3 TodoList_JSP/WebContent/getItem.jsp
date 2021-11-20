<%@ page import ="java.io.FileReader"%>
<%@ page import ="java.io.BufferedReader" %>  
<%@ page import="java.io.File" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
request.setCharacterEncoding("UTF-8");
String fileName = (String)request.getParameter("filename") + ".txt";
String filePath = request.getRealPath(fileName);
BufferedReader reader = new BufferedReader(new FileReader(filePath));
String day = reader.readLine();
String title =  reader.readLine();
String context = reader.readLine();
reader.close();
%>


<img alt= "사진이 안보입니다." src="img/delete.png" onclick = "closethemodify()">
			<form id = <%=request.getParameter("filename") + "_"%> name = modify_Form>
				<input id = "<%=request.getRealPath("")%>"name = "T" class = "m_title" value = <%= title %>  type = "text"><br>
				<textarea id = "<%=day %>" name = "C" class = "m_Contents"><%=context%></textarea><br>
				<input id = "<%=filePath%>" class = "modifybutton" type="button" value ="수정" onclick = "change(this)">
			</form>