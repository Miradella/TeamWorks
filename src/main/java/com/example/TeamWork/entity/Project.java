package com.example.TeamWork.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "projects")
public class Project  implements Serializable  {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_id_seq")
    @SequenceGenerator(name = "project_id_seq")
    private int projectid;
    @Column
    private String projectname;
    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private  Date deadline;
    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private Date projectstart;
    @Column
    private  int priority;


  @ManyToOne( cascade = CascadeType.REFRESH)
  @JoinColumn(name = "customerid")
  private Customer customer;

  @ManyToOne( cascade = CascadeType.REFRESH)
  @JoinColumn(name = "teamid")
  private Team team;

  @OneToMany(mappedBy = "project", cascade = CascadeType.REFRESH)
  @JsonIgnore
  private List<Task> tasks;

    public int getPriority() {
        return priority;
    }


    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Date getProjectStart() {
        return projectstart;
    }

    public int getProjectId() {
        return projectid;
    }

    public String getProjectName() {
        return projectname;
    }

    public void setProjectId(int projectId) {
        this.projectid = projectId;
    }

    public void setProjectName(String projectName) {
        this.projectname = projectName;
    }

    public void setProjectStart(Date projectStart) {
        this.projectstart = projectStart;
    }

    public Date getDeadline() {
        return deadline;
    }


    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }


    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

  public Team getTeam() {
    return team;
  }

  public void setTeam(Team team) {
    this.team = team;
  }

  public List<Task> getTasks() {
    return tasks;
  }

  public void setTasks(List<Task> tasks) {
    this.tasks = tasks;
  }
}



