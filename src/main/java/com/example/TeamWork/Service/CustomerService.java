package com.example.TeamWork.Service;

import com.example.TeamWork.Repository.CustomerRepository;
import com.example.TeamWork.Specification.CustomerSpecification;
import com.example.TeamWork.entity.Customer;
import com.example.TeamWork.exception.*;
import com.example.TeamWork.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
  @Autowired
  private CustomerRepository сustomerRepository;

  private boolean existsById(Integer id) {
    return сustomerRepository.existsById(id);
  }

  public Customer findById(Integer id) throws ResourceNotFoundException {
    Customer customer = сustomerRepository.findById(id).orElse(null);
    if (customer==null) {
      throw new ResourceNotFoundException("Cannot find Contact with id: " + id);
    }
    else return customer;
  }

  public List<Customer> findAll(int pageNumber, int rowPerPage) {
    List<Customer> customers = new ArrayList<>();
    сustomerRepository.findAll(PageRequest.of(pageNumber - 1, rowPerPage)).forEach(customers::add);
    return customers;
  }

  public List<Customer> findAllByName(String name, int pageNumber, int rowPerPage) {
    Customer filter = new Customer();
    filter.setName(name);
    Specification<Customer> spec = new CustomerSpecification(filter);

    List<Customer> customers = new ArrayList<>();
    сustomerRepository.findAll(spec, PageRequest.of(pageNumber - 1, rowPerPage)).forEach(customers::add);
    return customers;
  }

  public Customer save(Customer customer) throws BadResourceException, ResourceAlreadyExistsException {
    if (!StringUtils.isEmpty(customer.getName())) {
      if (customer != null && existsById(customer.getId())) {
        throw new ResourceAlreadyExistsException("Contact with id: " + customer.getId() +
          " already exists");
      }
      return сustomerRepository.save(customer);
    }
    else {
      BadResourceException exc = new BadResourceException("Failed to save contact");
      exc.addErrorMessage("Contact is null or empty");
      throw exc;
    }
  }

  public void update(Customer customer)
    throws BadResourceException, ResourceNotFoundException {
    if (!StringUtils.isEmpty(customer.getName())) {
      if (!existsById(customer.getId())) {
        throw new ResourceNotFoundException("Cannot find Contact with id: " + customer.getId());
      }
      сustomerRepository.save(customer);
    }
    else {
      BadResourceException exc = new BadResourceException("Failed to save contact");
      exc.addErrorMessage("Contact is null or empty");
      throw exc;
    }
  }

  public void updatePhone(Integer id, String phonenumber)
    throws ResourceNotFoundException {
    Customer customer = findById(id);
    customer.setPhone_number(phonenumber);
    сustomerRepository.save(customer);
  }

  public void deleteById(Integer id) throws ResourceNotFoundException {
    if (!existsById(id)) {
      throw new ResourceNotFoundException("Cannot find contact with id: " + id);
    }
    else {
      сustomerRepository.deleteById(id);
    }
  }

  public Long count() {
    return сustomerRepository.count();
  }
}
