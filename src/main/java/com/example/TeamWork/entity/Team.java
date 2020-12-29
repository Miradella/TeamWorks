package com.example.TeamWork.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "teams")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "team_id_seq")
    @SequenceGenerator(name = "team_id_seq")
    private int id;
    @Column
    private int maxcountofpeople;
    @Column
    private String specialization;
    /*
    @OneToOne( cascade = CascadeType.REFRESH)
    @JoinColumn(name = "leaderid")
    private  Employee employee;
*/
    @OneToMany(mappedBy = "team", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Project> projects;
  @OneToMany(mappedBy = "team", cascade = CascadeType.REFRESH)
  @JsonIgnore
  private List<Employee> employees;
    public String getSpecialization() {
        return specialization;
    }

    public void setTeamId(int teamId) {
        this.id = teamId;
    }

    public int getTeamId() {
        return id;
    }

    public int getMaxCountOfPeople() {
        return maxcountofpeople;
    }

    public void setMaxCountOfPeople(int maxCountOfPeople) {
        this.maxcountofpeople = maxCountOfPeople;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

  public List<Project> getProjects() {
    return projects;
  }

  public void setProjects(List<Project> projects) {
    this.projects = projects;
  }

  public List<Employee> getEmployees() {
    return employees;
  }

  public void setEmployees(List<Employee> employees) {
    this.employees = employees;
  }
/*
  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }
 */
}


