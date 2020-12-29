package com.example.TeamWork.Controller;

import com.example.TeamWork.Service.EmployeeService;
import com.example.TeamWork.Service.TeamService;
import com.example.TeamWork.entity.Employee;
import com.example.TeamWork.exception.BadResourceException;
import com.example.TeamWork.exception.ResourceAlreadyExistsException;
import com.example.TeamWork.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/api")
public class EmployeeController {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final int ROW_PER_PAGE = 20;

  @Autowired
  private EmployeeService employeeService;
  @Autowired
  private TeamService teamService;

  @GetMapping(value = "/employees", produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<List<Employee>> findAll(
    @RequestParam(value = "page", defaultValue = "1") int pageNumber) {
      return ResponseEntity.ok(employeeService.findAll(pageNumber, ROW_PER_PAGE));

  }
  @GetMapping(value = "/employees/team/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<List<Employee>> findbyTeam(
    @PathVariable int id,
    @RequestParam(value = "page", defaultValue = "1") int pageNumber) {
      try {
        return ResponseEntity.ok(employeeService.findAllByTeam(teamService.findById(id), pageNumber, ROW_PER_PAGE));
      } catch (ResourceNotFoundException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
      }

  }
  @GetMapping(value = "/employees/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<Employee> findEmployeeById(@PathVariable int id) {
    try {
      Employee employee = employeeService.findById(id);
      return ResponseEntity.ok(employee);
    } catch (ResourceNotFoundException ex) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
  }

  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  @PostMapping(value = "/employees")
  public ResponseEntity<Employee> addEmployee(@Valid @RequestBody Employee employee)
    throws URISyntaxException {
    try {
      Employee newEmployee = employeeService.save(employee);
      return ResponseEntity.created(new URI("/api/employees/" + newEmployee.getEmployeeId())).body(employee);
    } catch (ResourceAlreadyExistsException ex) {
      logger.error(ex.getMessage());
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    } catch (BadResourceException ex) {
      logger.error(ex.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  @PutMapping(value = "/employees/{id}")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<Employee> updateEmployee(@Valid @RequestBody Employee employee,
                                            @PathVariable int id) {
    try {
      employee.setEmployeeId(id);
      employeeService.update(employee);
      return ResponseEntity.ok().build();
    } catch (ResourceNotFoundException ex) {
      logger.error(ex.getMessage());
      return ResponseEntity.notFound().build();
    } catch (BadResourceException ex) {
      logger.error(ex.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }


  @DeleteMapping(path = "/employees/{id}")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<Void> deleteEmployeeById(@PathVariable int id) {
    try {
      employeeService.deleteById(id);
      return ResponseEntity.ok().build();
    } catch (ResourceNotFoundException ex) {
      logger.error(ex.getMessage());
      return ResponseEntity.notFound().build();
    }
  }
}
