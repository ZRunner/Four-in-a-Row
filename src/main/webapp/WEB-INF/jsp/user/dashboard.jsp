<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
	<%@include file="../include/header.jsp" %>
<body>
	<c:if test = "${page != null}"><c:out value = "${page}"/></c:if>
	<c:if test = "${page == null}"><c:out value = "profile"/></c:if>


	<%@include file="../include/footer.jsp" %>
</body>
</html>