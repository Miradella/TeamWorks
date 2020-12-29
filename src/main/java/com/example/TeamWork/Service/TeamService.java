package com.example.TeamWork.Service;

import com.example.TeamWork.Repository.TeamRepository;
import com.example.TeamWork.entity.Team;
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
public class TeamService {
  @Autowired
  private TeamRepository teamRepository;

  private boolean existsById(Integer id) {
    return teamRepository.existsById(id);
  }

  public Team findById(Integer id) throws ResourceNotFoundException {
    Team team = teamRepository.findById(id).orElse(null);
    if (team==null) {
      throw new ResourceNotFoundException("Cannot find Contact with id: " + id);
    }
    else return team;
  }
  public List<Team> findAll(int pageNumber, int rowPerPage) {
    List<Team> teams = new ArrayList<>();
    teamRepository.findAll(PageRequest.of(pageNumber - 1, rowPerPage)).forEach(teams::add);
    return teams;
  }

  public Team save(Team team) throws BadResourceException, ResourceAlreadyExistsException {
    if (!StringUtils.isEmpty(team.getSpecialization())) {
      if (team != null && existsById(team.getTeamId())) {
        throw new ResourceAlreadyExistsException("Contact with id: " + team.getTeamId() +
          " already exists");
      }
      return teamRepository.save(team);
    }
    else {
      BadResourceException exc = new BadResourceException("Failed to save contact");
      exc.addErrorMessage("Contact is null or empty");
      throw exc;
    }
  }

  public void update(Team team)
    throws BadResourceException, ResourceNotFoundException {
      if (!existsById(team.getTeamId())) {
        throw new ResourceNotFoundException("Cannot find Team with id: " + team.getTeamId());
      }
      teamRepository.save(team);
  }

  public void deleteById(Integer id) throws ResourceNotFoundException {
    if (!existsById(id)) {
      throw new ResourceNotFoundException("Cannot find contact with id: " + id);
    }
    else {
      teamRepository.deleteById(id);
    }
  }

  public Long count() {
    return teamRepository.count();
  }
}
