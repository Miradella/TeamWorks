package com.example.TeamWork.Repository;

import com.example.TeamWork.entity.Project;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProjectRepository extends PagingAndSortingRepository<Project, Integer>,
  JpaSpecificationExecutor<Project> {
  //List<Project> findByCustomer(Customer customer);
  }
