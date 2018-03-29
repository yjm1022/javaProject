<%@ page language="java" contentType="text/html; charset=UTf-8"
    pageEncoding="UTf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTf-8">
<title>listEmployee</title>
<%
	pageContext.setAttribute("emp_path",request.getContextPath());
 %>
</head>
<body>
	<table border="1" cellspacing="10" cellpadding="1">
		<tr>
			<td>编号</td>
			<td>姓名</td>
			<td>年龄</td>
			<td>邮箱</td>
			<td>电话</td>
			<td>部门</td>
		</tr>
		<c:forEach items="${page.list}" var="emp">
			<tr>
				<td>${emp.empId}</td>
				<td>${emp.empName }</td>
				<td>${emp.empAge }</td>
				<td>${emp.empEmail}</td>
				<td>${emp.empPhone }</td>
				<td>${emp.deptment.depName }</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="6">
				共${page.total } 条记录
				总 ${page.pages } 页数
				当前${page.pageNum } 页
				<a href="${emp_path }/employee/emps?pageNum=${page.pageNum -1}">上页</a>
				<c:forEach items="${ page.navigatepageNums}" var="nums">
					${nums}
				</c:forEach>
				
				<a href="${emp_path }/employee/emps?pageNum=${page.pageNum +1}">下页</a>
			</td>
		</tr>
	</table>
</body>
</html>