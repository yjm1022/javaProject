<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>index</title>
<%
	pageContext.setAttribute("empPath",request.getContextPath());
%>

<link href="${empPath }/strict/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<script type="text/javascript" src="${empPath }/strict/js/jquery-1.12.4.min.js"></script>  
<script type="text/javascript" src="${empPath }/strict/bootstrap/js/bootstrap.min.js"></script>


</head>
<body>
	<!-- update 模态框开始  -->
	<div class="modal fade" id="updateEmplModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="myModalLabel">Modal title</h4>
	      </div>
	      <div class="modal-body">
		      <form class="form-horizontal">
	      	  	<div class="form-group">
				    <label for="inputEmail3" class="col-sm-2 control-label">姓名</label>
				    <div class="col-sm-10">
				      	<input type="text" class="form-control" name="empName" id="empName_update_input" placeholder="Name">
				      	<span class="help-block"></span>	
				    </div>
				</div>
			  	<div class="form-group">
			    	<label for="inputEmail3" class="col-sm-2 control-label">年龄</label>
			    	<div class="col-sm-10">
			      		<input type="text" class="form-control" name="empAge" id="empAge_update_input" placeholder="Age">
			    		<span class="help-block"></span>	
			    	</div>
			  	</div>
			  	<div class="form-group">
			    	<label for="inputEmail3" class="col-sm-2 control-label">邮箱</label>
			    	<div class="col-sm-10">
			      		<!-- <input type="email" class="form-control" name="empEmail" id="empEmail_update_input" placeholder="Email">
			    		<span class="help-block"></span> -->
			    		<p class="form-control-static" id="empEmail_update_static"></p>
			    	</div>
			  	</div> 
			  	<div class="form-group">
			    	<label for="inputEmail3" class="col-sm-2 control-label">手机号</label>
			    	<div class="col-sm-10">
			      		<input type="text" class="form-control" name="empPhone" id="empPhone_update_input" placeholder="Phone">
			    		<span class="help-block"></span>	
			    	</div>
			  	</div>
			    <div class="form-group">
			    	<label class="col-sm-2 control-label">部门</label>
			    	<div class="col-sm-6">
			      		<select class="form-control" name="dId" id="select_dept_name">
						 	<!-- 这里显示部门信息 -->
						</select>
			    	</div>
			  	</div>  
			  </form>	
		  </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	        <button type="button" class="btn btn-primary" id="empl_update_btn">保存</button>
	      </div>
	    </div>
	  </div>
	</div>
	<!-- update 模态框结束 -->



	<!-- add 模态框开始  -->
	<div class="modal fade" id="addEmpl" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="myModalLabel">Modal title</h4>
	      </div>
	      <div class="modal-body">
		      <form class="form-horizontal">
	      	  	<div class="form-group">
				    <label for="inputEmail3" class="col-sm-2 control-label">姓名</label>
				    <div class="col-sm-10">
				      	<input type="text" class="form-control" name="empName" id="empName" placeholder="Name">
				      	<span class="help-block"></span>	
				    </div>
				</div>
			  	<div class="form-group">
			    	<label for="inputEmail3" class="col-sm-2 control-label">年龄</label>
			    	<div class="col-sm-10">
			      		<input type="text" class="form-control" name="empAge" id="empAge" placeholder="Age">
			    		<span class="help-block"></span>	
			    	</div>
			  	</div>
			  	<div class="form-group">
			    	<label for="inputEmail3" class="col-sm-2 control-label">邮箱</label>
			    	<div class="col-sm-10">
			      		<input type="email" class="form-control" name="empEmail" id="empEmail" placeholder="Email">
			    		<span class="help-block"></span>	
			    	</div>
			  	</div> 
			  	<div class="form-group">
			    	<label for="inputEmail3" class="col-sm-2 control-label">手机号</label>
			    	<div class="col-sm-10">
			      		<input type="text" class="form-control" name="empPhone" id="empPhone" placeholder="Phone">
			    		<span class="help-block"></span>	
			    	</div>
			  	</div>
			    <div class="form-group">
			    	<label class="col-sm-2 control-label">部门</label>
			    	<div class="col-sm-6">
			      		<select class="form-control" name="dId" id="select_dept_name">
						 	<!-- 这里显示部门信息 -->
						</select>
			    	</div>
			  	</div>  
			  </form>	
		  </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	        <button type="button" class="btn btn-primary" id="empl_save_btn">保存</button>
	      </div>
	    </div>
	  </div>
	</div>
	<!-- add 模态框结束 -->

	<!-- list显示部分开始 -->
	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<h2 class="">SSM</h2>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4 col-md-offset-10">
				<button class="btn btn-primary" id="add_empl_btn">新增</button>
				<button class="btn btn-danger" id="delete_empl_btn">删除</button>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<table class="table table-hover" id="empl_table">
					<thead>
						<tr>
							<th>
								<input type="checkbox" id="check_all">
							</th>
							<th>编号</th>
							<th>姓名</th>
							<th>年龄</th>
							<th>邮箱</th>
							<th>电话</th>
							<th>部门</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						
					</tbody>
				</table>
			</div>
		</div>
		<div class="row">
			<!-- 显示分页信息开始 -->
			<div class="col-md-6" id="empl_info_area">
				
			</div>
			<!-- 显示分页信息结束  -->
			
			<!-- 显示分页条数开始 -->
			<div class="col-md-6" id="empl_nav_area">
				
			</div>
			<!-- 显示分页条数结束 -->
		</div>
	</div>
	<!-- list显示部分结束 -->
