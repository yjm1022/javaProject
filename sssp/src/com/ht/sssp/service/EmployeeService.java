package com.ht.sssp.service;



import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ht.sssp.entity.Employee;
import com.ht.sssp.repository.EmployeeRepository;

@Service
public class EmployeeService {
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Transactional
	public void delete(Integer id) {
		employeeRepository.delete(id);
	}
	
	@Transactional
	public Employee get(Integer id) {
		return employeeRepository.findOne(id);
	}
	
	@Transactional
	public void save(Employee employee) {
		if(employee.getId() == null) {
			employee.setCreateDate(new Date());
		}
		employeeRepository.saveAndFlush(employee);
	}
	
	@Transactional(readOnly=true)
	public Employee getByLastName(String lastName) {
		return employeeRepository.getByLastName(lastName);
	}
	
	//readOnly=true …Ë÷√Œ™÷ª∂¡
	@Transactional(readOnly=true)
	public Page<Employee> getPage(int pageNo,int pageSize){
		PageRequest pageable = new PageRequest(pageNo-1, pageSize);
		return employeeRepository.findAll(pageable);
	} 
}
