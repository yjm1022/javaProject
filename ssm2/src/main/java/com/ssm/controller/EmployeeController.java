package com.ssm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ssm.bean.Employee;
import com.ssm.bean.Msg;
import com.ssm.service.EmployeeService;

@Controller
@RequestMapping("employee")
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;
	
	/**
	 * 删除员工
	 * */
	@RequestMapping(value="/deleteEmp/{empIds}",method=RequestMethod.DELETE)
	@ResponseBody
	public Msg deleteEmp(@PathVariable("empIds") String empIds) {
		if(empIds.contains("-")) {
			/*1-2-3-4*/
			List<Integer> listIds = new ArrayList<Integer>();
			String[] str_ids = empIds.split("-");
			for(String str:str_ids) {
				listIds.add(Integer.parseInt(str));
			}
			employeeService.deleteList(listIds);
			return Msg.success();
		}else {
			Integer ids= Integer.parseInt(empIds);
			employeeService.deleteEmp(ids);
			return Msg.success();
		}
	}
	
	/**修改员工数据*/
	@RequestMapping(value="/updateEmpl/{empId}",method=RequestMethod.PUT)
	@ResponseBody
	public Msg updateEmpl(Employee employee,HttpServletRequest request) {
		employeeService.saveUpdateEmpl(employee);
		return Msg.success();
	}
	
	/**依据id查找到员工*/
	@RequestMapping(value="/getById/{id}",method=RequestMethod.GET)
	@ResponseBody
	public Msg getByEmpId(@PathVariable("id") Integer id) {
		Employee employee = employeeService.getById(id);
		return Msg.success().add("employee", employee) ;
	}
	
	/*校验用户名是否可用*/
	@RequestMapping(value="/checkEmpName",method=RequestMethod.POST)
	@ResponseBody
	public Msg checkEmpName(@PathParam(value="empName") String empName) {
		Boolean b = employeeService.checkEmplName(empName);
		String reg = "(^[a-zA-Z0-9_-]{2,16}$)|(^[\u2E80-\u9FFF]{2,5})";
		if(!empName.matches(reg)) {
			return Msg.fail().add("Error","2-5位中文/2-16位英文和数字的组合");
		}
		if(b) {
			return Msg.success();
		}else {
			return Msg.fail().add("Error", "用户名不可用");
		}
	}
	
	/** 
	 * 添加保存用户数据 
	 *	@Valid 后面紧跟要校验的类 
	 *	BindingResult 封装错误信息
	 * */
	@RequestMapping(value="/saveEmpl",method=RequestMethod.POST)
	@ResponseBody
	public Msg saveEmpl(@Valid Employee employee,BindingResult result) {
		if(result.hasErrors()){
			/**
			 * 校验码失败
			 * result.getFieldErrors(); 得到所有错误信息
			 *  */
			Map<String,Object> map = new HashMap<String, Object>();
			List<FieldError> erroers = result.getFieldErrors();
			for(FieldError fieldError:erroers) {
				System.out.println("错误的字段"+fieldError.getField());
				System.out.println("错误信息"+fieldError.getDefaultMessage());
				map.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			
			return Msg.fail().add("fieldError", map);
		}else {
			employeeService.saveEmpl(employee);
			return Msg.success();
		}
		
	}
	
	
	/**JSON格式的分页查询*/
	@RequestMapping("/emps")
	@ResponseBody
	public Msg listEmpl(@PathParam(value="PageNum") String pageNum) {
		Integer pageNo = 1;
		try {
			// 把String类型转化成Integer类型	
			pageNo = Integer.parseInt(pageNum);
		} catch (Exception e) {}
		
		// 调用PageHelper进行分页,PageHelper.startPage(页码,每页显示多少条数据)    
		PageHelper.startPage(pageNo, 5);
		// 获取到所有的employee数据
		List<Employee> listEmpl = employeeService.getAll();
		// PageInfo 封装了分页信息	PageInfo(要传的数据,连续显示的页码5页)
		PageInfo page = new PageInfo(listEmpl,5);
		
		System.out.println("msg="+Msg.success().add("page",page));
		// Msg 是定义的通用的JSON传输数据类
		return Msg.success().add("page",page);
	}
	
	
	/**普通格式的分页查询*/
	//@RequestMapping("/emps")
	public String listEmployee(@PathParam(value="pageNum") String pageNum,Model model) {
		Integer pageNo = 1;
		try {
			pageNo = Integer.parseInt(pageNum);
		} catch (Exception e) {}
		
		// 引进 PagerHelper 分页插件
		// 传入  页码 和每页显示多少条数据
		PageHelper.startPage(pageNo, 5);
		// 查询到所有Employee数据
		List<Employee> listEmployee = employeeService.getAll();
		// PagerInfo 封装了分页的信息   PageInfo(数据,连续显示的页数); 这里是连续显示5页
		PageInfo page = new PageInfo(listEmployee,5);
		System.out.println(page);
		model.addAttribute("page",page);
		return "list";
	}
}
