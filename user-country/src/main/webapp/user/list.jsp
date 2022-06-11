<%--
  Created by IntelliJ IDEA.
  User: PC
  Date: 09/06/2022
  Time: 8:59 CH
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
        <a href="/persons?action=create">Add New User</a>
    </h2>
</center>
<center>
    <form action="/persons" method="get">
        <label for="cars">Search Name Person By Country</label>

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
        <input type="text" placeholder="Name Person" name="name_person">
        <button type="submit">Search</button>
    </form>
</center>
<div align="center">
    <table border="1" cellpadding="5" cellspacing="5">
        <tr>
            <th>Emp ID</th>
            <th>Emp Name</th>
            <th>Salary</th>
            <th>Country</th>
        </tr>

        <c:forEach var="user" items="${listUser}">
            <tr>
                <td><c:out value="${user.id}"/></td>
                <td><c:out value="${user.name}"/></td>
                <td><c:out value="${user.email}"/></td>
                <td>
                    <c:forEach var="country" items="${listCountry}">
                        <c:if test="${user.id_country == country.getIdCountry()}">
                            <c:out value="${country.getNameCountry()}"/>
                        </c:if>
                    </c:forEach>
                </td>
                <td>
                    <a href="/persons?action=edit&id=${user.id}">Edit</a>
                    <a href="/persons?action=delete&id=${user.id}">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>

    <%--For displaying Previous link except for the 1st page --%>
    <c:if test="${currentPage != 1}">
        <td><a href="/persons?page=${currentPage - 1}&id_country=${id_country}&name_person=${name_person}">Previous</a></td>
    </c:if>

    <%--For displaying Page numbers.
    The when condition does not display a link for the current page--%>
    <table border="1" cellpadding="5" cellspacing="5">
        <tr>
            <c:forEach begin="1" end="${noOfPages}" var="i">
                <c:choose>
                    <c:when test="${currentPage eq i}">
                        <td>${i}</td>
                    </c:when>
                    <c:otherwise>
                        <td><a href="/persons?page=${i}&id_country=${id_country}&name_person=${name_person}">${i}</a></td>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </tr>
    </table>

    <%--For displaying Next link --%>
    <c:if test="${currentPage lt noOfPages}">
        <td><a href="/persons?page=${currentPage + 1}&id_country=${id_country}&name_person=${name_person}">Next</a></td>
    </c:if>
</div>
</body>
</html>
