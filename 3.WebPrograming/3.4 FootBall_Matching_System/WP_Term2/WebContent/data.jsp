<%@ page import ="java.io.FileReader"%>
<%@ page import ="java.io.BufferedReader" %>
<%@ page import="java.io.File" %>
<%@ page import="java.util.*" %> 
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="data.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>매칭 결과</title>
</head>
<body>
	<%
	String filePath = request.getRealPath("") + "/data/";
	File f = new File(filePath); 
	File[] files = f.listFiles(); 
	int index = (int)(Math.random()*files.length);
	BufferedReader br = new BufferedReader(new FileReader(files[index]));
	br.readLine();
	%>
	<h1>매칭결과</h1>
	<div id="datalist">
	
	<%
	out.println("대표자 성명 : ");
	out.println(br.readLine()+"<br>--------------------------------------------<br>");
	out.println("연락처 : ");
	out.println(br.readLine()+"<br>--------------------------------------------<br>");
	out.println("인원 수 : ");
	out.println(br.readLine()+"<br>--------------------------------------------<br>");
	out.println("날짜 : ");
	out.println(br.readLine()+"<br>--------------------------------------------<br>");
	out.println("장소 : ");
	if(br.readLine().equals("A")){
		out.println("강변 풋살장");
	}
	else{
		out.println("케이지 풋살장");
	}
	out.println("<br>--------------------------------------------<br>");
	out.println("한마디 : ");
	out.println(br.readLine()+"<br>	--------------------------------------------<br>");
	br.close();
	%>
	<p>재 매칭을 원하시면 재 매칭 버튼을 눌러주세요</p>
	<form action="Main.html">
		<input type="submit" value="처음화면으로">
	</form>
	<form action="data.jsp">
		<input type="submit" value="재매칭">
	</form>
	</div>
</body>
</html>
<%--저장된 데이터를 가져오는 페이지이다. 해당 데이터가 들어간 파일의 파일 이름들을 모두 하나의 배열에 저장을 해두고, 그 데이터중 하나를 random함수를 이용해
임의로 하나를 뽑는다. 이후에, BufferedReader를 이용하여 데이터를 읽어준다.
위 페이지에서 랜덤으로 데이터를 뽑아와 매칭을 시켜주고, 이렇게 함으로써 최종적으로 이 프로그램이 수행하고자하는 결과를 가져오게된것이 된다--%>
