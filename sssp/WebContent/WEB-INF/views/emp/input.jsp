<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>input</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
	$(function(){
		$("#lastName").change(function(){
			var val = $(this).val();
			val = $.trim(val);
			$(this).val(val);
			
			// 在修改页面 若 lastName和之前的lastName 一样,则不发送ajax请求
			var old_lastName = $("#old_lastName").val();
			old_lastName = $.trim(old_lastName);
			if(old_lastName !=null && old_lastName != "" && old_lastName == val){
				alert("lastName 可用");
				return;
			}
			
			var url = "${pageContext.request.contextPath}/ajaxValidateLastName";
			var args = {"lastName":val,"date":new Date()};
			
			$.post(url,args,function(data){
				if(data == "0"){
					alert("lastName 可用");
				}else if(data == "1"){
					alert("lastName 不可用");
				}else{
					alert("网络故障.......");
				}
			});
			
		});
	})

</script>
</head>
<body>
	
	<c:set value="${pageContext.request.contextPath}/save" var="url"></c:set>
	<c:if test="${employee.id != null }">
		<c:set value="${pageContext.request.contextPath}/save/${employee.id}" var="url"></c:set>
	</c:if>
	
	<form:form action="${url}" method="POST" modelAttribute="employee">
		<c:if test="${employee.id != null }">
			<input type="hidden" id="old_lastName" value="${employee.lastName}">
			<form:hidden path="id"/>
			<input type="hidden" name="_method" vloue="PUT">
		</c:if>	
		
		LastName : <form:input path="lastName" id="lastName"/>
		<br>
		Email : <form:input path="email" />
		<br>
		Birth : <form:input path="birth"/>
		<br>
		Dempartment : 
		<form:select path="department.id" items="${department}"
			itemLabel="departmentName" itemValue="id">
		</form:select>
		<input type="submit" value="submit">
			
	</form:form>
</body>
</html>