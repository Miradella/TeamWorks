package com.example.TeamWork.Controller;

import com.example.TeamWork.Service.ProjectService;
import com.example.TeamWork.entity.Project;
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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
@RestController @CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/api")
public class ProjectController {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final int ROW_PER_PAGE = 20;

  @Autowired
  private ProjectService projectService;

  @GetMapping(value = "/projects", produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<List<Project>> findAll(
    @RequestParam(value="page", defaultValue="1") int pageNumber,
    @RequestParam(required=false) String name) {
    if (StringUtils.isEmpty(name)) {
      return ResponseEntity.ok(projectService.findAll(pageNumber, ROW_PER_PAGE));
    }
    else {
      return ResponseEntity.ok(projectService.findAllByName(name, pageNumber, ROW_PER_PAGE));
    }
  }
  /*
  @GetMapping(value = "/projectsbycustomer/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<List<Project>> findbyCustomer(
    @Valid @RequestBody Customer customer) {
    return ResponseEntity.ok(projectService.findbyCustomer(customer));
  } */
  @GetMapping(value = "/projects/{projectId}", produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<Project> findProjectById(@PathVariable int customerId) {
    try {
      Project project = projectService.findById(customerId);
      return ResponseEntity.ok(project);  // return 200, with json body
    } catch (ResourceNotFoundException ex) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // return 404, with null body
    }
  }

  @PostMapping(value = "/projects")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<Project> addProject(@Valid @RequestBody Project project)
    throws URISyntaxException {
    try {
      Project newProject = projectService.save(project);
      return ResponseEntity.created(new URI("/api/projects/" + newProject.getProjectId()))
        .body(project);
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

  @PutMapping(value = "/projects/{projectId}")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<Project> updateProject(@Valid @RequestBody Project project,
                                                 @PathVariable int projectId) {
    try {
      project.setProjectId(projectId);
      projectService.update(project);
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


  @DeleteMapping(path="/projects/{projectId}")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<Void> deleteProjectById(@PathVariable int projectId) {
    try {
      projectService.deleteById(projectId);
      return ResponseEntity.ok().build();
    } catch (ResourceNotFoundException ex) {
      logger.error(ex.getMessage());
      return ResponseEntity.notFound().build();
    }
  }
}


