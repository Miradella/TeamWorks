package com.example.TeamWork.Repository;

import com.example.TeamWork.entity.Employee;
import com.example.TeamWork.entity.Team;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmployeeRepository  extends CrudRepository<Employee, Integer>,
  JpaSpecificationExecutor<Employee> {
  List<Employee> findByTeam(Team team);
}
