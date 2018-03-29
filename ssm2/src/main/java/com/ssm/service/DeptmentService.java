package com.ssm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssm.bean.Deptment;
import com.ssm.dao.DeptmentMapper;

@Service
public class DeptmentService {

	@Autowired
	DeptmentMapper deptmentMapper;
	
	public List<Deptment> getDeptName() {
		List<Deptment> list = deptmentMapper.selectByExample(null);
		return list;
	}

}
