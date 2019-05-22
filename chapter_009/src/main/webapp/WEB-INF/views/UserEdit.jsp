<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Update of user</title>
</head>
<body>
<form action="<c:out value='${pageContext.servletContext.contextPath}'/>/" method="get">
    <input type="submit" value="Return to list of user">
</form>
<c:if test="${error != ''}">
    <div style="background-color: red">
        <c:out value="${error}"/>
    </div>
</c:if>
<br>User parameters:
<form action='<c:out value="${pageContext.servletContext.contextPath}"/>/edit' method='post'>
    Name : <input type='text' value="<c:out value="${user.name}"/>" name='name'>
    Login : <input
                type='text'
                value="<c:out value="${user.login}"/>"
                name='login'
                <c:if test="${(admin != 'true')}"> <c:out value="disabled"/></c:if>
    >
    Email : <input type='text' value="<c:out value="${user.email}"/>" name='email'>
    <c:if test="${admin == 'true'}">
    Role : <select name="role">
    <c:forEach items="${roles}" var="role">
    <option value="<c:out value='${role.name}'/>" <c:if test="${role.name == user.role}"><c:out
            value="selected"/></c:if> >
        <c:out value="${role.name}"/>
    </option>
    </c:forEach>
    </c:if>
    <c:if test="${admin != 'true'}">
        <input type="hidden" name="login" value="<c:out value="${user.login}"/>">
        <input type="hidden" name="role" value="<c:out value="${user.role}"/>">
    </c:if>
    <input type="hidden" name="id" value="<c:out value="${user.id}"/>">
    <input type="hidden" name="password" value="<c:out value="${user.password}"/>">
    <input type='submit'>
</form>
</body>
</html>
