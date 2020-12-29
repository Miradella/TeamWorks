package com.example.TeamWork.Service;

import com.example.TeamWork.Repository.EmployeeRepository;
import com.example.TeamWork.entity.Employee;
import com.example.TeamWork.entity.Team;
import com.example.TeamWork.exception.BadResourceException;
import com.example.TeamWork.exception.ResourceAlreadyExistsException;
import com.example.TeamWork.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {
  @Autowired
  private EmployeeRepository employeeRepository;

  private boolean existsById(Integer id) {
    return employeeRepository.existsById(id);
  }

  public Employee findById(Integer id) throws ResourceNotFoundException {
    Employee employee = employeeRepository.findById(id).orElse(null);
    if (employee == null) {
      throw new ResourceNotFoundException("Cannot find Contact with id: " + id);
    } else return employee;
  }
  public List<Employee> findAll(int pageNumber, int rowPerPage) {
    List<Employee> employees = new ArrayList<>();
    employeeRepository.findAll().forEach(employees::add);
    return employees;
  }
  public  List<Employee> findAllByTeam(Team team, int pageNumber, int rowPerPage) throws ResourceNotFoundException {
    return  employeeRepository.findByTeam(team);
  }
  public Employee save(Employee employee) throws BadResourceException, ResourceAlreadyExistsException {
    if (employee != null && existsById(employee.getEmployeeId())) {
      throw new ResourceAlreadyExistsException("Contact with id: " + employee.getEmployeeId() +
        " already exists");
    }
    return employeeRepository.save(employee);
  }


  public void update(Employee employee)
    throws BadResourceException, ResourceNotFoundException {
    if (!existsById(employee.getEmployeeId())) {
      throw new ResourceNotFoundException("Cannot find Contact with id: " + employee.getEmployeeId());
    }
    employeeRepository.save(employee);
  }


  public void deleteById(Integer id) throws ResourceNotFoundException {
    if (!existsById(id)) {
      throw new ResourceNotFoundException("Cannot find contact with id: " + id);
    } else {
      employeeRepository.deleteById(id);
    }
  }

  public Long count() {
    return employeeRepository.count();
  }
}
