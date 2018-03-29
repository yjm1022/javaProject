package com.ssm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.bean.Deptment;
import com.ssm.bean.Msg;
import com.ssm.service.DeptmentService;

@Controller
@RequestMapping("dept")
public class DeptmentController {
	@Autowired
	DeptmentService deptmentService;
	
	@RequestMapping(value="/getDeptName",method=RequestMethod.GET)
	@ResponseBody
	public Msg getDeptName() {
		List<Deptment> listDept = deptmentService.getDeptName();
		System.out.println(listDept);
		return Msg.success().add("listDept", listDept);
	}
}
