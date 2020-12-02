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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
@RestController
@RequestMapping(value = "/api")
public class CustomerController {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final int ROW_PER_PAGE = 5;

  @Autowired
  private CustomerService contactService;

  @GetMapping(value = "/customers", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Customer>> findAll(
    @RequestParam(value="page", defaultValue="1") int pageNumber,
    @RequestParam(required=false) String name) {
    if (StringUtils.isEmpty(name)) {
      return ResponseEntity.ok(contactService.findAll(pageNumber, ROW_PER_PAGE));
    }
    else {
      return ResponseEntity.ok(contactService.findAllByName(name, pageNumber, ROW_PER_PAGE));
    }
  }

  @GetMapping(value = "/customers/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Customer> findContactById(@PathVariable int customerId) {
    try {
      Customer book = contactService.findById(customerId);
      return ResponseEntity.ok(book);  // return 200, with json body
    } catch (ResourceNotFoundException ex) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // return 404, with null body
    }
  }

  @PostMapping(value = "/customers")
  public ResponseEntity<Customer> addContact(@Valid @RequestBody Customer customer)
    throws URISyntaxException {
    try {
      Customer newContact = contactService.save(customer);
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
  public ResponseEntity<Customer> updateContact(@Valid @RequestBody Customer customer,
                                               @PathVariable int customerId) {
    try {
      customer.setId(customerId);
      contactService.update(customer);
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

  @PatchMapping("/customers/{customerId}")
  public ResponseEntity<Void> updateAddress(@PathVariable int contactId,
                                            @RequestBody String phone_number) {
    try {
      contactService.updatePhone(contactId, phone_number);
      return ResponseEntity.ok().build();
    } catch (ResourceNotFoundException ex) {
      // log exception first, then return Not Found (404)
      logger.error(ex.getMessage());
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping(path="/customers/{customerId}")
  public ResponseEntity<Void> deleteContactById(@PathVariable int customerId) {
    try {
      contactService.deleteById(customerId);
      return ResponseEntity.ok().build();
    } catch (ResourceNotFoundException ex) {
      logger.error(ex.getMessage());
      return ResponseEntity.notFound().build();
    }
  }
}

