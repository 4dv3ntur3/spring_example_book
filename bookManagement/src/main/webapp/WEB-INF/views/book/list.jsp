<%@ page pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<html>  
<head>  
<title>책 목록</title>  
</head>  
<body>  
<h1>책 목록</h1>  
<p>
<%-- 검색 기능 추가 --%>
<form>
<input type="text" placeholder="검색" name="keyword" value="${keyword}"/>
<input type="submit" value="검색"/>
<p>
</form>
<%-- 검색 기능 --%>
<table>  
<thead>  
<tr>  
<td>제목</td>  
<td>카테고리</td>  
<td>가격</td>  
</tr>  
</thead>  
<tbody>  
<%-- JSTL에서 반복문을 사용하기 위한 구문  --%>
<%-- <c: 태그를 사용하기 위해 태그 라이브러리 선언 (위에) --%>
<c:forEach var="row" items="${data}">  <%-- 컨트롤러에서 전달받은 데이터 참조명: data --%>
<%-- 목록의 한 행을 나타내는 변수명  --%>
<tr>  
<td>  
<a href="/detail?bookId=${row.book_id}">  
${row.title}  
</a>  
</td>  
<td>${row.category}</td>  
<td><fmt:formatNumber type="number" maxFractionDigits="3" value="${row.price}" /></td>  
</tr>  
</c:forEach>  
</tbody>  
</table>  
<p>  
<a href="/create">생성</a>  
</p>  
</body>  
</html>  