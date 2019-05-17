<%@ page import="crud.model.User" %>
<%@ page import="crud.logic.ValidateService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add a new user</title>
</head>
<body>
<form action='<%=request.getContextPath()%>/create' method='post'>
    Name : <input type = 'text' name='name'/>
    Login : <input type = 'text' name='login'/>
    Email : <input type = 'text' name='email'/>
    <input type = 'submit'>
</form>
</br>
<form action="<%=request.getContextPath()%>/list/" method="get">
    <input type='submit' value='User list'>
</form>

<table style="border: 1px solid black;" border="1" cellpadding="1" cellspacing="1">
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Login</th>
        <th>Email</th>
    </tr>
    <%for (User user : ValidateService.getInstance().findAll()) {%>
    <tr>
        <td><%=user.id()%></td>
        <td><%=user.name()%></td>
        <td><%=user.login()%></td>
        <td><%=user.email()%></td>
    </tr>
    <%}%>
</table>
</body>
</html>
