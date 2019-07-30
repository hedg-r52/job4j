<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>List of users</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="<c:out value="${pageContext.servletContext.contextPath}"/>/user.css">
</head>
<body>
    <div class="container" style="margin-top: 30px;">
        <form>
            <c:if test="${(admin == 'true')}">
                <button type="submit" class="btn btn-default btn-xs" style="margin-top: 10px;"
                        formaction='<c:out value="${pageContext.servletContext.contextPath}"/>/create' formmethod="get">Create
                    new user
                </button>
            </c:if>
            <button type="submit" class="btn btn-default btn-xs" style="margin-top: 10px;"
                    formaction='<c:out value="${pageContext.servletContext.contextPath}"/>/signout' formmethod="post">Sign
                out
            </button>
        </form>
        <h3>List of users:</h3>
        <table class="table table-striped table-condensed" id="table-compact">
            <thead>
            <tr>
                <th>Id</th>
                <th>Name</th>
                <th>Login</th>
                <th>Email</th>
                <th>Role</th>
                <th>Country</th>
                <th>City</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${users}" var="user">
                <c:if test="${(admin == 'true') || (user.login == login)}">
                    <tr>
                        <td><c:out value="${user.id}"/></td>
                        <td><c:out value="${user.name}"/></td>
                        <td><c:out value="${user.login}"/></td>
                        <td><c:out value="${user.email}"/></td>
                        <td><c:out value="${user.role}"/></td>
                        <td><c:out value="${user.country}"/></td>
                        <td><c:out value="${user.city}"/></td>
                        <td style="padding: 0px">
                            <form style="margin: 0px">
                                <button name='id' type='hidden' class='btn btn-default btn-xs'
                                        value=
                                            <c:out value="${user.id}"/>
                                        <c:if test="${(admin != 'true')}">
                                            <c:out value="disabled"/>
                                        </c:if>
                                                formaction='<c:out value="${pageContext.servletContext.contextPath}"/>/users'
                                        formmethod="post">Delete</button>
                                <button name='id' type='hidden' class='btn btn-default btn-xs'
                                        value=
                                            <c:out value="${user.id}"/>
                                                formaction='<c:out value="${pageContext.servletContext.contextPath}"/>/edit'
                                        formmethod="get">Update</button>
                            </form>
                        </td>
                    </tr>
                </c:if>
            </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>
