<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="bean.User" %>	
<jsp:useBean id="users" class="dao.UsersDAO" scope="application"></jsp:useBean>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Users</title>
	</head>
	<body>
		<h1 style="color:#4a8fff;"> JSP.Pregled pacijenata [COVID-19] </h1>
		
		<c:forEach var="user" items="${users.findAll()}">
			<h2> ${user.ime }</h2>
		</c:forEach>
		
		
	</body>
</html>