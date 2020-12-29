package com.example.TeamWork.Repository;

import com.example.TeamWork.entity.Team;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TeamRepository extends PagingAndSortingRepository<Team, Integer>,
  JpaSpecificationExecutor<Team> {
}
