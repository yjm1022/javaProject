package com.ht.sssp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ht.sssp.entity.Department;
import com.ht.sssp.repository.DepartmentRepostory;

@Service
public class DepartmentService {
	
	@Autowired
	private DepartmentRepostory departmentRepostory;
	
	
	@Transactional(readOnly=true)
	public List<Department> getAll(){
		return departmentRepostory.getAll();
	}
	
}
