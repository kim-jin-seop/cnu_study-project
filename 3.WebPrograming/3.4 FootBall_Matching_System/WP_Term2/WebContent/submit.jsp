<%@ page import="java.io.File" %>
<%@ page import="java.io.FileWriter" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="submit.css">
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>submit</title>
</head>
<body>
    <%
    request.setCharacterEncoding("UTF-8");
    String fileName = (String)request.getParameter("tel") + ".txt";
	String dst = request.getRealPath("data\\"+fileName);
	
	String name = (String)request.getParameter("name");
    String tel = request.getParameter("tel");
    String num = request.getParameter("num");
    String date = request.getParameter("date");
    String place = request.getParameter("location");
    String tell = request.getParameter("tell");
    String password = request.getParameter("pw");
    
    File f = new File(dst);
    f.createNewFile();

    FileWriter fw = new FileWriter(f);
    fw.write( password + "\r\n" + name + "\r\n" + tel 
    		+ "\r\n" + num + "\r\n" + date
    		+ "\r\n" + place
    		+ "\r\n" + tell);
    fw.close();
    if(f.exists()) {%>
    <div class ="data">
    <h1>풋살 정보 입력 완료!</h1><br />
    <p>데이터가 성공적으로 저장되었습니다.</p>
    <form action="data.jsp">
    	<input type="submit" value="매칭받기">
    </form>
    </div>
    <%}
    else {%>
    <div class ="data">
    <h1>풋살 정보 저장 결과</h1><br />
    <p>저장에 실패 하였습니다.</p>
    <form action="Main.html">
    	<input type="submit" value="돌아가기">
    </form>
    </div>	
    <%}
    %>   

</body>
</html>
<%--데이터를 저장한다. 이전 페이지 inputdata.jsp에서 받은 데이터를 requset.getparameter를 이용히 가져온다.
파일 이름은 핸드폰 번호로 저장을하고, 데이터를 차례대로 저장한다.
fileWrite를 이용하여 파일을 작성하고 데이터를 저장할 수 있으며, 매칭을 받고싶으면, 매칭받기 버튼을 눌러서 매칭을 받을 수 있다.
위 페이지에서 랜덤으로 데이터를 뽑아와 매칭을 시켜주고, 이렇게 함으로써 최종적으로 이 프로그램이 수행하고자하는 결과를 가져오게된것이 된다--%>
