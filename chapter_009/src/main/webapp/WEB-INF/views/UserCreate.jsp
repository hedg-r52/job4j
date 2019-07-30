<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Add a new user</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="<c:out value="${pageContext.servletContext.contextPath}"/>/user.css">
    <script>
        function validate() {
            var message = '';
            var result = (($('#name').val() != '')
                && ($('#login').val() != '')
                && ($('#email').val() != '')
                && ($('#password').val() != '')
            );
            message += (($('#name').val() == '') ? "Field 'Name' empty.\n" : "");
            message += (($('#login').val() == '') ? "Field 'Login' empty.\n" : "");
            message += (($('#email').val() == '') ? "Field 'Email' empty.\n" : "");
            message += (($('#password').val() == '') ? "Field 'Password' empty.\n" : "");
            if (!result) {
                alert(message);
            }
            return result;
        }
        function clearParams() {
            $("input[type='text']").remove();
            $("input[type='password']").remove();
            $("input[type='email']").remove();
            $("input[type='hidden']").remove();
            $("select").remove();
        }
        $(loadCountries());
        function loadCountries() {
            $.ajax(
                "./countries", {
                    type: "get",
                    dataType: "json"
                }).done(function (countries) {
                var result = "";
                result += "<option selected disable> ...Choose country </option>";
                for (var i = 0; i < countries.length; ++i) {
                    result += "<option>" + countries[i].title + "</option>";
                }
                document.getElementById("country").innerHTML = result;
                if ("${user.country}" !== "") {
                    $("#country").val("${user.country}");
                    loadCities();
                } else {
                    $("#country").val();
                    $("#div-city").hide();

                }
            });
        }
        function loadCities() {
            var country = $("#country").val();
            $.ajax("./cities", {
                type: "get",
                data: "country=" + country,
                dataType: "json"
            }).done(function (cities) {
                var result = "";
                result += "<option selected disabled>... Choose city </option>";
                for (var i = 0; i < cities.length; ++i) {
                    result += "<option>" + cities[i].title + "</option>";
                }
                document.getElementById("city").innerHTML = result;
                var city = $('#city');
                $("#div-city").show(1000);
                if ('${user.city}' !== "" && city.find("option:contains('${user.city}')").length){
                    city.val("${user.city}");
                } else {
                    city.val();
                }
            });
        }
        $(
            (function(){
                $("#country").change(function(){
                    loadCities();
                });
            })
        );
    </script>
</head>
<body>
    <div class="container" style="margin-top: 30px;">
        <form class='form-horizontal' action="<c:out value="${pageContext.servletContext.contextPath}"/>/create" method="post">
            <div class="form-group">
                <label class="control-label col-sm-2" for="name">Name :</label>
                <div class="col-sm-6">
                    <input type='text' class="form-control" id='name' name='name' placeholder="Enter name"/>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-2" for="login">Login :</label>
                <div class="col-sm-6">
                    <input type='text' class="form-control" id='login' name='login' placeholder="Enter login"/>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-2" for="email">Email :</label>
                <div class="col-sm-6">
                    <input type='email' class="form-control" id='email' name='email' placeholder="Enter email"/>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-2" for="password">Password :</label>
                <div class="col-sm-6">
                    <input type='password' class="form-control" id='password' name='password' placeholder="Enter password"/>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-2" for="country">Country :</label>
                <div class="col-sm-6">
                    <select class="form-control" id='country' name='country'></select>
                </div>
            </div>
            <div class="form-group" id="div-city">
                <label class="control-label col-sm-2" for="city">City :</label>
                <div class="col-sm-6">
                    <select class="form-control" id='city' name='city'></select>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-2" for="role">Role :</label>
                <div class="col-sm-6">
                    <select name="role" class="form-control" id="role">
                        <c:forEach items="${roles}" var="role">
                            <option value="<c:out value='${role.name}'/>"
                                    <c:if test="${role.name == 'user'}"><c:out value="selected"/></c:if> >
                                <c:out value="${role.name}"/>
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-2" for="role"></label>
                <div class="col-sm-6">
                    <button type="submit" class="btn btn-default btn-xs" style="margin-top: 10px;" onclick="return validate();">Submit</button>
                    <button type="submit" class="btn btn-default btn-xs" style="margin-top: 10px;"
                            formaction='<c:out value="${pageContext.servletContext.contextPath}"/>/users' formmethod="get" onclick="clearParams();">User list</button>
                </div>
            </div>
        </form>

        <c:if test="${error != ''}">
            <div style="background-color: red">
                <c:out value="${error}"/>
            </div>
        </c:if>
        <div class="form-group">
            <label class="control-label col-sm-2" for="role"></label>
            <div class="col-sm-6">
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
                    </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${users}" var="user">
                            <tr>
                                <td><c:out value="${user.id}"/></td>
                                <td><c:out value="${user.name}"/></td>
                                <td><c:out value="${user.login}"/></td>
                                <td><c:out value="${user.email}"/></td>
                                <td><c:out value="${user.role}"/></td>
                                <td><c:out value="${user.country}"/></td>
                                <td><c:out value="${user.city}"/></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</body>
</html>
