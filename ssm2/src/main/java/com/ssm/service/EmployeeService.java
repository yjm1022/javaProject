package com.ssm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssm.bean.Employee;
import com.ssm.bean.EmployeeExample;
import com.ssm.bean.EmployeeExample.Criteria;
import com.ssm.dao.EmployeeMapper;

@Service
public class EmployeeService {

	@Autowired
	EmployeeMapper employeeMapper;
	
	/**
	 * 	查询所有用户
	 * */
	public List<Employee> getAll() {
		return employeeMapper.selectByExampleWithDept(null);
	}

	/**
	 * 添加保存用户数据
	 * */
	public void saveEmpl(Employee employee) {
		employeeMapper.insertSelective(employee);
	}

	/**
	 * 判断用户名是否已经存在
	 * 当count==0时返回true,表示该用户名可用
	 * count!=0 时返回false,表示该用户名不可用
	 * */
	public Boolean checkEmplName(String empName) {
		/*构建查询条件*/
		EmployeeExample example = new EmployeeExample();
		/*创建查询条件*/
		Criteria criteria = example.createCriteria();
		/*拼装查询条件*/
		criteria.andEmpNameEqualTo(empName);
		/** 按照条件 统计 数据条数 */		
		Long count = employeeMapper.countByExample(example);
		
		return count == 0;
	}
	
	/**
	 * 依据id查找到员工
	 * */
	public Employee getById(Integer id) {
		Employee employee =	employeeMapper.selectByPrimaryKey(id);
		return employee;
	}
	/**
	 * 保存更新的员工信息
	 * */
	public void saveUpdateEmpl(Employee employee) {
		employeeMapper.updateByPrimaryKeySelective(employee);
	}

	/**
	 * 删除单个员工
	 * */
	public void deleteEmp(Integer empId) {
		employeeMapper.deleteByPrimaryKey(empId);
	}

	/**
	 * 批量删除员工
	 * */
	public void deleteList(List<Integer> listIds) {
		EmployeeExample example = new EmployeeExample();
		Criteria criteria = example.createCriteria();
		// delete from xxx where id in (1,2,3,4,5,5,......)
		criteria.andEmpIdIn(listIds);
		employeeMapper.deleteByExample(example);
	}

}
