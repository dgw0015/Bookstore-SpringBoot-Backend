package com.bookstore.backend.Repository;

import com.bookstore.backend.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