</body>
<script type="text/javascript">
	/* 定义全局的总页数,当前页 */
	var totalNums,currentPage;

	$(function(){
		to_page(1);
	})
	
	/************    数据-显示-开始          *************/
	function to_page(pageNum){
		$.ajax({
			url:"${empPath}/employee/emps",
			data:"pageNum="+pageNum,
			type:"GET",
			success:function(result){
				// 打印在浏览器控制台
				//console.log(result);
				// 1. 解析显示员工数据表					
				build_empl_table(result)
				// 2. 解析显示分页信息
				build_empl_info(result);
				// 3. 解析显示分页条数据
				build_empl_nav(result);
			}
	});
	}
	
	// 1. 解析显示员工数据表
	function build_empl_table(result){
		// 清空表格
		$("#empl_table tbody").empty();
		// 获取到 employee 的 list
		var emps = result.extend.page.list;
		$.each(emps, function(index, item) {
			// 依据索引进行添加   item代表当前记录
			// 创建一个多选框
			var checkBoxTd = $("<td><input type='checkbox' class='check_item'></td>");
			// 创建td并通过append把数据添加进去
			var emplIdTd = $("<td></td>").append(item.empId);
			var emplNameTd = $("<td></td>").append(item.empName);
			var emplAgeTd = $("<td></td>").append(item.empAge);
			var emplEmailTd = $("<td></td>").append(item.empEmail);
			var emplPhoneTd = $("<td></td>").append(item.empPhone);
			var depNameTd = $("<td></td>").append(item.deptment.depName);
			
			var editBtn = $("<button></button>").addClass("btn btn-success edit_btn")
							.append("<span></span>").addClass("glyphicon glyphicon-pencil").append("编辑");
			var deltBtn = $("<button></button>").addClass("btn btn-danger delte_btn")
							.append("<span></span>").addClass("glyphicon glyphicon-trash").append("删除");
			var btnTd = $("<td></td>").append(editBtn).append(" ").append(deltBtn);
			// 给编辑按钮添加一个自定义的样式 存放员工id 好用来进行单个查找
			editBtn.attr("edit-id",item.empId);
			deltBtn.attr("dele-id",item.empId);
			// 把td添加都tr里面，再通过appendTo把tr放到table里面去
			$("<tr></tr>").append(checkBoxTd)
				.append(emplIdTd)
				.append(emplNameTd)
				.append(emplAgeTd)
				.append(emplEmailTd)
				.append(emplPhoneTd)
				.append(depNameTd)
				.append(btnTd)
				.appendTo("#empl_table tbody");
		})
	}
	
	// 2. 解析显示分页信息
	function build_empl_info(result){
		// 得到分页数据
		var emps = result.extend.page;
		// 清空数据
		$("#empl_info_area").empty();
		$("#empl_info_area").append("总"+emps.pages+"页数"
				+" 总"+emps.total+"条记录");
		totalNums = emps.total;
		currentPage = emps.pageNum;
	}
	
	// 3. 解析显示分页条
	function build_empl_nav(result){
		var emps = result.extend.page;
		$("#empl_nav_area").empty();
		
		// 创建ul 并添加样式		
		var ul = $("<ul></ul>").addClass("pagination");
		// 创建首页
		var nav_first = $("<li></li>").append($("<a></a>").append("首页").attr("href", "#"));
		// 创建上一页
		var nav_pre = $("<li></li>").append($("<a></a>").append("&laquo;"))
		// 判断是否还有上一页
		if(emps.hasPreviousPage == false){
			// 没有上一页   添加disabled 样式  让按键禁用
			nav_first.addClass("disabled");
			nav_pre.addClass("disabled");
		}else{
			// 有上一页 
			nav_first.click(function() {
				// 点击首页  跳第一页
				to_page(1);
			})
			nav_pre.click(function(){
				// 点击上一页      当前页减一
				to_page(emps.pageNum - 1);
			})
		}
		
		
		// 创建下一页
		var nav_next = $("<li></li>").append($("<a></a>").append("&raquo;"))
		// 创建尾页
		var nav_last = $("<li></li>").append($("<a></a>").append("尾页").attr("href", "#"));
		
		// 判断是否有下一页
		if(emps.hasNextPage == false){
			// 没有下一页   让按键禁用
			nav_next.addClass("disabled");
			nav_last.addClass("disabled");
		}else{
			// 有下一页
			nav_next.click(function() {
				// 点击下一页   当前页加一
				to_page(emps.pageNum + 1);
			})
			nav_last.click(function() {
				// 点击尾页     总页数就是尾页
				to_page(emps.pages)
			})
		}
		
		// 把 首页和上一页 添加到ul中
		ul.append(nav_first).append(nav_pre);
		// 循环出连续显示的页码	itme当前页码  
		$.each(emps.navigatepageNums, function(index, itme) {
			// li里面有个a标签       a标签里是当前页码
			var nav_nums = $("<li></li>").append($("<a></a>").append(itme));
			
			if(emps.pageNum == itme){
				// 如果页码 ==当前页码      让当前页码高亮
				nav_nums.addClass("active");
			}
			nav_nums.click(function() {
				// 点击哪个页码就跳哪个页码
				to_page(itme);
			})
			// 把循环出的连续页码放到ul
			ul.append(nav_nums);
		})
		
		// 把下一页和尾页放到ul里
		ul.append(nav_next).append(nav_last);
		// 把ul放到nav里面
		var navEml = $("<nav></nav>").append(ul);
		// nav 又是在div#empl_nav_area 里面
		navEml.appendTo("#empl_nav_area");
	}
	/************    数据-显示-结束          *************/
	
	
	/*********** 	数据-添加-开始 	******************/
	/* 
		check_toll 校验工具
		ele 	需要校验的id
		state 	校验时的状态success和error   对或者错
		content	填写的内容
	*/
	function check_toll(ele,state,content){
		/* 不管怎样   先清空父元素之前的样式     以及后一个元素的提示内容 */
		$(ele).parent().removeClass("has-success has-error");
		$(ele).next("span").text("");
		if("success" == state){
			$(ele).parent().addClass("has-success");
			$(ele).next("apan").text(content);
		}else if("error" == state){
			$(ele).parent().addClass("has-error");
			$(ele).next("span").text(content);
		}
	}
	/* 用正则表达式判断姓名和邮箱是否可用  */
	function check_add_form(){
		var emplName= $("#empName").val().trim();
		var regName = /(^[a-zA-Z0-9_-]{2,16}$)|(^[\u2E80-\u9FFF]{2,5})/;
		if(!regName.test(emplName)){
			/* 如果与与正则表达式不同 */
			check_toll("#empName","error","用户名可以是2-5位中文或者2-16位英文和数字的组合");
			return false;
		}else{
			check_toll("#empName","success","");
		}
		
		var emplEmail = $("#empEmail").val().trim();
		var regEmail = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
		if(!regEmail.test(emplEmail)){
			check_toll("#empEmail","error","邮箱格式不正确");
			return false;
		}else{
			check_toll("#empEmail", "success", "");
		}
		return true;
	}
	
	
	
	/* 清除表单 */
	function clean_addForm(ele){
		/* jquery表单没有清除方法   这里用的时Dom方法 */
		$(ele)[0].reset();
		/* 移除所有的 has-success has-error 样式*/
		$(ele).find("*").removeClass("has-success has-error");
		/* 清除所有提示信息 */
		$(ele).find(".help-block").text("");
	}
	
	/*数据库判断用户名是否存在 */
	$("#empName").change(function() {
		var enpname = this.value;
		$.ajax({
			url:"${empPath}/employee/checkEmpName",
			data:"empName="+enpname,
			type:"POST",
			success:function(result){
				// result.code==1 表示true
				if(result.code==1){
					check_toll("#empName","success","用户名可用");
					$("#empl_save_btn").attr("ajax_val","success");
				}else{
					check_toll("#empName","error",result.extend.Error);
					$("#empl_save_btn").attr("ajax_val","error");
				}
			}
		});
	});
	
	
	/*  点击添加按钮 弹出模态框 */
	$("#add_empl_btn").click(function(){
		/* 清除表格 */
		clean_addForm("#addEmpl form");
		/* 查询部门名称 */
		select_deptName("#addEmpl select");
		/* 弹出模态框 */
		$("#addEmpl").modal({
			backdrop:"static"
		});
		
	});
		
	/* 点击提交按钮进行提交  */
	$("#empl_save_btn").click(function(){
		// 调用姓名邮箱 校验方法
		if(!check_add_form()){
			return false;
		}
		
		//1、判断之前的ajax用户名校验是否成功。如果成功。
		if($(this).attr("ajax_val")=="error"){
			return false;
		}
		
		// $("#addEmpl form").serialize() 表单序列化,可以得到填写的数据
		$.ajax({
			url:"${empPath}/employee/saveEmpl",
			data:$("#addEmpl form").serialize(),
			type:"POST",
			success:function(result){
				if(result.code == 1){
					//alert(result.code);
					// 1. 提交成功后关闭 添加数据的模态框
					$("#addEmpl").modal('hide');
					// 2. 页面跳转到最后一页 ; 
					// 定义一个全局的数据总记录数,PageHelper分页方法在传入数字大于实际页码数时总是跳转到最后一页
					to_page(totalNums);
				}else{
					if(undefined != result.extend.fieldError.empName){
						check_toll("#empName","error",result.extend.fieldError.empName);
					}
					if(undefined != result.extend.fieldError.empEmail){
						check_toll("#empEmail","error",result.extend.fieldError.empEmail);
					}
				}
			}
		})
	});


	
	/* 遍历查询到deptment的name  也就是部门名称  */
	function select_deptName(ele){
		$(ele).empty();
		$.ajax({
				url:"${empPath}/dept/getDeptName",
				type:"GET",
				success:function(result){
					//console.log(result);
					$.each(result.extend.listDept, function() {
						var dept_option = $("<option></option>").append(this.depName).attr("value",this.depId);
						dept_option.appendTo(ele);				
					})
					
				}
		});
	}
	
	/*********** 	数据-添加-结束 	******************/

	
	/*************  数据-修改-开始 	*****************/
	/* 点击更新按钮 */
	$(document).on("click",".edit_btn",function() {
		/* 1. 查找部门名称  */
		select_deptName("#updateEmplModel select");
		/* 2. 显示出对应的员工信息  */
		getByIdEmp($(this).attr("edit-id"));
		/* 3. 把员工的id传给模态框的更新按钮 */
		$("#empl_update_btn").attr("edit-id",$(this).attr("edit-id"));
		/* 4. 弹出修改模态框 */
		$("#updateEmplModel").modal({
			backdrop:"static"
		});
	});
	
	function getByIdEmp(id){
		$.ajax({
			url:"${empPath}/employee/getById/"+id,
			type:"GET",
			success:function(result){
				//console.log(result);
				var emp = result.extend.employee;
				$("#empName_update_input").val(emp.empName);
				$("#empAge_update_input").val(emp.empAge);
				$("#empEmail_update_static").text(emp.empEmail);
				$("#empPhone_update_input").val(emp.empPhone);
				$("#updateEmplModel select").val([emp.dId])
			}
		});
	}
	
	$("#empl_update_btn").click(function() {
		var emplName= $("#empName_update_input").val().trim();
		var regName = /(^[a-zA-Z0-9_-]{2,16}$)|(^[\u2E80-\u9FFF]{2,5})/;
		if(!regName.test(emplName)){
			/* 如果与与正则表达式不同 */
			check_toll("#empName_update_input","error","用户名可以是2-5位中文或者2-16位英文和数字的组合");
			return false;
		}else{
			check_toll("#empName_update_input","success","");
		}
		$.ajax({
			url:"${empPath}/employee/updateEmpl/"+$(this).attr("edit-id"),
			type:"PUT",
			data:$("#updateEmplModel form").serialize(),
			success:function(result){
				/* 1. 关闭模态框 */
				$("#updateEmplModel").modal('hide');
				/* 2. 回到本页 */
				to_page(currentPage);
			}
		})
		
	})
	/*************  数据-修改-开始 	*****************/
	
	/*************	数据-删除-开始	*****************/
	/* 删除单个员工 */ 
	$(document).on("click",".delte_btn",function() {
		/* 得到要删除员工的姓名  */
		var emp_Name = $(this).parents("tr").find("td:eq(2)").text();
		/* 得到要删除员工的ID */
		var emp_id = $(this).attr("dele-id");
		if(confirm("真要删除【"+emp_Name+"】吗？")){
			$.ajax({
				url:"${empPath}/employee/deleteEmp/"+emp_id,
				type:"DELETE",
				success:function(result){
					/* 弹出删除成功信息 */
					alert(result.msg);
					/* 跳回当前页码 */
					to_page(currentPage);
				}
			});
		}
	});
	
	$("#check_all").click(function() {
		/* 判断当前总多选框谁否被选中	选中为true	未选中为false */
		var b = $(this).prop("checked");
		/* prop 
			第一个参数 	规定属性的名称
			第二个参数	规定属性的值
			
			当第二参数为true时选框被选中
			当第二参数为false时选框部不被选中
		*/
		$(".check_item").prop("checked",b);
	})
	
	$(document).on("click",".check_item",function(){
		/* 判断是否选中了当页所有的多选框	是-true	否-false */
		var f = $(".check_item:checked").length == $(".check_item").length; 
		/* 依据规定的属性值判断是否让总的多选框选中 */
		$("#check_all").prop("checked",f);
	})
	
	/* 点击删除按钮-批量删除员工 */
	$("#delete_empl_btn").click(function() {
		var del_emp_nameList = "";
		var del_emp_idList = "";
		$.each($(".check_item:checked"), function(index, item) {
			/* 寻找到要删除员工的姓名 */
			del_emp_nameList += $(this).parents("tr").find("td:eq(2)").text()+",";
			/* 寻找到要删除员工的ID */
			del_emp_idList += $(this).parents("tr").find("td:eq(1)").text()+"-";
		})
		/*去除del_emp_nameList最后面多余的 ,		把原先的 张三,李四,	变为  张三,李四 */
		del_emp_nameList = del_emp_nameList.substring(0, del_emp_nameList.length-1);
		del_emp_idList = del_emp_idList.substring(0, del_emp_idList.length-1);
		if(confirm("您确定要删除【"+del_emp_nameList+"】吗？")){
			$.ajax({
				url:"${empPath}/employee/deleteEmp/"+del_emp_idList,
				type:"DELETE",
				success:function(result){
					alert(result.msg);
					to_page(currentPage);
				}
			})
		}
		
	})
	/*************	数据-删除-结束	*****************/
	
</script>
</html>