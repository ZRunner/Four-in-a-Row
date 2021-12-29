<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>
		<c:if test = "${title != null}"><c:out value = "${title}"/></c:if>
		<c:if test = "${title == null}"><c:out value = "Projet JEE"/></c:if>
	</title>
	<c:forEach items="${css}" var ="css_style">
		${css_style}
	</c:forEach>
</head>
