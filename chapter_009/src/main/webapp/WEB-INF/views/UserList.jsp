<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>List of users</title>
</head>
<body>
    <form action='<c:out value="${pageContext.servletContext.contextPath}"/>/create' method='get'>
        <input type='submit' value='Create new user'>
    </form>
    <form action='<c:out value="${pageContext.servletContext.contextPath}"/>/signout' method="post">
        <input type="submit" value="Sign out">
    </form>
    <br>List of users:
    <table style="border: 1px solid black;" border="1" cellpadding="1" cellspacing="1">
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Login</th>
            <th>Email</th>
            <th>Role</th>
            <th></th>
            <th></th>
        </tr>

        <c:forEach items="${users}" var="user">
            <c:if test="${(admin == 'true') || (user.login == login)}">
            <tr>
                <td><c:out value="${user.id}"/></td>
                <td><c:out value="${user.name}"/></td>
                <td><c:out value="${user.login}"/></td>
                <td><c:out value="${user.email}"/></td>
                <td><c:out value="${user.role}"/></td>
                <td>

                    <form action='<c:out value="${pageContext.servletContext.contextPath}"/>/users' method='post'>
                        <button
                                name='id'
                                type='hidden'
                                value=<c:out value="${user.id}"/>
                                <c:if test="${(admin != 'true')}"> <c:out value="disabled"/></c:if>
                        >
                            Delete
                        </button>
                    </form>

                </td>
                <td>
                    <form action='<c:out value="${pageContext.servletContext.contextPath}"/>/edit' method='get'>
                        <button name='id' type='hidden' value=<c:out value="${user.id}"/>>Update</button>
                    </form>
                </td>
            </tr>
            </c:if>
        </c:forEach>
    </table>
</body>
</html>
