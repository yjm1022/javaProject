package com.ssm.bean;

public class Deptment {
    private Integer depId;

    private String depName;

    @Override
	public String toString() {
		return "Deptment [depId=" + depId + ", depName=" + depName + "]";
	}

	public Deptment() {
		super();
	}

	public Deptment(Integer depId, String depName) {
		super();
		this.depId = depId;
		this.depName = depName;
	}

	public Integer getDepId() {
        return depId;
    }

    public void setDepId(Integer depId) {
        this.depId = depId;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName == null ? null : depName.trim();
    }
}