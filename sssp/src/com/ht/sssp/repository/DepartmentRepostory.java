package com.ht.sssp.repository;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import com.ht.sssp.entity.Department;

public interface DepartmentRepostory extends JpaRepository<Department, Integer>{

	// @QueryHints ¶þ¼¶»º´æ×¢½â
	@QueryHints({@QueryHint(name=org.hibernate.ejb.QueryHints.HINT_CACHEABLE,value="true")})
	@Query("from Department d")
	List<Department> getAll();
}
