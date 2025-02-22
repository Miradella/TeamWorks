package com.example.TeamWork.Repository;

import com.example.TeamWork.entity.Project;
import com.example.TeamWork.entity.Team;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProjectRepository extends CrudRepository<Project, Integer>,
  JpaSpecificationExecutor<Project> {
  List<Project> findByTeam(Team team);
  }
