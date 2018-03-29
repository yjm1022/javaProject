package com.ssm.bean;

import javax.validation.constraints.Pattern;

public class Employee {
    private Integer empId;

    /**
     * @Pattern 给该字段一个规范
     * */
    @Pattern(regexp="(^[a-zA-Z0-9_-]{2,16}$)|(^[\\u2E80-\\u9FFF]{2,5})",
    		message="2-5位中文/2-16位英文和数字的组合")
    private String empName;

    private Integer empAge;
    
    @Pattern(regexp="^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})",
    		message="邮箱格式不正确")
    private String empEmail;

    private String empPhone;

    private Integer dId;
    
    private Deptment deptment;

    @Override
	public String toString() {
		return "Employee [empId=" + empId + ", empName=" + empName + ", empAge=" + empAge + ", empEmail=" + empEmail
				+ ", empPhone=" + empPhone + ", dId=" + dId + ", deptment=" + deptment + "]";
	}

	public Employee() {
		super();
	}

	public Employee(Integer empId, String empName, Integer empAge, String empEmail, String empPhone, Integer dId,
			Deptment deptment) {
		super();
		this.empId = empId;
		this.empName = empName;
		this.empAge = empAge;
		this.empEmail = empEmail;
		this.empPhone = empPhone;
		this.dId = dId;
		this.deptment = deptment;
	}

	public Deptment getDeptment() {
		return deptment;
	}

	public void setDeptment(Deptment deptment) {
		this.deptment = deptment;
	}

	public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName == null ? null : empName.trim();
    }

    public Integer getEmpAge() {
        return empAge;
    }

    public void setEmpAge(Integer empAge) {
        this.empAge = empAge;
    }

    public String getEmpEmail() {
        return empEmail;
    }

    public void setEmpEmail(String empEmail) {
        this.empEmail = empEmail == null ? null : empEmail.trim();
    }

    public String getEmpPhone() {
        return empPhone;
    }

    public void setEmpPhone(String empPhone) {
        this.empPhone = empPhone == null ? null : empPhone.trim();
    }

    public Integer getdId() {
        return dId;
    }

    public void setdId(Integer dId) {
        this.dId = dId;
    }
}