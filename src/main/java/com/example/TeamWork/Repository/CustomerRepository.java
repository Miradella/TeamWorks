package com.example.TeamWork.Repository;

import com.example.TeamWork.entity.Customer;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CustomerRepository extends PagingAndSortingRepository<Customer, Integer>,
  JpaSpecificationExecutor<Customer> {
}

