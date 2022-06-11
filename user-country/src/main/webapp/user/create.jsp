<%--
  Created by IntelliJ IDEA.
  User: PC
  Date: 10/06/2022
  Time: 9:22 SA
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>User Management Application</title>
</head>
<body>
<center>
    <h1>User Management</h1>
    <h2>
        <a href="/persons?action=users">List All Users</a>
    </h2>
</center>
<div align="center">
    <form method="post">
        <table border="1" cellpadding="5">
            <caption>
                <h2>Add New User</h2>
            </caption>
            <tr>
                <th>User Name:</th>
                <td>
                    <c:choose>
                        <c:when test="${name != ''}">
                            <input type="text" name="name" id="name" size="45" value="${name}"/>
                        </c:when>
                        <c:otherwise>
                            <input type="text" name="name" id="name" size="45"/>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
            <tr>
                <th>User Email:</th>
                <td>
                    <c:choose>
                        <c:when test="${email != ''}">
                            <input type="text" name="email" id="name" size="45" value="${email}"/>
                        </c:when>
                        <c:otherwise>
                            <input type="text" name="email" id="name" size="45"/>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
            <tr>
                <th>Country:</th>
                <td>
                    <select id="cars" name="id_country">
                        <c:forEach var="country" items="${listCountry}">
                            <c:choose>
                                <c:when test="${id_country == country.getIdCountry()}">
                                    ...<option value="${country.getIdCountry()}" selected>${country.getNameCountry()}</option>
                                </c:when>
                                <c:when test="${id_country != country.getIdCountry()}">
                                    ...<option value="${country.getIdCountry()}">${country.getNameCountry()}</option>
                                </c:when>
                            </c:choose>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input type="submit" value="Save"/>
                </td>
            </tr>
        </table>
    </form>
    <h6>
        <c:choose>
            <c:when test="${err != ''}">
                <c:out value="${err}"></c:out>
            </c:when>
        </c:choose>
        <c:choose>
            <c:when test="${alert != ''}">
                <c:out value="${alert}"></c:out>
            </c:when>
        </c:choose>
    </h6>
</div>
</body>
</html>

