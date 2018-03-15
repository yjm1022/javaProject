package com.ht.sssp.handler;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ht.sssp.entity.Department;
import com.ht.sssp.entity.Employee;
import com.ht.sssp.service.DepartmentService;
import com.ht.sssp.service.EmployeeService;

@Controller
public class EmployeeHandler {
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@RequestMapping(value="/delete/{id}",method=RequestMethod.DELETE)
	public String delete(@PathVariable(value="id") Integer id) {
		employeeService.delete(id);
		return "redirect:/emps";
	}
	
	@ModelAttribute
	public void getEmployee(@RequestParam(value="id",required=false) Integer id,
			Map<String,Object> map) {
		if(id != null) {
			Employee employee = employeeService.get(id);
			employee.setDepartment(null);
			map.put("employee", employee);
		}
	}
	
	@RequestMapping(value="/save/{id}",method=RequestMethod.POST)
	public String update(Employee employee) {
		employeeService.save(employee);
		return "redirect:/emps";
	}
	
	@RequestMapping(value="/emp/{id}",method=RequestMethod.GET)
	public String input(@PathVariable("id") Integer id,Map<String,Object> map) {
		Employee employee = employeeService.get(id);
		map.put("employee", employee);
		map.put("department", departmentService.getAll());
		return "emp/input";
	}
	
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public String save(Employee employee) {
		employeeService.save(employee);
		return "redirect:/emps";
	}
	
	@ResponseBody
	@RequestMapping(value="ajaxValidateLastName",method=RequestMethod.POST)
	public String validateLastName(@RequestParam(value="lastName",required=true) String lastName) {
		Employee employee = employeeService.getByLastName(lastName);
		if(employee == null) {
			return "0";
		}else {
			return "1";
		}
	}
	
	
	@RequestMapping(value="/emp",method = RequestMethod.GET)
	public String input(Map<String,Object> map) {
		map.put("department", departmentService.getAll());
		map.put("employee", new Employee());
		return "emp/input";
	}
	
	//value="pageNo",required=false,defaultValue="1"   
	//required=false 表示传入的pageNo可以是空,
	//defaultValue="1" 当传入的是空时默认值为 1
	// String pageNoStr   设为String类型是防止传入的是字母时造成系统瘫痪
	@RequestMapping("/emps")
	public String list(@RequestParam(value="pageNo",required=false,defaultValue="1") String pageNoStr,
			Map<String, Object> map) {
		int pageNo = 1;
		try {
			// 传入的是String类型时,转化成Int类型
			pageNo = Integer.parseInt(pageNoStr);
			if(pageNo < 1) {
				pageNo = 1;
			}
		} catch (Exception e) {}
		
		Page<Employee> page = employeeService.getPage(pageNo, 5);
		map.put("page", page);
		return "emp/list";
	}
	
}
