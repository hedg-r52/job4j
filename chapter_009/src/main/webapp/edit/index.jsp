<%@ page import="crud.logic.ValidateService" %>
<%@ page import="crud.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update of user</title>
</head>
<body>
<form action='<%=request.getContextPath()%>/list/' method='get'>
    <input type='submit' value='Return to list of user'>
</form>
<%
    int id = Integer.valueOf(request.getParameter("id"));
    User user = ValidateService.getInstance().findById(id).get();
%>
Parameter's user:<br/>
<form action="<%=request.getContextPath()%>/edit" method="post">
    Name : <input type = 'text' value="<%=user.name()%>" name='name'/>
    Login : <input type = 'text' value="<%=user.login()%>" name='login'/>
    Email : <input type = 'text' value="<%=user.email()%>" name='email'/>
    <input type="hidden" name="id" value="<%=user.id()%>">
    <input type = 'submit'>
</form>
</body>
</html>
