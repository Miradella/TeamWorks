package com.example.TeamWork.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "employees")
public class Employee {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_id_seq")
  @SequenceGenerator(name = "employee_id_seq")
  private int id;
  @Column
  private String name;
  @Column
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
  private Date birthday;

  @ManyToOne( cascade = CascadeType.REFRESH)
  @JoinColumn(name = "teamid")
  private Team team;
  @OneToMany(mappedBy = "employee", cascade = CascadeType.REFRESH)
  @JsonIgnore
  private List<Task> tasks;
  public int getEmployeeId() {
    return id;
  }


  public void setEmployeeId(int peopleId) {
    this.id = peopleId;
  }


  public Date getBirthday() {
    return birthday;
  }


  public void setBirthday(Date birthday) {
    this.birthday = birthday;
  }

  public Team getTeam() {
    return team;
  }

  public void setTeam(Team team) {
    this.team = team;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Task> getTasks() {
    return tasks;
  }

  public void setTasks(List<Task> tasks) {
    this.tasks = tasks;
  }
}

