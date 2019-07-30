<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Sign in</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script>
        function validate() {
            var message = '';
            var result = (($('#login').val() != '') && ($('#password').val() != ''));
            message += (($('#login').val() == '') ? "Field 'Login' empty.\n" : "");
            message += (($('#password').val() == '') ? "Field 'Password' empty.\n" : "");
            if (!result) {
                alert(message);
            }
            return result;
        }
    </script>
</head>
<body>
<div class="container" style="margin-top: 30px;">
    <div class="row">
        <div class="form-group row">
            <div class="col-xs-3">
                <c:if test="${error != ''}">
                    <div style="background-color: red">
                        <c:out value="${error}"/>
                    </div>
                </c:if>
                <form action="${pageContext.servletContext.contextPath}/signin" method="post">
                    <div class="input-group input-group-sm">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                        <input id="login" type="text" class="form-control form-control-sm" name="login" placeholder="Login">
                    </div>
                    <div class="input-group input-group-sm">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                        <input id="password" type="password" class="form-control form-control-sm" name="password"
                               placeholder="Password">
                    </div>
                    <button type="submit" class="btn btn-default btn-xs" style="margin-top: 10px;" onclick="return validate();">Sign in</button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
