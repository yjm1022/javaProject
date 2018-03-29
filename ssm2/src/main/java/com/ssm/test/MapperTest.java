package com.ssm.test;

import java.util.Random;
import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ssm.bean.Employee;
import com.ssm.dao.DeptmentMapper;
import com.ssm.dao.EmployeeMapper;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:applicationContext.xml"})
public class MapperTest {
	
	@Autowired
	DeptmentMapper deptmanetMapper;
	
	@Autowired
	EmployeeMapper employeeMapper;
	
	@Autowired
	SqlSession sqlSession;
	
	@Test
	public void mapperTest() {
		
		//ApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext.xml");
		//DepartmentMapper bean = ioc.getBean(DepartmentMapper.class);*/
		//System.out.println(departmentMapper);
		
		//deptmanetMapper.insertSelective(new Deptment(null,"������"));
		//deptmanetMapper.insertSelective(new Deptment(null,"���в�"));
		
		//employeeMapper.insertSelective(new Employee(null,"�",22,"123@qq.com","1098989098",1, null));
		
		
		EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
		for(int i=1;i<100;i++) {
			String str = UUID.randomUUID().toString().substring(0,5)+i;
			String it = UUID.randomUUID().toString().substring(0, 11);
			Random rand = new Random();
			Integer ag = null;
			String phone = null;
			  for(int k = 0; k<100; k++) {
				  ag = rand.nextInt(30) + 20;
				  phone = Integer.toString(rand.nextInt(999999999)+100000000);
			  }
			mapper.insertSelective(new Employee(null,str,ag,str+"@qq.com","166"+phone,1,null));
		}
	}
	
}
