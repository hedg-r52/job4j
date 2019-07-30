<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Update of user</title>
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
            );
            message += (($('#name').val() == '') ? "Field 'Name' empty.\n" : "");
            message += (($('#login').val() == '') ? "Field 'Login' empty.\n" : "");
            message += (($('#email').val() == '') ? "Field 'Email' empty.\n" : "");
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
                    $('#city').val("${user.city}");
                } else {
                    $('#city').val();
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
        <c:if test="${error != ''}">
            <div style="background-color: red">
                <c:out value="${error}"/>
            </div>
        </c:if>
        <form class='form-horizontal' action='<c:out value="${pageContext.servletContext.contextPath}"/>/edit' method='post'>
            <h3>User parameters:</h3>
            <div class="form-group">
                <label class="control-label col-sm-2" for="name">Name :</label>
                <div class="col-sm-6">
                    <input type='text' class="form-control" id='name' name='name' value="<c:out value="${user.name}"/>"/>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-2" for="login">Login :</label>
                <div class="col-sm-6">
                    <input type='text' class="form-control" id='login' name='login' value="<c:out value="${user.login}"/>"
                            <c:if test="${(admin != 'true')}"> <c:out value="disabled"/></c:if>
                    />
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-2" for="email">Email :</label>
                <div class="col-sm-6">
                    <input type='email' class="form-control" id='email' name='email' value="<c:out value="${user.email}"/>"/>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-2" for="password">Password :</label>
                <div class="col-sm-6">
                    <input type='password' class="form-control" id='password' name='password' value="<c:out value="${user.password}"/>"/>
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
                    <select name="role" class="form-control" id="role" <c:if test="${(admin != 'true')}"><c:out value="disabled"/></c:if>>
                        <c:forEach items="${roles}" var="role">
                            <option value="<c:out value='${role.name}'/>"
                                    <c:if test="${role.name == user.role}"><c:out
                                            value="selected"/></c:if> >
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
                            formaction="<c:out value='${pageContext.servletContext.contextPath}'/>/users" formmethod="get" onclick="clearParams();">Return to list of user</button>
                </div>
            </div>
            <c:if test="${admin != 'true'}">
                <input type="hidden" name="login" value="<c:out value="${user.login}"/>">
                <input type="hidden" name="role" value="<c:out value="${user.role}"/>">
            </c:if>
            <input type="hidden" name="id" value="<c:out value="${user.id}"/>">
        </form>
</body>
</html>
