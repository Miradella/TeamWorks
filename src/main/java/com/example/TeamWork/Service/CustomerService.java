package com.example.TeamWork.Service;

import com.example.TeamWork.Repository.CustomerRepository;
import com.example.TeamWork.entity.Customer;
import com.example.TeamWork.exception.BadResourceException;
import com.example.TeamWork.exception.ResourceAlreadyExistsException;
import com.example.TeamWork.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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

  public Customer findById(int id) throws ResourceNotFoundException {
    System.out.println(id);
    Customer customer = сustomerRepository.findById(id).orElse(null);
    System.out.println(customer.getName());
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

  public Customer save(Customer customer) throws BadResourceException, ResourceAlreadyExistsException {
    if (!StringUtils.isEmpty(customer.getName())) {
      if (customer != null && existsById(customer.getId())) {
        throw new ResourceAlreadyExistsException("Contact with id: " + customer.getId() +
          " already exists");
      }
      System.out.println(customer.getId());
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
