<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="bean.Stan" %>	
<jsp:useBean id="stanovi" class="dao.StanDAO" scope="application"></jsp:useBean>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Pregled stanova</title>
		<style>
		
		 	table,th,td
		 	{ 
		 		border: 2px solid grey;
		 	} 
		 
		 </style>
	</head>
	<body>
		<h1 style="color:red;"> JSP.Pregled stanova</h1>
		<table>
		<tr>
		
			<th>Broj stana</th>
			<th> Adresa </th>
			<th> Grad </th>
			<th> Broj soba </th>
			<th> Broj kvadrata </th>
			<th> Centralno grejanje </th>
			<th> Cena [EUR] </th>
			<th> </th>
			
		</tr>
		<c:forEach var="stan" items="${stanovi.findAll()}">
		<c:choose>
			<c:when test="${stan.dostupan.equals('dostupan')}">
				<tr style="background-color:grey;">
			</c:when>
			<c:otherwise>
				<tr>
			</c:otherwise>
		</c:choose>
		
		<td>${ stan.brojStana }</td>
		<td>${ stan.adresaStana }</td>
		<td>${ stan.gradStana }</td>
		<td>${ stan.brojSobaStana }</td>
		<td>${ stan.brojKvadrataStana }</td>
		<td>${ stan.centralnoGrejanjeStana }</td>
		<td>${ stan.cenaStana }</td>

		<c:choose>
			<c:when test="${stan.dostupan.equals('dostupan')}">
				<td></td>
			</c:when>
			<c:otherwise>
				<td> <a href="http://localhost:8081/PocetniServleti/PromeniDostupnostServlet?brojStana=${stan.brojStana }"> Dostupan </a></td>
			</c:otherwise>
		</c:choose>
		
		
		
		</tr>
		
		</c:forEach>
		
		
	</table>
	</body>
</html>