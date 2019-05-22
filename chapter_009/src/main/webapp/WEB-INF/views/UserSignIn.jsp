<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Sign in</title>
</head>
<body>
    <table>
        <tr>
            <th colspan=2>
                <c:if test="${error != ''}">
                    <div style="background-color: red">
                        <c:out value="${error}"/>
                    </div>
                </c:if>
            </th>
        </tr>
        <form action="${pageContext.servletContext.contextPath}/signin" method="post">
            <tr><td>Login:</td><td><input type="text" name="login"></td></tr>
            <tr><td>Password:</td><td><input type="password" name="password"></td></tr>
            <tr><td colspan=2><input type="submit"></td></tr>
        </form>
    </table>
</body>
</html>
