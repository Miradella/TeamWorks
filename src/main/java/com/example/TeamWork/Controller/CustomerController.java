package com.example.TeamWork.Controller;

import com.example.TeamWork.Service.CustomerService;
import com.example.TeamWork.entity.Customer;
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
public class CustomerController {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final int ROW_PER_PAGE = 20;

  @Autowired
  private CustomerService customerService;

  @GetMapping(value = "/customers", produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<List<Customer>> findAll(
    @RequestParam(value="page", defaultValue="1") int pageNumber) {
      return ResponseEntity.ok(customerService.findAll(pageNumber, ROW_PER_PAGE));
  }

  @GetMapping(value = "/customers/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<Customer> findCustomerById(@PathVariable int customerId) {
    try {
      Customer customer = customerService.findById(customerId);
      return ResponseEntity.ok(customer);  // return 200, with json body
    } catch (ResourceNotFoundException ex) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // return 404, with null body
    }
  }

  @PostMapping(value = "/customers")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<Customer> addCustomer(@Valid @RequestBody Customer customer)
    throws URISyntaxException {
    try {
      System.out.println(customer.getId());
      Customer newContact = customerService.save(customer);
      return ResponseEntity.created(new URI("/api/customers/" + newContact.getId()))
        .body(customer);
    } catch (ResourceAlreadyExistsException ex) {
      // log exception first, then return Conflict (409)
      logger.error(ex.getMessage());
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    } catch (BadResourceException ex) {
      // log exception first, then return Bad Request (400)
      logger.error(ex.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  @PutMapping(value = "/customers/{customerId}")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<Customer> updateCustomer(@Valid @RequestBody Customer customer,
                                               @PathVariable int customerId) {
    try {
      customer.setId(customerId);
      customerService.update(customer);
      return ResponseEntity.ok().build();
    } catch (ResourceNotFoundException ex) {
      // log exception first, then return Not Found (404)
      logger.error(ex.getMessage());
      return ResponseEntity.notFound().build();
    } catch (BadResourceException ex) {
      // log exception first, then return Bad Request (400)
      logger.error(ex.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  @DeleteMapping(path="/customers/{customerId}")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<Void> deleteCustomerById(@PathVariable int customerId) {
    try {
      customerService.deleteById(customerId);
      return ResponseEntity.ok().build();
    } catch (ResourceNotFoundException ex) {
      logger.error(ex.getMessage());
      return ResponseEntity.notFound().build();
    }
  }
}

