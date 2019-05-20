<%@ page import="crud.model.User" %>
<%@ page import="crud.logic.ValidateService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>List of users</title>
</head>
<body>
    <form action='<%=request.getContextPath()%>/create/' method='get'>
        <input type='submit' value='Create new user'>
    </form>
    <br>List of users:
    <table style="border: 1px solid black;" border="1" cellpadding="1" cellspacing="1">
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Login</th>
            <th>Email</th>
            <th></th>
            <th></th>
        </tr>
        <%for (User user : ValidateService.getInstance().findAll()) {%>
            <tr>
                <td><%=user.id()%></td>
                <td><%=user.name()%></td>
                <td><%=user.login()%></td>
                <td><%=user.email()%></td>
                <td>
                    <form action='<%=request.getContextPath()%>/list' method='post'>
                        <button name='id' type='hidden' value=<%=user.id()%>>Delete</button>
                    </form>
                </td>
                <td>
                    <form action='<%=request.getContextPath()%>/edit/' method='get'>
                        <button name='id' type='hidden' value=<%=user.id()%>>Update</button>
                    </form>
                </td>
            </tr>
        <%}%>
    </table>
</body>
</html>
