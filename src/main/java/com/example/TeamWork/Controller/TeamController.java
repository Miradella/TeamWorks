package com.example.TeamWork.Controller;

import com.example.TeamWork.Service.TeamService;
import com.example.TeamWork.entity.Team;
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

public class TeamController {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final int ROW_PER_PAGE = 20;

  @Autowired
  private TeamService teamService;

  @GetMapping(value = "/teams", produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<List<Team>> findAll(
    @RequestParam(value = "page", defaultValue = "1") int pageNumber) {
    return ResponseEntity.ok(teamService.findAll(pageNumber, ROW_PER_PAGE));
  }
  @GetMapping(value = "/teams/{teamId}", produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<Team> findTeamById(@PathVariable int teamId) {
    try {
      Team team = teamService.findById(teamId);
      return ResponseEntity.ok(team);  // return 200, with json body
    } catch (ResourceNotFoundException ex) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // return 404, with null body
    }
  }

  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  @PostMapping(value = "/teams")
  public ResponseEntity<Team> addProject(@Valid @RequestBody Team team)
    throws URISyntaxException {
    try {
      Team newTeam = teamService.save(team);
      return ResponseEntity.created(new URI("/api/teams/" + newTeam.getTeamId())).body(team);
    } catch (ResourceAlreadyExistsException ex) {
      logger.error(ex.getMessage());
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    } catch (BadResourceException ex) {
      logger.error(ex.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  @PutMapping(value = "/teams/{id}")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<Team> updateProject(@Valid @RequestBody Team team,
                                               @PathVariable int id) {
    try {
      team.setTeamId(id);
      teamService.update(team);
      return ResponseEntity.ok().build();
    } catch (ResourceNotFoundException ex) {
      logger.error(ex.getMessage());
      return ResponseEntity.notFound().build();
    } catch (BadResourceException ex) {
      logger.error(ex.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }


  @DeleteMapping(path = "/teams/{id}")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<Void> deleteTeamById(@PathVariable int id) {
    try {
      teamService.deleteById(id);
      return ResponseEntity.ok().build();
    } catch (ResourceNotFoundException ex) {
      logger.error(ex.getMessage());
      return ResponseEntity.notFound().build();
    }
  }
}
