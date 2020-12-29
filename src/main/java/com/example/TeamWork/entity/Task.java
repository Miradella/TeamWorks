package com.example.TeamWork.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "tasks")
public class Task {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_id_seq")
  @SequenceGenerator(name = "task_id_seq")
  private int id;
  @Column
  private String taskname;
  @Column
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
  private Date deadline;
  @Column
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
  private int priority;
  @Column
  private String description;

  @ManyToOne( cascade = CascadeType.REFRESH)
  @JoinColumn(name = "employeeid")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Employee employee;

  @ManyToOne( cascade = CascadeType.REFRESH)
  @JoinColumn(name = "projectid")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Project project;

  public void setPriority(int priority) {
    this.priority = priority;
  }

  public int getPriority() {
    return priority;
  }

  public Date getDeadline() {
    return deadline;
  }

  public int getTaskId() {
    return id;
  }

  public String getTaskName() {
    return taskname;
  }

  public void setTaskId(int taskId) {
    this.id = taskId;
  }

  public void setTaskName(String taskName) {
    this.taskname = taskName;
  }

  public void setDeadline(Date deadline) {
    this.deadline = deadline;
  }


  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }
}

