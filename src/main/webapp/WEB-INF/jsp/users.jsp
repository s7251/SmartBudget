<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
    <table class="table">
    <thead>
    <tr>
    <th>Users</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${users}" var="user">
    <tr>
    <td>
    ${user.name}
    </td>
    </tr>
    </c:forEach>
    </tbody>
    </table>