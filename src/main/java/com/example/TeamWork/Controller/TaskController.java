package com.example.TeamWork.Controller;

import com.example.TeamWork.Service.TaskService;
import com.example.TeamWork.entity.Task;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/api")
public class TaskController {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final int ROW_PER_PAGE = 20;

  @Autowired
  private TaskService taskService;

  @GetMapping(value = "/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<List<Task>> findAll(
    @RequestParam(value = "page", defaultValue = "1") int pageNumber) {
    return ResponseEntity.ok(taskService.findAll(pageNumber, ROW_PER_PAGE));
  }
  @GetMapping(value = "/tasks/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<Task> findTeamById(@PathVariable int id) {
    try {
      Task task = taskService.findById(id);
      return ResponseEntity.ok(task);
    } catch (ResourceNotFoundException ex) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
  }

  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  @PostMapping(value = "/tasks")
  public ResponseEntity<Task> addProject(@Valid @RequestBody Task task)
    throws URISyntaxException {
    try {
      Task newtask = taskService.save(task);
      return ResponseEntity.created(new URI("/api/tasks/" + newtask.getTaskId())).body(task);
    } catch (ResourceAlreadyExistsException ex) {
      logger.error(ex.getMessage());
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    } catch (BadResourceException ex) {
      logger.error(ex.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  @PutMapping(value = "/tasks/{id}")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<Task> updateProject(@Valid @RequestBody Task task,
                                                @PathVariable int id) {
    try {
      task.setTaskId(id);
      taskService.update(task);
      return ResponseEntity.ok().build();
    } catch (ResourceNotFoundException ex) {
      logger.error(ex.getMessage());
      return ResponseEntity.notFound().build();
    } catch (BadResourceException ex) {
      logger.error(ex.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }


  @DeleteMapping(path = "/tasks/{id}")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<Void> deleteTeamById(@PathVariable int id) {
    try {
      taskService.deleteById(id);
      return ResponseEntity.ok().build();
    } catch (ResourceNotFoundException ex) {
      logger.error(ex.getMessage());
      return ResponseEntity.notFound().build();
    }
  }
}
