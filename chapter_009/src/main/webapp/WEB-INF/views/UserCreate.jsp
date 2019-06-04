<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Add a new user</title>
</head>
<body>
<form action="<c:out value="${pageContext.servletContext.contextPath}"/>/create" method="post">
    <label>
        Name :
        <input type='text' name='name'/>
    </label>
    Login : <input type = 'text' name='login'/>
    <label>
        Email :
        <input type='text' name='email'/>
    </label>
    <label>
        Password :
        <input type="password" name='password'/>
    </label>
    <label>
        Role :
        <select name="role">
            <c:forEach items="${roles}" var="role">
                <option value="<c:out value='${role.name}'/>"
                <c:if test="${role.name == 'user'}"><c:out value="selected"/></c:if> >
                <c:out value="${role.name}"/>
                </option>
            </c:forEach>
        </select>
    </label>
    <input type = 'submit'>
</form>
<form action="<c:out value="${pageContext.servletContext.contextPath}"/>/" method="get">
    <input type='submit' value='User list'>
</form>
<c:if test="${error != ''}">
    <div style="background-color: red">
        <c:out value="${error}"/>
    </div>
</c:if>
<table style="border: 1px solid black;" border="1" cellpadding="1" cellspacing="1">
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Login</th>
        <th>Email</th>
        <th>Role</th>
    </tr>
    <c:forEach items="${users}" var="user">
    <tr>
        <td><c:out value="${user.id}"/></td>
        <td><c:out value="${user.name}"/></td>
        <td><c:out value="${user.login}"/></td>
        <td><c:out value="${user.email}"/></td>
        <td><c:out value="${user.role}"/></td>
    </tr>
    </c:forEach>
</table>
</body>
</html>
