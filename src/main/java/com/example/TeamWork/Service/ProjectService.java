package com.example.TeamWork.Service;

import com.example.TeamWork.Repository.ProjectRepository;
import com.example.TeamWork.entity.Project;
import com.example.TeamWork.exception.BadResourceException;
import com.example.TeamWork.exception.ResourceAlreadyExistsException;
import com.example.TeamWork.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {
  @Autowired
  private ProjectRepository projectRepository;

  private boolean existsById(Integer id) {
    return projectRepository.existsById(id);
  }

  public  Project findById(Integer id) throws ResourceNotFoundException {
    Project project = projectRepository.findById(id).orElse(null);
    if (project==null) {
      throw new ResourceNotFoundException("Cannot find Contact with id: " + id);
    }
    else return project;
  }
  public  List<Project> findAllByTeam(Integer id, int pageNumber, int rowPerPage) throws ResourceNotFoundException {
    TeamService teamService=new TeamService();
    return projectRepository.findByTeam(teamService.findById(id));
  }

  public List<Project> findAll(int pageNumber, int rowPerPage) {
    List<Project> projects = new ArrayList<>();
    projectRepository.findAll().forEach(projects::add);
    return projects;
  }
  public Project save(Project project) throws BadResourceException, ResourceAlreadyExistsException {
    if (!StringUtils.isEmpty(project.getProjectName())) {
      if (project != null && existsById(project.getProjectId())) {
        throw new ResourceAlreadyExistsException("Contact with id: " + project.getProjectId() +
          " already exists");
      }
      System.out.println(project.getProjectId());
      return projectRepository.save(project);
    }
    else {
      BadResourceException exc = new BadResourceException("Failed to save contact");
      exc.addErrorMessage("Contact is null or empty");
      throw exc;
    }
  }

  public void update(Project project)
    throws BadResourceException, ResourceNotFoundException {
    if (!StringUtils.isEmpty(project.getProjectName())) {
      if (!existsById(project.getProjectId())) {
        throw new ResourceNotFoundException("Cannot find Contact with id: " + project.getProjectId());
      }
      projectRepository.save(project);
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
      projectRepository.deleteById(id);
    }
  }

  public Long count() {
    return projectRepository.count();
  }
}
