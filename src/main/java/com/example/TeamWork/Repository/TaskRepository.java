package com.example.TeamWork.Repository;

import com.example.TeamWork.entity.Task;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TaskRepository  extends PagingAndSortingRepository<Task, Integer>,
  JpaSpecificationExecutor<Task> {
}
