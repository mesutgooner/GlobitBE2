package com.globits.da.repository;

import com.globits.da.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    @Query("select e from Employee e where e.name like %:keyword% or e.email like %:keyword% or e.phone like %:keyword%")
    List<Employee> search(String keyword);
}
